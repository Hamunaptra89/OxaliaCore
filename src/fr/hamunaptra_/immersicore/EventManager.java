package fr.hamunaptra_.immersicore;

import fr.hamunaptra_.immersicore.addon.bank.*;
import fr.hamunaptra_.immersicore.addon.bar.*;
import fr.hamunaptra_.immersicore.addon.shulker.*;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class EventManager {

    public static void Enable() {
        Rc("bar", new BarCommand());
        Rl(new BarListener());
        Rl(new BarBuy());

        Rl(new ShulkerListener());

        Rc("bank", new BankCommand());
        Rl(new BankListener());
        BankInterest.run();
    }

    private static void Rl(Listener l) {
        Bukkit.getPluginManager().registerEvents(l, Main.getInstance());
    }
    private static void Rc(String cmd, CommandExecutor exe) {
        Main.getInstance().getCommand(cmd).setExecutor(exe);
    }
}