package fr.hamunaptra_.oxaliacore.utils.api.config;

import fr.hamunaptra_.oxaliacore.Main;

import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CItems {

    private File file;
    private static YamlConfiguration citems;

    public CItems() {
        this.file = new File(Main.getInstance().getDataFolder() + "/config", "citems.yml");
        citems = YamlConfiguration.loadConfiguration(this.file);
    }

    public boolean exists() {
        if (this.file.exists()) {
            return true;
        }
        return false;
    }

    public void copy() {
        File file1 = new File(Main.getInstance().getDataFolder(), "citems.yml");
        File file2 = new File(Main.getInstance().getDataFolder() + "/config", "citems.yml");
        if (!exists()) {
            file2.delete();
            try {
                FileUtils.copyFile(file1, file2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        file1.delete();
        citems = YamlConfiguration.loadConfiguration(file2);
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
