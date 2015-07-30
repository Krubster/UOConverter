package ru.alastar;

import com.sk89q.worldedit.Vector;
import org.bukkit.Material;

/**
 * Created by Alastar on 30.07.2015.
 */
public class LogSchema extends Schema {

    public LogSchema(byte sub, int facing)
    {
        if(facing == 0)//east
        {
            blocks.put(new Vector(1,1,0), new LandInfo(Material.LOG, sub));
            blocks.put(new Vector(0,1,0), new LandInfo(Material.LOG, sub));
            blocks.put(new Vector(-1,1,0), new LandInfo(Material.LOG, sub));

        }
        else //south
        {

            blocks.put(new Vector(0,1,1), new LandInfo(Material.LOG, sub));
            blocks.put(new Vector(0,1,0), new LandInfo(Material.LOG, sub));
            blocks.put(new Vector(0,1,-1), new LandInfo(Material.LOG, sub));
        }
    }
}
