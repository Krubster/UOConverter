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
}
