package com.example.myapp.activity;

import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import com.example.myapp.R;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by renren on 16/5/28.
 * blog address: http://blog.csdn.net/lonelyroamer/article/details/7949969
 */
public class MutilThreadTestActivity extends BaseActivity {
    private TextView tv;

    @Override
    protected void initView() {
        tv = (TextView) findViewById(R.id.tv);

        /**创建Thread的方式**/
        //1. 继承Thread类，重写)方法，然后直接new这个对象的实例，创建一个线程的实例。然后调用start()方法启动线程
        Thread thread1 = new Thread(){
            @Override
            public void run() {
                super.run();
                System.out.println("thread1 start!!!");
            }
        };
        thread1.start();
        //2. 实现Runnable接口，重写run()方法，然后调用new Thread(runnable)的方式创建一个线程，然后调用start()方法启动线程其实看Thread的源文件，发现它也是实现了Runnable接口的。
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread2 start!!!");
            }
        });
        thread2.start();
        /**sleep()方法**/
        //sleep是静态方法，最好不要用Thread的实例对象调用它，因为它睡眠的始终是当前正在运行的线程，而不是调用它的线程对象，它只对正在运行状态的线程对象有效
        for(int i=0;i<10;i++){
            System.out.println("main thread sleep"+i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //Java线程调度是Java多线程的核心，只有良好的调度，才能充分发挥系统的性能，提高程序的执行效率。但是不管程序员怎么编写调度，只能最大限度的影响线程执行的次序，而不能做到精准控制。
        // 因为使用sleep方法之后，线程是进入阻塞状态的，只有当睡眠的时间结束，才会重新进入到就绪状态，而就绪状态进入到运行状态，是由系统控制的，我们不可能精准的去干涉它，
        // 所以如果调用Thread.sleep(1000)使得线程睡眠1秒，可能结果会大于1秒。
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<3;i++){
                    System.out.println("thread3 " + i + "次执行！");
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread3.start();
        Thread thread4 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<3;i++){
                    System.out.println("thread4 " + i + "次执行！");
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread4.start();
        /**yield()方法**/
        //yield()方法和sleep()方法有点相似，它也是Thread类提供的一个静态的方法，它也可以让当前正在执行的线程暂停，让出cpu资源给其他的线程。
        //但是和sleep()方法不同的是，它不会进入到阻塞状态，而是进入到就绪状态.
        //yield()方法只是让当前线程暂停一下，重新进入就绪的线程池中，让系统的线程调度器重新调度器重新调度一次，
        //完全可能出现这样的情况：当某个线程调用yield()方法之后，线程调度器又将其调度出来重新进入到运行状态执行。
        //实际上，当某个线程调用了yield()方法暂停之后，优先级与当前线程相同，或者优先级比当前线程更高的就绪状态的线程更有可能获得执行的机会，当然，只是有可能，因为我们不可能精确的干涉cpu调度线程。
        new MyYieldThread("低级",1).start();
        new MyYieldThread("中级",5).start();
        new MyYieldThread("高级",10).start();
        /**
         * 关于sleep()方法和yield()方的区别如下：
         ①、sleep方法暂停当前线程后，会进入阻塞状态，只有当睡眠时间到了，才会转入就绪状态。而yield方法调用后 ，是直接进入就绪状态，所以有可能刚进入就绪状态，又被调度到运行状态。
         ②、sleep方法声明抛出了InterruptedException，所以调用sleep方法的时候要捕获该异常，或者显示声明抛出该异常。而yield方法则没有声明抛出任务异常。
         ③、sleep方法比yield方法有更好的可移植性，通常不要依靠yield方法来控制并发线程的执行。**/

        /**join()方法**/
        //线程的合并的含义就是将几个并行线程的线程合并为一个单线程执行，应用场景是当一个线程必须等待另一个线程执行完毕才能执行时
        final Thread thread5 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    System.out.println("thread5 run " + i + "times");
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread5.start();

        Thread thread6 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    thread5.join(50);//将thread5加入到thread6后面，不过如果thread5在1毫秒时间内没执行完，则thread6便不再等待它执行完，进入就绪状态，等待cpu调度
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for(int i=0;i<10;i++){
                    System.out.println("thread6 run " + i + "times");
                }
            }
        });
        thread6.start();
        /**TODO 守护线程，没搞懂**/
        /**正确结束线程**/
        //Thread.stop()、Thread.suspend、Thread.resume、Runtime.runFinalizersOnExit这些终止线程运行的方法已经被废弃了，使用它们是极端不安全的！
        //想要安全有效的结束一个线程，可以使用下面的方法。
        //1、正常执行完run方法，然后结束掉
        //2、控制循环条件和判断条件的标识符来结束掉线程
        Thread thread7 = new Thread(new Runnable() {
            boolean flag = true;
            int i = 0;
            @Override
            public void run() {
                while (flag){
                    System.out.println("thread7 run " + i + "times");
                    i ++;
                    if(i == 10){
                        flag = false;
                    }
                }
            }
        });
        thread7.start();
        //3、使用interrupt结束一个线程。
        //使用第2中方法的标识符来结束一个线程，是一个不错的方法，但是如果，该线程是处于sleep、wait、join的状态的时候，while循环就不会执行，那么我们的标识符就无用武之地了，当然也不能再通过它来结束处于这3种状态的线程了。
        /**守护线程**/
        //其实User Thread(用户)线程和Daemon Thread(守护)守护线程本质上来说去没啥区别的，唯一的区别之处就在虚拟机的离开：如果User Thread全部撤离，那么Daemon Thread也就没啥线程好服务的了，所以虚拟机也就退出了。
        //守护线程并非虚拟机内部可以提供，用户也可以自行的设定守护线程，方法：public final void setDaemon(boolean on) ；
        //1）、thread.setDaemon(true)必须在thread.start()之前设置，否则会跑出一个IllegalThreadStateException异常。你不能把正在运行的常规线程设置为守护线程。  （备注：这点与守护进程有着明显的区别，守护进程是创建后，让进程摆脱原会话的控制+让进程摆脱原进程组的控制+让进程摆脱原控制终端的控制；所以说寄托于虚拟机的语言机制跟系统级语言有着本质上面的区别）
        //2）、 在Daemon线程中产生的新线程也是Daemon的。  （这一点又是有着本质的区别了：守护进程fork()出来的子进程不再是守护进程，尽管它把父进程的进程相关信息复制过去了，但是子进程的进程的父进程不是init进程，所谓的守护进程本质上说就是“父进程挂掉，init收养，然后文件0,1,2都是/dev/null，当前目录到/”）
        //3）、不是所有的应用都可以分配给Daemon线程来进行服务，比如读写操作或者计算逻辑。因为在Daemon Thread还没来的及进行操作时，虚拟机可能已经退出了。
        /**
         * 多线程引起的数据访问安全问题
         */
        //A,B同时取钱
        Account account = new Account();
        new Thread(new T1(account,"A")).start();
        new Thread(new T1(account,"B")).start();
        /**
         * 死锁
         */
        //A从账户1取钱，存到账户2去
        //B从账户2取钱，存到账户1去
        Account account1 = new Account("account1");
        Account account2 = new Account("account2");
        new Thread(new T2(account1,account2,"dead-lock-thread-1")).start();
        new Thread(new T2(account2,account1,"dead-lock-thread-2")).start();
        /**
         * 线程的协调操作
         */
        // 生产者和消费者的问题
        // 比如有生产者不断的生产馒头，放入一个篮子里，而消费者不断的从篮子里拿馒头吃。
        // 并且，当篮子满的时候，生产者通知消费者来吃馒头，并且自己等待不在生产馒头。
        // 当篮子没满的的时候，由消费者通知生产者生产馒头。这样不断的循环。
        Storage storage = new Storage();
        new Producer(storage,10,"xz-p-1").start();
        new Producer(storage,5,"xz-p-2").start();
        new Producer(storage,15,"xz-p-3").start();
        new Producer(storage,20,"xz-p-4").start();
        new Producer(storage,10,"xz-p-5").start();
        new Producer(storage,5,"xz-p-6").start();
        new Producer(storage,15,"xz-p-7").start();
        new Producer(storage,30,"xz-p-8").start();
        new Customer(storage,10,"xz-c-1").start();
        new Customer(storage,6,"xz-c-2").start();
        //Obj.wait()，与Obj.notify()必须要与synchronized(Obj)一起使用，也就是wait,与notify是针对已经获取了Obj锁进行操作，从语法角度来说就是Obj.wait(),Obj.notify必须在synchronized(Obj){...}语句块内。
        // 从功能上来说wait就是说线程在获取对象锁后，主动释放对象锁，同时本线程休眠。直到有其它线程调用对象的notify()唤醒该线程，才能继续获取对象锁，并继续执行。相应的notify()就是对对象锁的唤醒操作。
        // 但有一点需要注意的是notify()调用后，并不是马上就释放对象锁的，而是在相应的synchronized(){}语句块执行结束，自动释放锁后，JVM会在wait()对象锁的线程中随机选取一线程，赋予其对象锁，唤醒线程，继续执行。
        // 这样就提供了在线程间同步、唤醒的操作。
        // Thread.sleep()与Object.wait()二者都可以暂停当前线程，释放CPU控制权，主要的区别在于Object.wait()在释放CPU同时，释放了对象锁的控制。
        Storage2 storage2 = new Storage2();
        new Producer2(storage2).start();
        new Customer2(storage2).start();
        new Producer2(storage2).start();
        new Producer2(storage2).start();
        new Customer2(storage2).start();
        /**
         * 线程池
         * 1、public static ExecutorService newFixedThreadPool(int nThreads)：创建一个可重用的、具有固定线程数的线程池。
         * 2、public static ExecutorService newSingleThreadExecutor()：创建一个只有单线程的线程池，它相当于newFixedThreadPool方法是传入的参数为1
         * 3、public static ExecutorService newCachedThreadPool()：创建一个具有缓存功能的线程池，系统根据需要创建线程，这些线程将会被缓存在线程池中。
         * 4、public static ScheduledExecutorService newSingleThreadScheduledExecutor：创建只有一条线程的线程池，他可以在指定延迟后执行线程任务
         * 5、public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize)：创建具有指定线程数的线程池，它可以再指定延迟后执行线程任务，
         * corePoolSize指池中所保存的线程数，即使线程是空闲的也被保存在线程池内。
         */
        ExecutorService pool = Executors.newFixedThreadPool(5);//创建一个固定大小为5的线程池
        for(int i=0;i<10;i++){
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "正在执行");
                }
            });
        }
        //延迟线程池
        ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(6);
        for(int i=0;i<4;i++){
            scheduledPool.submit(new Runnable() { //立即执行的
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "正在执行...");
                }
            });
        }
        scheduledPool.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "正在执行...");
            }
        },1000, TimeUnit.MILLISECONDS); //延时1秒执行
        scheduledPool.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "正在执行...");
            }
        },1000, TimeUnit.MILLISECONDS); //延时1秒执行
        scheduledPool.shutdown();
    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.activity_mutil_thread;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {

    }

    class MyYieldThread extends Thread{

        public MyYieldThread(String name, int pro) {
            super(name);// 设置线程的名称
            this.setPriority(pro);// 设置优先级
        }

        @Override
        public void run() {
            for (int i = 0; i < 30; i++) {
                System.out.println(this.getName() + "线程第" + i + "次执行！");
                if (i % 5 == 0)
                    Thread.yield();
            }
        }
    }

    class T1 implements Runnable{
        private Account account;
        private String threadName;
        public T1(Account account,String threadName){
            this.account = account;
            this.threadName = threadName;
        }
        @Override
        public void run() {
            System.out.println( threadName + "去取钱了");

            synchronized (account){
                System.out.println(threadName + "开始取钱了" );
                if(account.money > 3000){
                    System.out.println(threadName + "取钱时账户余额 ：" + account.money);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    account.money = account.money - 3000;
                    System.out.println(threadName + "取出3000元，账户还剩余" + account.money + "元");
                }else{
                    System.out.println(threadName + "取钱余额不足");
                }
            }
        }
    }

    class T2 implements Runnable{
        private Account outMoneyAccount;
        private Account saveMoneyAccount;
        private String threadName;
        public T2(Account outMoneyAccount,Account saveMoneyAccount,String threadName) {
            this.outMoneyAccount=outMoneyAccount;
            this.saveMoneyAccount=saveMoneyAccount;
            this.threadName = threadName;
        }
        public void run() {
            System.out.println(threadName + "去账户" + outMoneyAccount.accountName + "取钱了,余额为:" + outMoneyAccount.money);
            synchronized (outMoneyAccount) {
                System.out.println(threadName + "开始取账户" + outMoneyAccount.accountName + "的钱了,余额为：" + outMoneyAccount.money);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                outMoneyAccount.money = outMoneyAccount.money - 3000;
                System.out.println(threadName + "完成账户" + outMoneyAccount.accountName + "的取钱操作了,余额为：" + outMoneyAccount.money);
                //放在这里就产生了死锁
                System.out.println(threadName + "去账户" + saveMoneyAccount.accountName + "存钱了,余额为：" + saveMoneyAccount.money);
                synchronized (saveMoneyAccount) {
                    System.out.println(threadName + "开始向账户" + saveMoneyAccount.accountName + "存钱了");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    saveMoneyAccount.money = saveMoneyAccount.money + 3000;
                    System.out.println(threadName + "完成账户" + saveMoneyAccount.accountName + "的存钱操作了,余额为：" + saveMoneyAccount.money);
                }

            }
            //放在这里就解决在死锁
//            System.out.println(threadName + "去账户" + saveMoneyAccount.accountName + "存钱了,余额为：" + saveMoneyAccount.money);
//            synchronized (saveMoneyAccount) {
//                System.out.println(threadName + "开始向账户" + saveMoneyAccount.accountName + "存钱了");
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                saveMoneyAccount.money = saveMoneyAccount.money + 3000;
//                System.out.println(threadName + "完成账户" + saveMoneyAccount.accountName + "的存钱操作了,余额为：" + saveMoneyAccount.money);
//            }
        }
    }

    class Account{
        public int money = 5000;
        public String accountName;
        public Account(){
            accountName = "account";
        }
        public Account(String accountName){
            this.accountName = accountName;
        }
    }

    class Producer extends Thread{
        private Storage storage;
        private int num; // 生产的数量
        private String threadName;

        public Producer(Storage storage,int num,String threadName){
            this.storage = storage;
            this.num = num;
            this.threadName = threadName;
        }

        @Override
        public void run() {
            super.run();
            System.out.println(threadName + "准备进行生产了");
            synchronized (storage.list){
                while(storage.list.size() + num > storage.MAX_SIZE){
                    System.out.println(threadName + "暂时不能执行生产任务，因为要生产" + num +"个，但是仓库现在有" + storage.list.size() + "个");
                    try {
                        storage.list.wait(); // 放弃锁的持有
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for(int i = 0;i<num;i++){
                    storage.list.add(new Object());
                }
                System.out.println(threadName + "生产了" + num + "个，现在仓库有" + storage.list.size() + "个");
                storage.list.notifyAll(); // 通知其他需要list持有的线程一起来抢占list
            }
        }
    }

    class Customer extends Thread{
        private Storage storage;
        private int num; // 消费的数量
        private String threadName;


        public Customer(Storage storage,int num,String threadName){
            this.storage = storage;
            this.num = num;
            this.threadName = threadName;
        }
        @Override
        public void run() {
            super.run();
            System.out.println(threadName + "准备进行消费了");
            synchronized (storage.list){
                while(storage.list.size() - num < 0){
                    System.out.println(threadName + "暂时不能执行消费任务，因为要消费" + num +"个，但是仓库现在只有" + storage.list.size() + "个");
                    try {
                        storage.list.wait(); // 放弃锁的持有
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for(int i = 0;i<num;i++){
                    storage.list.remove();
                }
                System.out.println(threadName + "消费了" + num + "个，现在仓库有" + storage.list.size() + "个");
                storage.list.notifyAll(); // 通知其他需要list持有的线程一起来抢占list
            }
        }
    }

    class Storage{
        public final int MAX_SIZE = 100;
        public LinkedList<Object> list = new LinkedList<>();
    }

    /**
     * 正确的生产者消费者模式
     */
    class Storage2{
        private final int MAX_SIZE = 100;
        private LinkedList<String> list = new LinkedList<>();

        public synchronized void push(String s){
            while (list.size() + 1 > MAX_SIZE){
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~仓库Storage2满了放不进去了~~~~~~~~~~~~~~~~~~~~~~~~");
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.add(s);
            System.out.println("生产了" + s + "放进仓库Storage2");
            notifyAll();
        }

        public synchronized void pop(){
            while (list.size() - 1 <0){
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!仓库Storage2为空不能消费!!!!!!!!!!!!!!!!!!!!!!");
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("从仓库Storage2消费了" + list.getLast());
            list.remove();
            notifyAll();
        }
    }

    class Producer2 extends Thread{
        private Storage2 mStorage;

        public Producer2(Storage2 storage2){
            this.mStorage = storage2;
        }

        @Override
        public void run() {
            super.run();
            for(int i=0;i<2000;i++){
                mStorage.push("产品" + i);
            }
        }
    }

    class Customer2 extends Thread{
        private Storage2 mStorage;

        public Customer2(Storage2 storage2){
            this.mStorage = storage2;
        }
        @Override
        public void run() {
            super.run();
            for(int i=0;i<2000;i++){
                mStorage.pop();
            }
        }
    }
}
