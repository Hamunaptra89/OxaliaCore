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

        OxaliaData data = new OxaliaData(p);

        if (args.length < 1) {
            BankGuis.BankMain(p);
            return true;
        }

        if (args[0].equalsIgnoreCase("help")) {
            p.sendMessage(Color.set(Bank.getString(BANK_KEY + "Help")));
        } else if (args[0].equalsIgnoreCase("balance")) {
            p.sendMessage(Color.set(Bank.getString(BANK_KEY + "Balance").replace("%amount%", Decimal.format(data.getBalance()))));
        } else if (args[0].equalsIgnoreCase("deposit")) {
            deposit(p, data, args);
        } else if (args[0].equalsIgnoreCase("withdraw")) {
            withdraw(p,data, args);
        } else if (args[0].equalsIgnoreCase("interest")) {
            interest(p, data);
        }
        return true;
    }

    private void deposit(Player p, OxaliaData data, String[] args) {
        if (args.length < 2) {
            p.sendMessage(Color.set(Bank.getString(BANK_KEY + "Deposit.Help")));
            return;
        }

        try {
            double amount = parseDepositAmount(args[1], p);
            double bank_limit = Bank.getDouble("Bank.Maximum");

            if (amount > 0 && amount <= Main.eco.getBalance(p) && (data.getBalance() + amount) <= bank_limit) {
                EconomyResponse response = Main.eco.withdrawPlayer(p, amount);
                if (response.transactionSuccess()) {
                    data.deposit(amount);
                    p.sendMessage(Color.set(Bank.getString(BANK_KEY + "Deposit.Success").replace("%amount%", Decimal.format(amount))));
                } else {
                    p.sendMessage(Color.set(Bank.getString(BANK_KEY + "NoMoney")));
                }
            } else {
                p.sendMessage(Color.set(Bank.getString(BANK_KEY + "NoMoney")));
            }
        } catch (NumberFormatException e) {
            p.sendMessage(Color.set(Bank.getString(BANK_KEY + "InvalidNumber")));
        }
    }

    private void withdraw(Player p, OxaliaData data, String[] args) {
        if (args.length < 2) {
            p.sendMessage(Color.set(Bank.getString(BANK_KEY + "Withdraw.Help")));
            return;
        }

        try {
            double amount = parseWithdrawAmount(args[1], data);

            if (amount > 0 && amount <= data.getBalance()) {
                data.withdraw(amount);
                EconomyResponse response = Main.eco.depositPlayer(p, amount);

                if (response.transactionSuccess()) {
                    p.sendMessage(Color.set(Bank.getString(BANK_KEY + "Withdraw.Success").replace("%amount%", Decimal.format(amount))));
                } else {
                    p.sendMessage(Color.set(Bank.getString(BANK_KEY + "NoMoney")));
                }
            } else {
                p.sendMessage(Color.set(Bank.getString(BANK_KEY + "NoMoney")));
            }
        } catch (NumberFormatException e) {
            p.sendMessage(Color.set(Bank.getString(BANK_KEY + "InvalidNumber")));
        }
    }

    private void interest(Player p, OxaliaData data) {
        if (Bank.getBoolean(INTEREST_KEY + "Enable")) {
            if (data.getBalance() >= Bank.getInt(INTEREST_KEY + "Minimum")) {
                p.sendMessage(Color.set(Bank.getString(INTEREST_KEY + "Message.Time")));
            } else {
                p.sendMessage(Color.set(Bank.getString(INTEREST_KEY + "Message.NeedMoreMoney")));
            }
        } else {
            p.sendMessage(Color.set(Bank.getString(INTEREST_KEY + "Message.Disable")));
        }
    }

    private double parseDepositAmount(String amount, Player p) {
        if (amount.endsWith("%")) {
            double bal = Main.eco.getBalance(p);
            return bal * Double.parseDouble(amount.replace("%", "")) / 100.0;
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
            double bal = data.getBalance();
            return bal * Double.parseDouble(amount.replace("%", "")) / 100.0;
        } else {
            try {
                double parsedAmount = Double.parseDouble(amount);
                return Math.round(parsedAmount * 100.0) / 100.0;
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }
    }
}
