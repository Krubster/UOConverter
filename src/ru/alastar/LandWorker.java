package ru.alastar;

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

    public LandWorker() {
    }
    @SuppressWarnings({"deprecated"})
    @Override
    public void run() {
        if (processing) {
            try {
                if (str == null)
                    str = new FileInputStream(f);

                while (str.available() > 0 && processed < UOConverter.tilePerUpdate) {

                    str.read(bytes);
                    x = ByteBuffer.wrap(bytes).getInt();

                    str.read(bytes);
                    z = ByteBuffer.wrap(bytes).getInt();

                    str.read(bytes);
                    y = ByteBuffer.wrap(bytes).getInt();

                    str.read(bytes);
                    id = ByteBuffer.wrap(bytes).getInt();

                    bl = UOConverter.getBlockEqualById(id);
                    if (bl.useModifier)
                        mod = UOConverter.UOmod;
                    else
                        mod = 1;

                    blockPrePlace(bl, x, y, z, id);

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
                            block = world.getBlockAt(x, j, z);
                            if (Material.getMaterial(bl.matId) != null)
                                block.setType(Material.getMaterial(bl.matId));
                            else
                                block.setTypeId(bl.matId);
                            block.setData(bl.subId);
                            block.setBiome(Biome.values()[bl.biomeid]);
                        }
                    }
                    if(UOConverter.clearAbove){
                        for (j = (int) Math.ceil(y * mod) + UOConverter.heightOffset + 1; j < world.getMaxHeight(); ++j) {
                            block = world.getBlockAt(x, j, z);
                            block.setType(Material.AIR);
                        }
                    }
                    ++processed;
                }
                if (!(str.available() > 0)) {
                    str.close();
                    str = null;
                    processing = false;

                    UOConverter.log.info("[LWorker]Finished!(" + f.getName() + ")");

                    if (nextInc) {
                        UOConverter.log.info("[LWorker]Running next file...");

                        ++count;
                        File file = new File(fileName + count + ".bin");
                        if (file.exists()) {
                            UOConverter.log.info("[LWorker]Launching new worker for " + count + " file");
                            UOConverter.instance.launchLWorker(null, world, file, 240);
                        } else {
                            UOConverter.log.info("[LWorker]There's no files left, halting!");
                            nextInc = false;
                            count = 1;
                        }
                    }
                } else {
                    UOConverter.log.info("[LWorker]" + (1.f - ((float) str.available() / (float) fileSize)) * 100 + "%");
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
            UOConverter.log.info("[LWorker]Begin!(" + f.getName() + ")");
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
