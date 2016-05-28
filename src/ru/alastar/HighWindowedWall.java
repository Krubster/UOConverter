package ru.alastar;

import org.bukkit.Material;

import java.io.Serializable;

/**
 * Created by Alastar on 30.07.2015.
 */
public class HighWindowedWall extends Schema implements Serializable {
    public HighWindowedWall(int mat) {
        blocks.put(new UOVector(0, 1, 0), new LandInfo(mat));//yeah,looks like a concrete :)
    }

    public HighWindowedWall(Material mat) {
        blocks.put(new UOVector(0, 1, 0), new LandInfo(mat));//yeah,looks like a concrete :)
    }
}
