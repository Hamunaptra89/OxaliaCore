package fr.hamunaptra_.oxaliacore.utils.api.chat;

import fr.hamunaptra_.oxaliacore.Main;
import fr.hamunaptra_.oxaliacore.utils.ConfigManager;
import fr.hamunaptra_.oxaliacore.utils.api.config.*;
import fr.hamunaptra_.oxaliacore.utils.api.data.*;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Color {

    static ConfigManager Config = ConfigManager.getInstance();
    private final Player p;

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
                .replaceAll("%oxalia_bank_balance%", String.format("%,2f", Data.getBalance()))
                .replaceAll("%oxalia_bank_interest_money%", String.format("%,2f", Data.getBalance() * Bank.getDouble("Bank.Interest.Percent")))
                .replaceAll("%oxalia_bank_interest_time%", Bank.getString("Bank.PlaceHolders.Interest.Time")
                        .replace("%hour", String.valueOf(hour))
                        .replace("%min", String.valueOf(min))
                        .replace("%sec", String.valueOf(sec))
                )
                .replaceAll("%oxalia_bank_deposit_25%", String.format("%.2f", Main.economy.getBalance(p) / 4))
                .replaceAll("%oxalia_bank_deposit_50%", String.format("%.2f", Main.economy.getBalance(p) / 2))
                .replaceAll("%oxalia_bank_deposit_100%", String.format("%.2f", Main.economy.getBalance(p)))
                .replaceAll("%oxalia_bank_withdraw_25%", String.format("%.2f", Data.getBalance() / 4))
                .replaceAll("%oxalia_bank_withdraw_50%", String.format("%.2f", Data.getBalance() / 2))
                .replaceAll("%oxalia_bank_withdraw_100%", String.format("%.2f", Data.getBalance())));
    }

    public List<String> set(List<String> s) {
        List<String> lores = new ArrayList<>();
        DataManager Data = new DataManager(p);

        int lastInterest = Data.getInterestTime();
        int hour = lastInterest % 3600;
        int min = (lastInterest % 3600) / 60;
        int sec = lastInterest % 60;

        for (String line : s) {
            lores.add(ChatColor.translateAlternateColorCodes('&', line
                    .replaceAll("%oxalia_bank_balance%", String.format("%.2f", Data.getBalance()))
                    .replaceAll("%oxalia_bank_interest_money%", String.format("%.2f", Data.getBalance() * Bank.getDouble("Bank.Interest.Percent")))
                    .replaceAll("%oxalia_bank_interest_time%", Bank.getString("Bank.PlaceHolders.Interest.Time")
                            .replace("%hour", String.valueOf(hour))
                            .replace("%min", String.valueOf(min))
                            .replace("%sec", String.valueOf(sec))
                    )
                    .replaceAll("%oxalia_bank_deposit_25%", String.format("%.2f", Main.economy.getBalance(p) / 4))
                    .replaceAll("%oxalia_bank_deposit_50%", String.format("%.2f", Main.economy.getBalance(p) / 2))
                    .replaceAll("%oxalia_bank_deposit_100%", String.format("%.2f", Main.economy.getBalance(p)))
                    .replaceAll("%oxalia_bank_withdraw_25%", String.format("%.2f", Data.getBalance() / 4))
                    .replaceAll("%oxalia_bank_withdraw_50%", String.format("%.2f", Data.getBalance() / 2))
                    .replaceAll("%oxalia_bank_withdraw_100%", String.format("%.2f", Data.getBalance()))));
        }
        return lores;
    }

    public void formatted(Player p, String path) {
        String s = Config.getConfig().getString(path);
        if (s != null) {
            String[] lignes = s.split("\n");
            for (String ligne : lignes) {
                String ligneNettoyee = ligne.replaceAll("[']+", "").trim();
                p.sendMessage(set(ligneNettoyee));
            }
        }
    }
}
