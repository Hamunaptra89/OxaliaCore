package fr.hamunaptra_.oxaliacore.addon.shulker;

import fr.hamunaptra_.oxaliacore.Main;
import fr.hamunaptra_.oxaliacore.utils.*;
import fr.hamunaptra_.oxaliacore.utils.api.chat.Color;

import org.bukkit.*;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ShulkerListener implements Listener {

    ConfigManager Config = ConfigManager.getInstance();
    Map<UUID, ItemStack> ShulkerBoxes = new HashMap<>();

    private boolean IsShulkerBox(Material m) {
        return switch (m) {
            case SHULKER_BOX, RED_SHULKER_BOX, MAGENTA_SHULKER_BOX, PINK_SHULKER_BOX, PURPLE_SHULKER_BOX, YELLOW_SHULKER_BOX, ORANGE_SHULKER_BOX, LIME_SHULKER_BOX, GREEN_SHULKER_BOX, CYAN_SHULKER_BOX, BLUE_SHULKER_BOX, LIGHT_BLUE_SHULKER_BOX, LIGHT_GRAY_SHULKER_BOX, GRAY_SHULKER_BOX, BROWN_SHULKER_BOX, BLACK_SHULKER_BOX, WHITE_SHULKER_BOX ->
                    true;
            default -> false;
        };
    }

    private void OpenShulkerbox(HumanEntity p, ItemStack i) {
        if (ShulkerBoxes.containsKey(p.getUniqueId()) && ShulkerBoxes.get(p.getUniqueId()).equals(i)) {
            return;
        }

        ItemMeta m = i.getItemMeta();
        PersistentDataContainer d = m.getPersistentDataContainer();
        NamespacedKey nbtKey = new NamespacedKey(Main.getInstance(), "__shulkerbox_plugin");
        if (!d.has(nbtKey, PersistentDataType.STRING)) {
            d.set(nbtKey, PersistentDataType.STRING, String.valueOf(System.currentTimeMillis()));
            i.setItemMeta(m);
        }

        Inventory ShulkerInv = ((ShulkerBox) ((BlockStateMeta) m).getBlockState()).getSnapshotInventory();
        Inventory inv;
        if (!m.hasDisplayName()) {
            inv = Bukkit.createInventory(null, InventoryType.SHULKER_BOX);
        } else {
            inv = Bukkit.createInventory(null, InventoryType.SHULKER_BOX, m.getDisplayName());
        }
        inv.setContents(ShulkerInv.getContents());

        p.openInventory(inv);
        Bukkit.getServer().getPlayer(p.getUniqueId()).playSound(p, Sound.BLOCK_SHULKER_BOX_OPEN, SoundCategory.BLOCKS, 1.0f, 1.2f);

        ShulkerBoxes.put(p.getUniqueId(), i);

    }

    private void CloseShulkerbox(HumanEntity p) {
        ItemStack i = ShulkerBoxes.get(p.getUniqueId());
        BlockStateMeta m = (BlockStateMeta) i.getItemMeta();
        ShulkerBox s = (ShulkerBox) m.getBlockState();
        s.getInventory().setContents(p.getOpenInventory().getTopInventory().getContents());

        PersistentDataContainer data = m.getPersistentDataContainer();
        NamespacedKey nbtKey = new NamespacedKey(Main.getInstance(), "__shulkerbox_plugin");
        if (data.has(nbtKey, PersistentDataType.STRING)) {
            data.remove(nbtKey);
        }

        m.setBlockState(s);
        i.setItemMeta(m);

        Bukkit.getServer().getPlayer(p.getUniqueId()).playSound(p, Sound.BLOCK_SHULKER_BOX_CLOSE, SoundCategory.BLOCKS, 1.0f, 1.2f);

        ShulkerBoxes.remove(p.getUniqueId());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void InventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Color Color = new Color(p);

        if (e.getAction() == InventoryAction.NOTHING)
            return;
        if (!e.isRightClick() || e.isShiftClick()) {
            if (this.ShulkerBoxes.containsKey(e.getWhoClicked().getUniqueId()) && (e

                    .getAction() == InventoryAction.HOTBAR_SWAP || e
                    .getAction() == InventoryAction.HOTBAR_MOVE_AND_READD || (e
                    .getCurrentItem() != null && IsShulkerBox(e.getCurrentItem().getType()))))
                e.setCancelled(true);
            return;
        }
        InventoryType clickedInventory = e.getClickedInventory().getType();
        if (clickedInventory != InventoryType.PLAYER && clickedInventory != InventoryType.ENDER_CHEST && clickedInventory != InventoryType.SHULKER_BOX && clickedInventory != InventoryType.CREATIVE)
            return;
        ItemStack item = e.getCurrentItem();
        if (item == null)
            return;
        if (item.getAmount() != 1)
            return;
        Material itemType = item.getType();

        if (clickedInventory != InventoryType.SHULKER_BOX && IsShulkerBox(itemType)) {
            if (!p.hasPermission(Config.getConfig().getString("Config.Shulker.Permission"))) {
                p.sendMessage(Color.set(Config.getConfig().getString("Config.Shulker.Message.NoPerm")));
                return;
            }
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin) Main.getInstance(), () -> OpenShulkerbox(e.getWhoClicked(), item));
            e.setCancelled(true);
        }
    }


    @EventHandler(priority = EventPriority.NORMAL)
    public void RightClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Color Color = new Color(p);

        ItemStack i = p.getInventory().getItemInMainHand();
        Material m = i.getType();

        if (e.getHand() != EquipmentSlot.HAND || e.getAction() != Action.RIGHT_CLICK_AIR)
            return;

        if (IsShulkerBox(m) && i.getAmount() == 1) {
            if (!p.hasPermission(Config.getConfig().getString("Config.Shulker.Permission"))) {
                p.sendMessage(Color.set(Config.getConfig().getString("Config.Shulker.Message.NoPerm")));
                return;
            }
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin) Main.getInstance(), () -> OpenShulkerbox((HumanEntity) p, i));
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void InventoryClose(InventoryCloseEvent e) {
        if (ShulkerBoxes.containsKey(e.getPlayer().getUniqueId())) {
            CloseShulkerbox(e.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void Death(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if (ShulkerBoxes.containsKey(p.getUniqueId())) {
            CloseShulkerbox(p);
        }
    }
}
