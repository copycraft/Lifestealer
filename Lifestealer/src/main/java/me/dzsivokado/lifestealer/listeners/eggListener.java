package me.dzsivokado.lifestealer.listeners;

import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class eggListener implements Listener {

    @EventHandler
    public void onAdvancement(PlayerAdvancementDoneEvent event) {
        Player player = event.getPlayer();
        Advancement advancement = event.getAdvancement();
        String advancementKey = advancement.getKey().getKey();

        if ("end/dragon_egg".equals(advancementKey)) {
            // Replace with the command you want to run
            String command = "give " + player.getName() + " diamond 1";
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            player.sendMessage("Congratulations on obtaining the dragon egg! You've been rewarded with a diamond.");
        }
    }
}
