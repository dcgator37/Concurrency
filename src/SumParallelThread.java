public class SumParallelThread extends Thread implements Runnable{
    public SumParallelThread(int[] array) {
        this.array = array;
    }

    public void run() {
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
    }

    public int getSum() {
        return sum;
    }

    private int[] array;
    private int sum;
}
