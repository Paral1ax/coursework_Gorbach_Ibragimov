package me.constantine.courseworkmod.utils.executors.build.farm;

import me.constantine.courseworkmod.utils.executors.build.EmptySpace;
import org.bukkit.Location;
import org.bukkit.Material;

public class FarmExecutor {
    public static void process() {
        EmptySpace.empty();
        build();
    }

    private static void build() {
        Location min = EmptySpace.minLoc;
        Location max = EmptySpace.maxLoc;
        for (int x = (int) min.getX(); x < (int) max.getX(); x++) {
            for (int z = (int) min.getZ(); z < (int) max.getZ(); z++) {
                if (z == (int) min.getZ() || z == (int) max.getZ() - 1 ||
                        x == (int) min.getX() || x == (int) max.getX() - 1) {
                    Location cord = new Location(max.getWorld(), x, min.getY(), z);
                    cord.getBlock().setType(Material.OAK_FENCE);
                }
            }
        }
    }
}
