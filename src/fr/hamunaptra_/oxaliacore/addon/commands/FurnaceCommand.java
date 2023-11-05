package fr.hamunaptra_.oxaliacore.addon.commands;

import fr.hamunaptra_.oxaliacore.utils.chat.Color;
import fr.hamunaptra_.oxaliacore.utils.files.config.Config;
import fr.hamunaptra_.oxaliacore.utils.items.Items;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class FurnaceCommand implements CommandExecutor {

    private static final Map<Material, Material> smelt = new HashMap<>();
    String path = "Config.Commands.Furnace.";

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String str, String[] args) {
        Player p = (Player) s;
        Color Color = new Color(p);

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
            p.sendMessage(Color.set(Config.getString(path + "Success")));
        } else {
            p.sendMessage(Color.set(Config.getString(path + "NoMaterial")));
        }

        return true;
    }

    private void getSmeltedMaterial() {

        if (Config.isString(path + "Article")) {
            ConfigurationSection s = Config.getConfigurationSection(path + "Article");

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
