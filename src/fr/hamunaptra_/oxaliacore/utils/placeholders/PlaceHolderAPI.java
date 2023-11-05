package fr.hamunaptra_.oxaliacore.utils.placeholders;

import fr.hamunaptra_.oxaliacore.Main;
import fr.hamunaptra_.oxaliacore.utils.chat.*;
import fr.hamunaptra_.oxaliacore.utils.files.config.*;
import fr.hamunaptra_.oxaliacore.utils.files.data.*;

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

        OxaliaData Data = new OxaliaData(p);

        if (s.equals("bank_balance")) {
            return String.valueOf(Decimal.format(Data.getBalance()));
        }

        if (s.equals("bank_interest_time")) {
            long time = Data.getInterestTime();
            long min = (time % 3600) / 60;
            long sec = time % 60;

            Color Color = new Color(p);

            return (Color.set(Bank.getString("Bank.PlaceHolders.Interest.Time"))
                    .replace("%min", String.valueOf(min))
                    .replace("%sec", String.valueOf(sec)));
        }

        if (s.equals("bank_interest_money")) {
            return String.valueOf(Data.getBalance() * Bank.getDouble("Bank.Interest.Percent"));
        }

        return s;
    }

}
