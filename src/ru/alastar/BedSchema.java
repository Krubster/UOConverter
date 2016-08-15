package ru.alastar;

import org.bukkit.Material;

import java.io.Serializable;

/**
 * Created by Alastar on 30.07.2015.
 */
public class BedSchema extends Schema implements Serializable {
    public BedSchema(int facing) {
        if (facing == 0)     //south/north
        {
            blocks.put(new UOVector(0, 1, 1), new LandInfo(Material.BED_BLOCK.getId(), (byte) 0));
            blocks.put(new UOVector(0, 1, 0), new LandInfo(Material.BED_BLOCK.getId(), (byte) 10));

        } else             //east/west
        {
            blocks.put(new UOVector(1, 1, 0), new LandInfo(Material.BED_BLOCK.getId(), (byte) 3));
            blocks.put(new UOVector(0, 1, 0), new LandInfo(Material.BED_BLOCK.getId(), (byte) 11));

        }
    }
}
