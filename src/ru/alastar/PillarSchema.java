package ru.alastar;

import com.sk89q.worldedit.Vector;
import org.bukkit.Material;

/**
 * Created by Alastar on 30.07.2015.
 */
public class PillarSchema extends Schema{
    public PillarSchema(Material mat, int height, Material mat2){
        for(int i = 0; i < height; ++i)
        {
            blocks.put(new Vector(0,i,0), new LandInfo(mat));
        }
        blocks.put(new Vector(0,height,0), new LandInfo(mat2));
    }
}
