package fr.hamunaptra_.immersicore.addon.bank;

import fr.hamunaptra_.immersicore.Main;
import fr.hamunaptra_.immersicore.utils.api.chat.*;
import fr.hamunaptra_.immersicore.utils.api.config.Bank;
import fr.hamunaptra_.immersicore.utils.api.data.DataManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BankInterest {

    public static void run() {
        Bukkit.getServer().getScheduler().runTaskTimer(Main.getInstance(), () -> {
            String path = "Bank.Interest.";

            if (Bank.getBoolean(path + "Enable")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    DataManager Data = new DataManager(p);
                    Data.removeInterestTime(1);

                    if (Data.getInterestTime() <= 0 && Data.getBalance() >= Bank.getDouble(path + "Minimum")) {
                        double amount = Data.getBalance() * Bank.getDouble(path + "Percent");
                        Data.deposit(amount);

                        Color Color = new Color(p);
                        p.sendMessage(Color.set(Bank.getString(path + "Message.Get")).replaceAll("%interest%", String.valueOf(amount)));
                    }
                    Data.setInterestTime(Bank.getInt(path + "Time"));
                }
            }
        }, 20, 20);
    }
}
