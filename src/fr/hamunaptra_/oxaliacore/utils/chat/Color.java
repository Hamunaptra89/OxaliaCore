package fr.hamunaptra_.oxaliacore.utils.chat;

import fr.hamunaptra_.oxaliacore.utils.files.config.*;

import me.clip.placeholderapi.PlaceholderAPI;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

public class Color {

    private static Player p;

    public Color(Player p) {
        Color.p = p;
    }

    public static String set(String str) {
        return PlaceholderAPI.setPlaceholders(p, ChatColor.translateAlternateColorCodes('&', str));
    }

    public static List<String> set(List<String> s) {
        List<String> lores = new ArrayList<>();

        for (String str : s) {
            lores.add(PlaceholderAPI.setPlaceholders(p, ChatColor.translateAlternateColorCodes('&', str)));
        }

        return lores;
    }

    public static void formatted(String path) {
        String s = Config.getString(path);
        if (s != null) {
            String[] lignes = s.split("\n");
            for (String ligne : lignes) {
                String clearedline = ligne.replaceAll("[']+", "").trim();
                p.sendMessage(set(clearedline));
            }
        }
    }
}
