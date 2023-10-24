package fr.hamunaptra_.immersicore.addon.bank;

import fr.hamunaptra_.immersicore.utils.api.chat.*;
import fr.hamunaptra_.immersicore.utils.api.config.*;
import fr.hamunaptra_.immersicore.utils.api.items.*;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class BankGuis implements Listener {

    public static void BankMenuMain(Player p) {
        Color Color = new Color(p);

        Inventory inv = Bukkit.createInventory(null, Bank.getInt("Bank.Guis.Main.Inv.Slot"), Color.set(Bank.getString("Bank.Guis.Main.Inv.Name")));
        ConfigurationSection s = Bank.getConfigurationSection("Bank.Guis.Main.Item");

        if (s != null) {
            for (String key : s.getKeys(false)) {
                String path = "Bank.Guis.Main.Item." + key;
                if (Bank.getInt(path + ".Slot") >= 0 &&
                        Bank.getInt(path + ".Slot") < Bank.getInt("Bank.Guis.Main.Inv.Slot")) {

                    Items item = new Items(Material.getMaterial(Bank.getString(path + ".Material")), Bank.getInt(path + ".Amount"))
                            .setName(Color.set(Bank.getString(path + ".Name")))
                            .setLore(Color.set(Bank.getStringList(path + ".Lore")));

                    inv.setItem(Bank.getInt(path + ".Slot"), item.im());
                }
                p.openInventory(inv);
            }
        }
    }

    public static void BankMenuDeposit(Player p) {
        Color Color = new Color(p);

        Inventory inv = Bukkit.createInventory(null, Bank.getInt("Bank.Guis.Deposit.Inv.Slot"), Color.set(Bank.getString("Bank.Guis.Deposit.Inv.Name")));
        ConfigurationSection s = Bank.getConfigurationSection("Bank.Guis.Deposit.Item");

        if (s != null) {
            for (String key : s.getKeys(false)) {
                String path = "Bank.Guis.Deposit.Item." + key;
                if (Bank.getInt(path + ".Slot") >= 0 &&
                        Bank.getInt(path + ".Slot") < Bank.getInt("Bank.Guis.Deposit.Inv.Slot")) {

                    Items item = new Items(Material.getMaterial(Bank.getString(path + ".Material")), Bank.getInt(path + ".Amount"))
                            .setName(Color.set(Bank.getString(path + ".Name")))
                            .setLore(Color.set(Bank.getStringList(path + ".Lore")));

                    inv.setItem(Bank.getInt(path + ".Slot"), item.im());
                }
                p.openInventory(inv);
            }
        }
    }

    public static void BankMenuWithdraw(Player p) {
        Color Color = new Color(p);

        Inventory inv = Bukkit.createInventory(null, Bank.getInt("Bank.Guis.Withdraw.Inv.Slot"), Color.set(Bank.getString("Bank.Guis.Withdraw.Inv.Name")));
        ConfigurationSection s = Bank.getConfigurationSection("Bank.Guis.Withdraw.Item");

        if (s != null) {
            for (String key : s.getKeys(false)) {
                String path = "Bank.Guis.Withdraw.Item." + key;
                if (Bank.getInt(path + ".Slot") >= 0 &&
                        Bank.getInt(path + ".Slot") < Bank.getInt("Bank.Guis.Withdraw.Inv.Slot")) {

                    Items item = new Items(Material.getMaterial(Bank.getString(path + ".Material")), Bank.getInt(path + ".Amount"))
                            .setName(Color.set(Bank.getString(path + ".Name")))
                            .setLore(Color.set(Bank.getStringList(path + ".Lore")));

                    inv.setItem(Bank.getInt(path + ".Slot"), item.im());
                }
                p.openInventory(inv);
            }
        }
    }
}
