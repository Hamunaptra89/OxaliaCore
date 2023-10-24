package fr.hamunaptra_.oxaliacore.addon.bank;

import fr.hamunaptra_.oxaliacore.Main;
import fr.hamunaptra_.oxaliacore.utils.api.chat.*;
import fr.hamunaptra_.oxaliacore.utils.api.config.Bank;
import fr.hamunaptra_.oxaliacore.utils.api.data.DataManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BankInterest {
    public static void run() {
        Bukkit.getServer().getScheduler().runTaskTimer(Main.getInstance(), new Runnable() {
            public void run() {
                String path = "Bank.Interest.";
                if (Bank.getBoolean(path + "Enable")) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        DataManager Data = new DataManager(p);
                        Color Color = new Color(p);

                        if (Data.getInterestTime() > 0 && Data.getBalance() >= Bank.getDouble(path + "Minimum")) {
                            Data.removeInterestTime(1);
                        }

                        if (Data.getInterestTime() == 0 && Data.getBalance() >= Bank.getDouble(path + "Minimum")) {
                            Data.setInterestTime(Bank.getInt(path + "Time"));
                            double amount = Data.getBalance() * Bank.getDouble(path + "Percent");
                            Data.deposit(amount);
                            p.sendMessage(Color.set(Bank.getString(path + "Message.Get")).replaceAll("%interest%", String.valueOf(amount)));
                        }
                    }
                }
            }
        }, 20, 20);
    }
}
