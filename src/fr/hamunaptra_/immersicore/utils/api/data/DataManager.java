package fr.hamunaptra_.immersicore.utils.api.data;

import fr.hamunaptra_.immersicore.Main;
import fr.hamunaptra_.immersicore.utils.api.config.Bank;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class DataManager {
    private FileConfiguration data;
    private File file;

    public DataManager(Player p) {
        this.file = new File(Main.getInstance().getDataFolder() + "/data", p.getName() + ".yml");
        this.data = YamlConfiguration.loadConfiguration(file);

        if (!file.exists()) {
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
        double balance = data.getDouble("Bank.Balance");
        data.set("Bank.Balance", balance + amount);
        save();
    }

    public void withdraw(double amount) {
        double balance = data.getDouble("Bank.Balance");
        data.set("Bank.Balance", balance - amount);
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
