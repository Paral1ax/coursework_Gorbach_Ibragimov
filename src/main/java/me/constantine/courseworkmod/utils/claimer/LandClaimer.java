package me.constantine.courseworkmod.utils.claimer;

import me.constantine.courseworkmod.CourseWorkMod;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class LandClaimer {
    private Block first;
    private Block second;
    private List<Block> list = new ArrayList<>();

    public Block getFirst() {
        return first;
    }

    public void setFirst(Block first) {
        this.first = first;
    }

    public Block getSecond() {
        return second;
    }

    public void setSecond(Block second) {
        this.second = second;
    }

    public void process() {
        if (first == null || second == null) return;
        CourseWorkMod.PLAYER.sendRawMessage("WORK");
        World world = first.getWorld();
        list.add(first);
        list.add(second);
        //double mainY = Math.min(first.getY(), second.getY());
        list.add(new Location(world, second.getX(), first.getY(), first.getZ()).getBlock());
        list.add(new Location(world, first.getX(), second.getY(), second.getZ()).getBlock());
        for (Block block : list) {
            block.setType(Material.GOLD_BLOCK);
        }
        clean();
    }

    public List<Block> getList() {
        return list;
    }

    private void clean() {
        first = null;
        second = null;
        list.clear();
    }
}
