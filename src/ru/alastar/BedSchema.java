package ru.alastar;

import com.sk89q.worldedit.Vector;
import org.bukkit.Material;

/**
 * Created by Alastar on 30.07.2015.
 */
public class BedSchema extends Schema {
    public BedSchema(int facing)
    {
        if(facing == 0)     //south/north
        {
            blocks.put(new Vector(0, 1, 0), new LandInfo(Material.BED_BLOCK, (byte)0));
            blocks.put(new Vector(0, 1, 1), new LandInfo(Material.BED_BLOCK, (byte)8));

        }
        else             //east/west
        {
            blocks.put(new Vector(1, 1, 0), new LandInfo(Material.BED_BLOCK, (byte)3));
            blocks.put(new Vector(0, 1, 0), new LandInfo(Material.BED_BLOCK, (byte)11));

        }
    }
}
