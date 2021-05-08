package me.constantine.courseworkmod.utils.executors.build;

import me.constantine.courseworkmod.CourseWorkMod;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmptySpace {
    private static double minX, minY, minZ, maxX, maxZ, houseY;
    private static List<Block> list = CourseWorkMod.landClaimer.getList();
    public static Location minLoc;
    public static Location maxLoc;

    public static void empty() {
        compare();
        minLoc = new Location(CourseWorkMod.PLAYER.getWorld(), minX, minY, minZ);
        maxLoc = new Location(CourseWorkMod.PLAYER.getWorld(), maxX, houseY, maxZ);
        for (int i = (int) minLoc.getX(); i <= (int) maxLoc.getX(); i++) {
            for (int j = (int) minLoc.getY(); j <= (int) maxLoc.getY(); j++) {
                for (int z = (int) minLoc.getZ(); z <= (int) maxLoc.getZ(); z++) {
                    Location cord = new Location(CourseWorkMod.PLAYER.getWorld(), i, j, z);
                    cord.getBlock().setType(Material.AIR);
                }
            }
        }
    }

    private static void compare() {
        List<Integer> listX = new ArrayList<>();
        List<Integer> listY = new ArrayList<>();
        List<Integer> listZ = new ArrayList<>();
        for (Block block : list)
            listX.add(block.getX());
        for (Block block : list)
            listY.add(block.getY());
        for (Block block : list)
            listZ.add(block.getZ());
        minX = Collections.min(listX);
        minY = Collections.min(listY);
        minZ = Collections.min(listZ);
        maxX = Collections.max(listX);
        maxZ = Collections.max(listZ);
        houseY = 5;
    }
}
