package com.william.bootleg_bereal.utilities;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtils {
    public static byte[] compressImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(data.length);
        byte[] temp = new byte[4*1024];

        try {
            while (!deflater.finished()) {
                int size = deflater.deflate(temp);
                byteArrayOutputStream.write(temp, 0, size);
            }

            byteArrayOutputStream.close();
        } catch (Exception ignored) {

        }

        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(data.length);
        byte[] temp = new byte[4*1024];

        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(temp);
                byteArrayOutputStream.write(temp, 0, count);
            }

            byteArrayOutputStream.close();
        } catch (Exception ignored) {

        }

        return byteArrayOutputStream.toByteArray();
    }
}
