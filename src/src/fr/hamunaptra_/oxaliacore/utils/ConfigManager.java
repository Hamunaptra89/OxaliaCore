package fr.hamunaptra_.oxaliacore.utils;

import fr.hamunaptra_.oxaliacore.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    static ConfigManager instance = new ConfigManager();

    Plugin p;

    FileConfiguration config;

    File file;

    public static ConfigManager getInstance() {
        return instance;
    }

    public void setup(Plugin p) {
        config = p.getConfig();
        config.options().copyDefaults(true);
        file = new File(Main.getInstance().getDataFolder(), "config.yml");
        save();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.DARK_GRAY + "Could not save config.yml");
        }
    }
}