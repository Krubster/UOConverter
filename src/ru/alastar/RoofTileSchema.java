package ru.alastar;

import org.bukkit.Material;

import java.io.Serializable;

/**
 * Created by Alastar on 30.07.2015.
 */
public class RoofTileSchema extends Schema implements Serializable {

    public RoofTileSchema(int mat, byte sub) {
        blocks.put(new UOVector(-1, 0, -1), new LandInfo(mat, sub));
    }

    public RoofTileSchema(Material mat, byte sub) {
        blocks.put(new UOVector(-1, 0, -1), new LandInfo(mat.getId(), sub));
    }
}
