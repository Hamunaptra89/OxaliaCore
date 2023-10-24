    package fr.hamunaptra_.immersicore;

    import fr.hamunaptra_.immersicore.utils.*;
    import fr.hamunaptra_.immersicore.utils.api.config.*;
    import fr.hamunaptra_.immersicore.utils.api.placeholders.*;

    import net.milkbowl.vault.economy.Economy;

    import org.bukkit.plugin.RegisteredServiceProvider;
    import org.bukkit.plugin.java.JavaPlugin;

    public class Main extends JavaPlugin {
        public static Economy economy;
        public static Economy getEconomy() {
            return economy;
        }

        private ConfigManager Config = ConfigManager.getInstance();

        private static Main main;
        public static Main getInstance() {
            return main;
        }

        @Override
        public void onEnable() {
            main = this;

            if (!setupEconomy()) {
                getLogger().severe("Vault or an economy plugin is missing!");
                getServer().getPluginManager().disablePlugin(this);
                return;
            }

            new PlaceHolderAPI().register();

            Config.setup(this);

            Bank bank = new Bank();
            saveResource("bank.yml", false);
            bank.copy();

            Bar bar = new Bar();
            saveResource("bar.yml", false);
            bar.copy();

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

            economy = rsp.getProvider();
            return true;
        }
    }
