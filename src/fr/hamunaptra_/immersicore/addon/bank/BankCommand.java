package fr.hamunaptra_.immersicore.addon.bank;

import fr.hamunaptra_.immersicore.Main;
import fr.hamunaptra_.immersicore.utils.api.chat.Color;
import fr.hamunaptra_.immersicore.utils.api.config.Bank;
import fr.hamunaptra_.immersicore.utils.api.data.DataManager;

import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class BankCommand implements CommandExecutor {

    String key = "Bank.Message.";

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        Player p = (Player) s;
        Color Color = new Color(p);
        DataManager data = new DataManager(p);

        if (args.length < 1) {
            p.sendMessage(Color.set(Bank.getString(key + "Help")));
            return true;
        }

        if (args[0].equalsIgnoreCase("balance")) {
            p.sendMessage(Color.set(Bank.getString(key + "Balance").replaceAll("%amount%", String.valueOf(data.getBalance()))));

        } else if (args[0].equalsIgnoreCase("open")) {
            BankGuis.BankMenuMain(p);

        } else if (args[0].equalsIgnoreCase("deposit")) {
            if (args.length < 2) {
                p.sendMessage(Color.set(Bank.getString(key + "Deposit.Help")));
                return true;
            }

            try {
                double amount = Double.parseDouble(args[1]);
                if (amount > 0 && data.getBalance() + amount <= Bank.getDouble("Bank.Maximum")) {
                    EconomyResponse r = Main.economy.withdrawPlayer((OfflinePlayer) p, amount);
                    if (r.transactionSuccess()) {
                        data.deposit(amount);
                        p.sendMessage(Color.set(Bank.getString(key + "Deposit.Success").replaceAll("%amount%", String.valueOf(amount))));
                    } else {
                        p.sendMessage(Color.set(Bank.getString(key + "NoMoney")));
                    }
                } else {
                    p.sendMessage(Color.set(Bank.getString(key + "InvalidNumber")));
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
                double amount = Double.parseDouble(args[1]);
                if (data.getBalance() - amount >= 0) {
                    EconomyResponse r = Main.economy.depositPlayer((OfflinePlayer) p, amount);

                    if (r.transactionSuccess()) {
                        data.withdraw(amount);
                        p.sendMessage(Color.set(Bank.getString(key + "Withdraw.Success").replaceAll("%amount%", String.valueOf(amount))));
                    } else {
                        p.sendMessage(Color.set(Bank.getString(key + "NoMoney")));
                    }
                } else {
                    p.sendMessage(Color.set(Bank.getString(key + "InvalidNumber")));
                }
            } catch (NumberFormatException e) {
                p.sendMessage(Color.set(Bank.getString(key + "InvalidNumber")));
            }

        } else if (args[0].equalsIgnoreCase("interest")) {
            if (Bank.getBoolean("Bank.Interest.Enable")) {
                double interest = data.getBalance() * Bank.getDouble("Bank.Interest.Percent");
                int lastInterest = data.getInterestTime();
                int min = (lastInterest % 3600) / 60;
                int sec = lastInterest % 60;

                p.sendMessage(Color.set(Bank.getString("Bank.Interest.Message.Time"))
                        .replace("%min", String.valueOf(min))
                        .replace("%sec", String.valueOf(sec))
                        .replace("%interest%", String.valueOf(interest)));
            }
        } else {
            p.sendMessage(Color.set(Bank.getString(key + "Help")));
        }

        return true;
    }
}