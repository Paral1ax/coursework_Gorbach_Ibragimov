package me.constantine.courseworkmod.commands;

import me.constantine.courseworkmod.entity.Mob;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Player;

public class MobCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            WorldServer world = ((CraftWorld)player.getWorld()).getHandle();
            Mob mob = new Mob(player);
            world.addEntity(mob);
            player.sendMessage("Mob spawned");
        } else {
            Bukkit.broadcastMessage("Only a player can use this command");
        }
        return false;
    }
}
