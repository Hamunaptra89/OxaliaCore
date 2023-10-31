package fr.hamunaptra_.oxaliacore.addon.bank;

import fr.hamunaptra_.oxaliacore.Main;
import fr.hamunaptra_.oxaliacore.utils.chat.*;
import fr.hamunaptra_.oxaliacore.utils.files.config.*;
import fr.hamunaptra_.oxaliacore.utils.files.data.*;

import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class BankCommand implements CommandExecutor {

    String key = "Bank.Message.";
    String path = "Bank.Interest.";

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        if (!(s instanceof Player p)) {
            return false;
        }

        Color Color = new Color(p);
        BankData db = new BankData(p);

        if (args.length < 1) {
            BankGuis.BankMain(p);
            return true;
        }

        if (args[0].equalsIgnoreCase("help")) {
            p.sendMessage(Color.set(Bank.getString(key + "Help")));

        } else if (args[0].equalsIgnoreCase("balance")) {
            p.sendMessage(Color.set(Bank.getString(key + "Balance").replace("%amount%", Decimal.format(db.getBalance()))));

        } else if (args[0].equalsIgnoreCase("deposit")) {
            if (args.length < 2) {
                p.sendMessage(Color.set(Bank.getString(key + "Deposit.Help")));
                return true;
            }

            try {
                double amount;

                if (args[1].endsWith("%")) {
                    amount = Main.eco.getBalance((OfflinePlayer) p) * Double.parseDouble(args[1].replaceAll("%", "")) / 100.0;
                } else {
                    amount = Double.parseDouble(args[1]);
                }

                if (amount > 0 && amount <= Main.eco.getBalance((OfflinePlayer) p)) {
                    EconomyResponse r = Main.eco.withdrawPlayer((OfflinePlayer) p, amount);
                    if (r.transactionSuccess()) {
                        db.deposit(amount);
                        p.sendMessage(Color.set(Bank.getString(key + "Deposit.Success").replace("%amount%", Decimal.format(amount))));

                    } else {
                        p.sendMessage(Color.set(Bank.getString(key + "NoMoney")));
                    }
                } else {
                    p.sendMessage(Color.set(Bank.getString(key + "NoMoney")));
                }
            } catch (NumberFormatException e) {
                p.sendMessage(Color.set(Bank.getString(key + "InvalidNumber")));
            }

        } else if (args[0].equalsIgnoreCase("withdraw")) {
            if (args.length < 2) {
                p.sendMessage(Color.set(Bank.getString(key + "Withdraw.Help")));
                return true;
            }

            try {
                double amount;

                if (args[1].endsWith("%")) {
                    amount = db.getBalance() * Double.parseDouble(args[1].replace("%", "")) / 100.0;
                } else {
                    amount = Double.parseDouble(args[1]);
                }

                if (amount > 0 && amount <= db.getBalance()) {
                    db.withdraw(amount);

                    EconomyResponse r = Main.eco.depositPlayer((OfflinePlayer) p, amount);

                    if (r.transactionSuccess()) {
                        p.sendMessage(Color.set(Bank.getString(key + "Withdraw.Success").replace("%amount%", Decimal.format(amount))));

                    } else {
                        p.sendMessage(Color.set(Bank.getString(key + "NoMoney")));
                    }
                } else {
                    p.sendMessage(Color.set(Bank.getString(key + "NoMoney")));
                }
            } catch (NumberFormatException e) {
                p.sendMessage(Color.set(Bank.getString(key + "InvalidNumber")));
            }

        } else if (args[0].equalsIgnoreCase("interest")) {
            if (Bank.getBoolean(path + "Enable")) {
                if (db.getBalance() >= Bank.getInt(path + "Minimum")) {
                    p.sendMessage(Color.set(Bank.getString(path + "Message.Time")));

                } else {
                    p.sendMessage(Color.set(Bank.getString(path + "Message.NeedMoreMoney")));
                }
            } else {
                p.sendMessage(Color.set(Bank.getString(path + "Message.Disable")));
            }
        }
        return true;
    }
}
