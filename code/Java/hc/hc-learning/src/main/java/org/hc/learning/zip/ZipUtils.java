package org.hc.learning.zip;

import lombok.SneakyThrows;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Zip工具类 适用简单业务
 */
public class ZipUtils {

    private static byte[] bytes = new byte[1024];

    /**
     * 将InputStream写入压缩流
     * @param input
     */
    @SneakyThrows
    private static void putZipEntry(String filename, InputStream input, ZipOutputStream zip) {
        ZipEntry entry = new ZipEntry(filename);
        zip.putNextEntry(entry);
        int len = -1;
        byte[] bytes = new byte[1024];
        while ((len = input.read(bytes)) != -1) {
            zip.write(bytes, 0 , len);
        }
        zip.closeEntry();
    }

    @SneakyThrows
    private static void putZipEntry(String filename, byte[] data, ZipOutputStream zip) {
        ZipEntry zipEntry = new ZipEntry(filename);
        zip.putNextEntry(zipEntry);
        zip.write(data);
        zip.closeEntry();
    }

    /*public static ZipOutputStream getZip(OutputStream output){
        return new ZipOutputStream(output);
    }

    @SneakyThrows
    public static ZipOutputStream getZip(String filepath) {
        return new ZipOutputStream(new FileOutputStream(filepath));
    }*/

    @SneakyThrows
    public static byte[] getZipByteArray(Map<String, byte[]> map){
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(byteOutput);
        Set<Map.Entry<String, byte[]>> entries = map.entrySet();
        for (Map.Entry<String, byte[]> entry : entries) {
            ZipUtils.putZipEntry(entry.getKey(), entry.getValue(), zip);
        }
        /*zip.finish();*/
        zip.close();

        byteOutput.close();
        return byteOutput.toByteArray();
    }

    @SneakyThrows
    public static byte[] getZipByteArrayFromStream(Map<String, InputStream> map){
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(byteOutput);
        Set<Map.Entry<String, InputStream>> entries = map.entrySet();
        for (Map.Entry<String, InputStream> entry : entries) {
            ZipUtils.putZipEntry(entry.getKey(), entry.getValue(), zip);
        }
        /*zip.finish();*/
        zip.close();

        byteOutput.close();
        return byteOutput.toByteArray();
    }

    /*@SneakyThrows
    @Test
    public void makeZipFile() {
        ZipOutputStream zip = ZipUtils.getZip("C:\\Users\\DHAdmin\\Desktop\\HelloWorld.zip");
        ZipUtils.putZipEntry("hello.txt", "Hello".getBytes(), zip);
        ZipUtils.putZipEntry("hello 1.txt", "Hello".getBytes(), zip);
        ZipUtils.putZipEntry("hello 2.txt", "Hello".getBytes(), zip);
        zip.finish();
        zip.close();
    }

    @SneakyThrows
    @Test
    public void makeZipByte() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ZipOutputStream zip = ZipUtils.getZip(output);
        ZipUtils.putZipEntry("world.txt", "world".getBytes(), zip);
        ZipUtils.putZipEntry("world1.txt", "world".getBytes(), zip);
        ZipUtils.putZipEntry("world2.txt", "world".getBytes(), zip);
        zip.finish();
        zip.close();

        byte[] bytes = output.toByteArray();
        FileOutputStream fileOutput = new FileOutputStream("C:\\Users\\DHAdmin\\Desktop\\world.zip");
        fileOutput.write(bytes);
        fileOutput.close();
    }*/

    @SneakyThrows
    @Test
    public void makeZipByteByMap() {
        HashMap<String, byte[]> map = new HashMap<>();
        map.put("Hello.txt", "Hello".getBytes());
        map.put("Hello A.txt", "Hello A".getBytes());
        map.put("Hello B.txt", "Hello B".getBytes());

        byte[] bytes = getZipByteArray(map);
        FileOutputStream fileOutput = new FileOutputStream("C:\\Users\\DHAdmin\\Desktop\\world.map.zip");
        fileOutput.write(bytes);
        fileOutput.close();
    }

}
