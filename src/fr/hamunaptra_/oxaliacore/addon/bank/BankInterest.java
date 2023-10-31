package fr.hamunaptra_.oxaliacore.addon.bank;

import fr.hamunaptra_.oxaliacore.Main;
import fr.hamunaptra_.oxaliacore.utils.chat.*;
import fr.hamunaptra_.oxaliacore.utils.files.data.*;
import fr.hamunaptra_.oxaliacore.utils.files.config.*;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BankInterest {

    public static String path = "Bank.Interest.";

    public static void run() {
        Bukkit.getServer().getScheduler().runTaskTimer(Main.getInstance(), new Runnable() {
            public void run() {
                if (Bank.getBoolean(path + "Enable")) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        BankData Data = new BankData(p);
                        Color Color = new Color(p);

                        if (Data.getInterestTime() > 0) {
                            Data.removeInterestTime(1);
                            return;
                        }

                        if (Data.getInterestTime() == 0) {
                            if (Data.getBalance() <= Bank.getDouble(path + "Minimum")) {
                                Data.setInterestTime(Bank.getInt(path + "Time"));
                                return;

                            } else if (Data.getBalance() >= Bank.getDouble(path + "Minimum")) {
                                Data.setInterestTime(Bank.getInt(path + "Time"));
                                double amount = Data.getBalance() * Bank.getDouble(path + "Percent");
                                Data.deposit(amount);
                                p.sendMessage(Color.set(Bank.getString(path + "Message.Get")));
                                return;
                            }
                        }
                    }
                }
            }
        }, 20, 20);
    }
}