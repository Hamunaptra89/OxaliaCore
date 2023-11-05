package fr.hamunaptra_.oxaliacore.addon.commands;

import fr.hamunaptra_.oxaliacore.Main;
import fr.hamunaptra_.oxaliacore.utils.chat.Color;

import fr.hamunaptra_.oxaliacore.utils.files.config.Bank;
import fr.hamunaptra_.oxaliacore.utils.files.config.Bar;
import fr.hamunaptra_.oxaliacore.utils.files.config.CItems;
import fr.hamunaptra_.oxaliacore.utils.files.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String str, String[] args) {
        Player p = (Player)s;
        Color color = new Color(p);

        if (cmd.getName().equalsIgnoreCase("chatclear")) {
            if (p.hasPermission("OxaliaCore.Admin")) {
                for (int i = 0; i < 250; ++i) {
                    Bukkit.broadcastMessage(" ");
                }
            }
            return false;
        }

        if (cmd.getName().equalsIgnoreCase("discord")) {
            color.formatted(p, "Config.Commands.Discord");
            return false;
        }

        if (cmd.getName().equalsIgnoreCase("link")) {
            color.formatted(p, "Config.Commands.Link");
            return false;
        }

        if (cmd.getName().equalsIgnoreCase("site")) {
            color.formatted(p, "Config.Commands.Site");
            return false;
        }

        if (cmd.getName().equalsIgnoreCase("store")) {
            color.formatted(p, "Config.Commands.Store");
            return false;
        }

        if (cmd.getName().equalsIgnoreCase("votes")) {
            color.formatted(p, "Config.Commands.Vote");
            return false;
        }

        if (cmd.getName().equalsIgnoreCase("clearreloadall")) {
            if (p.hasPermission("OxaliaCore.Admin")) {
                p.getServer().dispatchCommand(s, "plugman reload ClearLag");
                p.getServer().dispatchCommand(s, "plugman reload ClearLagTimer");
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

        if (cmd.getName().equalsIgnoreCase("oxaliacorereload")) {
            if (p.hasPermission("OxaliaCore.Admin")) {
                p.sendMessage(color.set(Config.getString("Config.Messages.Reload")));

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
                Bukkit.getLogger().log(Level.INFO, "---------------[OxaliaCore]---------------");

            }
            return false;
        }

        return true;
    }
}
