package fr.hamunaptra_.oxaliacore;

import fr.hamunaptra_.oxaliacore.addon.announce.*;
import fr.hamunaptra_.oxaliacore.addon.bank.*;
import fr.hamunaptra_.oxaliacore.addon.bar.*;
import fr.hamunaptra_.oxaliacore.addon.commands.*;
import fr.hamunaptra_.oxaliacore.addon.customitems.*;
import fr.hamunaptra_.oxaliacore.addon.log.*;
import fr.hamunaptra_.oxaliacore.addon.shulker.*;

import fr.hamunaptra_.oxaliacore.utils.files.config.*;
import fr.hamunaptra_.oxaliacore.utils.placeholders.*;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

import java.util.logging.Level;

public class EventManager {

    public static void Enable() {
        long start = System.currentTimeMillis();

        Bukkit.getLogger().log(Level.INFO, "---------------[OxaliaCore]---------------");
        Bukkit.getLogger().log(Level.INFO, "");
        Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Version : " + Main.getInstance().getDescription().getVersion());
        Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Author : " + Main.getInstance().getDescription().getAuthors());
        Bukkit.getLogger().log(Level.INFO, "");
        Bukkit.getLogger().log(Level.INFO, "---------------[OxaliaCore]---------------");
        Bukkit.getLogger().log(Level.INFO, "");

        Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Register PlaceHolderAPI integration.");
        new PlaceHolderAPI().register();
        
        Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Loading OxaliaCore/config.yml configuration file.");
        Config config = new Config();
        config.copy();

        if (Config.getBoolean("Config.Module.Bank")) {
            Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Loading OxaliaCore/configs/bank.yml configuration file.");
            Bank bank = new Bank();
            Main.getInstance().saveResource("bank.yml", false);
            bank.copy();
        }

        if (Config.getBoolean("Config.Module.Bar")) {
            Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Loading OxaliaCore/configs/bar.yml configuration file.");
            Bar bar = new Bar();
            Main.getInstance().saveResource("bar.yml", false);
            bar.copy();
        }

        if (Config.getBoolean("Config.Module.CItems")) {
            Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Loading OxaliaCore/configs/citems.yml configuration file.");
            CItems cItems = new CItems();
            Main.getInstance().saveResource("citems.yml", false);
            cItems.copy();
        }



        /*
            Announce Module setup.
         */

        if (Config.getBoolean("Config.Module.Announce")) {
            Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Enabling Announce module.");
            AnnounceTask.run();
        }

        /*
            Bank Module setup.
         */

        if (Config.getBoolean("Config.Module.Bank")) {
            Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Enabling Bank module.");
            Rc("bank", new BankCommand());
            Rl(new BankListener());
            Rl(new BankGuis());
            BankInterest.run();
        }

        /*
            Bar Module setup.
         */

        if (Config.getBoolean("Config.Module.Bar")) {
            Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Enabling Bar module.");
            Rc("bar", new BarCommand());
            Rl(new BarListener());
            Rl(new BarBuy());
        }

        /*
            CItems Module setup.
         */

        if (Config.getBoolean("Config.Module.CItems")) {
            Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Enabling CustomItems module.");
            Rc("citems", new ItemCommand());
            Rl(new ItemListener());
        }

        /*
            Logs Module setup.
         */

        if (Config.getBoolean("Config.Module.Logs")) {
            Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Enabling Log module.");
            Rl(new LogsListener());
            Rc("deletelog", new LogCommand());
            Rc("viewlog", new LogCommand());
        }

        /*
            Shulker Module setup.
         */

        if (Config.getBoolean("Config.Module.Shulker")) {
            Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Enabling Shulker module.");
            Rl(new ShulkerListener());
        }

        /*
            Shulker Module setup.
         */

        if (Config.getBoolean("Config.Module.TimedTask")) {
            Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Enabling TimedTask module.");
            TimedTask.run();
        }

        Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Loading commands.");

        Rc("discord", new MainCommand());
        Rc("furnace", new MainCommand());
        Rc("link", new MainCommand());
        Rc("site", new MainCommand());
        Rc("store", new MainCommand());
        Rc("votes", new MainCommand());

        Rc("oxaliacore", new AdminCommand());
        Rc("clearreloadall", new AdminCommand());
        Rc("chatclear", new AdminCommand());


        long end = System.currentTimeMillis();
        long time = end - start;

        Bukkit.getLogger().log(Level.INFO, "");
        Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Loaded in " + time + " ms.");

        Bukkit.getLogger().log(Level.INFO, "---------------[OxaliaCore]---------------");

    }

    private static void Rl(Listener l) {
        Bukkit.getPluginManager().registerEvents(l, Main.getInstance());
    }
    private static void Rc(String cmd, CommandExecutor exe) {
        Main.getInstance().getCommand(cmd).setExecutor(exe);
    }
}