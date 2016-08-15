package ru.alastar;

import org.bukkit.Material;

import java.io.Serializable;

/**
 * Created by Alastar on 30.07.2015.
 */
public class LogSchema extends Schema implements Serializable {

    public LogSchema(byte sub, int facing) {
        if (facing == 0)//east
        {
            blocks.put(new UOVector(1, 1, 0), new LandInfo(Material.LOG.getId(), sub));
            blocks.put(new UOVector(0, 1, 0), new LandInfo(Material.LOG.getId(), sub));
            blocks.put(new UOVector(-1, 1, 0), new LandInfo(Material.LOG.getId(), sub));

        } else //south
        {

            blocks.put(new UOVector(0, 1, 1), new LandInfo(Material.LOG.getId(), sub));
            blocks.put(new UOVector(0, 1, 0), new LandInfo(Material.LOG.getId(), sub));
            blocks.put(new UOVector(0, 1, -1), new LandInfo(Material.LOG.getId(), sub));
        }
    }
}
