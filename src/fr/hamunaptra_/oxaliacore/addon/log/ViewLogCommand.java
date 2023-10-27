package fr.hamunaptra_.oxaliacore.addon.log;

import fr.hamunaptra_.oxaliacore.Main;
import fr.hamunaptra_.oxaliacore.utils.api.chat.Color;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ViewLogCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        Color Color = new Color((Player) s);
        if (!s.hasPermission("OxaliaCore.Admin")) {
            s.sendMessage("Vous n'avez pas la permission.");
            return true;
        }

        if (!cmd.getName().equalsIgnoreCase("viewlog")) {
            return false;
        }

        File file = new File(Main.getInstance().getDataFolder(), "log/block.yml");

        if (!file.exists()) {
            s.sendMessage("Le fichier 'log/block.yml' n'existe pas.");
            return true;
        }

        List<String> logData = loadLogData(file);
        int page = (args.length > 0) ? Math.max(1, Math.min(Integer.parseInt(args[0]), (int) Math.ceil((double) logData.size() / 15))) : 1;

        int startIndex = (page - 1) * 15;
        int endIndex = Math.min(startIndex + 15, logData.size());

        s.sendMessage(Color.set("&8&m                              " + "&8(&a&lLog &e&l" + page + "&7&l/&e&l" + (int) Math.ceil((double) logData.size() / 15) + "&r&8)&8&m                              "));

        for (int i = startIndex; i < endIndex; i++) {
            s.sendMessage(logData.get(i));
        }

        s.sendMessage(Color.set("&8&m                                                                           "));

        return true;
    }

    private List<String> loadLogData(File logFile) {
        List<String> logData = new ArrayList();

        try (Scanner scanner = new Scanner(logFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                ChatColor color = line.matches("\\d{2}/\\d{2}/\\d{4}, \\d{2}:\\d{2}:\\d{2}:.*")
                        ? ChatColor.YELLOW : ChatColor.GRAY;

                logData.add(color + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return logData;
    }

}
