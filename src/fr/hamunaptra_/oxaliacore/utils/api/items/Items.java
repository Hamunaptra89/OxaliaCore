package fr.hamunaptra_.oxaliacore.utils.api.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.*;

import java.util.Arrays;
import java.util.List;

public class Items {
    private ItemStack i;

    public Items(Material m, int... quantities) {
        if (quantities.length == 0) {
            this.i = new ItemStack(m);
        } else {
            int quantity = Math.max(1, quantities[0]);
            this.i = new ItemStack(m, quantity);

            if (quantities.length > 1) {
                i.setAmount(Math.min(quantity, 64));
            }
        }
    }


    public Items setName(String displayName) {
        ItemMeta m = i.getItemMeta();
        m.setDisplayName(displayName);
        i.setItemMeta(m);
        return this;
    }

    public Items setFlag(ItemFlag itemFlag) {
        ItemMeta m = i.getItemMeta();
        m.addItemFlags(itemFlag);
        i.setItemMeta(m);
        return this;
    }

    public Items setPotion(PotionType type, boolean isSplash) {
        PotionMeta m = (PotionMeta) i.getItemMeta();
        PotionData d = new PotionData(type, isSplash, false);

        m.setBasePotionData(d);
        i.setItemMeta(m);

        return this;
    }

    public Items setLore(List<String> lore) {
        ItemMeta meta = i.getItemMeta();
        meta.setLore(lore);
        i.setItemMeta(meta);
        return this;
    }

    public ItemStack im() {
        return i;
    }
}