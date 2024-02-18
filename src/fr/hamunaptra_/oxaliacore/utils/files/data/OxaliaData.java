package fr.hamunaptra_.oxaliacore.utils.files.data;

import fr.hamunaptra_.oxaliacore.Main;
import fr.hamunaptra_.oxaliacore.utils.files.config.*;

import org.bukkit.configuration.file.*;
import org.bukkit.entity.Player;

import java.io.*;

public class OxaliaData {

    private final FileConfiguration data;
    private final File file;

    public OxaliaData(Player p) {
        this.file = new File(Main.getInstance().getDataFolder() + "/data" + "/bank", p.getName() + ".yml");
        this.data = YamlConfiguration.loadConfiguration(file);

        if (!this.file.exists()) {
            data.set("Bank.Balance", 0);
            data.set("Bank.Cooldown", Bank.getInt("Bank.Interest.Time"));
            save();
        }
    }

    public void save() {
        try {
            data.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getBalance() {
        return data.getDouble("Bank.Balance");
    }
    public void setBalance(double amount) {
        data.set("Bank.Balance", amount);
        save();
    }

    public void deposit(double amount) {
        data.set("Bank.Balance", getBalance() + amount);
        save();
    }
    public void withdraw(double amount) {
        data.set("Bank.Balance", getBalance() - amount);
        save();
    }

    public int getInterestTime() {
        return data.getInt("Bank.Cooldown");
    }
    public void setInterestTime(int time) {
        data.set("Bank.Cooldown", time);
        save();
    }

    public void removeInterestTime(int time) {
        data.set("Bank.Cooldown", getInterestTime() - time);
        save();
    }
}
