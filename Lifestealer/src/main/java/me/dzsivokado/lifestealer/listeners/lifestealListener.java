package me.dzsivokado.lifestealer.listeners;

import me.dzsivokado.lifestealer.Lifestealer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

import static me.dzsivokado.lifestealer.functions.*;

public class lifestealListener implements Listener {

    private final Lifestealer plugin;
    private final Map<String, Integer> stolenHearts = new HashMap<>();

    public lifestealListener(Lifestealer plugin) {
        this.plugin = plugin;
    }

    public static void onLol(String asd) {
        System.out.println(asd);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getPlayer();

        if (e.getEntity().getKiller() instanceof Player) {
            Player killer = (Player) e.getEntity().getKiller();
            ItemStack killerItem = killer.getInventory().getItemInMainHand();

            if (killerItem.getType() == Material.NETHERITE_SWORD) {
                ItemMeta meta = killerItem.getItemMeta();
                if (meta != null && meta.hasDisplayName() && "Lifestealer".equals(meta.getDisplayName())) {
                    int stolen = stolenHearts.getOrDefault(killer.getName(), 0);

                    if (stolen < 28) { // 14 hearts * 2 health points each = 28 health points
                        p.setMaxHealth(Math.max(p.getMaxHealth() - 2, 2)); // Ensure player does not go below 1 heart (2 health points)
                        killer.setMaxHealth(Math.min(killer.getMaxHealth() + 2, 28)); // Max health capped at 20 hearts (40 health points)
                        stolenHearts.put(killer.getName(), stolen + 2);
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.sendMessage(killer.getName() + " now has " + killer.getMaxHealth() + " health points, while " + p.getName() + " now has " + p.getMaxHealth() + " health points.");
                        }
                    } else {
                        killer.sendMessage("You cannot steal more than 14 hearts.");
                    }
                } else {
                    donothing();
                }
            } else {
                donothing();
            }
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        ItemStack droppedItem = e.getItemDrop().getItemStack();
        ItemMeta meta = droppedItem.getItemMeta();

        if (meta != null && meta.hasDisplayName() && "Lifestealer".equals(meta.getDisplayName())) {
            Player player = e.getPlayer();
            resetPlayerHealth(player);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            checkInventory((Player) e.getWhoClicked());
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getPlayer() instanceof Player) {
            checkInventory((Player) e.getPlayer());
        }
    }

    private void checkInventory(Player player) {
        boolean hasLifestealer = false;

        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == Material.NETHERITE_SWORD) {
                ItemMeta meta = item.getItemMeta();
                if (meta != null && meta.hasDisplayName() && "Lifestealer".equals(meta.getDisplayName())) {
                    hasLifestealer = true;
                    break;
                }
            }
        }

        if (!hasLifestealer) {
            resetPlayerHealth(player);
        }
    }

    private void resetPlayerHealth(Player player) {
        int stolen = stolenHearts.getOrDefault(player.getName(), 0);
        if (stolen > 0) {
            player.setMaxHealth(player.getMaxHealth() - stolen);
            stolenHearts.remove(player.getName());
            player.sendMessage("All stolen hearts have been removed as you no longer have the Lifestealer.");
        }
    }
}
