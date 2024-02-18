package fr.hamunaptra_.oxaliacore.utils.files.config;

import fr.hamunaptra_.oxaliacore.Main;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.*;

public class Config {

    private File file;
    private static YamlConfiguration config;

    public Config() {
        this.file = new File(Main.getInstance().getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(this.file);
    }

    public boolean exists() {
        if (this.file.exists()) {
            return true;
        }
        return false;
    }

    public void copy() {
        File file1 = new File(Main.getInstance().getDataFolder(), "config.yml");
        if (!exists()) {
            file1.delete();
            try {
                FileUtils.copyInputStreamToFile(Main.getInstance().getResource("config.yml"), file1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file1);
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(this.file);
    }

    public static boolean getBoolean(String s) {
        return config.getBoolean(s);
    }

    public static String getString(String path) {
        return config.getString(path);
    }

    public static List<String> getStringList(String path) {
        return config.getStringList(path);
    }

    public static int getInt(String path) {
        return config.getInt(path);
    }

    public static ConfigurationSection getConfigurationSection(String path) {
        return config.getConfigurationSection(path);
    }

    public static boolean isString(String s) {
        return config.isString(s);
    }
}
