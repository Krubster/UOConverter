package ru.alastar;

import com.sk89q.worldedit.Vector;
import org.bukkit.Material;

/**
 * Created by Alastar on 31.07.2015.
 */
public class StairsSouth extends Schema {
    public StairsSouth(Material mat)
    {
        blocks.put(new Vector(0,0,0), new LandInfo(mat, (byte)4));

    }
    public StairsSouth(int id)
    {
        blocks.put(new Vector(0,0,0), new LandInfo(id, (byte)4));

    }
}
