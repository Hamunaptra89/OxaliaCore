package fr.hamunaptra_.immersicore.addon.bank;

import fr.hamunaptra_.immersicore.Main;
import fr.hamunaptra_.immersicore.utils.api.chat.*;
import fr.hamunaptra_.immersicore.utils.api.config.Bank;
import fr.hamunaptra_.immersicore.utils.api.data.DataManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BankInterest {
    
  public static void run() {
    Bukkit.getServer().getScheduler().runTaskTimer((Plugin)Main.getInstance(), new Runnable() {
          public void run() {
            String path = "Bank.Interest.";
            if (Bank.getBoolean(path + "Enable"))
              for (Player p : Bukkit.getOnlinePlayers()) {
                DataManager Data = new DataManager(p);
                Data.InterestCooldownRemove(1);
                if (Data.getLastInterest() <= 0 && Data.getBalance() >= Bank.getDouble(path + "Minimum")) {
                  double amount = Data.getBalance() * Bank.getDouble(path + "Percent");
                  Data.deposit(amount);
                  p.sendMessage(Color.set(Bank.getString(path + "Message.Get")).replaceAll("%interest%", String.valueOf(amount)));
                } 
                Data.setLastInterest(Bank.getInt(path + "Time"));
              }  
          }
        }20L, 20L);
  }
}
