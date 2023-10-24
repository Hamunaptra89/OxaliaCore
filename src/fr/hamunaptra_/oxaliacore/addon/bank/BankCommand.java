    package fr.hamunaptra_.oxaliacore.addon.bank;

    import fr.hamunaptra_.oxaliacore.Main;
    import fr.hamunaptra_.oxaliacore.utils.api.chat.Color;
    import fr.hamunaptra_.oxaliacore.utils.api.config.Bank;
    import fr.hamunaptra_.oxaliacore.utils.api.data.DataManager;

    import net.milkbowl.vault.economy.EconomyResponse;

    import org.bukkit.OfflinePlayer;
    import org.bukkit.command.*;
    import org.bukkit.entity.Player;

    import javax.xml.crypto.Data;
    import java.text.DecimalFormat;

    public class BankCommand implements CommandExecutor {

        String key = "Bank.Message.";
        String path = "Bank.Interest.";
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        @Override
        public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
            Player p = (Player) s;
            Color Color = new Color(p);
            DataManager Data = new DataManager(p);

            if (args.length < 1) {
                BankGuis.BankMenuMain(p);
                return true;
            }

            if (args[0].equalsIgnoreCase("help")) {
                p.sendMessage(Color.set(Bank.getString(key + "Help")));

            } else if (args[0].equalsIgnoreCase("balance")) {
                double balance = Data.getBalance();
                String formattedBalance = decimalFormat.format(balance);
                p.sendMessage(Color.set(Bank.getString(key + "Balance").replaceAll("%amount%", formattedBalance)));

            } else if (args[0].equalsIgnoreCase("deposit")) {
                if (args.length < 2) {
                    p.sendMessage(Color.set(Bank.getString(key + "Deposit.Help")));
                    return true;
                }

                try {
                    double amount;
                    if (args[1].endsWith("%")) {
                        double percentage = Double.parseDouble(args[1].replaceAll("%", "")) / 100.0;
                        double playerBalance = Main.economy.getBalance((OfflinePlayer) p);
                        amount = playerBalance * percentage;
                    } else {
                        amount = Double.parseDouble(args[1]);
                    }

                    if (amount > 0 && amount <= Main.economy.getBalance((OfflinePlayer) p)) {
                        EconomyResponse r = Main.economy.withdrawPlayer((OfflinePlayer) p, amount);
                        if (r.transactionSuccess()) {
                            Data.deposit(amount);
                            String formattedAmount = decimalFormat.format(amount);
                            p.sendMessage(Color.set(Bank.getString(key + "Deposit.Success").replaceAll("%amount%", formattedAmount)));
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
                        double percentage = Double.parseDouble(args[1].replaceAll("%", "")) / 100.0;
                        amount = Data.getBalance() * percentage;
                    } else {
                        amount = Double.parseDouble(args[1]);
                    }

                    if (amount > 0 && amount <= Data.getBalance()) {
                        Data.withdraw(amount);
                        EconomyResponse r = Main.economy.depositPlayer((OfflinePlayer) p, amount);

                        if (r.transactionSuccess()) {
                            String formattedAmount = decimalFormat.format(amount);
                            p.sendMessage(Color.set(Bank.getString(key + "Withdraw.Success").replaceAll("%amount%", formattedAmount)));
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
                if (Bank.getBoolean(path + "Enable")) {
                    if (Data.getBalance() >= Bank.getInt(path + "Minimum")) {
                        double interest = Data.getBalance() * Bank.getDouble(path + "Percent");
                        int lastInterest = Data.getInterestTime();
                        int hour = lastInterest / 3600;
                        int min = (lastInterest % 3600) / 60;
                        int sec = lastInterest % 60;

                        String formattedInterest = decimalFormat.format(interest);
                        p.sendMessage(Color.set(Bank.getString(path + "Message.Time")
                                .replace("%hour", String.valueOf(hour))
                                .replace("%min", String.valueOf(min))
                                .replace("%sec", String.valueOf(sec))
                                .replace("%interest%", formattedInterest)));
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