package fr.hamunaptra_.oxaliacore;

import fr.hamunaptra_.oxaliacore.addon.announce.*;
import fr.hamunaptra_.oxaliacore.addon.bank.*;
import fr.hamunaptra_.oxaliacore.addon.bar.*;
import fr.hamunaptra_.oxaliacore.addon.commands.*;
import fr.hamunaptra_.oxaliacore.addon.customitems.*;
import fr.hamunaptra_.oxaliacore.addon.economie.*;
import fr.hamunaptra_.oxaliacore.addon.log.*;
import fr.hamunaptra_.oxaliacore.addon.shulker.*;

import fr.hamunaptra_.oxaliacore.utils.files.config.*;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

import java.util.logging.Level;

public class EventManager {

    public static void Enable() {
        Bukkit.getLogger().log(Level.INFO, "---------------[OxaliaCore]---------------");
        Bukkit.getLogger().log(Level.INFO, "");
        Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Version : " + Main.getInstance().getDescription().getVersion());
        Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Author : " + Main.getInstance().getDescription().getAuthors());
        Bukkit.getLogger().log(Level.INFO, "");
        Bukkit.getLogger().log(Level.INFO, "---------------[OxaliaCore]---------------");
        Bukkit.getLogger().log(Level.INFO, "");
        Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Register PlaceHolderAPI integration.");
        Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Loading OxaliaCore/config.yml configuration file.");
        Config config = new Config();
        config.copy();
        Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Loading OxaliaCore/configs/bank.yml configuration file.");
        Bank bank = new Bank();
        Main.getInstance().saveResource("bank.yml", false);
        bank.copy();
        Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Loading OxaliaCore/configs/bar.yml configuration file.");
        Bar bar = new Bar();
        Main.getInstance().saveResource("bar.yml", false);
        bar.copy();
        Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Loading OxaliaCore/configs/citems.yml configuration file.");
        CItems cItems = new CItems();
        Main.getInstance().saveResource("citems.yml", false);
        cItems.copy();

        Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Setup AnnounceTask module.");
        AnnounceTask.run();

        Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Setup Bank module.");
        Rc("bank", new BankCommand());
        Rl(new BankListener());
        Rl(new BankGuis());
        BankInterest.run();

        Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Setup Bar module.");
        Rc("bar", new BarCommand());
        Rl(new BarListener());
        Rl(new BarBuy());

        Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Setup BitCoins module.");
        Rl(new BitCoinsListener());
        BitCoinsUpdate.run();

        Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Setup CustomItems module.");
        Rc("citems", new ItemCommand());
        Rl(new ItemListener());

        Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Setup Log module.");
        Rl(new LogsListener());
        Rc("deletelog", new LogCommand());
        Rc("viewlog", new LogCommand());

        Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Setup Shulker module.");
        Rl(new ShulkerListener());


        Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Setup TimedTask module.");
        TimedTask.run();

        Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Loading commands.");
        Rc("chatclear", new MainCommand());
        Rc("discord", new MainCommand());
        Rc("link", new MainCommand());
        Rc("site", new MainCommand());
        Rc("store", new MainCommand());
        Rc("votes", new MainCommand());
        Rc("clearreloadall", new MainCommand());
        Rc("furnace", new FurnaceCommand());

        Bukkit.getLogger().log(Level.INFO, "");
        Bukkit.getLogger().log(Level.INFO, "---------------[OxaliaCore]---------------");
    }

    private static void Rl(Listener l) {
        Bukkit.getPluginManager().registerEvents(l, Main.getInstance());
    }
    private static void Rc(String cmd, CommandExecutor exe) {
        Main.getInstance().getCommand(cmd).setExecutor(exe);
    }
}