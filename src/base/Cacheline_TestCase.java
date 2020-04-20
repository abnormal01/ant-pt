package base;

/**
 * 缓存行
 * <p>
 *     一个缓存行为64byte，比较在同一缓存行和不同缓存行的速度
 *     根据缓存一致性协议，一个线程更新缓存行的数据会通知其他操作该缓存行的线程从主内存重新读取
 * </p>
 *
 * @author liyue
 * @create 2020-04-20 10:29
 */
public class Cacheline_TestCase {
    static class SameCacheline {
        volatile long arr[] = new long[2];
        void test() throws Exception {
            Thread t1 = new Thread(() -> {
                for (int i = 0; i < 10000_0000L; i++) {
                    arr[0] = i;
                }
            }, "t1");

            Thread t2 = new Thread(() -> {
                for (int i = 0; i < 10000_0000L; i++) {
                    arr[0] = i;
                }
            }, "t2");

            long start = System.nanoTime();
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            System.out.println("same cache line：" + (System.nanoTime() - start));
        }
    }

    static class DifferCacheline {
        volatile long arr[] = new long[16];
        void test() throws Exception {
            Thread t1 = new Thread(() -> {
                for (int i = 0; i < 10000_0000L; i++) {
                    arr[0] = i;
                }
            }, "t1");

            Thread t2 = new Thread(() -> {
                for (int i = 0; i < 10000_0000L; i++) {
                    arr[15] = i;
                }
            }, "t2");

            long start = System.nanoTime();
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            System.out.println("differ cache line：" + (System.nanoTime() - start));
        }
    }

    public static void main(String[] args) throws Exception {
        SameCacheline sameCacheline = new SameCacheline();
        sameCacheline.test();
        DifferCacheline differCacheline = new DifferCacheline();
        differCacheline.test();
    }
}
