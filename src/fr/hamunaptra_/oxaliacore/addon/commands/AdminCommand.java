package fr.hamunaptra_.oxaliacore.addon.commands;

import fr.hamunaptra_.oxaliacore.utils.chat.*;
import fr.hamunaptra_.oxaliacore.utils.files.config.*;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class AdminCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            return false;
        }

        if (cmd.getName().equalsIgnoreCase("clearreloadall")) {
            if (p.hasPermission("OxaliaCore.Admin")) {
                p.getServer().dispatchCommand(p, "plugman reload ClearLag");
                p.getServer().dispatchCommand(p, "plugman reload ClearLagTimer");
            }
            return false;
        }

        if (cmd.getName().equalsIgnoreCase("chatclear")) {
            if (p.hasPermission("OxaliaCore.Admin")) {
                for (int i = 0; i < 250; ++i) {
                    Bukkit.broadcastMessage(" ");
                }
            }
            return false;
        }

        if (cmd.getName().equalsIgnoreCase("oxaliacore")) {
            if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
                if (p.hasPermission("OxaliaCore.Admin")) {
                    p.sendMessage(Color.set(Config.getString("Config.Messages.Reload")));

                    long start = System.currentTimeMillis();

                    Bukkit.getLogger().log(Level.INFO, "---------------[OxaliaCore]---------------");
                    Bukkit.getLogger().log(Level.INFO, "");
                    Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Reloading OxaliaCore/config.yml configuration file.");
                    Config config = new Config();
                    config.reload();
                    Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Reloading OxaliaCore/configs/bank.yml configuration file.");
                    Bank bank = new Bank();
                    bank.reload();
                    Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Reloading OxaliaCore/configs/bar.yml configuration file.");
                    Bar bar = new Bar();
                    bar.reload();
                    Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Reloading OxaliaCore/configs/citems.yml configuration file.");
                    CItems cItems = new CItems();
                    cItems.reload();
                    Bukkit.getLogger().log(Level.INFO, "");

                    long end = System.currentTimeMillis();
                    long time = end - start;

                    Bukkit.getLogger().log(Level.INFO, "[OxaliaCore] Loaded in " + time + " ms.");

                    Bukkit.getLogger().log(Level.INFO, "---------------[OxaliaCore]---------------");
                }
            }
            return true;

        }
        return true;
    }
}
