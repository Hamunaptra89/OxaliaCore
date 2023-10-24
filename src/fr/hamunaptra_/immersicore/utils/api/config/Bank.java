package fr.hamunaptra_.immersicore.utils.api.config;

import fr.hamunaptra_.immersicore.Main;

import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Bank {

    private File file;
    private static YamlConfiguration bank;

    public Bank() {
        this.file = new File(Main.getInstance().getDataFolder() + "/config", "bank.yml");
        bank = YamlConfiguration.loadConfiguration(this.file);
    }

    public boolean exists() {
        if (this.file.exists()) {
            return true;
        }
        return false;
    }

    public void copy() {
        File file1 = new File(Main.getInstance().getDataFolder(), "bank.yml");
        File file2 = new File(Main.getInstance().getDataFolder() + "/config", "bank.yml");
        if (!exists()) {
            file2.delete();
            try {
                FileUtils.copyFile(file1, file2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        file1.delete();
        bank = YamlConfiguration.loadConfiguration(file2);
    }

    public static String getString(String path) {
        return bank.getString(path);
    }

    public static boolean getBoolean(String path) {
        return bank.getBoolean(path);
    }

    public static int getInt(String path) {
        return bank.getInt(path);
    }

    public static double getDouble(String path) {
        return bank.getDouble(path);
    }

    public static ConfigurationSection getConfigurationSection(String path) {
        return bank.getConfigurationSection(path);
    }

    public static List<String> getStringList(String path) {
        return bank.getStringList(path);
    }
}
