package fr.hamunaptra_.oxaliacore.addon.bar;

import fr.hamunaptra_.oxaliacore.Main;
import fr.hamunaptra_.oxaliacore.utils.chat.*;
import fr.hamunaptra_.oxaliacore.utils.files.config.*;
import fr.hamunaptra_.oxaliacore.utils.items.*;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class BarListener implements Listener {

    String bar = "Bar.Item.";

    @EventHandler
    public void onDrink(PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();
        ItemStack i = e.getItem();
        String iName = i.getItemMeta().getDisplayName();

        Random r = new Random();
        int rdm = r.nextInt(100 + 1);

        if (i.getType() == Material.POTION && i.hasItemMeta() && i.getItemMeta().hasDisplayName()) {

            if (iName.equals(Color.set(Bar.getString(bar + "Beer.Name")))) {
                e.setCancelled(true);
                p.getInventory().setItemInMainHand(new Items(Material.AIR).im());

                if (rdm <= Bar.getInt(bar + "Beer.Chance")) {
                    Main.eco.depositPlayer((OfflinePlayer) p, Bar.getInt(bar + "Beer.Reward"));
                    p.sendMessage(Color.set(Bar.getString(bar + "Beer.Message.Win")));
                }

                else {
                    p.sendMessage(Color.set(Bar.getString(bar + "Beer.Message.Lose")));
                }
            }

            if (iName.equals(Color.set(Bar.getString(bar + "Dodo.Name")))) {
                e.setCancelled(true);
                p.getInventory().setItemInMainHand(new Items(Material.AIR).im());

                if (rdm <= Bar.getInt(bar + "Dodo.Chance")) {
                    Main.eco.depositPlayer((OfflinePlayer) p, Bar.getInt(bar + "Dodo.Reward"));
                    p.sendMessage(Color.set(Bar.getString(bar + "Dodo.Message.Win")));
                }

                else {
                    p.sendMessage(Color.set(Bar.getString(bar + "Dodo.Message.Lose")));
                }
            }

            if (iName.equals(Color.set(Bar.getString(bar + "Wine.Name")))) {
                e.setCancelled(true);
                p.getInventory().setItemInMainHand(new Items(Material.AIR).im());

                if (rdm <= Bar.getInt(bar + "Wine.Chance")) {
                    Main.eco.depositPlayer((OfflinePlayer) p, Bar.getInt(bar + "Wine.Reward"));
                    p.sendMessage(Color.set(Bar.getString(bar + "Wine.Message.Win")));
                }

                else {
                    p.sendMessage(Color.set(Bar.getString(bar + "Wine.Message.Lose")));
                }
            }

            if (iName.equals(Color.set(Bar.getString(bar + "Tequila.Name")))) {
                e.setCancelled(true);
                p.getInventory().setItemInMainHand(new Items(Material.AIR).im());

                if (rdm <= Bar.getInt(bar + "Tequila.Chance")) {
                    Main.eco.depositPlayer((OfflinePlayer) p, Bar.getInt(bar + "Tequila.Reward"));
                    p.sendMessage(Color.set(Bar.getString(bar + "Tequila.Message.Win")));
                }

                else {
                    p.sendMessage(Color.set(Bar.getString(bar + "Tequila.Message.Lose")));
                }
            }

            if (iName.equals(Color.set(Bar.getString(bar + "Vodka.Name")))) {
                e.setCancelled(true);
                p.getInventory().setItemInMainHand(new Items(Material.AIR).im());

                if (rdm <= Bar.getInt(bar + "Vodka.Chance")) {
                    Main.eco.depositPlayer((OfflinePlayer) p, Bar.getInt(bar + "Vodka.Reward"));
                    p.sendMessage(Color.set(Bar.getString(bar + "Vodka.Message.Win")));
                }

                else {
                    p.sendMessage(Color.set(Bar.getString(bar + "Vodka.Message.Lose")));
                }
            }

            if (iName.equals(Color.set(Bar.getString(bar + "Rhum.Name")))) {
                e.setCancelled(true);
                p.getInventory().setItemInMainHand(new Items(Material.AIR).im());

                if (rdm <= Bar.getInt(bar + "Rhum.Chance")) {
                    Main.eco.depositPlayer((OfflinePlayer) p, Bar.getInt(bar + "Rhum.Reward"));
                    p.sendMessage(Color.set(Bar.getString(bar + "Rhum.Message.Win")));
                }

                else {
                    p.sendMessage(Color.set(Bar.getString(bar + "Rhum.Message.Lose")));
                }
            }

            if (iName.equals(Color.set(Bar.getString(bar + "Whisky.Name")))) {
                e.setCancelled(true);
                p.getInventory().setItemInMainHand(new Items(Material.AIR).im());

                if (rdm <= Bar.getInt(bar + "Whisky.Chance")) {
                    Main.eco.depositPlayer((OfflinePlayer) p, Bar.getInt(bar + "Whisky.Reward"));
                    p.sendMessage(Color.set(Bar.getString(bar + "Whisky.Message.Win")));
                }

                else {
                    p.sendMessage(Color.set(Bar.getString(bar + "Whisky.Message.Lose")));
                }
            }
        }
    }
}