package ru.alastar;

<<<<<<< HEAD
=======
import com.sk89q.worldedit.*;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.filtering.GaussianKernel;
import com.sk89q.worldedit.filtering.HeightMapFilter;
import com.sk89q.worldedit.regions.CuboidRegion;
>>>>>>> origin/master
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by Alastar on 31.07.2015.
 */
public class LandWorker implements Runnable {

    boolean processing = false;
    int processed = 0;
    FileInputStream str = null;
    World world;
    File f = null;
    int j = 0;
    boolean nextInc = false;
    String fileName = "";
    int count = 1;
    int fileSize = 0;
    int x, y, z, id;
    byte[] bytes = new byte[4];
    double mod = 0;
    LandInfo bl;
    Block block;
<<<<<<< HEAD
=======

    public LandWorker() {
    }
>>>>>>> origin/master

    public LandWorker() {
    }
    @SuppressWarnings({"deprecated"})
    @Override
    public void run() {
        if (processing) {
            try {
                if (str == null)
                    str = new FileInputStream(f);

<<<<<<< HEAD
                while (str.available() > 0 && processed < UOConverter.tilePerUpdate) {
=======
                while (str.available() > 0 && processed < Main.tilePerUpdate) {
>>>>>>> origin/master

                    str.read(bytes);
                    x = ByteBuffer.wrap(bytes).getInt();

                    str.read(bytes);
                    z = ByteBuffer.wrap(bytes).getInt();

                    str.read(bytes);
                    y = ByteBuffer.wrap(bytes).getInt();

                    str.read(bytes);
                    id = ByteBuffer.wrap(bytes).getInt();

<<<<<<< HEAD
                    bl = UOConverter.getBlockEqualById(id);
                    if (bl.useModifier)
                        mod = UOConverter.UOmod;
=======
                    bl = Main.getBlockEqualById(id);
                    if (bl.useModifier)
                        mod = Main.MultisUOmod;
>>>>>>> origin/master
                    else
                        mod = 1;

                    blockPrePlace(bl, x, y, z, id);
<<<<<<< HEAD

                    if (bl.fill) {
                        if(!UOConverter.clearAbove) {
                            block = world.getBlockAt(x, (int) Math.ceil(y * mod) + UOConverter.heightOffset, z);
                            if (Material.getMaterial(bl.matId) != null)
                                block.setType(Material.getMaterial(bl.matId));
                            else
                                block.setTypeId(bl.matId);
                            block.setData(bl.subId);
                            block.setBiome(Biome.values()[bl.biomeid]);

                            for (j = (int) Math.ceil((y * mod) + UOConverter.heightOffset - 1); j >= (int) Math.ceil(y * mod) + UOConverter.heightOffset - 3; --j) {
                                block = world.getBlockAt(x, j, z);
                                block.setType(Material.getMaterial(UOConverter.stoneInfo.matId));
                                block.setData(UOConverter.stoneInfo.subId);
                            }

                            for (j = (int) Math.ceil(y * mod) + UOConverter.heightOffset - 4; j >= 0; --j) {
                                block = world.getBlockAt(x, j, z);
                                block.setType(Material.getMaterial(UOConverter.dirtInfo.matId));
                                block.setData(UOConverter.dirtInfo.subId);
                            }
                        }
                    } else {

                        for (j = 0; j < (int) Math.ceil(y * mod) + UOConverter.heightOffset; ++j) {
=======

                    if (bl.fill) {
                        block = world.getBlockAt(x, (int) Math.ceil(y * mod) + Main.heightOffset, z);
                        if (Material.getMaterial(bl.matId) != null)
                            block.setType(Material.getMaterial(bl.matId));
                        else
                            block.setTypeId(bl.matId);
                        block.setData(bl.subId);
                        block.setBiome(Biome.values()[bl.biomeid]);

                        for (j = (int) Math.ceil((y * mod) + Main.heightOffset - 1); j >= (int) Math.ceil(y * mod) + Main.heightOffset - 3; --j) {
                            block = world.getBlockAt(x, j, z);
                            block.setType(Material.getMaterial(Main.stoneInfo.matId));
                            block.setData(Main.stoneInfo.subId);
                        }

                        for (j = (int) Math.ceil(y * mod) + Main.heightOffset - 4; j >= 0; --j) {
                            block = world.getBlockAt(x, j, z);
                            block.setType(Material.getMaterial(Main.dirtInfo.matId));
                            block.setData(Main.dirtInfo.subId);
                        }
                    } else {

                        for (j = 0; j < (int) Math.ceil(y * mod) + Main.heightOffset; ++j) {
>>>>>>> origin/master
                            block = world.getBlockAt(x, j, z);
                            if (Material.getMaterial(bl.matId) != null)
                                block.setType(Material.getMaterial(bl.matId));
                            else
                                block.setTypeId(bl.matId);
                            block.setData(bl.subId);
                            block.setBiome(Biome.values()[bl.biomeid]);
<<<<<<< HEAD
                        }
                    }
                    if(UOConverter.clearAbove){
                        for (j = (int) Math.ceil(y * mod) + UOConverter.heightOffset + 1; j < world.getMaxHeight(); ++j) {
                            block = world.getBlockAt(x, j, z);
                            block.setType(Material.AIR);
=======
>>>>>>> origin/master
                        }
                    }
                    ++processed;
                }
                if (!(str.available() > 0)) {
                    str.close();
                    str = null;
                    processing = false;

<<<<<<< HEAD
                    UOConverter.log.info("[LWorker]Finished!(" + f.getName() + ")");

                    if (nextInc) {
                        UOConverter.log.info("[LWorker]Running next file...");
=======
                    Main.log.info("[LWorker]Finished!(" + f.getName() + ")");

                    if (nextInc) {
                        Main.log.info("[LWorker]Running next file...");
>>>>>>> origin/master

                        ++count;
                        File file = new File(fileName + count + ".bin");
                        if (file.exists()) {
<<<<<<< HEAD
                            UOConverter.log.info("[LWorker]Launching new worker for " + count + " file");
                            UOConverter.instance.launchLWorker(null, world, file, 240);
                        } else {
                            UOConverter.log.info("[LWorker]There's no files left, halting!");
=======
                            Main.log.info("[LWorker]Launching new worker for " + count + " file");
                            Main.instance.launchLWorker(null, world, file, 240);
                        } else {
                            Main.log.info("[LWorker]There's no files left, halting!");
>>>>>>> origin/master
                            nextInc = false;
                            count = 1;
                        }
                    }
                } else {
<<<<<<< HEAD
                    UOConverter.log.info("[LWorker]" + (1.f - ((float) str.available() / (float) fileSize)) * 100 + "%");
=======
                    Main.log.info("[LWorker]" + (1.f - ((float) str.available() / (float) fileSize)) * 100 + "%");
>>>>>>> origin/master
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.processed = 0;
        }
    }

    private void blockPrePlace(LandInfo bl, float x, float y, float z, int id) {
        if (f.getName().contains("Felucca"))//we are scanning felucca
        {
<<<<<<< HEAD
            //  UOConverter.log.info("We are scanning felucca!");
            if (bl.biomeid == Biome.EXTREME_HILLS.ordinal()) {
                mod = UOConverter.UOHillsMod;
            }
            if (x >= 5120) {
                mod = UOConverter.UOmod;
            }
        }
        if (f.getName().contains("Malas"))//we are scanning malas
        {
            if (bl.biomeid == Biome.EXTREME_HILLS.ordinal()) {
                mod = UOConverter.UOHillsMod;
=======
            //  Main.log.info("We are scanning felucca!");
            if (bl.biomeid == Biome.EXTREME_HILLS.ordinal()) {
                mod = Main.UOHillsMod;
            }
            if (x >= 5120) {
                mod = Main.UOmod;
>>>>>>> origin/master
            }
        }
    }

    public void setNextInc(String file, int i) {
        fileName = System.getProperty("user.dir") + "\\conv\\" + file;
        nextInc = true;
        count = i;
    }

    public void set(World w, File f) {
        if (!processing) {
            this.world = w;
            this.f = f;
            this.processing = true;
<<<<<<< HEAD
            UOConverter.log.info("[LWorker]Begin!(" + f.getName() + ")");
=======
            Main.log.info("[LWorker]Begin!(" + f.getName() + ")");
>>>>>>> origin/master
            try {
                if (str == null)
                    str = new FileInputStream(f);
                fileSize = str.available();
                str.close();
                str = null;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
