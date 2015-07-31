package ru.alastar;

import org.bukkit.Material;
import org.bukkit.block.Biome;

/**
 * Created by Alastar on 29.07.2015.
 */
public class LandInfo {
    Material mat;
    byte subId;
    Biome biome;
    boolean fill;
    private boolean modded = false;
    private int modId;
    public boolean useModifier = true;

    public LandInfo(int modId )
    {
        this.modded = true;
        this.modId = modId;
        this.subId = 0;
        this.biome = Biome.BEACH;
        this.fill = false;
    }

    public LandInfo(int m, byte s, Biome b, boolean f)
    {
        this.modded = true;
        this.modId = m;
        this.subId = s;
        this.biome = b;
        this.fill = f;
    }

    public LandInfo(int modId, byte sub)
    {
        this.modded = true;
        this.modId = modId;
        this.subId = sub;
        this.biome = Biome.BEACH;
        this.fill = false;
    }



    public LandInfo(Material m, byte sub)
    {
        this.mat = m;
        this.subId = sub;
        this.biome = Biome.BEACH;
        this.fill = false;
    }

    public LandInfo(Material m)
    {
        this.mat = m;
        this.subId = 0;
        this.biome = Biome.BEACH;
        this.fill = false;
    }

    public LandInfo(Material m, byte s, Biome b, boolean f)
    {
        this.mat = m;
        this.subId = s;
        this.biome = b;
        this.fill = f;
    }


    public LandInfo(Material m, byte s, Biome b, boolean f, boolean modifier)
    {
        this.mat = m;
        this.subId = s;
        this.biome = b;
        this.fill = f;
        this.useModifier = modifier;
    }

    public boolean isModded() {
        return modded;
    }

    public void setModded(boolean modded) {
        this.modded = modded;
    }

    public int getModId() {
        return modId;
    }

    public void setModId(int modId) {
        this.modId = modId;
    }
}
