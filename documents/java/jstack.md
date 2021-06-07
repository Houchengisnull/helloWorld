[toc]

- **参考**

  [java命令--jstack 工具](https://www.cnblogs.com/kongzhongqijing/articles/3630264.html)

# 使用

``` shell
jstack $pid
```

## 正常运行的线程堆栈

- **NormalTask**

  ``` java
  import lombok.SneakyThrows;
  import lombok.extern.slf4j.Slf4j;
  
  import java.util.concurrent.ExecutorService;
  import java.util.concurrent.Executors;
  
  @Slf4j
  public class NormalTask implements Runnable {
  
      @SneakyThrows
      @Override
      public void run() {
          int count = 0;
          while (true) {
              log.debug("第{}次执行任务", ++count);
              int sum = 0;
              for (int i = 0; i < 999999999; i++) {
                  sum += i;
              }
              Thread.sleep(1000);
          }
      }
  
      public static void main(String[] args) {
          /*Thread thread = new Thread(new NormalTask());
          thread.start();*/
          ExecutorService service = Executors.newFixedThreadPool(2);
          service.submit(new NormalTask());
      }
  }
  ```

  启动后，通过`jps`与`jstack $pid > normal.dump`将结果保存到`normal.dump`。

  结果如下：

  ``` text
  2021-06-07 17:46:16
  Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.151-b12 mixed mode):
  
  "DestroyJavaVM" #12 prio=5 os_prio=0 tid=0x0000000003775000 nid=0x32c0 waiting on condition [0x0000000000000000]
     java.lang.Thread.State: RUNNABLE
  
  "pool-1-thread-1" #11 prio=5 os_prio=0 tid=0x000000001a6be000 nid=0x3654 waiting on condition [0x000000001acbe000]
     java.lang.Thread.State: TIMED_WAITING (sleeping)
  	at java.lang.Thread.sleep(Native Method)
  	at org.hc.learning.thread.jstack.NormalTask.run(NormalTask.java:22)
  	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
  	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
  	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
  	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
  	at java.lang.Thread.run(Thread.java:748)
  
  "Service Thread" #10 daemon prio=9 os_prio=0 tid=0x0000000019a79800 nid=0x3f30 runnable [0x0000000000000000]
     java.lang.Thread.State: RUNNABLE
  
  "C1 CompilerThread2" #9 daemon prio=9 os_prio=2 tid=0x0000000019a25800 nid=0x4ce4 waiting on condition [0x0000000000000000]
     java.lang.Thread.State: RUNNABLE
  
  "C2 CompilerThread1" #8 daemon prio=9 os_prio=2 tid=0x00000000199de800 nid=0x114 waiting on condition [0x0000000000000000]
     java.lang.Thread.State: RUNNABLE
  
  "C2 CompilerThread0" #7 daemon prio=9 os_prio=2 tid=0x00000000199da000 nid=0x3080 waiting on condition [0x0000000000000000]
     java.lang.Thread.State: RUNNABLE
  
  "Monitor Ctrl-Break" #6 daemon prio=5 os_prio=0 tid=0x00000000199bc000 nid=0x3a24 runnable [0x0000000019e0f000]
     java.lang.Thread.State: RUNNABLE
  	at java.net.SocketInputStream.socketRead0(Native Method)
  	at java.net.SocketInputStream.socketRead(SocketInputStream.java:116)
  	at java.net.SocketInputStream.read(SocketInputStream.java:171)
  	at java.net.SocketInputStream.read(SocketInputStream.java:141)
  	at sun.nio.cs.StreamDecoder.readBytes(StreamDecoder.java:284)
  	at sun.nio.cs.StreamDecoder.implRead(StreamDecoder.java:326)
  	at sun.nio.cs.StreamDecoder.read(StreamDecoder.java:178)
  	- locked <0x00000000d62caea0> (a java.io.InputStreamReader)
  	at java.io.InputStreamReader.read(InputStreamReader.java:184)
  	at java.io.BufferedReader.fill(BufferedReader.java:161)
  	at java.io.BufferedReader.readLine(BufferedReader.java:324)
  	- locked <0x00000000d62caea0> (a java.io.InputStreamReader)
  	at java.io.BufferedReader.readLine(BufferedReader.java:389)
  	at com.intellij.rt.execution.application.AppMainV2$1.run(AppMainV2.java:64)
  
  "Attach Listener" #5 daemon prio=5 os_prio=2 tid=0x00000000183bf000 nid=0x4e38 waiting on condition [0x0000000000000000]
     java.lang.Thread.State: RUNNABLE
  
  "Signal Dispatcher" #4 daemon prio=9 os_prio=2 tid=0x0000000019718800 nid=0x2a54 runnable [0x0000000000000000]
     java.lang.Thread.State: RUNNABLE
  
  "Finalizer" #3 daemon prio=8 os_prio=1 tid=0x0000000018399800 nid=0x3414 in Object.wait() [0x000000001970e000]
     java.lang.Thread.State: WAITING (on object monitor)
  	at java.lang.Object.wait(Native Method)
  	- waiting on <0x00000000d5e08ec8> (a java.lang.ref.ReferenceQueue$Lock)
  	at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:143)
  	- locked <0x00000000d5e08ec8> (a java.lang.ref.ReferenceQueue$Lock)
  	at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:164)
  	at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:209)
  
  "Reference Handler" #2 daemon prio=10 os_prio=2 tid=0x0000000003864000 nid=0x240c in Object.wait() [0x000000001960f000]
     java.lang.Thread.State: WAITING (on object monitor)
  	at java.lang.Object.wait(Native Method)
  	- waiting on <0x00000000d5e06b68> (a java.lang.ref.Reference$Lock)
  	at java.lang.Object.wait(Object.java:502)
  	at java.lang.ref.Reference.tryHandlePending(Reference.java:191)
  	- locked <0x00000000d5e06b68> (a java.lang.ref.Reference$Lock)
  	at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:153)
  
  "VM Thread" os_prio=2 tid=0x0000000018377800 nid=0x2304 runnable 
  
  "GC task thread#0 (ParallelGC)" os_prio=0 tid=0x000000000378a800 nid=0x4d94 runnable 
  
  "GC task thread#1 (ParallelGC)" os_prio=0 tid=0x000000000378c000 nid=0x3b80 runnable 
  
  "GC task thread#2 (ParallelGC)" os_prio=0 tid=0x000000000378d800 nid=0x1140 runnable 
  
  "GC task thread#3 (ParallelGC)" os_prio=0 tid=0x0000000003790000 nid=0xd4c runnable 
  
  "VM Periodic Task Thread" os_prio=2 tid=0x0000000019b0e000 nid=0x41e0 waiting on condition 
  
  JNI global references: 33
  ```

  由上分析，尽管在`ExecutorService service = Executors.newFixedThreadPool(2);`在线程池中启动了两个核心线程，但是在`jstack`只出现了`pool-1-thread-1`。

  处于`Runnable`的仅有`pool-1-thread-1`。

## 自旋运行的线程堆栈

- **NullTask**

  ``` java
  import lombok.extern.slf4j.Slf4j;
  
  import java.util.concurrent.ExecutorService;
  import java.util.concurrent.Executors;
  
  @Slf4j
  public class NullTask implements Runnable{
  
      @Override
      public void run() {
          while (true){
  
          }
      }
  
      public static void main(String[] args) {
          /*Thread thread = new Thread(new NormalTask());
          thread.start();*/
          ExecutorService service = Executors.newFixedThreadPool(2);
          service.submit(new NullTask());
          service.submit(new NullTask());
      }
  }
  ```

  运行之后的`stackdump`信息如下：

  ``` text
  "pool-1-thread-2" #14 prio=5 os_prio=0 tid=0x0000000019942000 nid=0x43b0 runnable [0x000000001a13e000]
     java.lang.Thread.State: RUNNABLE
          at org.hc.learning.thread.jstack.NullTask.run(NullTask.java:13)
          at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
          at java.util.concurrent.FutureTask.run$$$capture(FutureTask.java:266)
          at java.util.concurrent.FutureTask.run(FutureTask.java)
          at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
          at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
          at java.lang.Thread.run(Thread.java:748)
  
  "pool-1-thread-1" #13 prio=5 os_prio=0 tid=0x0000000019944000 nid=0x319c runnable [0x000000001a03e000]
     java.lang.Thread.State: RUNNABLE
          at org.hc.learning.thread.jstack.NullTask.run(NullTask.java:13)
          at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
          at java.util.concurrent.FutureTask.run$$$capture(FutureTask.java:266)
          at java.util.concurrent.FutureTask.run(FutureTask.java)
          at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
          at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
          at java.lang.Thread.run(Thread.java:748)
  
  ```

  可见即使没有日志输出，但是`pool-1-thread-1`与`pool-1-thread-2`同样为`Runnable`状态。

## 死锁的线程堆栈

- **DeadLockTask**

  ``` java
  
  import lombok.SneakyThrows;
  import lombok.extern.slf4j.Slf4j;
  
  import java.util.concurrent.ExecutorService;
  import java.util.concurrent.Executors;
  import java.util.concurrent.locks.ReentrantLock;
  
  @Slf4j
  public class DeadLockTask implements Runnable{
  
      private static final ReentrantLock lockOne = new ReentrantLock();
      private static final ReentrantLock lockTwo = new ReentrantLock();
  
      @SneakyThrows
      @Override
      public void run() {
          int count = 1;
          if (lockOne.tryLock()) {
              Thread.sleep(1000);
          }
          if (lockTwo.tryLock()) {
              Thread.sleep(1000);
          }
          lockOne.lock();
          lockTwo.lock();
          log.info("get 2 locks");
          lockTwo.unlock();
          lockOne.unlock();
      }
  
      public static void main(String[] args) {
          ExecutorService service = Executors.newFixedThreadPool(2);
          service.execute(new DeadLockTask());
          service.execute(new DeadLockTask());
      }
  }
  ```

- **堆栈信息**

  ``` text
  
  Found one Java-level deadlock:
  =============================
  "pool-1-thread-2":
    waiting for ownable synchronizer 0x00000000d68ce880, (a java.util.concurrent.locks.ReentrantLock$NonfairSync),
    which is held by "pool-1-thread-1"
  "pool-1-thread-1":
    waiting for ownable synchronizer 0x00000000d68ce8b0, (a java.util.concurrent.locks.ReentrantLock$NonfairSync),
    which is held by "pool-1-thread-2"
  
  Java stack information for the threads listed above:
  ===================================================
  "pool-1-thread-2":
  	at sun.misc.Unsafe.park(Native Method)
  	- parking to wait for  <0x00000000d68ce880> (a java.util.concurrent.locks.ReentrantLock$NonfairSync)
  	at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
  	at java.util.concurrent.locks.AbstractQueuedSynchronizer.parkAndCheckInterrupt(AbstractQueuedSynchronizer.java:836)
  	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireQueued(AbstractQueuedSynchronizer.java:870)
  	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(AbstractQueuedSynchronizer.java:1199)
  	at java.util.concurrent.locks.ReentrantLock$NonfairSync.lock(ReentrantLock.java:209)
  	at java.util.concurrent.locks.ReentrantLock.lock(ReentrantLock.java:285)
  	at org.hc.learning.thread.jstack.DeadLockTask.run(DeadLockTask.java:26)
  	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
  	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
  	at java.lang.Thread.run(Thread.java:748)
  "pool-1-thread-1":
  	at sun.misc.Unsafe.park(Native Method)
  	- parking to wait for  <0x00000000d68ce8b0> (a java.util.concurrent.locks.ReentrantLock$NonfairSync)
  	at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
  	at java.util.concurrent.locks.AbstractQueuedSynchronizer.parkAndCheckInterrupt(AbstractQueuedSynchronizer.java:836)
  	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireQueued(AbstractQueuedSynchronizer.java:870)
  	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(AbstractQueuedSynchronizer.java:1199)
  	at java.util.concurrent.locks.ReentrantLock$NonfairSync.lock(ReentrantLock.java:209)
  	at java.util.concurrent.locks.ReentrantLock.lock(ReentrantLock.java:285)
  	at org.hc.learning.thread.jstack.DeadLockTask.run(DeadLockTask.java:27)
  	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
  	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
  	at java.lang.Thread.run(Thread.java:748)
  
  Found 1 deadlock.
  ```

  显而易见的出现死锁。