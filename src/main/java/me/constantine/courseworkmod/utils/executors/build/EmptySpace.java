package me.constantine.courseworkmod.utils.executors.build;

import me.constantine.courseworkmod.CourseWorkMod;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmptySpace {
    private static double minX, minY, minZ, maxX, maxY, maxZ;
    private static List<Block> list = CourseWorkMod.landClaimer.getList();
    public static Location minLoc;
    public static Location maxLoc;

    public static void empty() {
        compare();
        int count = 0;
        minLoc = new Location(CourseWorkMod.PLAYER.getWorld(), minX, minY, minZ);
        maxLoc = new Location(CourseWorkMod.PLAYER.getWorld(), maxX, maxY, maxZ);
        for (int i = (int) minLoc.getX(); i <= (int) maxLoc.getX(); i++) {
            for (int j = (int) minLoc.getY(); j <= (int) maxLoc.getY(); j++) {
                for (int z = (int) minLoc.getZ(); z <= (int) maxLoc.getZ(); z++) {
                    Location cord = new Location(maxLoc.getWorld(), i, j, z);
                    cord.getBlock().setType(Material.AIR);
                    count++;
                }
            }
        }
        Bukkit.broadcastMessage(String.valueOf(count));
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
        maxY = minY + 5;
        maxZ = Collections.max(listZ);
    }
}
