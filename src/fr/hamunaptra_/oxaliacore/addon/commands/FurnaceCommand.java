package fr.hamunaptra_.oxaliacore.addon.commands;

import fr.hamunaptra_.oxaliacore.utils.api.items.Items;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FurnaceCommand implements CommandExecutor {

    private static final Map<Material, Material> smelt = new HashMap<>();

    static {
        smelt.put(Material.COAL_ORE, Material.COAL);
        smelt.put(Material.DEEPSLATE_COAL_ORE, Material.COAL);
        smelt.put(Material.IRON_ORE, Material.IRON_INGOT);
        smelt.put(Material.DEEPSLATE_IRON_ORE, Material.IRON_INGOT);
        smelt.put(Material.COPPER_ORE, Material.COPPER_INGOT);
        smelt.put(Material.DEEPSLATE_COPPER_ORE, Material.COPPER_INGOT);
        smelt.put(Material.GOLD_ORE, Material.GOLD_INGOT);
        smelt.put(Material.DEEPSLATE_GOLD_ORE, Material.GOLD_INGOT);
        smelt.put(Material.REDSTONE_ORE, Material.REDSTONE);
        smelt.put(Material.DEEPSLATE_REDSTONE_ORE, Material.REDSTONE);
        smelt.put(Material.EMERALD_ORE, Material.EMERALD);
        smelt.put(Material.DEEPSLATE_EMERALD_ORE, Material.EMERALD);
        smelt.put(Material.LAPIS_ORE, Material.LAPIS_LAZULI);
        smelt.put(Material.DEEPSLATE_LAPIS_ORE, Material.LAPIS_LAZULI);
        smelt.put(Material.DIAMOND_ORE, Material.DIAMOND);
        smelt.put(Material.DEEPSLATE_DIAMOND_ORE, Material.DIAMOND);
        smelt.put(Material.NETHER_GOLD_ORE, Material.GOLD_INGOT);
        smelt.put(Material.NETHER_QUARTZ_ORE, Material.QUARTZ);
        smelt.put(Material.ANCIENT_DEBRIS, Material.NETHERITE_SCRAP);
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String str, String[] args) {
        if (!(s instanceof Player)) {
            s.sendMessage("Cette commande ne peut être utilisée que par un joueur.");
            return true;
        }
        Player p = (Player) s;

        for (ItemStack itemStack : p.getInventory().getContents()) {
            if (itemStack != null) {
                Material material = itemStack.getType();

                if (smelt.containsKey(material)) {
                    int itemCount = itemStack.getAmount();
                    Material ingotMaterial = smelt.get(material);
                    ItemStack smeltingResult = new ItemStack(ingotMaterial, itemCount);

                    p.getInventory().removeItem(itemStack);
                    p.getInventory().addItem(smeltingResult);
                }
            }
        }

        p.sendMessage("Tous les minerais et lingots ont été cuits dans le fourneau.");
        return true;
    }
}


