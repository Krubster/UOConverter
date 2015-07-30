package ru.alastar;

import com.sk89q.worldedit.Vector;

import java.util.HashMap;

/**
 * Created by Alastar on 29.07.2015.
 */
public class Schema {
    public HashMap<Vector, LandInfo> blocks; //pos mod, block info

    public Schema()
    {
        blocks = new HashMap<>();
    }
}
