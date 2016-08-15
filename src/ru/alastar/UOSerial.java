package ru.alastar;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

/**
 * Created by Alastar on 24.05.2016.
 */
public interface UOSerial {
    public void serialize(BufferedOutputStream str);

    public void deserialize(BufferedInputStream str);

}
