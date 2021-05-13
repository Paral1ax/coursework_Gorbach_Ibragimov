package me.constantine.courseworkmod.commands.mob;

import me.constantine.courseworkmod.CourseWorkMod;
import me.constantine.courseworkmod.entity.Mob;
import me.constantine.courseworkmod.utils.executors.help.MobHelpExecutor;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Player;

public class MobSpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length > 0)
                if (args[0].equals("help")) {
                    MobHelpExecutor.process();
                    return false;
                }
            Player player = (Player) sender;
            WorldServer world = ((CraftWorld) player.getWorld()).getHandle();
            if (CourseWorkMod.MOB != null) {
                Bukkit.broadcastMessage("You already have a mob");
                return false;
            }
            Mob mob = new Mob(player);
            world.addEntity(mob);
            player.sendMessage("Mob spawned");
        } else {
            Bukkit.broadcastMessage("Only a player can use this command");
        }
        return false;
    }
}
