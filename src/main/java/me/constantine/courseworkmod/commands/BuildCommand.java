package me.constantine.courseworkmod.commands;

import me.constantine.courseworkmod.CourseWorkMod;
import me.constantine.courseworkmod.utils.containers.MessagesContainer;
import me.constantine.courseworkmod.utils.executors.build.farm.FarmExecutor;
import me.constantine.courseworkmod.utils.executors.build.houses.HouseFirstExecutor;
import me.constantine.courseworkmod.utils.executors.build.houses.HouseSecondExecutor;
import me.constantine.courseworkmod.utils.executors.help.HelpExecutor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuildCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args[0].equals("house1")) {
                if (!checkWand()) return false;
                HouseFirstExecutor.process();
            } else if (args[0].equals("house2")) {
                if(!checkWand()) return false;
                HouseSecondExecutor.process();
            } else if (args[0].equals("farm")) {
                if(!checkWand()) return false;
                FarmExecutor.process();
            } else if (args[0].equals("help")) {
                HelpExecutor.process();
            }
        } else {
            Bukkit.broadcastMessage("Only a player can use this command");
        }
        return false;
    }

    private boolean checkWand() {
        if (CourseWorkMod.landClaimer.getFirst() == null) {
            Bukkit.broadcastMessage(MessagesContainer.WAND_NULL);
            return false;
        }
        return true;
    }
}
