package org.hc.learning.net.ftp;

import org.apache.commons.net.ftp.FTPFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface FTPService {

    static final String ISO88591 = "ISO-8859-1";
    static final String GBK = "GBK";
    static final String UTF8 = "UTF-8";

    // FTPService getInstance(String host, int port, String username, String password, String encoding);

    /**
     * 下载
     * @param downloadPath 下载路径
     * @return
     */
    byte[] download(String downloadPath);

    /**
     * 上传
     * @param storePath 保存路径
     * @param bytes 文件流
     * @return
     */
    boolean upload(String storePath, byte[] bytes);

    /**
     * 删除
     * @param deletePath 删除路径
     * @return
     */
    boolean delete(String deletePath);

    /**
     * 文件是否存在
     * @param directory
     * @return
     */
    boolean isExistDirectory(String directory);

    /**
     * 创建文件夹
     * @param directory
     * @return
     */
    boolean makeDirectory(String directory);

    /**
     * 当前文件/文件夹
     * @return
     */
    List<FTPFile> list(String path);

    /**
     * 内置方法处理控制连接编码
     * @param fileName 文件名
     * @param encoding 编码
     * @return ISO8859-1编码的文件名
     * @throws UnsupportedEncodingException
     */
    default String getFileNameAsISO88591(String fileName, String encoding) throws UnsupportedEncodingException {
        return new String(fileName.getBytes(encoding), ISO88591);
    }

    /**
     * 读取文件流
     * @param input
     * @return
     * @throws IOException
     */
    default byte[] read(InputStream input) throws IOException {
        int i = 0;
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        while ((i = input.read(buffer))>0) {
            output.write(buffer, 0, i);
        }
        output.close();
        return output.toByteArray();
    }
}
