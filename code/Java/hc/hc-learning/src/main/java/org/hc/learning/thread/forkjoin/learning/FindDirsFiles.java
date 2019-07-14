package org.hc.learning.thread.forkjoin.learning;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * 无返回值的分治编程
 * 遍历指定目录（含子目录）查找指定类型文件
 */
public class FindDirsFiles extends RecursiveAction {

    private String fileType;
    private File path;

    public FindDirsFiles(File path, String fileType) {
        this.path = path;
        this.fileType = fileType;
    }

    /**
     * 执行任务
     */
    @Override
    protected void compute() {
        List<FindDirsFiles> subTasks = new ArrayList<>();

        File[] files = path.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 对每个子目录都新建一个子任务
                    subTasks.add(new FindDirsFiles(file, fileType));
                } else {
                    // System.out.println(file.getAbsolutePath());
                    // 遇到文件 检查
                    if (file.getAbsolutePath().endsWith(fileType)) {
                       System.out.println("文件:" + file.getAbsolutePath());
                    }
                }
            }
            if (!subTasks.isEmpty()) {
                // 在当前的 ForkJoinPool 上调度所有的子任务。
                for (FindDirsFiles subTask : invokeAll(subTasks)) {
                    subTask.join(); // 获取子任务返回值
                }
            }
        };
    }

    public static void main(String [] args){
        long timeVar = System.currentTimeMillis();
        // 用一个 ForkJoinPool 实例调度总任务
        ForkJoinPool pool = new ForkJoinPool();
        FindDirsFiles task = new FindDirsFiles(new File("C:\\Users\\60993\\Desktop\\helloWorld\\documents\\C++"), "md");

        /**
         * 异步提交
         * 与主线程中工作并行执行
         */
        pool.execute(task);
        // 同步提交
        // pool.invoke(task);
        // 有返回值
        // pool.submit(task);

        /*主线程做自己的业务工作*/
        System.out.println("Task is Running......");
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int otherWork = 0;

        for(int i=0;i<100;i++){
            otherWork = otherWork+i;
        }
        System.out.println("Main Thread done sth......,otherWork="
                +otherWork);
        // 阻塞方法
        // **Fork-Join任务完成后才执行之后代码 sout("Task end")**
        task.join();
        System.out.println("Task end");
        System.out.println("任务耗时:" + (System.currentTimeMillis() - timeVar) + "ms");
    }
}
