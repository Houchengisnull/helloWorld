package org.hc.learning.net.ftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class QuickFTPClient implements FTPService{

    private static FTPClient client;
    private static volatile QuickFTPClient instance;
    private String host;
    private int port;
    private String username;
    private String password;
    private String encoding;

    public QuickFTPClient(String host, int port, String username, String password, String encoding) {
        // init Params
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.encoding = encoding;
        // init FTPClient
        client = new FTPClient();
        // 将FTP服务的发送命令与接受信息打印到控制台
        /*client.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));*/
        try {
            client.connect(host, port);
            int reply = client.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                // connect failed
                log.error("connection error!Please ensure network is connected!And check ip and port!");
            }
            client.login(username, password);
            client.setFileType(FTP.BINARY_FILE_TYPE);
            client.enterLocalPassiveMode();
            if (encoding.equalsIgnoreCase(UTF8)) { // pattern: UTF-8(忽略大小写)
                client.sendCommand("OPTS UTF8", "ON");
            }
            client.setControlEncoding(encoding);
        } catch (IOException e) {
            log.debug(e.getMessage(), e);
        }
    }

    @Override
    public byte[] download(String downloadPath) {
        InputStream input = null;
        byte[] bytes = null;
        try {
            String filepath = FTPService.getFileNameAsISO88591(downloadPath, encoding);
            input = client.retrieveFileStream(filepath);
            bytes = FTPService.read(input);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return bytes;
    }

    @Override
    public boolean upload(String storePath, byte[] bytes) {
        boolean result = false;
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        String filepath = null;
        try {
            filepath = FTPService.getFileNameAsISO88591(storePath, encoding);
            result = client.storeFile(filepath, input);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public boolean delete(String deletePath) {
        boolean result = false;
        try {
            String filepath = FTPService.getFileNameAsISO88591(deletePath, encoding);
            result = client.deleteFile(deletePath);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public boolean isExistDirectory(String directory) {
        boolean result = false;
        try {
            client.changeToParentDirectory();
            result = client.changeWorkingDirectory(directory);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public boolean makeDirectory(String directory) {
        boolean result = false;
        try {
            client.changeToParentDirectory();
            result = client.makeDirectory(directory);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public List<FTPFile> list(String path) {
        FTPFile[] ftpFiles = null;
        List<FTPFile> list = null;
        try {
            ftpFiles = client.listFiles(path);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        if (ftpFiles != null) {
            list = new ArrayList<>();
            for (FTPFile ftpFile : ftpFiles) {
                list.add(ftpFile);
            }
        }

        return list;
    }

    @Override
    public List<FTPFile> listTree(String path) {
        String currentPath = path;
        List<FTPFile> result = new ArrayList<>();
        LinkedList<String> stack = new LinkedList<>();
        do {
            if (stack.size() > 0) {
                currentPath = stack.pop();
            }
            log.debug("list {}", currentPath);
            List<FTPFile> list = list(currentPath);

            for (FTPFile ftpFile : list) {
                if (ftpFile.isFile()) {
                    log.debug("found file: {}", currentPath + "/" + ftpFile.getName());
                    result.add(ftpFile);
                } else {
                    stack.push(currentPath + "/" + ftpFile.getName());
                }
            }
        } while (stack.size() > 0);
        return result;
    }

}
