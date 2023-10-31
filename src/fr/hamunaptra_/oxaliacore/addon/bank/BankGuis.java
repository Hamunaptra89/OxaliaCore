package fr.hamunaptra_.oxaliacore.addon.bank;

import fr.hamunaptra_.oxaliacore.utils.chat.*;
import fr.hamunaptra_.oxaliacore.utils.files.config.*;
import fr.hamunaptra_.oxaliacore.utils.items.*;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class BankGuis implements Listener {

    public static String bank = "Bank.Guis.";

    public static void BankMain(Player p) {
        Color Color = new Color(p);

        Inventory inv = Bukkit.createInventory(null, Bank.getInt(bank + "Main.Inv.Slot"), Color.set(Bank.getString(bank + "Main.Inv.Name")));
        ConfigurationSection s = Bank.getConfigurationSection(bank + "Main.Item");

        if (s != null) {
            for (String key : s.getKeys(false)) {
                String path = bank + "Main.Item." + key;
                if (Bank.getInt(path + ".Slot") >= 0 &&
                        Bank.getInt(path + ".Slot") < Bank.getInt(bank + "Main.Inv.Slot")) {

                    Items item = new Items(Material.getMaterial(Bank.getString(path + ".Material")), Bank.getInt(path + ".Amount"))
                            .setName(Color.set(Bank.getString(path + ".Name")))
                            .setLore(Color.set(Bank.getStringList(path + ".Lore")));

                    inv.setItem(Bank.getInt(path + ".Slot"), item.im());
                }
                p.openInventory(inv);
            }
        }
    }

    public static void BankDeposit(Player p) {
        Color Color = new Color(p);

        Inventory inv = Bukkit.createInventory(null, Bank.getInt(bank + "Deposit.Inv.Slot"), Color.set(Bank.getString(bank + "Deposit.Inv.Name")));
        ConfigurationSection s = Bank.getConfigurationSection(bank + "Deposit.Item");

        if (s != null) {
            for (String key : s.getKeys(false)) {
                String path = bank + "Deposit.Item." + key;
                if (Bank.getInt(path + ".Slot") >= 0 &&
                        Bank.getInt(path + ".Slot") < Bank.getInt(bank + "Deposit.Inv.Slot")) {

                    Items item = new Items(Material.getMaterial(Bank.getString(path + ".Material")), Bank.getInt(path + ".Amount"))
                            .setName(Color.set(Bank.getString(path + ".Name")))
                            .setLore(Color.set(Bank.getStringList(path + ".Lore")));

                    inv.setItem(Bank.getInt(path + ".Slot"), item.im());
                }
                p.openInventory(inv);
            }
        }
    }

    public static void BankWithdraw(Player p) {
        Color Color = new Color(p);

        Inventory inv = Bukkit.createInventory(null, Bank.getInt(bank + "Withdraw.Inv.Slot"), Color.set(Bank.getString(bank + "Withdraw.Inv.Name")));
        ConfigurationSection s = Bank.getConfigurationSection(bank + "Withdraw.Item");

        if (s != null) {
            for (String key : s.getKeys(false)) {
                String path = bank + "Withdraw.Item." + key;
                if (Bank.getInt(path + ".Slot") >= 0 &&
                        Bank.getInt(path + ".Slot") < Bank.getInt(bank + "Withdraw.Inv.Slot")) {

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
