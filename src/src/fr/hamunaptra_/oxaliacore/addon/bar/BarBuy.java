package fr.hamunaptra_.oxaliacore.addon.bar;

import fr.hamunaptra_.oxaliacore.Main;
import fr.hamunaptra_.oxaliacore.utils.api.chat.*;
import fr.hamunaptra_.oxaliacore.utils.api.config.Bar;
import fr.hamunaptra_.oxaliacore.utils.api.items.*;

import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.potion.PotionType;

public class BarBuy implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Color Color = new Color(p);
        String inv = e.getView().getTitle();
        String bar = "Bar.Item.";
        String NoMoney = Color.set("Bar.Message.NoMoney");

        if (e.getCurrentItem() == null) return;
        if (e.getCurrentItem().getItemMeta() == null) return;

        String getName = e.getCurrentItem().getItemMeta().getDisplayName();

        if (inv.equals(Color.set(Bar.getString("Bar.Inv.Name")))) {
            e.setCancelled(true);

            if (getName.equals(Color.set(Bar.getString(bar + "Beer.Name")))) {
                EconomyResponse r = Main.economy.withdrawPlayer((OfflinePlayer) p, Bar.getInt(bar + "Beer.Price"));

                if (r.transactionSuccess()) {
                    p.sendMessage(Color.set(Bar.getString(bar + "Beer.Message.Buy")));
                    String type = Bar.getString(bar + "Beer.Type");
                    PotionType potionType = PotionType.valueOf(type);

                    p.getInventory().addItem(new Items(Material.POTION)
                            .setName(Color.set(Bar.getString(bar + "Beer.Name")))
                            .setLore(Color.set(Bar.getStringList(bar + "Beer.Lore")))
                            .setPotion(potionType, false)
                            .setFlag(ItemFlag.HIDE_POTION_EFFECTS)
                            .im());
                    p.closeInventory();

                } else {
                    p.sendMessage(NoMoney);
                }
            }

            if (getName.equals(Color.set(Bar.getString(bar + "Dodo.Name")))) {
                EconomyResponse r = Main.economy.withdrawPlayer((OfflinePlayer)p, Bar.getInt(bar + "Dodo.Price"));

                if (r.transactionSuccess()) {
                    p.sendMessage(Color.set(Bar.getString(bar + "Dodo.Message.Buy")));
                    String type = Bar.getString(bar + "Dodo.Type");
                    PotionType potionType = PotionType.valueOf(type);

                    p.getInventory().addItem(new Items(Material.POTION)
                            .setName(Color.set(Bar.getString(bar + "Dodo.Name")))
                            .setLore(Color.set(Bar.getStringList(bar + "Dodo.Lore")))
                            .setPotion(potionType, false)
                            .setFlag(ItemFlag.HIDE_POTION_EFFECTS)
                            .im());
                    p.closeInventory();

                } else {
                    p.sendMessage(NoMoney);
                }
            }

            if (getName.equals(Color.set(Bar.getString(bar + "Wine.Name")))) {
                EconomyResponse r = Main.economy.withdrawPlayer((OfflinePlayer)p, Bar.getInt(bar + "Wine.Price"));

                if (r.transactionSuccess()) {
                    p.sendMessage(Color.set(Bar.getString(bar + "Wine.Message.Buy")));
                    String type = Bar.getString(bar + "Wine.Type");
                    PotionType potionType = PotionType.valueOf(type);

                    p.getInventory().addItem(new Items(Material.POTION)
                            .setName(Color.set(Bar.getString(bar + "Wine.Name")))
                            .setLore(Color.set(Bar.getStringList(bar + "Wine.Lore")))
                            .setPotion(potionType, false)
                            .setFlag(ItemFlag.HIDE_POTION_EFFECTS)
                            .im());
                    p.closeInventory();

                } else {
                    p.sendMessage(NoMoney);
                }
            }

            if (getName.equals(Color.set(Bar.getString(bar + "Tequila.Name")))) {
                EconomyResponse r = Main.economy.withdrawPlayer((OfflinePlayer)p, Bar.getInt(bar + "Tequila.Price"));

                if (r.transactionSuccess()) {
                    p.sendMessage(Color.set(Bar.getString(bar + "Tequila.Message.Buy")));
                    String type = Bar.getString(bar + "Tequila.Type");
                    PotionType potionType = PotionType.valueOf(type);

                    p.getInventory().addItem(new Items(Material.POTION)
                            .setName(Color.set(Bar.getString(bar + "Tequila.Name")))
                            .setLore(Color.set(Bar.getStringList(bar + "Tequila.Lore")))
                            .setPotion(potionType, false)
                            .setFlag(ItemFlag.HIDE_POTION_EFFECTS)
                            .im());
                    p.closeInventory();

                } else {
                    p.sendMessage(NoMoney);
                }
            }

            if (getName.equals(Color.set(Bar.getString(bar + "Vodka.Name")))) {
                EconomyResponse r = Main.economy.withdrawPlayer((OfflinePlayer)p, Bar.getInt(bar + "Vodka.Price"));

                if (r.transactionSuccess()) {
                    p.sendMessage(Color.set(Bar.getString(bar + "Vodka.Message.Buy")));
                    String type = Bar.getString(bar + "Vodka.Type");
                    PotionType potionType = PotionType.valueOf(type);

                    p.getInventory().addItem(new Items(Material.POTION)
                            .setName(Color.set(Bar.getString(bar + "Vodka.Name")))
                            .setLore(Color.set(Bar.getStringList(bar + "Vodka.Lore")))
                            .setPotion(potionType, false)
                            .setFlag(ItemFlag.HIDE_POTION_EFFECTS)
                            .im());
                    p.closeInventory();

                } else {
                    p.sendMessage(NoMoney);
                }
            }

            if (getName.equals(Color.set(Bar.getString(bar + "Rhum.Name")))) {
                EconomyResponse r = Main.economy.withdrawPlayer((OfflinePlayer)p, Bar.getInt(bar + "Rhum.Price"));

                if (r.transactionSuccess()) {
                    p.sendMessage(Color.set(Bar.getString(bar + "Rhum.Message.Buy")));
                    String type = Bar.getString(bar + "Rhum.Type");
                    PotionType potionType = PotionType.valueOf(type);

                    p.getInventory().addItem(new Items(Material.POTION)
                            .setName(Color.set(Bar.getString(bar + "Rhum.Name")))
                            .setLore(Color.set(Bar.getStringList(bar + "Rhum.Lore")))
                            .setPotion(potionType, false)
                            .setFlag(ItemFlag.HIDE_POTION_EFFECTS)
                            .im());
                    p.closeInventory();

                } else {
                    p.sendMessage(NoMoney);
                }
            }

            if (getName.equals(Color.set(Bar.getString(bar + "Whisky.Name")))) {
                EconomyResponse r = Main.economy.withdrawPlayer((OfflinePlayer)p, Bar.getInt(bar + "Whisky.Price"));

                if (r.transactionSuccess()) {
                    p.sendMessage(Color.set(Bar.getString(bar + "Whisky.Message.Buy")));
                    String type = Bar.getString(bar + "Whisky.Type");
                    PotionType potionType = PotionType.valueOf(type);

                    p.getInventory().addItem(new Items(Material.POTION)
                            .setName(Color.set(Bar.getString(bar + "Whisky.Name")))
                            .setLore(Color.set(Bar.getStringList(bar + "Whisky.Lore")))
                            .setPotion(potionType, false)
                            .setFlag(ItemFlag.HIDE_POTION_EFFECTS)
                            .im());
                    p.closeInventory();

                } else {
                    p.sendMessage(NoMoney);
                }
            }
        }
    }
}