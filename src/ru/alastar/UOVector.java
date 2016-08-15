package ru.alastar;

import com.sk89q.worldedit.Vector;

import java.io.Serializable;

/**
 * Created by Alastar on 23.05.2016.
 */
public class UOVector extends Vector implements Serializable {
    public UOVector(int i, int i1, int i2) {
        super(i, i1, i2);
    }

    public UOVector() {

    }
}
