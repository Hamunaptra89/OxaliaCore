package fr.hamunaptra_.oxaliacore.addon.bank;

import fr.hamunaptra_.oxaliacore.Main;
import fr.hamunaptra_.oxaliacore.utils.api.chat.Color;
import fr.hamunaptra_.oxaliacore.utils.api.config.Bank;
import fr.hamunaptra_.oxaliacore.utils.api.data.DataManager;

import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class BankListener implements Listener {

    private final Map<Player, Double> DepositNumber = new HashMap<>();
    private final Map<Player, Boolean> DepositNumberMap = new HashMap<>();

    private final Map<Player, Double> WithDrawNumber = new HashMap<>();
    private final Map<Player, Boolean> WithDrawNumberMap = new HashMap<>();

    @EventHandler
    public void PlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        Color Color = new Color(p);
        DataManager data = new DataManager(p);

        if (Bank.getBoolean("Bank.Death.Enable")) {
            double amount = data.getBalance() * Bank.getDouble("Bank.Death.Percent");
            data.setBalance(amount);
            p.sendMessage(Color.set(Bank.getString("Bank.Death.Message").replaceAll("%amount%", String.valueOf(amount))));
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        Color Color = new Color(p);
        DataManager Data = new DataManager(p);

        if (DepositNumberMap.getOrDefault(p, false)) {
            try {
                double deposit = Double.parseDouble(e.getMessage());
                EconomyResponse r = Main.economy.withdrawPlayer((OfflinePlayer) p, deposit);

                if (r.transactionSuccess()) {
                    DepositNumber.put(p, deposit);
                    Data.deposit(deposit);
                    p.sendMessage(Color.set(Bank.getString("Bank.Message.Deposit.Success")
                            .replaceAll("%amount%", String.valueOf(deposit))));
                } else {
                    p.sendMessage(Color.set(Bank.getString("Bank.Message.NoMoney")));
                }

                DepositNumberMap.put(p, false);
            } catch (NumberFormatException f) {
                p.sendMessage(Color.set(Bank.getString("Bank.Message.InvalidNumber")));
            }
            e.setCancelled(true);
        }

        if (WithDrawNumberMap.getOrDefault(p, false)) {
            try {
                double withdraw = Double.parseDouble(e.getMessage());
                EconomyResponse r = Main.economy.depositPlayer((OfflinePlayer) p, withdraw);

                if (r.transactionSuccess()) {
                    WithDrawNumber.put(p, withdraw);
                    Data.withdraw(withdraw);
                    p.sendMessage(Color.set(Bank.getString("Bank.Message.Withdraw.Success")
                            .replaceAll("%amount%", String.valueOf(withdraw))));
                } else {
                    p.sendMessage(Color.set(Bank.getString("Bank.Message.NoMoney")));
                }

                WithDrawNumberMap.put(p, false);
            } catch (NumberFormatException f) {
                p.sendMessage(Color.set(Bank.getString("Bank.Message.InvalidNumber")));
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Color Color = new Color(p);

        if (e.getCurrentItem() == null) return;
        if (e.getCurrentItem().getItemMeta() == null) return;

        ItemStack i = e.getCurrentItem();
        String getName = i.getItemMeta().getDisplayName();

        if (e.getView().getTitle().equals(Color.set(Bank.getString("Bank.Guis.Main.Inv.Name"))) || e.getView().getTitle().equals(Color.set(Bank.getString("Bank.Guis.Deposit.Inv.Name"))) || e.getView().getTitle().equals(Color.set(Bank.getString("Bank.Guis.Withdraw.Inv.Name")))) {
            e.setCancelled(true);

            if (getName.equals(Color.set(Bank.getString("Bank.Guis.Main.Item.Deposit.Name")))) {
                BankGuis.BankMenuDeposit(p);
            }

            else if (getName.equals(Color.set(Bank.getString("Bank.Guis.Main.Item.Withdraw.Name")))) {
                BankGuis.BankMenuWithdraw(p);
            }

            else if (getName.equals(Color.set("§aMenu §8§l» §bMétier"))) {
                if (!DepositNumberMap.getOrDefault(p, false)) {
                    p.sendMessage("Entrez un chiffre :");
                    DepositNumberMap.put(p, true);
                    e.setCancelled(true);
                    p.closeInventory();
                }
            }

            else if (getName.equals(Color.set("§aMenu §8§l» §dCollection"))) {
                if (!WithDrawNumberMap.getOrDefault(p, false)) {
                    p.sendMessage("Entrez un chiffre :");
                    WithDrawNumberMap.put(p, true);
                    e.setCancelled(true);
                }
            }
        }
    }
}
