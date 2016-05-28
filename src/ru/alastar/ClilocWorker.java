package ru.alastar;

import org.bukkit.World;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by Alastar on 26.05.2016.
 */
public class ClilocWorker implements Runnable {
    int x, y, z, id;
    FileInputStream str;
    int height;
    int processed;
    byte[] bytes = new byte[4];
    UOVector vec = new UOVector();
    Schema schm;
    boolean processing = false;
    int count = 1;
    String fileName = "";
    File f;
    World world;
    int fileSize;
    int mode = 0;
    LandInfo l;

    @Override
    public void run() {
        if (processing) {

            try {
                if (str == null)
                    str = new FileInputStream(f);

                while (str.available() > 0 && processed < Main.tilePerUpdate) {

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

                    if (mode == 0) {
                        schm = Main.getSchemaById(id);
                        if (schm == Main.errorSchm) {
                            vec = new UOVector(x, (int) (Math.ceil(y * Main.MultisUOmod) + Main.heightOffset), z);
                            Main._unknown_clilocs.put(vec, id);
                            ++processed;
                        }
                    } else {
                        l = Main.getBlockEqualById(id);
                        if (l == Main.stoneInfo) {
                            vec = new UOVector(x, (int) (Math.ceil(y * Main.MultisUOmod) + Main.heightOffset), z);
                            Main._unknown_clilocs.put(vec, id);
                            ++processed;
                        }
                    }
                }

                if (!(str.available() > 0)) {
                    str.close();
                    str = null;
                    processing = false;
                    Main.log.info("[ClilocWorker]Finished!(" + f.getName() + ")");

                    Main.log.info("[ClilocWorker]Running next file...");

                    ++count;
                    File file = new File("conv/" + fileName + count + ".bin");
                    if (file.exists()) {
                        Main.log.info("[LWorker]Launching new worker for " + fileName + count + " file");

                        Main.instance.launchClilocWorker(null, world, fileName, 0, mode, count);
                    } else {
                        Main.log.info("[ClilocWorker]There's no files left, halting!");
                        count = 1;
                        Main.saveUnknown();
                    }
                } else {
                    Main.log.info("[ClilocWorker]" + (1.f - ((float) str.available() / (float) fileSize)) * 100 + "%");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.processed = 0;
        }
    }

    public void set(World w, String fn, int mode, int id) {
        if (!processing) {
            count = id;
            this.mode = mode;
            this.world = w;
            fileName = fn;
            this.processing = true;
            f = new File(System.getProperty("user.dir") + "\\conv\\" + fileName + id + ".bin");
            Main.log.info("[ClilocWorker]Begin!(" + f.getName() + ")");
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
