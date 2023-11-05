package fr.hamunaptra_.oxaliacore.addon.economie;

import fr.hamunaptra_.oxaliacore.Main;

import org.bukkit.Bukkit;

import java.text.DecimalFormat;
import java.util.Random;

public class BitCoinsUpdate {

    public static void run() {
        Bukkit.getServer().getScheduler().runTaskTimer(Main.getInstance(), new Runnable() {

            private double bitcoinValue = 25000.0;
            private double minValue = 10000.0;
            private double maxValue = 40000.0;
            private double maxFluctuation = 1000.0;
            private double maxDown= 250.0;
            private double maxUp = 250.0;
            private DecimalFormat decimal = new DecimalFormat("#.00");

            public void run() {
                Random r = new Random();

                double fluctuation = -maxFluctuation + r.nextDouble() * (2 * maxFluctuation);
                double nouvelleValeur = bitcoinValue + fluctuation;

                if (nouvelleValeur < bitcoinValue - maxDown) {
                    nouvelleValeur = bitcoinValue - maxDown;
                } else if (nouvelleValeur > bitcoinValue + maxUp) {
                    nouvelleValeur = bitcoinValue + maxUp;
                }

                bitcoinValue = Math.max(minValue, Math.min(maxValue, nouvelleValeur));
                String formattedValue = decimal.format(bitcoinValue);
                Bukkit.broadcastMessage("Bitcoins value: " + formattedValue + " $");
            }
        }, 0,  600 * 20);
    }
}



