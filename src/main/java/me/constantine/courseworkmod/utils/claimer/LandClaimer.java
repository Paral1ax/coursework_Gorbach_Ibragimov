package me.constantine.courseworkmod.utils.claimer;

import me.constantine.courseworkmod.CourseWorkMod;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LandClaimer {
    private Block first;
    private Block second;
    private List<Block> list = new ArrayList<>();
    private double minX, minY, minZ, maxX, maxY, maxZ;
    private Location minLoc, maxLoc;

    public void process() {
        if (first == null || second == null) return;
        World world = first.getWorld();
        list.add(first);
        list.add(second);
        double mainY = Math.min(first.getY(), second.getY());
        list.add(new Location(world, second.getX(), mainY, first.getZ()).getBlock());
        list.add(new Location(world, first.getX(), mainY, second.getZ()).getBlock());
        compare();
        if (!validationArea(minLoc, maxLoc)) {
            Bukkit.broadcastMessage("This area is too small for building");
            clean();
        }
        for (Block block : list) {
            block.setType(Material.OAK_PLANKS);
        }
    }

    public void clean() {
        first = null;
        second = null;
        list.clear();
    }

    private void compare() {
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
        minLoc = new Location(first.getWorld(), minX, minY, minZ);
        maxLoc = new Location(first.getWorld(), maxX, maxY, maxZ);
    }

    private static boolean validationArea(Location start, Location end) {
        if (Math.abs(start.getX() - end.getX()) > 4) {
            return Math.abs(start.getZ() - end.getZ()) > 4;
        }
        return false;
    }

    public Block getSecond() {
        return second;
    }

    public Block getFirst() {
        return first;
    }


    public List<Block> getList() {
        return list;
    }

    public Location getMinLoc() {
        return minLoc;
    }

    public Location getMaxLoc() {
        return maxLoc;
    }

    public void setSecond(Block second) {
        this.second = second;
    }

    public void setFirst(Block first) {
        this.first = first;
    }
}
