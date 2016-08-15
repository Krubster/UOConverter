package ru.alastar;

/**
 * Created by Alastar on 24.06.2015.
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * SLAPI = Saving/Loading API
 * API for Saving and Loading Objects.
 *
 * @author Tomsik68
 */
public class SLAPI {
    public static void save(Object obj, String path) throws Exception {
        FileOutputStream fout = new FileOutputStream(path);
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(obj);
        oos.flush();
        oos.close();
        fout.close();
    }

    public static Object load(String path) throws Exception {
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object result = ois.readObject();
        ois.close();
        fis.close();
        return result;
    }
}