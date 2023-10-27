package fr.hamunaptra_.oxaliacore.addon.log;

import fr.hamunaptra_.oxaliacore.utils.api.data.*;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogsListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        String block = e.getBlock().getType().name();
        LogManager Log = new LogManager();

        int x = e.getBlock().getX();
        int y = e.getBlock().getY();
        int z = e.getBlock().getZ();
        String w = e.getBlock().getWorld().getName();

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss.SSS");
        String date = df.format(new Date());

        String log = p.getName() + " place " + block + " (" + w + ", "+ x + ", " + y + ", " + z + ")";

        Log.set(date, log);
    }



    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        String block = e.getBlock().getType().name();
        LogManager Log = new LogManager();

        int x = e.getBlock().getX();
        int y = e.getBlock().getY();
        int z = e.getBlock().getZ();
        String w = e.getBlock().getWorld().getName();

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss.SSS");
        String date = df.format(new Date());

        String log = p.getName() + " break " + block + " (" + w + ", "+ x + ", " + y + ", " + z + ")";

        Log.set(date, log);
    }
}
