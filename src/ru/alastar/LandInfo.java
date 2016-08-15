package ru.alastar;

import org.bukkit.Material;
import org.bukkit.block.Biome;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * Created by Alastar on 29.07.2015.
 */
public class LandInfo implements Serializable, UOSerial {
    //private int modId = 0;
    public boolean useModifier = true;
    //Material mat;
    int matId = 0;
    byte subId = 0;
    //Biome biome;
    int biomeid = 0;
    boolean fill = false;

    public LandInfo() {

    }

    public LandInfo(int m, byte s, int b, boolean f) {
        this.matId = m;
        this.subId = s;
        this.biomeid = b;
        this.fill = f;
    }

    public LandInfo(Material m, byte s, Biome b, boolean f) {
        this.matId = m.getId();
        this.subId = s;
        this.biomeid = b.ordinal();
        this.fill = f;
    }

    public LandInfo(int modId, byte sub) {
        this.matId = modId;
        this.subId = sub;
        this.biomeid = Biome.BEACH.ordinal();
        this.fill = false;
    }

    public LandInfo(Material modId, byte sub) {
        this.matId = modId.getId();
        this.subId = sub;
        this.biomeid = Biome.BEACH.ordinal();
        this.fill = false;
    }

    public LandInfo(int m) {
        this.matId = m;
        this.subId = 0;
        this.biomeid = Biome.BEACH.ordinal();
        this.fill = false;
    }

    public LandInfo(Material m) {
        this.matId = m.getId();
        this.subId = 0;
        this.biomeid = Biome.BEACH.ordinal();
        this.fill = false;
    }

    public LandInfo(int m, byte s, Biome b, boolean f, boolean modifier) {
        this.matId = m;
        this.subId = s;
        this.biomeid = b.ordinal();
        this.fill = f;
        this.useModifier = modifier;
    }

    public LandInfo(Material m, byte s, Biome b, boolean f, boolean modifier) {
        this.matId = m.getId();
        this.subId = s;
        this.biomeid = b.ordinal();
        this.fill = f;
        this.useModifier = modifier;
    }

    @Override
    public void serialize(BufferedOutputStream str) {
        try {
            //UOConverter.log.info("MATERIAL: " + matId);
            str.write(ByteBuffer.allocate(4).putInt(matId).array());
            str.write(ByteBuffer.allocate(1).put(subId).array());
            str.write(ByteBuffer.allocate(4).putInt(biomeid).array());

            if (fill)
                str.write(ByteBuffer.allocate(1).put((byte) 1).array());
            else
                str.write(ByteBuffer.allocate(1).put((byte) 0).array());

            if (useModifier)
                str.write(ByteBuffer.allocate(1).put((byte) 1).array());
            else
                str.write(ByteBuffer.allocate(1).put((byte) 0).array());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deserialize(BufferedInputStream str) {
        byte[] bytes = ByteBuffer.allocate(4).array();
        byte[] one = {0};
        try {
            str.read(bytes);
            matId = ByteBuffer.wrap(bytes).getInt();
            // UOConverter.log.info("MATERIAL: " + matId);
            str.read(one);
            subId = ByteBuffer.wrap(one).get();
            str.read(bytes);
            biomeid = ByteBuffer.wrap(bytes).getInt();

            str.read(one);
            byte f = ByteBuffer.wrap(one).get();
            if (f == 1)
                fill = true;
            else fill = false;

            str.read(one);
            f = ByteBuffer.wrap(one).get();
            if (f == 1)
                useModifier = true;
            else useModifier = false;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
