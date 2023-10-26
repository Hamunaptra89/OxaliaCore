package fr.hamunaptra_.oxaliacore.addon.log;

import fr.hamunaptra_.oxaliacore.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.File;

public class ClearLogCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("OxaliaCore.Admin")) {
            if (cmd.getName().equalsIgnoreCase("deleteLog")) {
                File logDirectory = new File(Main.getInstance().getDataFolder(), "log");
                File file1 = new File(logDirectory, "block.yml");

                if (file1.exists()) {
                    if (file1.delete()) {
                        sender.sendMessage("Fichier 'log/block.yml' supprimé avec succès.");

                        if (logDirectory.isDirectory() && logDirectory.list().length == 0) {
                            if (logDirectory.delete()) {
                                sender.sendMessage("Répertoire 'log' vidé et supprimé avec succès.");
                            } else {
                                sender.sendMessage("Impossible de supprimer le répertoire 'log'.");
                            }
                        }
                    } else {
                        sender.sendMessage("Impossible de supprimer le fichier 'log/block.yml'.");
                    }
                } else {
                    sender.sendMessage("Le fichier 'log/block.yml' n'existe pas.");
                }

                return true;
            }
        } else {
            sender.sendMessage("Vosu n'avez pas la permission");
        }
        return false;
    }
}
