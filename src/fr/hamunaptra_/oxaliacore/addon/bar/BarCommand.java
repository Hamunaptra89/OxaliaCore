package fr.hamunaptra_.oxaliacore.addon.bar;

import fr.hamunaptra_.oxaliacore.utils.chat.*;
import fr.hamunaptra_.oxaliacore.utils.files.config.*;
import fr.hamunaptra_.oxaliacore.utils.items.*;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.potion.PotionType;

public class BarCommand implements CommandExecutor {
    String inv_path = "Bar.Inv.";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            return false;
        }

        if (p.hasPermission(Bar.getString(inv_path + "Permission"))) {
            Inventory inv = Bukkit.createInventory(null, Bar.getInt(inv_path + "Slot"), Color.set(Bar.getString(inv_path + "Name")));

            ConfigurationSection s = Bar.getConfigurationSection("Bar.Item");

            if (s != null) {
                for (String key : s.getKeys(false)) {
                    String path = "Bar.Item." + key;
                    if (Bar.getInt(path + ".Slot") >= 0 && Bar.getInt(path + ".Slot") < Bar.getInt("Bar.Inv.Slot")) {
                        Items item = new Items(Material.getMaterial(Bar.getString(path + ".Material")))
                                .setName(Color.set(Bar.getString(path + ".Name")))
                                .setLore(Color.set(Bar.getStringList(path + ".Lore")));

                        if (Bar.isString(path + ".Type")) {
                            String type = Bar.getString(path + ".Type");
                            try {
                                PotionType potionType = PotionType.valueOf(type);
                                item.setPotion(potionType, false);
                                item.setFlag(ItemFlag.HIDE_POTION_EFFECTS);
                            } catch (IllegalArgumentException e) {
                            }
                        }
                        inv.setItem(Bar.getInt(path + ".Slot"), item.im());
                        p.openInventory(inv);
                    }
                }
            }
        } else {
            p.sendMessage(Color.set(Bar.getString("Bar.Message.NoPerm")));
        }
        return true;
    }
}