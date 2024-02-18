package fr.hamunaptra_.oxaliacore.addon.bank;

import fr.hamunaptra_.oxaliacore.Main;
import fr.hamunaptra_.oxaliacore.utils.chat.*;
import fr.hamunaptra_.oxaliacore.utils.files.config.*;
import fr.hamunaptra_.oxaliacore.utils.files.data.*;

import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.*;

import java.util.*;

public class BankListener implements Listener {

    private Map<Player, Double> DepositNumber = new HashMap<>();
    private Map<Player, Boolean> DepositNumberMap = new HashMap<>();

    private Map<Player, Double> WithDrawNumber = new HashMap<>();
    private Map<Player, Boolean> WithDrawNumberMap = new HashMap<>();

    String msg = "Bank.Message.";
    String gui = "Bank.Guis.";

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Color Color = new Color(p);
        OxaliaData Data = new OxaliaData(p);

        if (e.getCurrentItem() == null) return;
        if (e.getCurrentItem().getItemMeta() == null) return;

        ItemStack i = e.getCurrentItem();
        String name = i.getItemMeta().getDisplayName();

        if (e.getView().getTitle().equals(Color.set(Bank.getString(gui + "Main.Inv.Name")))
                || e.getView().getTitle().equals(Color.set(Bank.getString(gui + "Deposit.Inv.Name")))
                || e.getView().getTitle().equals(Color.set(Bank.getString(gui + "Withdraw.Inv.Name")))) {
            e.setCancelled(true);
        }


        if (name.equals(Color.set(Bank.getString(gui + "Main.Item.Deposit.Name")))) {
            BankGuis.BankDeposit(p);
        } else if (name.equals(Color.set(Bank.getString(gui + "Main.Item.Withdraw.Name")))) {
            BankGuis.BankWithdraw(p);
        } else if (name.equals(Color.set(Bank.getString(gui + "Main.Item.Leave.Name")))) {
            p.closeInventory();

        } else if (name.equals(Color.set(Bank.getString(gui + "Deposit.Item.Return.Name")))) {
            BankGuis.BankMain(p);
        } else if (name.equals(Color.set(Bank.getString(gui + "Withdraw.Item.Return.Name")))) {
            BankGuis.BankMain(p);


        } else if (name.equals(Color.set(Bank.getString(gui + "Deposit.Item.Deposit25.Name")))) {
            p.getServer().dispatchCommand(p, Bank.getString(gui + "Deposit.Item.Deposit25.Command"));
        } else if (name.equals(Color.set(Bank.getString(gui + "Deposit.Item.Deposit50.Name")))) {
            p.getServer().dispatchCommand(p, Bank.getString(gui + "Deposit.Item.Deposit50.Command"));
        } else if (name.equals(Color.set(Bank.getString(gui + "Deposit.Item.Deposit100.Name")))) {
            p.getServer().dispatchCommand(p, Bank.getString(gui + "Deposit.Item.Deposit100.Command"));
        } else if (name.equals(Color.set(Bank.getString(gui + "Withdraw.Item.Withdraw25.Name")))) {
            p.getServer().dispatchCommand(p, Color.set(Bank.getString(gui + "Withdraw.Item.Withdraw25.Command")));
        } else if (name.equals(Color.set(Bank.getString(gui + "Withdraw.Item.Withdraw50.Name")))) {
            p.getServer().dispatchCommand(p, Bank.getString(gui + "Withdraw.Item.Withdraw50.Command"));
        } else if (name.equals(Color.set(Bank.getString(gui + "Withdraw.Item.Withdraw100.Name")))) {
            p.getServer().dispatchCommand(p, Bank.getString(gui + "Withdraw.Item.Withdraw100.Command"));


        } else if (name.equals(Color.set(Bank.getString(gui + "Deposit.Item.SpecialAmount.Name")))) {
            if (!DepositNumberMap.getOrDefault(p, false)) {
                p.closeInventory();
                DepositNumberMap.put(p, true);
                p.sendMessage(Color.set(Bank.getString(msg + "Deposit.SpecialAmount")));
            }

        } else if (name.equals(Color.set(Bank.getString(gui + "Withdraw.Item.SpecialAmount.Name")))) {
            if (Data.getBalance() <= 0) {
                p.sendMessage(Color.set(Bank.getString(msg + "NoMoney")));
            } else {
                if (!WithDrawNumberMap.getOrDefault(p, false)) {
                    p.closeInventory();
                    WithDrawNumberMap.put(p, true);
                    p.sendMessage(Color.set(Bank.getString(msg + "Withdraw.SpecialAmount")));
                }
            }
        }
    }

    @EventHandler
    public void PlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        Color Color = new Color(p);
        OxaliaData Data = new OxaliaData(p);

        if (Bank.getBoolean("Bank.Death.Enable")) {
            double amount = Data.getBalance() * Bank.getDouble("Bank.Death.Percent");
            Data.setBalance(amount);
            p.sendMessage(Color.set(Bank.getString("Bank.Death.Message").replaceAll("%amount%", String.valueOf(amount))));
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        Color Color = new Color(p);
        OxaliaData Data = new OxaliaData(p);
        String message = e.getMessage();

        if (DepositNumberMap.getOrDefault(p, false)) {
            double amount = parseDepositAmount(message, p);

            if (amount > 0 && amount <= Main.eco.getBalance(p) && amount > Bank.getDouble("Bank.Maximum")) {
                EconomyResponse r = Main.eco.depositPlayer((OfflinePlayer) p, amount);

                if (r.transactionSuccess()) {
                    DepositNumber.put(p, amount);
                    Data.deposit(amount);
                    p.sendMessage(Color.set(Bank.getString(msg + "Deposit.Success").replaceAll("%amount%", String.valueOf(Decimal.format(amount)))));
                } else {
                    p.sendMessage(Color.set(Bank.getString(msg + "NoMoney")));
                }
            }
            resetTransactionFlags(p);
            e.setCancelled(true);
        }

        if (WithDrawNumberMap.getOrDefault(p, false)) {
            double amount = parseWithdrawAmount(message, Data);
            double balance = Data.getBalance();

            if (amount > 0 && amount <= balance) {
                EconomyResponse r = Main.eco.depositPlayer((OfflinePlayer) p, amount);

                if (r.transactionSuccess()) {
                    WithDrawNumber.put(p, amount);
                    Data.withdraw(amount);
                    p.sendMessage(Color.set(Bank.getString(msg + "Withdraw.Success").replaceAll("%amount%", String.valueOf(Decimal.format(amount)))));
                } else {
                    p.sendMessage(Color.set(Bank.getString(msg + "NoMoney")));
                }
            }
            resetTransactionFlags(p);
            e.setCancelled(true);
        }
    }

    private double parseDepositAmount(String amount, Player p) {
        if (amount.endsWith("%")) {
            return Main.eco.getBalance(p) * Double.parseDouble(amount.replace("%", "")) / 100.0;
        } else {
            try {
                double parsedAmount = Double.parseDouble(amount);
                return Math.round(parsedAmount * 100.0) / 100.0;
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }
    }

    private double parseWithdrawAmount(String amount, OxaliaData data) {
        if (amount.endsWith("%")) {
            double bankbalance = data.getBalance();
            return bankbalance * Double.parseDouble(amount.replace("%", "")) / 100.0;
        } else {
            try {
                double parsedAmount = Double.parseDouble(amount);
                return Math.round(parsedAmount * 100.0) / 100.0;
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }
    }

    private void resetTransactionFlags(Player p) {
        DepositNumberMap.put(p, false);
        WithDrawNumberMap.put(p, false);
    }

    private final Map<Player, BukkitTask> UpdateGui = new HashMap<>();

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        if (e.getPlayer() instanceof Player p) {
            String Gui = e.getView().getTitle();
            Color Color = new Color(p);

            if (Gui.equals(Color.set(Bank.getString(gui + "Main.Inv.Name"))) ||
                    Gui.equals(Color.set(Bank.getString(gui + "Deposit.Inv.Name"))) ||
                    Gui.equals(Color.set(Bank.getString(gui + "Withdraw.Inv.Name"))) && !UpdateGui.containsKey(p)) {
                openInventory(p, Gui);
            }
        }
    }

    private void openInventory(Player p, String Gui) {
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                Color Color = new Color(p);

                if (Gui.equals(Color.set(Bank.getString(gui + "Main.Inv.Name")))) {
                    BankGuis.BankMain(p);
                } else if (Gui.equals(Color.set(Bank.getString(gui + "Deposit.Inv.Name")))) {
                    BankGuis.BankDeposit(p);
                } else if (Gui.equals(Color.set(Bank.getString(gui + "Withdraw.Inv.Name")))) {
                    BankGuis.BankWithdraw(p);
                }
            }
        }.runTaskTimer(Main.getInstance(), 10, 20);

        UpdateGui.put(p, task);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getPlayer() instanceof Player p) {
            Color Color = new Color(p);

            if (e.getView().getTitle().equals(Color.set(Bank.getString(gui + "Main.Inv.Name"))) || e.getView().getTitle().equals(Color.set(Bank.getString(gui + "Deposit.Inv.Name"))) || e.getView().getTitle().equals(Color.set(Bank.getString(gui + "Withdraw.Inv.Name"))) && UpdateGui.containsKey(p)) {
                BukkitTask task = UpdateGui.get(p);
                task.cancel();
                UpdateGui.remove(p);
            }
        }
    }
}