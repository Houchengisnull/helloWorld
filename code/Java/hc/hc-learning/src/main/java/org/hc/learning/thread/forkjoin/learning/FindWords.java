package org.hc.learning.thread.forkjoin.learning;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * 遍历指定目录(含子目录)查找包含**keyWords**的文件
 * - 输出
 *     - 内容
 *     - 文件名
 *     - 行数
 */
public class FindWords extends RecursiveAction {
    /**
     * 查询关键字
     */
    private String keyWords;
    /**
     * 遍历路径
     */
    private File path;

    private FilenameFilter filenameFilter;

    /**
     * 构造器
     * @param path 遍历路径
     * @param keyWords 关键字
     * @param filenameFilter 文件名称过滤器（仅对文件生效）
     */
    public FindWords(File path, String keyWords, FilenameFilter filenameFilter) {
        this.path = path;
        this.keyWords = keyWords;
        this.filenameFilter = filenameFilter;
        /*if (keyWords == null || "".equals(keyWords)) {
            System.err.println("无效关键字");
        }*/
    }

    @Override
    protected void compute() {
        ArrayList<FindWords> subTasks = new ArrayList<>();
        if (path.isDirectory()){
            File[] files = path.listFiles();
            if (files != null) {
                for (File file : files) {
                    boolean accept = filenameFilter == null || filenameFilter.accept(file, file.getName());
                    if (accept || file.isDirectory()){ // pass directory
                        subTasks.add(new FindWords(file, keyWords, filenameFilter));
                    }
                }
            }
            if (!subTasks.isEmpty()) {
                invokeAll(subTasks);
            }
        } else {
            // real service
            findWords();
        }

    }

    private void findWords() {
        // 查看当前代码根据[文件|文件夹]作为分解的任务
        // System.out.println(Thread.currentThread().getName() + " > " + file.getAbsolutePath());
        try(FileInputStream fileInputStream = new FileInputStream(path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader)
        ){
            StringBuilder builder;
            String line;
            // 文件行数
            int index = 1;
            while ((line = reader.readLine()) != null){
                if (line.contains(keyWords)) {
                    builder = new StringBuilder();
                    builder.append(path.getAbsolutePath());
                    builder.append(":").append(index);
                    builder.append("\t").append(line);
                    System.out.println(builder.toString());
                }
                index++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 关键字
        String keyWords = "同步";
        // 检索路径
        File file = new File("C:\\Users\\60993\\Desktop\\helloWorld\\documents");
        ForkJoinPool pool = new ForkJoinPool();
        /*FindWords task = new FindWords(file, keyWords, new FilenameFilter() {
            @Override
            public synchronized boolean accept(File dir, String name) {
                if (name.contains("Java")) {
                    return true;
                }
                return false;
            }
        });*/
        FindWords task = new FindWords(file, keyWords,null);
        pool.invoke(task);
    }
}
