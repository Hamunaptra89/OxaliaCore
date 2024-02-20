package fr.hamunaptra_.oxaliacore.addon.customitems;

import fr.hamunaptra_.oxaliacore.utils.chat.*;
import fr.hamunaptra_.oxaliacore.utils.files.config.*;
import fr.hamunaptra_.oxaliacore.utils.items.*;

import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class ItemCommand implements CommandExecutor {
    String key = "CItems.";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if (!(sender instanceof Player p)) {
            return false;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("all")) {
                if (p.hasPermission(CItems.getString("CItems.Admin.Permission"))) {
                    p.getInventory().addItem(new Items(Material.POTATO)
                            .setName(Color.set(CItems.getString(key + "Potato.Name")))
                            .setLore(Color.set(CItems.getStringList(key + "Potato.Lore"))).im());
                    p.getInventory().addItem(new Items(Material.CARROT)
                            .setName(Color.set(CItems.getString(key + "Carrot.Name")))
                            .setLore(Color.set(CItems.getStringList(key + "Carrot.Lore"))).im());
                    p.getInventory().addItem(new Items(Material.CAKE)
                            .setName(Color.set(CItems.getString(key + "Cake.Name")))
                            .setLore(Color.set(CItems.getStringList(key + "Cake.Lore"))).im());
                    p.getInventory().addItem(new Items(Material.BREAD)
                            .setName(Color.set(CItems.getString(key + "Bread.Name")))
                            .setLore(Color.set(CItems.getStringList(key + "Bread.Lore"))).im());
                    p.getInventory().addItem(new Items(Material.COOKIE)
                            .setName(Color.set(CItems.getString(key + "Cookie.Name")))
                            .setLore(Color.set(CItems.getStringList(key + "Cookie.Lore"))).im());
                    return false;
                }
            }
        }
        return true;
    }
}