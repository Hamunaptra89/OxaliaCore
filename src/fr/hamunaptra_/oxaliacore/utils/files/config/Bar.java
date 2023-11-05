package fr.hamunaptra_.oxaliacore.utils.files.config;

import fr.hamunaptra_.oxaliacore.Main;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Bar {

    private final File file;
    private static YamlConfiguration bar;

    public Bar() {
        this.file = new File(Main.getInstance().getDataFolder() + "/configs", "bar.yml");
        bar = YamlConfiguration.loadConfiguration(this.file);
    }

    public boolean exists() {
        if (this.file.exists()) {
            return true;
        }
        return false;
    }

    public void copy() {
        File sourceFile = new File(Main.getInstance().getDataFolder(), "bar.yml");
        File targetFile = new File(Main.getInstance().getDataFolder() + "/configs", "bar.yml");

        if (!targetFile.exists()) {
            try {
                FileUtils.copyFile(sourceFile, targetFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        sourceFile.delete();
        bar = YamlConfiguration.loadConfiguration(targetFile);
    }

    public void reload() {
        bar = YamlConfiguration.loadConfiguration(this.file);
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
