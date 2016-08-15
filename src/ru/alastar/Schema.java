package ru.alastar;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alastar on 29.07.2015.
 */
public class Schema implements Serializable, UOSerial {

    public HashMap<UOVector, LandInfo> blocks; //pos mod, block info

    public Schema() {
        blocks = new HashMap<>();
    }

    @Override
    public void serialize(BufferedOutputStream str) {
        try {
            str.write(ByteBuffer.allocate(4).putInt(blocks.size()).array()); //writing size of blocks hashmap
            for (Map.Entry<UOVector, LandInfo> block : blocks.entrySet()) {
                //Writing block xyz
                str.write(ByteBuffer.allocate(4).putInt(block.getKey().getBlockX()).array());
                str.write(ByteBuffer.allocate(4).putInt(block.getKey().getBlockY()).array());
                str.write(ByteBuffer.allocate(4).putInt(block.getKey().getBlockZ()).array());
                //
                //Writing block data
                block.getValue().serialize(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deserialize(BufferedInputStream str) {
        byte[] bytes = ByteBuffer.allocate(4).array();
        try {
            str.read(bytes);
            int amt = ByteBuffer.wrap(bytes).getInt(); // reading amt of da blocks
            int x, y, z;
            LandInfo info;
            for (int i = 0; i < amt; ++i) {
                str.read(bytes);
                x = ByteBuffer.wrap(bytes).getInt(); // reading x
                str.read(bytes);
                y = ByteBuffer.wrap(bytes).getInt(); // reading y
                str.read(bytes);
                z = ByteBuffer.wrap(bytes).getInt(); // reading z
                info = new LandInfo();
                info.deserialize(str);
                blocks.put(new UOVector(x, y, z), info);
                // UOConverter.log.info(Material.getMaterial(info.matId).toString());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
