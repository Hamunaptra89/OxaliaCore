package fr.hamunaptra_.oxaliacore.addon.announce;

import fr.hamunaptra_.oxaliacore.Main;
import fr.hamunaptra_.oxaliacore.utils.files.config.*;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TimedTask {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public static void run() {
        Bukkit.getServer().getScheduler().runTaskTimer(Main.getInstance(), () -> {

            ConfigurationSection runTaskSection = Config.getConfigurationSection("Config.RunTask");
            if (runTaskSection != null) {
                Map<String, Object> events = runTaskSection.getValues(false);
                String currentTime = dateFormat.format(new Date());

                for (String name : events.keySet()) {
                    String path = "Config.RunTask." + name;
                    if (Config.isString(path + ".Time")) {
                        String time = Config.getString(path + ".Time");

                        if (time.equals(currentTime)) {
                            List<String> commands = Config.getStringList(path + ".Commands");
                            for (String command : commands) {
                                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
                            }
                        }
                    }
                }
            }
        }, 0, 20L);
    }
}
