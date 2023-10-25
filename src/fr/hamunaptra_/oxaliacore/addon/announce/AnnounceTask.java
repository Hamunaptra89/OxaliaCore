package fr.hamunaptra_.oxaliacore.addon.announce;

import fr.hamunaptra_.oxaliacore.Main;
import fr.hamunaptra_.oxaliacore.utils.ConfigManager;
import fr.hamunaptra_.oxaliacore.utils.api.chat.Color;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AnnounceTask {

    static ConfigManager Config = ConfigManager.getInstance();
    private static int messages = 0;
    static String path = "Config.Announcer.";

    public static void run() {
        Bukkit.getServer().getScheduler().runTaskTimer(Main.getInstance(), new Runnable() {
            public void run() {
                if (Config.getConfig().getBoolean(path + "Enable")) {
                    if (Bukkit.getOnlinePlayers().isEmpty()) {
                        return;
                    }

                    for (Player p : Bukkit.getOnlinePlayers()) {
                        Color Color = new Color(p);

                        String message = Config.getConfig().getStringList(path + "Messages").get(messages);
                        String formattedMessage = Color.set(message);
                        p.sendMessage(formattedMessage);
                    }

                    messages++;

                    if (messages >= Config.getConfig().getStringList(path + "Messages").size()) {
                        messages = 0;
                    }
                }
            }
        }, 0, Config.getConfig().getInt("Config.Announcer.Interval") * 20L);
    }
}
