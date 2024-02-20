package fr.hamunaptra_.oxaliacore.addon.announce;

import fr.hamunaptra_.oxaliacore.Main;
import fr.hamunaptra_.oxaliacore.utils.chat.*;
import fr.hamunaptra_.oxaliacore.utils.files.config.*;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AnnounceTask {

    private static int messages = 0;
    static String path = "Config.Announcer.";

    public static void run() {
        Bukkit.getServer().getScheduler().runTaskTimer(Main.getInstance(), new Runnable() {
            public void run() {
                if (Bukkit.getOnlinePlayers().isEmpty()) {
                    return;
                }

                for (Player p : Bukkit.getOnlinePlayers()) {
                    String message = Config.getStringList(path + "Messages").get(messages);
                    String formattedMessage = Color.set(message);

                    p.sendMessage(formattedMessage);
                }

                messages++;

                if (messages >= Config.getStringList(path + "Messages").size()) {
                    messages = 0;
                }
            }
        }, 0, Config.getInt("Config.Announcer.Interval") * 20L);
    }
}
