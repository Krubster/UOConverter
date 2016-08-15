package ru.alastar;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by Alastar on 31.07.2015.
 */
public class MultisWorker implements Runnable {

    boolean processing = false;
    int processed = 0;
    boolean nextInc = false;
    String fileName;
    int count = 1;
    FileInputStream str;
    World world;
    File f;
    int j;
    int fileSize = 0;
    UOVector vec = new UOVector(0, 0, 0);
    int x, y, z, height, id;
    byte[] bytes = new byte[4];
    double mod = 0;
    LandInfo bl;
    Block block;
    Schema schm;
    public MultisWorker() {
    }

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

                    str.read(bytes);
                    height = ByteBuffer.wrap(bytes).getInt();

<<<<<<< HEAD
                    schm = UOConverter.getSchemaById(id);
=======
                    schm = Main.getSchemaById(id);
>>>>>>> origin/master
                    if (schm != null) {
                        if (schm.blocks != null) {
                            for (UOVector mod : schm.blocks.keySet()) {
                                bl = schm.blocks.get(mod);
<<<<<<< HEAD
                                block = world.getBlockAt(x + mod.getBlockX(), (int) (Math.ceil(y * UOConverter.MultisUOmod) + UOConverter.heightOffset + mod.getBlockY()) /*- height*/, z + mod.getBlockZ());
=======
                                block = world.getBlockAt(x + mod.getBlockX(), (int) (Math.ceil(y * Main.MultisUOmod) + Main.heightOffset + mod.getBlockY()) /*- height*/, z + mod.getBlockZ());
>>>>>>> origin/master

                                if (Material.getMaterial(bl.matId) != null)
                                    block.setType(Material.getMaterial(bl.matId));
                                else
                                    block.setTypeId(bl.matId);
                                block.setData(bl.subId);
                            }
                        }
                        ++processed;
                    }
                }

                if (!(str.available() > 0)) {
                    str.close();
                    str = null;
                    processing = false;
<<<<<<< HEAD
                    UOConverter.log.info("[MWorker]Finished!(" + f.getName() + ")");
                    if (nextInc) {
                        UOConverter.log.info("[MWorker]Running next file...");
=======
                    Main.log.info("[MWorker]Finished!(" + f.getName() + ")");
                    if (nextInc) {
                        Main.log.info("[MWorker]Running next file...");
>>>>>>> origin/master

                        ++count;
                        File file = new File(fileName + count + ".bin");
                        if (file.exists()) {
<<<<<<< HEAD
                            UOConverter.instance.launchMWorker(null, world, file, 200);
                        } else {
                            UOConverter.log.info("[MWorker]There's no files left, halting!");
=======
                            Main.instance.launchMWorker(null, world, file, 200);
                        } else {
                            Main.log.info("[MWorker]There's no files left, halting!");
>>>>>>> origin/master
                            nextInc = false;
                            count = 1;
                        }
                    }
                } else {
<<<<<<< HEAD
                    UOConverter.log.info("[MWorker]" + (1.f - ((float) str.available() / (float) fileSize)) * 100 + "%");
=======
                    Main.log.info("[MWorker]" + (1.f - ((float) str.available() / (float) fileSize)) * 100 + "%");
>>>>>>> origin/master

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.processed = 0;
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
            UOConverter.log.info("[MWorker]Begin!(" + f.getName() + ")");
=======
            Main.log.info("[MWorker]Begin!(" + f.getName() + ")");
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
