package me.constantine.courseworkmod.utils.executors.build.farm;

import me.constantine.courseworkmod.CourseWorkMod;
import me.constantine.courseworkmod.utils.executors.build.EmptySpace;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

public class FarmExecutor {
    public static void process() {
        build();
    }

    private static void build() {
        Location min = EmptySpace.minLoc;
        Location max = EmptySpace.maxLoc;
        if (validationArea(min, max)) {
            Bukkit.broadcastMessage("The area is too small");
            return;
        }
        EmptySpace.empty();
        for (int x = (int) min.getX(); x <= (int) max.getX(); x++) {
            for (int z = (int) min.getZ(); z <= (int) max.getZ(); z++) {
                if (z == (int) min.getZ() || z == (int) max.getZ() ||
                        x == (int) min.getX() || x == (int) max.getX()) {
                    Location cord = new Location(max.getWorld(), x, min.getY() + 1, z);
                    cord.getBlock().setType(Material.OAK_FENCE);
                    if (min.getX()==x&&z==(max.getZ()-min.getZ()+1)/2)
                        cord.getBlock().setType(Material.OAK_FENCE_GATE);
                } else {
                    Location cord = new Location(max.getWorld(), x, min.getY(), z);
                    cord.getBlock().setType(Material.FARMLAND);
                }
            }
        }
        CourseWorkMod.landClaimer.clean();
    }

    private static boolean validationArea(Location start, Location end) {
        if (Math.abs(start.getX() - end.getX()) > 4) {
            return Math.abs(start.getZ() - end.getZ()) > 4;
        }
        return false;
    }
}
