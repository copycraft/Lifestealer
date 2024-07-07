package me.dzsivokado.lifestealer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class functions {
    public static void Broadcast(String text){

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(text);
        }

    }

    public static void donothing(){

    }

    public static void giveSword(Player p) {




        ItemStack sword = new ItemStack(Material.NETHERITE_SWORD);
        List<String> lore = new ArrayList<String>();
        ItemMeta meta = (ItemMeta) sword.getItemMeta();

        lore.add( "§3I Love you");
        lore.add("§3very well");
        meta.setDisplayName( "§6The SWORD");
        meta.setLore(lore);
        meta.setUnbreakable(true);
        sword.setItemMeta(meta);


        p.getInventory().addItem(sword);
    }

    public static void killall(String asd){
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setHealth(0);
        }
    }
}

