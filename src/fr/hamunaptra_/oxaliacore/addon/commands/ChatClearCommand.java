package fr.hamunaptra_.oxaliacore.addon.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class ChatClearCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String string, String[] args) {
        Player p = (Player)s;

        if (p.hasPermission("OxaliaCore.Admin")) {
            for (int i = 0; i < 250; ++i) {
                Bukkit.broadcastMessage(" ");
            }
        }
        return false;
    }
}