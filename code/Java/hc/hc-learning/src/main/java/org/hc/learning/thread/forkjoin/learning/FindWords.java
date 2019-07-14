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

    public FindWords(File path, String keyWords) {
        this.path = path;
        this.keyWords = keyWords;

        if (keyWords == null || "".equals(keyWords)) {
            System.err.println("无效关键字");
        }
    }

    @Override
    protected void compute() {
        ArrayList<FindWords> subTasks = new ArrayList<>();

        File[] files = path.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    subTasks.add(new FindWords(file, keyWords));
                } else {
                    // real service
                    findWords(file);
                }
                if (!subTasks.isEmpty()) {
                    // 在当前的ForkJoinPool上调度所有的子任务
                    // 多路树结构
                    for (FindWords subTask : invokeAll(subTasks)) {
                        subTask.join();
                    }
                }
            }
        }
    }

    public void findWords(File file) {
        try(FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
        ){
            StringBuilder builder = null;
            String line = null;
            // 文件行数
            int index = 1;
            while ((line = reader.readLine()) != null){
                if (line.contains(keyWords)) {
                    builder = new StringBuilder(path.getAbsolutePath());
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
        FindWords task = new FindWords(file, keyWords);
        pool.invoke(task);
    }
}
