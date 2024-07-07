package me.dzsivokado.lifestealer.listeners;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class banisherListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        if (killer != null) {
            for (ItemStack item : killer.getInventory().getContents()) {
                if (item != null && item.getType() == Material.WOODEN_SWORD && item.getItemMeta().getDisplayName().equals(ChatColor.RED + "Slot Bannisher")) {
                    Bukkit.getScheduler().runTask(Bukkit.getPluginManager().getPlugin("Lifestealer"), () -> {
                        Bukkit.getBanList(BanList.Type.NAME).addBan(victim.getName(), "You have been banned for being killed with the Slot Bannisher!", null, killer.getName());
                        victim.kickPlayer("You have been banned for being killed with the Slot Bannisher!");
                    });
                    break;
                }
            }
        }
    }
}
