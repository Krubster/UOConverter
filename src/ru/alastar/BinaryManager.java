package ru.alastar;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alastar on 24.06.2015.
 */
public class BinaryManager {

    public HashMap<Integer, Schema> loadSchemasDB(String path) {
        try {

            File db_file = new File(path);
            if (db_file.exists()) {
                FileInputStream fis;
                BufferedInputStream bis;
                int id;
                Schema schem;
                HashMap<Integer, Schema> toRet = new HashMap<>();
                String n;
                for (File f : db_file.listFiles()) {
                    n = f.getName();
<<<<<<< HEAD
                    //UOConverter.log.info("Loading Schema " + n + "...");
=======
                    //Main.log.info("Loading Schema " + n + "...");
>>>>>>> origin/master
                    id = Integer.parseInt(n.substring(0, n.lastIndexOf(".")));
                    fis = new FileInputStream(f);
                    bis = new BufferedInputStream(fis);
                    schem = new Schema();
                    schem.deserialize(bis);

                    toRet.put(id, schem);
                    bis.close();
                    fis.close();
<<<<<<< HEAD
                    //UOConverter.log.info("Done! Size:" + f.getUsableSpace());
=======
                    //Main.log.info("Done! Size:" + f.getUsableSpace());
>>>>>>> origin/master

                }
                return toRet;
            } else {
                // createDB(path);
                // loadDB(path);
                return new HashMap<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public HashMap<UOVector, Integer> loadUnknown(String path) {
        try {

            File db_file = new File(System.getProperty("user.dir") + "\\" + path);
            if (db_file.exists()) {
                FileInputStream fis;
                BufferedInputStream bis;
                int id, x, y, z, count;
                HashMap<UOVector, Integer> toRet = new HashMap<>();
                fis = new FileInputStream(db_file);
                bis = new BufferedInputStream(fis);
                byte[] bytes = ByteBuffer.allocate(4).array();
                bis.read(bytes);
                count = ByteBuffer.wrap(bytes).getInt();
                for (int i = 0; i < count; ++i) {
                    bis.read(bytes);
                    x = ByteBuffer.wrap(bytes).getInt();
                    bis.read(bytes);
                    y = ByteBuffer.wrap(bytes).getInt();
                    bis.read(bytes);
                    z = ByteBuffer.wrap(bytes).getInt();
                    bis.read(bytes);
                    id = ByteBuffer.wrap(bytes).getInt();
                    toRet.put(new UOVector(x, y, z), id);
                }
                bis.close();
                fis.close();
                return toRet;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    public HashMap<Integer, LandInfo> loadBlocksDB(String path) {
        try {

            File db_file = new File(path);
            if (db_file.exists()) {
                FileInputStream fis;
                BufferedInputStream bis;
                int id;
                LandInfo schem;
                HashMap<Integer, LandInfo> toRet = new HashMap<>();
                String n = " ";
                for (File f : db_file.listFiles()) {
                    n = f.getName();
<<<<<<< HEAD
                    //    UOConverter.log.info("Loading LandInfo " + n + "...");
=======
                    //    Main.log.info("Loading LandInfo " + n + "...");
>>>>>>> origin/master
                    id = Integer.parseInt(n.substring(0, n.lastIndexOf(".")));
                    fis = new FileInputStream(f);
                    bis = new BufferedInputStream(fis);
                    schem = new LandInfo();
                    schem.deserialize(bis);
                    toRet.put(id, schem);
                    bis.close();
                    fis.close();
<<<<<<< HEAD
                    //    UOConverter.log.info("Done! Size:" + f.getUsableSpace());
=======
                    //    Main.log.info("Done! Size:" + f.getUsableSpace());
>>>>>>> origin/master

                }
                return toRet;
            } else {
                // createDB(path);
                // loadDB(path);
                return new HashMap<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public <T> void saveDB(HashMap<Integer, T> t, String path) {
        try {
            // SLAPI.save(housings, path);
            File tempF = new File(System.getProperty("user.dir") + "/" + path);
<<<<<<< HEAD
            // UOConverter.log.info(tempF.getPath());
=======
            // Main.log.info(tempF.getPath());
>>>>>>> origin/master
            if (!tempF.exists())
                try {
                    Files.createDirectory(tempF.toPath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            for (Map.Entry<Integer, T> entry : t.entrySet()) {
                FileOutputStream fos = new FileOutputStream(path + "/" + String.valueOf(entry.getKey()) + ".bin");
                BufferedOutputStream out = new BufferedOutputStream(fos);
                ((UOSerial) entry.getValue()).serialize(out); // writing da schema
                out.close();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> void dumpDB(HashMap<Integer, T> housings, String path) {
        try {
            File dir = new File("dumps");
            if (!dir.exists()) {
                dir.mkdir();
            }
            SLAPI.save(housings, path + "-dump-" + Calendar.getInstance().getTime().toString() + ".bin");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
