package ru.alastar;

import org.bukkit.Material;

import java.io.Serializable;

/**
 * Created by Alastar on 31.07.2015.
 */
public class StairsEast extends Schema implements Serializable {
    public StairsEast(int id) {
        blocks.put(new UOVector(0, 0, 0), new LandInfo(id, (byte) 1));
    }

    public StairsEast(Material id) {
        blocks.put(new UOVector(0, 0, 0), new LandInfo(id, (byte) 1));
    }
}
