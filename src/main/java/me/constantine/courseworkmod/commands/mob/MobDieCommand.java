package me.constantine.courseworkmod.commands.mob;

import me.constantine.courseworkmod.CourseWorkMod;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MobDieCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (CourseWorkMod.MOB != null) {
                for (ItemStack item : CourseWorkMod.MOB.getInventory().getContents())
                    CourseWorkMod.PLAYER.getInventory().addItem(item);
                CourseWorkMod.MOB.setHealth(0);
            }
        } else {
            Bukkit.broadcastMessage("Only a player can use this command");
        }
        return false;
    }
}
