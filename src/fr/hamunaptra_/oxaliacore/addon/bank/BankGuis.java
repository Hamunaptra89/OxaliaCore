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

    public static String BANK_KEY = "Bank.Guis.";

    public static void openBankGui(Player p, String guiType) {
        Color color = new Color(p);
        String guiKey = BANK_KEY + guiType;

        int invSlot = Bank.getInt(guiKey + ".Inv.Slot");
        String invName = color.set(Bank.getString(guiKey + ".Inv.Name"));
        Inventory inv = Bukkit.createInventory(null, invSlot, invName);

        ConfigurationSection s = Bank.getConfigurationSection(guiKey + ".Item");

        if (s != null) {
            for (String key : s.getKeys(false)) {
                String path = guiKey + ".Item." + key;
                int slot = Bank.getInt(path + ".Slot");

                if (slot >= 0 && slot < invSlot) {
                    Material material = Material.getMaterial(Bank.getString(path + ".Material"));
                    int amount = Bank.getInt(path + ".Amount");
                    String itemName = color.set(Bank.getString(path + ".Name"));
                    Items item = new Items(material, amount)
                            .setName(itemName)
                            .setLore(color.set(Bank.getStringList(path + ".Lore")));
                    inv.setItem(slot, item.im());
                }
            }
        }

        p.openInventory(inv);
    }

    public static void BankMain(Player p) {
        openBankGui(p, "Main");
    }

    public static void BankDeposit(Player p) {
        openBankGui(p, "Deposit");
    }

    public static void BankWithdraw(Player p) {
        openBankGui(p, "Withdraw");
    }
}
