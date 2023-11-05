package fr.hamunaptra_.oxaliacore.utils.files.data;

import fr.hamunaptra_.oxaliacore.Main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class OxaliaLog {

    private FileConfiguration data;
    private File file;

    public OxaliaLog(Player p) {
        this.file = new File(Main.getInstance().getDataFolder() + "/log", p.getName() + ".yml");
        this.data = YamlConfiguration.loadConfiguration(file);

        if (!file.exists()) {
            this.data = new YamlConfiguration();
            save();
        } else {
            this.data = YamlConfiguration.loadConfiguration(this.file);
        }
    }

    public void save() {
        try {
            data.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void set(String key, Object value) {
        data.set(key, value);
        save(); 
    }
}
