package me.dzsivokado.lifestealer.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.dzsivokado.lifestealer.functions.giveSword;

public class giveKard implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player p = (Player) sender;

        giveSword(p);
        return true;
    }
}
