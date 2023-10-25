package fr.hamunaptra_.oxaliacore.addon.bank;

import fr.hamunaptra_.oxaliacore.Main;
import fr.hamunaptra_.oxaliacore.utils.api.chat.*;
import fr.hamunaptra_.oxaliacore.utils.api.config.*;
import fr.hamunaptra_.oxaliacore.utils.api.items.*;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class BankGuis implements Listener {

    private final Map<Player, BukkitTask> UpdateGui = new HashMap<>();

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        if (e.getPlayer() instanceof Player) {
            Player p = (Player) e.getPlayer();
            Color color = new Color(p);

            if (e.getView().getTitle().equals(color.set(Bank.getString("Bank.Guis.Main.Inv.Name"))) && !UpdateGui.containsKey(p)) {
                BukkitTask task = new BukkitRunnable() {
                    @Override
                    public void run() {
                        BankMenuMain(p);
                    }
                }.runTaskTimer(Main.getInstance(), 20, 20);

                UpdateGui.put(p, task);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getPlayer() instanceof Player) {
            Player p = (Player) e.getPlayer();
            Color Color = new Color(p);

            if (e.getView().getTitle().equals(Color.set(Bank.getString("Bank.Guis.Main.Inv.Name"))) && UpdateGui.containsKey(p)) {
                BukkitTask task = UpdateGui.get(p);
                task.cancel();
                UpdateGui.remove(p);
            }
        }
    }


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
