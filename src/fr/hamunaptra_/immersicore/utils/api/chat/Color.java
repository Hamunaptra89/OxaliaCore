package fr.hamunaptra_.immersicore.utils.api.chat;

import fr.hamunaptra_.immersicore.utils.api.data.DataManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Color {

    private Player p;

    public Color(Player p) {
        this.p = p;
    }

    public String set(String s) {
        DataManager Data = new DataManager(p);
        return ChatColor.translateAlternateColorCodes('&', s
                .replaceAll("%immersicore_bank_balance%", String.valueOf(Data.getBalance())));
    }


    public List<String> set(List<String> lore) {
        DataManager Data = new DataManager(p);
        List<String> lores = new ArrayList<>();

        for (String line : lore) {
            lores.add(ChatColor.translateAlternateColorCodes('&', line
                    .replaceAll("%immersicore_bank_balance%", String.valueOf(Data.getBalance()))));
        }

        return lores;
    }
}
