package fr.hamunaptra_.oxaliacore.addon.economie;

import fr.hamunaptra_.oxaliacore.Main;
import org.bukkit.Bukkit;
import java.util.Random;

public class BitCoinsUpdate {

    public static void run() {
        Bukkit.getServer().getScheduler().runTaskTimer(Main.getInstance(), new Runnable() {
            public void run() {
                Random r = new Random();

                double bitcoinValue = 20000.0;
                double minValue = 20000.0;
                double maxValue = 50000.0;
                double maxFluctuation = 500.0;

                double fluctuation = -maxFluctuation + r.nextDouble() * (2 * maxFluctuation);
                bitcoinValue += fluctuation;
                bitcoinValue = Math.max(minValue, Math.min(maxValue, bitcoinValue));
                Bukkit.broadcastMessage("Bitcoins value : " + bitcoinValue + " $");
            }
        }, 0, 100L);
    }
}
