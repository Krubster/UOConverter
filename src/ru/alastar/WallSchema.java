package ru.alastar;

import com.sk89q.worldedit.Vector;
import org.bukkit.Material;
import org.bukkit.block.Biome;

/**
 * Created by Alastar on 30.07.2015.
 */
public class WallSchema extends Schema {
    public WallSchema(Material mat, byte sub,  boolean windowed)
    {
        blocks.put(new Vector(0,1,0), new LandInfo(mat, sub, Biome.DEEP_OCEAN, false));
        if(!windowed)
        {
            blocks.put(new Vector(0,2,0), new LandInfo(mat, sub, Biome.DEEP_OCEAN, false));
        }
        blocks.put(new Vector(0,3,0), new LandInfo(mat, sub, Biome.DEEP_OCEAN, false));

    }
}
