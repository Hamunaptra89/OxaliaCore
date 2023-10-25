package fr.hamunaptra_.oxaliacore.addon.commands;

import fr.hamunaptra_.oxaliacore.utils.api.chat.Color;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String str, String[] args) {
        Player p = (Player)s;
        Color Color = new Color(p);

        if (cmd.getName().equalsIgnoreCase("store")) {
            Color.formatted(p, "Config.Commands.Store");
            return false;
        }

        if (cmd.getName().equalsIgnoreCase("site")) {
            Color.formatted(p, "Config.Commands.Site");
            return false;
        }

        if (cmd.getName().equalsIgnoreCase("discord")) {
            Color.formatted(p, "Config.Commands.Discord");
            return false;
        }

        if (cmd.getName().equalsIgnoreCase("clearreloadall")) {
            p.getServer().dispatchCommand(s, "plugman reload ClearLag");
            p.getServer().dispatchCommand(s, "plugman reload ClearLagTimer");
            return false;
        }

        return true;
    }
}
