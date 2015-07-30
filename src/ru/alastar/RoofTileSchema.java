package ru.alastar;

import com.sk89q.worldedit.Vector;
import org.bukkit.Material;

/**
 * Created by Alastar on 30.07.2015.
 */
public class RoofTileSchema extends Schema {

    public RoofTileSchema(Material mat, byte sub)
    {
        blocks.put(new Vector(-1,0,-1), new LandInfo(mat, sub));
    }

    public  RoofTileSchema(int modId, byte sub)
    {
        LandInfo li = new LandInfo(Material.BROWN_MUSHROOM);
        li.setModded(true);
        li.setModId(modId);
        blocks.put(new Vector(-1,0,-1), li);
    }
}
