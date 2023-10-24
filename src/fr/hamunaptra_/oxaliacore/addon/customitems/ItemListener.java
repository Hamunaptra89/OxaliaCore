package fr.hamunaptra_.oxaliacore.addon.customitems;

import fr.hamunaptra_.oxaliacore.utils.api.chat.*;
import fr.hamunaptra_.oxaliacore.utils.api.config.*;
import fr.hamunaptra_.oxaliacore.utils.api.items.*;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

public class ItemListener implements Listener{
    String key = "CItems.";

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();
        Color Color = new Color(p);

        ItemStack i = e.getItem();
        String getName = i.getType().getData().getName();

        if (CItems.getBoolean(key + "Consume")) {
            if (getName.equals(Color.set(CItems.getString(key + "Potato.Name")))) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, CItems.getInt(key + "Potato.Time") * 20, 1));
            } else if (getName.equals(Color.set(CItems.getString(key + "Carrot.Name")))) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, CItems.getInt(key + "Carrot.Time") * 20, 2));
            } else if (getName.equals(Color.set(CItems.getString(key + "Bread.Name")))) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, CItems.getInt(key + "Bread.Time") * 20, 2));
            } else if (getName.equals(Color.set(CItems.getString(key + "Cookie.Name")))) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, CItems.getInt(key + "Cookie.Time") * 20, 0));
            } else if (getName.equals(Color.set(CItems.getString(key + "Cake.Name")))) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, CItems.getInt(key + "Cake.Time") * 20, 2));
            }
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Color Color = new Color(p);

        if (!CItems.getBoolean(key + "Consume") || e.getAction() != Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack i = e.getItem();
            String getName = i.getItemMeta().getDisplayName();

            if (getName.equals(Color.set(CItems.getString(key + "Potato.Name")))) {
                e.setCancelled(true);
                p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, CItems.getInt(key + "Potato.Time") * 20, 1));
                consumeItem(p, 1, i);
            } else if (getName.equals(Color.set(CItems.getString(key + "Carrot.Name")))) {
                e.setCancelled(true);
                p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, CItems.getInt(key + "Carrot.Time") * 20, 2));
                consumeItem(p, 1, i);
            } else if (getName.equals(Color.set(CItems.getString(key + "Bread.Name")))) {
                e.setCancelled(true);
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, CItems.getInt(key + "Bread.Time") * 20, 2));
                consumeItem(p, 1, i);
            } else if (getName.equals(Color.set(CItems.getString(key + "Cookie.Name")))) {
                e.setCancelled(true);
                p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, CItems.getInt(key + "Cookie.Time") * 20, 0));
                consumeItem(p, 1, i);
            } else if (getName.equals(Color.set(CItems.getString(key + "Cake.Name")))) {
                e.setCancelled(true);
                p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, CItems.getInt(key + "Cake.Time") * 20, 2));
                consumeItem(p, 1, i);
            }
        }
    }

    public void consumeItem(Player p, int amount, ItemStack item) {
        Map<Integer, ? extends ItemStack> ammo = p.getInventory().all(item);
        int found = 0;
        for (ItemStack stack : ammo.values())
            found += stack.getAmount();
        if (amount > found)
            return;
        for (Integer index : ammo.keySet()) {
            ItemStack stack = ammo.get(index);
            int removed = Math.min(amount, stack.getAmount());
            amount -= removed;
            if (stack.getAmount() == removed) {
                p.getInventory().setItem(index, new Items(Material.AIR).im());
            } else {
                stack.setAmount(stack.getAmount() - removed);
            }
            if (amount <= 0)
                break;
        }
    }
}
