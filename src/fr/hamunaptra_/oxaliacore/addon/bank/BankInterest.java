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
                        OxaliaData data = new OxaliaData(p);
                        Color color = new Color(p);

                        if (data.getInterestTime() > 0) {
                            data.removeInterestTime(1);
                            return;
                        }

                        if (data.getInterestTime() == 0) {
                            if (data.getBalance() <= Bank.getDouble(path + "Minimum")) {
                                data.setInterestTime(Bank.getInt(path + "Time"));
                                return;

                            } else if (data.getBalance() >= Bank.getDouble(path + "Minimum")) {
                                data.setInterestTime(Bank.getInt(path + "Time"));
                                double amount = data.getBalance() * Bank.getDouble(path + "Percent");
                                data.deposit(amount);
                                p.sendMessage(color.set(Bank.getString(path + "Message.Get")));
                                return;
                            }
                        }
                    }
                }
            }
        }, 20, 20);
    }
}