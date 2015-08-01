package ru.alastar;

import com.sk89q.worldedit.Vector;
import org.bukkit.Material;

/**
 * Created by Alastar on 30.07.2015.
 */
public class FloorTileSchema extends Schema {
    public FloorTileSchema(Material mat, byte sub)
    {
        blocks.put(new Vector(0,0,0), new LandInfo(mat, sub));
    }
    public FloorTileSchema(int id, byte sub)
    {
        blocks.put(new Vector(0,0,0), new LandInfo(id, sub));
    }
}
