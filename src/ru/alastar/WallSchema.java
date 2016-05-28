package ru.alastar;

import org.bukkit.Material;
import org.bukkit.block.Biome;

import java.io.Serializable;

/**
 * Created by Alastar on 30.07.2015.
 */
public class WallSchema extends Schema implements Serializable {
    public WallSchema(int mat, byte sub, boolean windowed) {
        blocks.put(new UOVector(0, 1, 0), new LandInfo(mat, sub, Biome.DEEP_OCEAN.ordinal(), false));
        if (!windowed) {
            blocks.put(new UOVector(0, 2, 0), new LandInfo(mat, sub, Biome.DEEP_OCEAN.ordinal(), false));
        }
        blocks.put(new UOVector(0, 3, 0), new LandInfo(mat, sub, Biome.DEEP_OCEAN.ordinal(), false));

    }

    public WallSchema(Material mat, byte sub, boolean windowed) {
        blocks.put(new UOVector(0, 1, 0), new LandInfo(mat, sub, Biome.DEEP_OCEAN, false));
        if (!windowed) {
            blocks.put(new UOVector(0, 2, 0), new LandInfo(mat, sub, Biome.DEEP_OCEAN, false));
        }
        blocks.put(new UOVector(0, 3, 0), new LandInfo(mat, sub, Biome.DEEP_OCEAN, false));

    }
}
