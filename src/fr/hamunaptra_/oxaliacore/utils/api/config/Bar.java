package fr.hamunaptra_.oxaliacore.utils.api.config;

import fr.hamunaptra_.oxaliacore.Main;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Bar {

    private File file;
    private static YamlConfiguration bar;

    public Bar() {
        this.file = new File(Main.getInstance().getDataFolder() + "/config", "bar.yml");
        bar = YamlConfiguration.loadConfiguration(this.file);
    }

    public boolean exists() {
        if (this.file.exists()) {
            return true;
        }
        return false;
    }

    public void copy() {
        File file1 = new File(Main.getInstance().getDataFolder(), "bar.yml");
        File file2 = new File(Main.getInstance().getDataFolder() + "/config", "bar.yml");
        if (!exists()) {
            file2.delete();
            try {
                FileUtils.copyFile(file1, file2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        file1.delete();
        bar = YamlConfiguration.loadConfiguration(file2);
    }

    public static String getString(String path) {
        return bar.getString(path);
    }

    public static List<String> getStringList(String path) {
        return bar.getStringList(path);
    }

    public static int getInt(String path) {
        return bar.getInt(path);
    }

    public static ConfigurationSection getConfigurationSection(String path) {
        return bar.getConfigurationSection(path);
    }

    public static boolean isString(String s) {
        return bar.isString(s);
    }
}
