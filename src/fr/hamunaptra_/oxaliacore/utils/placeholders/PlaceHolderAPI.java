package fr.hamunaptra_.oxaliacore.utils.placeholders;

import fr.hamunaptra_.oxaliacore.Main;
import fr.hamunaptra_.oxaliacore.utils.chat.*;
import fr.hamunaptra_.oxaliacore.utils.files.config.*;
import fr.hamunaptra_.oxaliacore.utils.files.data.*;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceHolderAPI extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "oxaliacore";
    }

    @Override
    public String getAuthor() {
        return Main.getInstance().getDescription().getAuthors().toString();
    }

    @Override
    public String getVersion() {
        return Main.getInstance().getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player p, @NotNull String s) {
        if (p == null) {
            return "";
        }

        OxaliaData data = new OxaliaData(p);

        if (s.equals("bank_balance")) {
            return String.valueOf(Decimal.format(data.getBalance()));
        }

        if (s.equals("bank_interest_time")) {
            long time = data.getInterestTime();
            long min = (time % 3600) / 60;
            long sec = time % 60;

            return (Color.set(Bank.getString("Bank.PlaceHolders.Interest.Time"))
                    .replace("%min", String.valueOf(min))
                    .replace("%sec", String.valueOf(sec)));
        }

        if (s.equals("bank_interest_amount")) {
            return String.valueOf(data.getBalance() * Bank.getDouble("Bank.Interest.Percent"));
        }

        if (s.startsWith("bank_deposit_")) {
            int percentage;

            percentage = Integer.parseInt(s.replace("bank_deposit_", ""));

            if (Main.eco.getBalance(p) >= 0) {
                return String.valueOf(Decimal.format((percentage * Main.eco.getBalance(p)) / 100));
            }
        }

        if (s.startsWith("bank_withdraw_")) {
            int percentage;

            percentage = Integer.parseInt(s.replace("bank_withdraw_", ""));

            if (data.getBalance() >= 0) {
                return String.valueOf(Decimal.format((percentage * data.getBalance()) / 100));
            }
        }

        return s;
    }
}
