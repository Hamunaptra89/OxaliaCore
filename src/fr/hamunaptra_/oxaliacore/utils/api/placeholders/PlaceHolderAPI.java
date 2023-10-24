package fr.hamunaptra_.oxaliacore.utils.api.placeholders;

import fr.hamunaptra_.oxaliacore.Main;
import fr.hamunaptra_.oxaliacore.utils.api.chat.*;
import fr.hamunaptra_.oxaliacore.utils.api.config.*;
import fr.hamunaptra_.oxaliacore.utils.api.data.*;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

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
    public String onPlaceholderRequest(Player p, String s) {
        if (p == null) {
            return "";
        }

        DataManager Data = new DataManager(p);

        if (s.equals("bank_balance")) {
            double balance = Data.getBalance();
            return String.valueOf(balance);
        }

        if (s.equals("bank_interest_time")) {
            long lastInterest = Data.getInterestTime();
            long min = (lastInterest % 3600) / 60;
            long sec = lastInterest % 60;

            Color Color = new Color(p);

            return (Color.set(Bank.getString("Bank.PlaceHolders.Interest.Time"))
                    .replace("%min", String.valueOf(min))
                    .replace("%sec", String.valueOf(sec)));
        }

        if (s.equals("bank_interest_money")) {
            double interest = Data.getBalance() * Bank.getDouble("Bank.Interest.Percent");
            return String.valueOf(interest);
        }

        return s;
    }

}
