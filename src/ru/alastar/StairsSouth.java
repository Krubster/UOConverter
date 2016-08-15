package ru.alastar;

import org.bukkit.Material;

import java.io.Serializable;

/**
 * Created by Alastar on 31.07.2015.
 */
public class StairsSouth extends Schema implements Serializable {

    public StairsSouth(int id) {
        blocks.put(new UOVector(0, 0, 0), new LandInfo(id, (byte) 4));

    }

    public StairsSouth(Material id) {
        blocks.put(new UOVector(0, 0, 0), new LandInfo(id, (byte) 4));

    }
}
