package fr.hamunaptra_.oxaliacore.addon.bank;

import fr.hamunaptra_.oxaliacore.Main;
import fr.hamunaptra_.oxaliacore.utils.chat.*;
import fr.hamunaptra_.oxaliacore.utils.files.config.*;
import fr.hamunaptra_.oxaliacore.utils.files.data.*;

import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class BankCommand implements CommandExecutor {

    private static final String BANK_KEY = "Bank.Message.";
    private static final String INTEREST_KEY = "Bank.Interest.";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            return false;
        }

        Color color = new Color(p);
        OxaliaData data = new OxaliaData(p);

        if (args.length < 1) {
            BankGuis.BankMain(p);
            return true;
        }

        if (args[0].equalsIgnoreCase("help")) {
            p.sendMessage(color.set(Bank.getString(BANK_KEY + "Help")));
        } else if (args[0].equalsIgnoreCase("balance")) {
            p.sendMessage(color.set(Bank.getString(BANK_KEY + "Balance").replace("%amount%", Decimal.format(data.getBalance()))));
        } else if (args[0].equalsIgnoreCase("deposit")) {
            deposit(p, color, data, args);
        } else if (args[0].equalsIgnoreCase("withdraw")) {
            withdraw(p, color, data, args);
        } else if (args[0].equalsIgnoreCase("interest")) {
            interest(p, color, data);
        }
        return true;
    }

    private void deposit(Player p, Color color, OxaliaData data, String[] args) {
        if (args.length < 2) {
            p.sendMessage(color.set(Bank.getString(BANK_KEY + "Deposit.Help")));
            return;
        }

        try {
            double amount = parseAmount(args[1], p);
            if (amount > 0 && amount <= Main.eco.getBalance(p)) {
                EconomyResponse response = Main.eco.withdrawPlayer(p, amount);
                if (response.transactionSuccess()) {
                    data.deposit(amount);
                    p.sendMessage(color.set(Bank.getString(BANK_KEY + "Deposit.Success").replace("%amount%", Decimal.format(amount))));
                } else {
                    p.sendMessage(color.set(Bank.getString(BANK_KEY + "NoMoney")));
                }
            } else {
                p.sendMessage(color.set(Bank.getString(BANK_KEY + "NoMoney")));
            }
        } catch (NumberFormatException e) {
            p.sendMessage(color.set(Bank.getString(BANK_KEY + "InvalidNumber")));
        }
    }

    private void withdraw(Player p, Color color, OxaliaData data, String[] args) {
        if (args.length < 2) {
            p.sendMessage(color.set(Bank.getString(BANK_KEY + "Withdraw.Help")));
            return;
        }

        try {
            double amount = parseAmount(args[1], p);
            if (amount > 0 && amount <= data.getBalance()) {
                data.withdraw(amount);
                EconomyResponse response = Main.eco.depositPlayer(p, amount);
                if (response.transactionSuccess()) {
                    p.sendMessage(color.set(Bank.getString(BANK_KEY + "Withdraw.Success").replace("%amount%", Decimal.format(amount))));
                } else {
                    p.sendMessage(color.set(Bank.getString(BANK_KEY + "NoMoney")));
                }
            } else {
                p.sendMessage(color.set(Bank.getString(BANK_KEY + "NoMoney")));
            }
        } catch (NumberFormatException e) {
            p.sendMessage(color.set(Bank.getString(BANK_KEY + "InvalidNumber")));
        }
    }

    private void interest(Player p, Color color, OxaliaData data) {
        if (Bank.getBoolean(INTEREST_KEY + "Enable")) {
            if (data.getBalance() >= Bank.getInt(INTEREST_KEY + "Minimum")) {
                p.sendMessage(color.set(Bank.getString(INTEREST_KEY + "Message.Time")));
            } else {
                p.sendMessage(color.set(Bank.getString(INTEREST_KEY + "Message.NeedMoreMoney")));
            }
        } else {
            p.sendMessage(color.set(Bank.getString(INTEREST_KEY + "Message.Disable")));
        }
    }

    private double parseAmount(String amount, Player p) {
        if (amount.endsWith("%")) {
            return Main.eco.getBalance(p) * Double.parseDouble(amount.replace("%", "")) / 100.0;
        } else {
            return Double.parseDouble(amount);
        }
    }
}
