package fr.hamunaptra_.oxaliacore;

import fr.hamunaptra_.oxaliacore.addon.announce.*;
import fr.hamunaptra_.oxaliacore.addon.bank.*;
import fr.hamunaptra_.oxaliacore.addon.bar.*;
import fr.hamunaptra_.oxaliacore.addon.commands.*;
import fr.hamunaptra_.oxaliacore.addon.customitems.*;
import fr.hamunaptra_.oxaliacore.addon.shulker.*;
import fr.hamunaptra_.oxaliacore.utils.*;
import fr.hamunaptra_.oxaliacore.utils.api.config.*;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

public class EventManager {

    public static void Enable() {
        ConfigManager.getInstance().setup(Main.getInstance());

        Bank bank = new Bank();
        Main.getInstance().saveResource("bank.yml", false);
        bank.copy();
        Bar bar = new Bar();
        Main.getInstance().saveResource("bar.yml", false);
        bar.copy();
        CItems cItems = new CItems();
        Main.getInstance().saveResource("citems.yml", false);
        cItems.copy();

        AnnounceTask.run();

        Rc("bank", new BankCommand());
        Rl(new BankListener());
        Rl(new BankGuis());
        BankInterest.run();

        Rc("bar", new BarCommand());
        Rl(new BarListener());
        Rl(new BarBuy());

        Rc("citems", new ItemCommand());
        Rc("citems", new ItemCommand());
        Rl(new ItemListener());

        Rc("chatclear", new MainCommand());
        Rc("discord", new MainCommand());
        Rc("link", new MainCommand());
        Rc("site", new MainCommand());
        Rc("store", new MainCommand());
        Rc("votes", new MainCommand());
        Rc("clearreloadall", new MainCommand());

        Rl(new ShulkerListener());
    }

    private static void Rl(Listener l) {
        Bukkit.getPluginManager().registerEvents(l, Main.getInstance());
    }
    private static void Rc(String cmd, CommandExecutor exe) {
        Main.getInstance().getCommand(cmd).setExecutor(exe);
    }
}