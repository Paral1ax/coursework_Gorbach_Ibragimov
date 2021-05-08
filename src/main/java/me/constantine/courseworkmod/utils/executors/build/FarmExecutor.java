package me.constantine.courseworkmod.utils.executors.build;

import me.constantine.courseworkmod.CourseWorkMod;
import org.bukkit.Location;
import org.bukkit.Material;

public class FarmExecutor {
    public static void process() {
        Location playerLoc = CourseWorkMod.PLAYER.getLocation();
        Location min = new Location(playerLoc.getWorld(), playerLoc.getX() - 5, playerLoc.getY() - 1, playerLoc.getZ() - 5);
        Location max = new Location(playerLoc.getWorld(), playerLoc.getX() + 5, playerLoc.getY() - 1, playerLoc.getZ() + 5);
        for (int x = (int) min.getX(); x < (int) max.getX(); x++) {
            for (int z = (int) min.getZ(); z < (int) max.getZ(); z++) {
                if (z == (int) min.getZ() || z == (int) max.getZ() - 1 ||
                        x == (int) min.getX() || x == (int) max.getX() - 1) {
                    Location cord = new Location(playerLoc.getWorld(), x, playerLoc.getY(), z);
                    cord.getBlock().setType(Material.OAK_FENCE);
                }
            }
        }
    }
}
