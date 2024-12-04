package com.william.bootleg_bereal.utilities;

import java.io.ByteArrayOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtils {
    public static byte[] compressImage(byte[] data) {
//        Deflater deflater = new Deflater();
//        deflater.setLevel(Deflater.BEST_COMPRESSION);
//        deflater.setInput(data);
//        deflater.finish();
//
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(data.length);
//        byte[] temp = new byte[4*1024];
//
//        try {
//            while (!deflater.finished()) {
//                int size = deflater.deflate(temp);
//                byteArrayOutputStream.write(temp, 0, size);
//            }
//
//            byteArrayOutputStream.close();
//        } catch (Exception ignored) {
//
//        }
//
//        deflater.end();
//
//        return byteArrayOutputStream.toByteArray();

        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        while (!deflater.finished()) {
            int compressedSize = deflater.deflate(buffer);
            outputStream.write(buffer, 0, compressedSize);
        }

        return outputStream.toByteArray();
    }

    public static byte[] decompressImage(byte[] data) throws DataFormatException {
//        Inflater inflater = new Inflater();
//        inflater.setInput(data);
//
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(data.length);
//        byte[] temp = new byte[4*1024];
//
//        try {
//            while (!inflater.finished()) {
//                int count = inflater.inflate(temp);
//                byteArrayOutputStream.write(temp, 0, count);
//            }
//
//            byteArrayOutputStream.close();
//        } catch (Exception ignored) {
//
//        }
//
//        inflater.end();
//
//        return byteArrayOutputStream.toByteArray();

        Inflater inflater = new Inflater();
        inflater.setInput(data);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        while (!inflater.finished()) {
            int decompressedSize = inflater.inflate(buffer);
            outputStream.write(buffer, 0, decompressedSize);
        }

        return outputStream.toByteArray();
    }
}
