package ru.alastar;

import org.bukkit.Material;

import java.io.Serializable;

/**
 * Created by Alastar on 31.07.2015.
 */
public class StairsWest extends Schema implements Serializable {
    public StairsWest(int id) {
        blocks.put(new UOVector(0, 0, 0), new LandInfo(id, (byte) 3));

    }

    public StairsWest(Material id) {
        blocks.put(new UOVector(0, 0, 0), new LandInfo(id, (byte) 3));

    }
}
