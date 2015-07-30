package ru.alastar;

import com.sk89q.worldedit.Vector;
import org.bukkit.Material;

/**
 * Created by Alastar on 30.07.2015.
 */
public class HighWindowedWall extends Schema {
    public HighWindowedWall(Material mat)
    {
        blocks.put(new Vector(0,1,0), new LandInfo(mat));//yeah,looks like a concrete :)
    }
}
