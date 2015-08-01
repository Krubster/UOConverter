package ru.alastar;

import com.sk89q.worldedit.Vector;
import org.bukkit.Material;

/**
 * Created by Alastar on 31.07.2015.
 */
public class StairsNorth extends Schema{
    public StairsNorth(Material mat)
    {
        blocks.put(new Vector(0,1,0), new LandInfo(mat, (byte)2));

    }
    public StairsNorth(int id)
    {
        blocks.put(new Vector(0,1,0), new LandInfo(id, (byte)2));

    }
}
