package fr.hamunaptra_.oxaliacore.utils.files.config;

import fr.hamunaptra_.oxaliacore.Main;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.*;

public class Bank {

    private final File file;
    private static YamlConfiguration bank;

    public Bank() {
        this.file = new File(Main.getInstance().getDataFolder() + "/configs", "bank.yml");
        bank = YamlConfiguration.loadConfiguration(this.file);
    }

    public void copy() {
        File sourceFile = new File(Main.getInstance().getDataFolder(), "bank.yml");
        File targetFile = new File(Main.getInstance().getDataFolder() + "/configs", "bank.yml");

        if (!targetFile.exists()) {
            try {
                FileUtils.copyFile(sourceFile, targetFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        sourceFile.delete();
        bank = YamlConfiguration.loadConfiguration(targetFile);
    }

    public void reload() {
        bank = YamlConfiguration.loadConfiguration(this.file);
    }

    public static boolean getBoolean(String s) {
        return bank.getBoolean(s);
    }

    public static String getString(String path) {
        return bank.getString(path);
    }

    public static List<String> getStringList(String path) {
        return bank.getStringList(path);
    }

    public static int getInt(String path) {
        return bank.getInt(path);
    }

    public static ConfigurationSection getConfigurationSection(String path) {
        return bank.getConfigurationSection(path);
    }

    public static double getDouble(String path) {
        return bank.getDouble(path);
    }
}
