package fr.hamunaptra_.oxaliacore.utils.api.chat;

import fr.hamunaptra_.oxaliacore.utils.api.config.*;
import fr.hamunaptra_.oxaliacore.utils.api.data.*;

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

        int lastInterest = Data.getInterestTime();
        int hour = lastInterest % 3600;
        int min = (lastInterest % 3600) / 60;
        int sec = lastInterest % 60;

        return ChatColor.translateAlternateColorCodes('&', s
                .replaceAll("%oxalia_bank_balance%", String.valueOf(Data.getBalance()))
                .replaceAll("%oxalia_bank_interest_money%", String.valueOf(Data.getBalance() * Bank.getDouble("Bank.Interest.Percent")))
                .replaceAll("%oxalia__bank_interest_time_%", Bank.getString("Bank.PlaceHolders.Interest.Time")
                        .replace("%hour", String.valueOf(hour))
                        .replace("%min", String.valueOf(min))
                        .replace("%sec", String.valueOf(sec))));
    }


    public List<String> set(List<String> lore) {
        DataManager Data = new DataManager(p);
        List<String> lores = new ArrayList<>();

        for (String line : lore) {
            lores.add(ChatColor.translateAlternateColorCodes('&', line
                    .replaceAll("%oxalia_bank_balance%", String.valueOf(Data.getBalance()))));
        }

        return lores;
    }
}
