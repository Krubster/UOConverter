package ru.alastar;

import org.bukkit.Material;

import java.io.Serializable;

/**
 * Created by Alastar on 30.07.2015.
 */
public class PillarSchema extends Schema implements Serializable {
    public PillarSchema(int mat, int height, int mat2) {
        for (int i = 0; i < height; ++i) {
            blocks.put(new UOVector(0, i, 0), new LandInfo(mat));
        }
        blocks.put(new UOVector(0, height, 0), new LandInfo(mat2));
    }

    public PillarSchema(Material mat, int height, Material mat2) {
        for (int i = 0; i < height; ++i) {
            blocks.put(new UOVector(0, i, 0), new LandInfo(mat));
        }
        blocks.put(new UOVector(0, height, 0), new LandInfo(mat2));
    }
}
