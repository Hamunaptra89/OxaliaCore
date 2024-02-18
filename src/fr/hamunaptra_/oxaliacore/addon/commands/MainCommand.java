package fr.hamunaptra_.oxaliacore.addon.commands;

import fr.hamunaptra_.oxaliacore.utils.chat.*;
import fr.hamunaptra_.oxaliacore.utils.files.config.*;
import fr.hamunaptra_.oxaliacore.utils.items.*;

import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class MainCommand implements CommandExecutor {

    private static final Map<Material, Material> smelt = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            return false;
        }

        Color color = new Color(p);

        if (cmd.getName().equalsIgnoreCase("discord")) {
            color.formatted("Config.Commands.Discord");
            return false;
        }

        if (cmd.getName().equalsIgnoreCase("link")) {
            color.formatted("Config.Commands.Link");
            return false;
        }

        if (cmd.getName().equalsIgnoreCase("site")) {
            color.formatted("Config.Commands.Site");
            return false;
        }

        if (cmd.getName().equalsIgnoreCase("store")) {
            color.formatted("Config.Commands.Store");
            return false;
        }

        if (cmd.getName().equalsIgnoreCase("votes")) {
            color.formatted("Config.Commands.Vote");
            return false;
        }

        if (cmd.getName().equalsIgnoreCase("furnace")) {
            boolean ifissmelting = false;
            getSmeltedMaterial();

            for (ItemStack i : p.getInventory().getContents()) {
                if (i != null) {
                    Material material = i.getType();

                    if (smelt.containsKey(material)) {
                        Material smelted = smelt.get(material);

                        p.getInventory().removeItem(i);
                        p.getInventory().addItem(new Items(Material.getMaterial(String.valueOf(smelted)), i.getAmount()).im());

                        ifissmelting = true;
                    }
                }
            }

            if (ifissmelting) {
                p.sendMessage(color.set(Config.getString("Config.Commands.Furnace.Success")));
            } else {
                p.sendMessage(color.set(Config.getString("Config.Commands.Furnace.NoMaterial")));
            }

            return true;
        }

        return true;
    }

    private void getSmeltedMaterial() {
        if (Config.isString("Config.Commands.Furnace.Article")) {
            ConfigurationSection s = Config.getConfigurationSection("Config.Commands.Furnace.Article");

            for (String key : s.getKeys(false)) {
                Material base = Material.getMaterial(s.getString(key + ".Base"));
                Material result = Material.getMaterial(s.getString(key + ".Result"));

                if (base != null && result != null) {
                    smelt.put(base, result);
                }
            }
        }
    }
}
