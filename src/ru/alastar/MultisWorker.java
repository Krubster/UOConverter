package ru.alastar;

import com.sk89q.worldedit.Vector;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;

/**
 * Created by Alastar on 31.07.2015.
 */
public class MultisWorker implements Runnable {

    public MultisWorker() {
    }

    boolean processing;
    int processed = 0;
    FileInputStream str;
    World world;
    File f;
    int j;

    @Override
    public void run() {
        if (processing) {
            try {
                if (str == null)
                    str = new FileInputStream(f);

                int x = 0;
                int y = 0;//y in minecraft coords
                int z = 0;//z in minecraft coords
                int id = 0;
                int height = 0;     //needed to get true multi's position - don't really needed, we already have true Z
                LandInfo bl;
                byte[] bytes = new byte[4];
                Block block;
                Schema schm;
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

                    //log.info("Multi Tile: (" + x + ", " + y + ", " + z + ") ID - " + id + " Height: " + height);

                    schm = Main.getSchemaById(id);

                    for (Vector mod : schm.blocks.keySet()) {
                        bl = schm.blocks.get(mod);
                        block = world.getBlockAt(x + mod.getBlockX(), (int) (y * Main.MultisUOmod + Main.heightOffset + mod.getBlockY()) /*- height*/, z + mod.getBlockZ());

                        if(!bl.isModded())
                            block.setType(bl.mat);
                        else
                            block.setTypeId(bl.getModId());
                        block.setData(bl.subId);
                    }
                    ++processed;
                }
                if (!(str.available() > 0)) {
                    str.close();
                    str = null;
                    processing = false;
                    Main.log.info("Finished!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.processed = 0;
        }
    }

    public void set(World w, File f) {
        if (!processing) {
            this.world = w;
            this.f = f;
            this.processing = true;
        }
    }
}
