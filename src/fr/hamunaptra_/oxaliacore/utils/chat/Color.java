package fr.hamunaptra_.oxaliacore.utils.chat;

import fr.hamunaptra_.oxaliacore.Main;
import fr.hamunaptra_.oxaliacore.utils.files.ConfigManager;
import fr.hamunaptra_.oxaliacore.utils.files.config.Bank;
import fr.hamunaptra_.oxaliacore.utils.files.data.BankData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Color {
    private final Player p;
    ConfigManager Config = ConfigManager.getInstance();

    public Color(Player p) {
        this.p = p;
    }

    public String set(String s) {
        BankData Data = new BankData(p);

        int time = Data.getInterestTime();
        int hour = time % 3600;
        int min = (time % 3600) / 60;
        int sec = time % 60;

        return ChatColor.translateAlternateColorCodes('&', s
                .replace("%oxalia_bank_balance%", Decimal.format(Data.getBalance()))
                .replace("%oxalia_bank_interest_money%", Decimal.format(Data.getBalance() * Bank.getDouble("Bank.Interest.Percent")))
                .replace("%oxalia_bank_interest_time%", Bank.getString("Bank.PlaceHolders.Interest.Time")
                        .replace("%hour", String.valueOf(hour))
                        .replace("%min", String.valueOf(min))
                        .replace("%sec", String.valueOf(sec))
                )
                .replace("%oxalia_bank_deposit_25%", Decimal.format(Main.eco.getBalance(p) / 4))
                .replace("%oxalia_bank_deposit_50%", Decimal.format(Main.eco.getBalance(p) / 2))
                .replace("%oxalia_bank_deposit_100%", Decimal.format(Main.eco.getBalance(p)))
                .replace("%oxalia_bank_withdraw_25%", Decimal.format(Data.getBalance() / 4))
                .replace("%oxalia_bank_withdraw_50%", Decimal.format(Data.getBalance() / 2))
                .replace("%oxalia_bank_withdraw_100%", Decimal.format(Data.getBalance())));
    }

    public List<String> set(List<String> s) {
        List<String> lores = new ArrayList<>();
        BankData Data = new BankData(p);

        int time = Data.getInterestTime();
        int hour = time % 3600;
        int min = (time % 3600) / 60;
        int sec = time % 60;

        for (String line : s) {
            lores.add(ChatColor.translateAlternateColorCodes('&', line
                    .replace("%oxalia_bank_balance%", Decimal.format(Data.getBalance()))
                    .replace("%oxalia_bank_interest_money%", Decimal.format(Data.getBalance() * Bank.getDouble("Bank.Interest.Percent")))
                    .replace("%oxalia_bank_interest_time%", Bank.getString("Bank.PlaceHolders.Interest.Time")
                            .replace("%hour", String.valueOf(hour))
                            .replace("%min", String.valueOf(min))
                            .replace("%sec", String.valueOf(sec))
                    )
                    .replace("%oxalia_bank_deposit_25%", Decimal.format(Main.eco.getBalance(p) / 4))
                    .replace("%oxalia_bank_deposit_50%", Decimal.format(Main.eco.getBalance(p) / 2))
                    .replace("%oxalia_bank_deposit_100%", Decimal.format(Main.eco.getBalance(p)))
                    .replace("%oxalia_bank_withdraw_25%", Decimal.format(Data.getBalance() / 4))
                    .replace("%oxalia_bank_withdraw_50%", Decimal.format(Data.getBalance() / 2))
                    .replace("%oxalia_bank_withdraw_100%", Decimal.format(Data.getBalance()))));
        }
        return lores;
    }

    public void formatted(Player p, String path) {
        String s = Config.getConfig().getString(path);
        if (s != null) {
            String[] lignes = s.split("\n");
            for (String ligne : lignes) {
                String clearedline = ligne.replaceAll("[']+", "").trim();
                p.sendMessage(set(clearedline));
            }
        }
    }
}
