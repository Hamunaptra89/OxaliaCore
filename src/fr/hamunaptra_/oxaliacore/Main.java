package fr.hamunaptra_.oxaliacore;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Main extends JavaPlugin {
    public static Main instance;
    public static Main getInstance() {
        return instance;
    }
    public static Economy eco;

    @Override
    public void onEnable() {
        instance = this;

        if (!getDescription().getAuthors().contains("Halbrand__")) {
            Bukkit.getConsoleSender().sendMessage("§cThe original author name is Halbrand__ (Plugin name: OxaliaCore)");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        if (!getDescription().getName().equals("OxaliaCore")) {
            Bukkit.getConsoleSender().sendMessage("§cThe original plugin name is OxaliaCore (Author name: Halbrand__)");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        if (!setupEconomy()) {
            getLogger().severe("Vault or an economy plugin is missing!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        EventManager.Enable();
    }

    @Override
    public void onDisable() {
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }

        eco = rsp.getProvider();
        return true;
    }
}