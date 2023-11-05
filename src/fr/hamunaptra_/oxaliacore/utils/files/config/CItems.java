package fr.hamunaptra_.oxaliacore.utils.files.config;

import fr.hamunaptra_.oxaliacore.Main;

import org.bukkit.configuration.file.YamlConfiguration;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CItems {

    private final File file;
    private static YamlConfiguration citems;

    public CItems() {
        this.file = new File(Main.getInstance().getDataFolder() + "/configs", "citems.yml");
        citems = YamlConfiguration.loadConfiguration(this.file);
    }

    public boolean exists() {
        return this.file.exists();
    }

    public void copy() {
        File sourceFile = new File(Main.getInstance().getDataFolder(), "citems.yml");
        File targetFile = new File(Main.getInstance().getDataFolder() + "/configs", "citems.yml");

        if (!targetFile.exists()) {
            try {
                FileUtils.copyFile(sourceFile, targetFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        sourceFile.delete();
        citems = YamlConfiguration.loadConfiguration(targetFile);
    }

    public void reload() {
        citems = YamlConfiguration.loadConfiguration(this.file);
    }

    public static String getString(String s) {
        return citems.getString(s);
    }

    public static List<String> getStringList(String s) {
        return citems.getStringList(s);
    }

    public static boolean getBoolean(String s) {
        return citems.getBoolean(s);
    }

    public static int getInt(String s) {
        return citems.getInt(s);
    }
}
