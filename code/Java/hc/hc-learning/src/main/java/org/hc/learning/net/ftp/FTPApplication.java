package org.hc.learning.net.ftp;

import org.apache.commons.net.ftp.FTPFile;

import java.util.List;

public class FTPApplication {

    /**
     * FTP目录结构:
     *  |- Android.md
     *  |- database.transaction.isolation.md
     *  |- Ecplise.md
     *  |- encoding.md
     *  |+ mysql
     *      |- mysql.command.md
     *      |- mysql.md
     *  |+ oracle
     *      |- oracle.体系.md
     *      |- oracle.使用.md
     *  |+ python
     *      |- io.md
     *      |- python.base.md
     *      |- python.docx
     * @param args
     */
    public static void main(String[] args) {
        /*
            正确
         */
        FTPService service = QuickFTPClient.getInstance("127.0.0.1", 21, "ftpuser", "P@ssw0rd", "UTF-8");
        /*
            错误端口
         */
        // QuickFTPClient.getInstance("127.0.0.1", 22, "ftpuser", "P@ssw0rd", "UTF-8");
        /*
            错误的用户
         */
        // QuickFTPClient.getInstance("127.0.0.1", 21, "errorUser", "P@ssw0rd", "UTF-8");
        /*
            错误的密码
         */
        // QuickFTPClient.getInstance("127.0.0.1", 21, "ftpuser", "errorPassword", "UTF-8");

        /**
         * list测试
         */
        // List<FTPFile> list = service.list("/web"); // 效果正确
        // List<FTPFile> list = service.list("web"); // 效果正确
        List<FTPFile> list = service.list("/");
        for (FTPFile ftpFile : list) {
            boolean directory = ftpFile.isDirectory();
            if (directory) {
                System.out.println("found directory:\t" + ftpFile.getName());
            } else {
                System.out.println("found file:\t" + ftpFile.getName());
            }
        }
        /**
         *
         */
        System.out.println("/ is exist:" + service.isExistDirectory("/"));
        System.out.println("/web is exist:" + service.isExistDirectory("/web"));
        System.out.println("/web/tomcat.md is exist:" + service.isExistDirectory("/web/tomcat"));
        System.out.println("/web/tomcat.md is exist:" + service.isExistDirectory("/web/tomcat.md"));
        // System.out.println("/web/non-exist.md is exist:" + service.isExist("/web/tomcat.md"));

        service.makeDirectory("/hello");
        service.makeDirectory("/hello/world");
    }

}
