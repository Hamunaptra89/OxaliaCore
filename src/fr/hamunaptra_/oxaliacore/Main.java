package fr.hamunaptra_.oxaliacore;

import fr.hamunaptra_.oxaliacore.utils.placeholders.*;

import net.milkbowl.vault.economy.Economy;

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

        if (!setupEconomy()) {
            getLogger().severe("Vault or an economy plugin is missing!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        new PlaceHolderAPI().register();

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