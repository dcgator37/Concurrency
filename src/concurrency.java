import java.util.Random;
import java.util.Arrays;

public class concurrency {
    public static int generateRandomInt() {
        Random rand = new Random();

        return rand.nextInt(11);
    }
    public static void main(String[] args) {
        int SIZE = 200000000;
        int sum = 0;
        int[] numbers = new int[SIZE];

        for (int i = 0; i<SIZE;i++) {
            numbers[i] = generateRandomInt();
        }

        double startTime = System.currentTimeMillis();
        for (int i = 0; i<SIZE;i++) {
            sum += numbers[i];
        }
        double endTime = System.currentTimeMillis();

        System.out.println("Linear time in milliseconds: " + (endTime-startTime) + " Sum: " + sum);

        int nLength = numbers.length;
        int startPos = 0;

        int[] a = Arrays.copyOfRange(numbers,startPos,nLength/5);
        startPos = nLength/5;
        int[] b = Arrays.copyOfRange(numbers, startPos, startPos + nLength/5);
        startPos = nLength/5*2;
        int[] c = Arrays.copyOfRange(numbers, startPos, startPos + nLength/5);
        startPos = nLength/5*3;
        int[] d = Arrays.copyOfRange(numbers, startPos, startPos + nLength/5);
        startPos = nLength/5*4;
        int[] e = Arrays.copyOfRange(numbers, startPos, nLength);

        SumParallelThread t1 = new SumParallelThread(a);
        SumParallelThread t2 = new SumParallelThread(b);
        SumParallelThread t3 = new SumParallelThread(c);
        SumParallelThread t4 = new SumParallelThread(d);
        SumParallelThread t5 = new SumParallelThread(e);

        startTime = System.currentTimeMillis();

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();

        } catch (InterruptedException error) {
            System.out.println("error: " + error.getMessage());
        }

        endTime = System.currentTimeMillis();

        System.out.println("Parallel time in milliseconds: " + (endTime-startTime) + " Sum: " + (t1.getSum() + t2.getSum() + t3.getSum() + t4.getSum() + t5.getSum()));
    }
}
