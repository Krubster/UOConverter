package ru.alastar;

import org.bukkit.Material;

import java.io.Serializable;

/**
 * Created by Alastar on 30.07.2015.
 */
public class FloorTileSchema extends Schema implements Serializable {
    public FloorTileSchema(int id, byte sub) {
        blocks.put(new UOVector(0, 0, 0), new LandInfo(id, sub));
    }

    public FloorTileSchema(Material id, byte sub) {
        blocks.put(new UOVector(0, 0, 0), new LandInfo(id, sub));
    }
}
