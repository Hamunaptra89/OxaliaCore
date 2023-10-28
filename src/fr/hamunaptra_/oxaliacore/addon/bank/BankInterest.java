package fr.hamunaptra_.oxaliacore.addon.bank;

import fr.hamunaptra_.oxaliacore.Main;
import fr.hamunaptra_.oxaliacore.utils.api.chat.Color;
import fr.hamunaptra_.oxaliacore.utils.api.config.Bank;
import fr.hamunaptra_.oxaliacore.utils.api.data.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class BankInterest {

    private static final DecimalFormat df = new DecimalFormat("#0.00");
    public static String path = "Bank.Interest.";

    public static void run() {
        Bukkit.getServer().getScheduler().runTaskTimer(Main.getInstance(), new Runnable() {
            public void run() {
                if (Bank.getBoolean(path + "Enable")) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        DataManager Data = new DataManager(p);
                        Color Color = new Color(p);

                        if (Data.getInterestTime() > 0) {
                            Data.removeInterestTime(1);
                        }

                        if (Data.getInterestTime() == 0 && Data.getBalance() <= Bank.getDouble(path + "Minimum")) {
                            Data.setInterestTime(Bank.getInt(path + "Time"));
                        }

                        else if (Data.getInterestTime() == 0 && Data.getBalance() >= Bank.getDouble(path + "Minimum")) {
                            Data.setInterestTime(Bank.getInt(path + "Time"));
                            double amount = Data.getBalance() * Bank.getDouble(path + "Percent");
                            Data.deposit(amount);
                            String formattedAmount = df.format(amount);
                            p.sendMessage(Color.set(Bank.getString(path + "Message.Get")).replaceAll("%interest%", formattedAmount));
                        }
                    }
                }
            }
        }, 20, 20);
    }
}