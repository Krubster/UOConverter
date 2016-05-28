package ru.alastar;

import org.bukkit.Material;

import java.io.Serializable;

/**
 * Created by Alastar on 31.07.2015.
 */
public class StairsNorth extends Schema implements Serializable {

    public StairsNorth(int id) {
        blocks.put(new UOVector(0, 1, 0), new LandInfo(id, (byte) 2));

    }

    public StairsNorth(Material id) {
        blocks.put(new UOVector(0, 1, 0), new LandInfo(id, (byte) 2));

    }
}
