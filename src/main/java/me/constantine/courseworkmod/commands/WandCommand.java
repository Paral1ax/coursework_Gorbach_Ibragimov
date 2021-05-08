package me.constantine.courseworkmod.commands;

import me.constantine.courseworkmod.items.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WandCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            player.getInventory().addItem(ItemManager.wand);
        } else {
            Bukkit.broadcastMessage("Only a player can send this command");
        }
        return false;
    }
}
