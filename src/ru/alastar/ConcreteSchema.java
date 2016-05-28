package ru.alastar;

import org.bukkit.Material;

import java.io.Serializable;

/**
 * Created by Alastar on 30.07.2015.
 */
public class ConcreteSchema extends Schema implements Serializable {

    public ConcreteSchema(int mat, byte sub) {
        blocks.put(new UOVector(0, 0, 0), new LandInfo(mat, sub));
    }

    public ConcreteSchema(Material mat, byte sub) {
        blocks.put(new UOVector(0, 0, 0), new LandInfo(mat, sub));
    }
}
