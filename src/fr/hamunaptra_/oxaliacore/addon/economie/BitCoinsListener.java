package fr.hamunaptra_.oxaliacore.addon.economie;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Random;

public class BitCoinsListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Random r = new Random();

        if (e.getBlock().getType() == Material.STONE) {
            double minedBitcoin = 0.1 + r.nextDouble() * 0.9;
            double bitcoinValue = 20000.0;
            double earnings = minedBitcoin * bitcoinValue;

            p.sendMessage("Vous avez gagné " + minedBitcoin + " Bitcoin en minant un bloc de roche !");
            p.sendMessage("Cela équivaut à $" + earnings);
        }
    }
}
