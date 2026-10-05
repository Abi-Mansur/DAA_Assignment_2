import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class BenchmarkRunner {
    private static final int[] SIZES = {100, 1000, 10000, 100000};
    private static final int RUNS = 5;

    public static void main(String[] args) {
        File resultsDir = new File("results");
        if (!resultsDir.exists()) resultsDir.mkdirs();

        try (PrintWriter writer = new PrintWriter(new FileWriter("results/results.csv"))) {
            writer.println("workload,variant,structure,n,time_ms,steps,moves,comparisons");

            for (int n : SIZES) {
                runW1(writer, n);
                runW2(writer, n);
                runW3(writer, n, "head");
                runW3(writer, n, "middle");
                runW4(writer, n);
            }
            System.out.println("Benchmark completed. Results saved to results/results.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void runW1(PrintWriter writer, int n) {

        Metrics mArr = new Metrics();
        long[] timesArr = new long[RUNS];
        for (int r = 0; r < RUNS + 1; r++) {
            DynamicArray arr = new DynamicArray(mArr);
            Random rand = new Random(42);
            for (int i = 0; i < n; i++) arr.add(rand.nextInt());
            mArr.reset();
            long start = System.currentTimeMillis();
            for (int i = 0; i < 10000; i++) arr.get(rand.nextInt(n));
            long elapsed = System.currentTimeMillis() - start;
            if (r > 0) timesArr[r - 1] = elapsed;
        }
        Arrays.sort(timesArr);
        writeRow(writer, "W1", "-", "DynamicArray", n, timesArr[RUNS / 2], mArr);

        Metrics mList = new Metrics();
        long[] timesList = new long[RUNS];
        for (int r = 0; r < RUNS + 1; r++) {
            MyLinkedList list = new MyLinkedList(mList);
            Random rand = new Random(42);
            for (int i = 0; i < n; i++) list.add(rand.nextInt());
            mList.reset();
            long start = System.currentTimeMillis();
            for (int i = 0; i < 10000; i++) list.get(rand.nextInt(n));
            long elapsed = System.currentTimeMillis() - start;
            if (r > 0) timesList[r - 1] = elapsed;
        }
        Arrays.sort(timesList);
        writeRow(writer, "W1", "-", "MyLinkedList", n, timesList[RUNS / 2], mList);
    }

    private static void runW2(PrintWriter writer, int n) {
        Metrics mArr = new Metrics();
        long[] timesArr = new long[RUNS];
        for (int r = 0; r < RUNS + 1; r++) {
            DynamicArray arr = new DynamicArray(mArr);
            Random rand = new Random(42);
            int[] values = new int[n];
            for (int i = 0; i < n; i++) {
                values[i] = rand.nextInt();
                arr.add(values[i]);
            }
            mArr.reset();
            long start = System.currentTimeMillis();
            for (int i = 0; i < 1000; i++) {
                int query = (i % 2 == 0) ? values[rand.nextInt(n)] : rand.nextInt() + 100000000;
                arr.contains(query);
            }
            long elapsed = System.currentTimeMillis() - start;
            if (r > 0) timesArr[r - 1] = elapsed;
        }
        Arrays.sort(timesArr);
        writeRow(writer, "W2", "-", "DynamicArray", n, timesArr[RUNS / 2], mArr);

        Metrics mList = new Metrics();
        long[] timesList = new long[RUNS];
        for (int r = 0; r < RUNS + 1; r++) {
            MyLinkedList list = new MyLinkedList(mList);
            Random rand = new Random(42);
            int[] values = new int[n];
            for (int i = 0; i < n; i++) {
                values[i] = rand.nextInt();
                list.add(values[i]);
            }
            mList.reset();
            long start = System.currentTimeMillis();
            for (int i = 0; i < 1000; i++) {
                int query = (i % 2 == 0) ? values[rand.nextInt(n)] : rand.nextInt() + 100000000;
                list.contains(query);
            }
            long elapsed = System.currentTimeMillis() - start;
            if (r > 0) timesList[r - 1] = elapsed;
        }
        Arrays.sort(timesList);
        writeRow(writer, "W2", "-", "MyLinkedList", n, timesList[RUNS / 2], mList);
    }

    private static void runW3(PrintWriter writer, int n, String variant) {

        Metrics mArr = new Metrics();
        long[] timesArr = new long[RUNS];
        for (int r = 0; r < RUNS + 1; r++) {
            DynamicArray arr = new DynamicArray(mArr);
            Random rand = new Random(42);
            for (int i = 0; i < n; i++) arr.add(rand.nextInt());
            mArr.reset();
            long start = System.currentTimeMillis();
            int idx = variant.equals("head") ? 0 : arr.size() / 2;
            for (int i = 0; i < 1000; i++) arr.add(idx, rand.nextInt());
            for (int i = 0; i < 1000; i++) arr.remove(idx);
            long elapsed = System.currentTimeMillis() - start;
            if (r > 0) timesArr[r - 1] = elapsed;
        }
        Arrays.sort(timesArr);
        writeRow(writer, "W3", variant, "DynamicArray", n, timesArr[RUNS / 2], mArr);


        Metrics mList = new Metrics();
        long[] timesList = new long[RUNS];
        for (int r = 0; r < RUNS + 1; r++) {
            MyLinkedList list = new MyLinkedList(mList);
            Random rand = new Random(42);
            for (int i = 0; i < n; i++) list.add(rand.nextInt());
            mList.reset();
            long start = System.currentTimeMillis();
            int idx = variant.equals("head") ? 0 : list.size() / 2;
            for (int i = 0; i < 1000; i++) list.add(idx, rand.nextInt());
            for (int i = 0; i < 1000; i++) list.remove(idx);
            long elapsed = System.currentTimeMillis() - start;
            if (r > 0) timesList[r - 1] = elapsed;
        }
        Arrays.sort(timesList);
        writeRow(writer, "W3", variant, "MyLinkedList", n, timesList[RUNS / 2], mList);
    }

    private static void runW4(PrintWriter writer, int n) {
        Metrics mHeap = new Metrics();
        long[] times = new long[RUNS];
        for (int r = 0; r < RUNS + 1; r++) {
            MinHeap heap = new MinHeap(n, mHeap);
            Random rand = new Random(42);
            mHeap.reset();
            long start = System.currentTimeMillis();
            for (int i = 0; i < n; i++) heap.insert(rand.nextInt());
            for (int i = 0; i < n; i++) heap.extractMin();
            long elapsed = System.currentTimeMillis() - start;
            if (r > 0) times[r - 1] = elapsed;
        }
        Arrays.sort(times);
        writeRow(writer, "W4", "-", "MinHeap", n, times[RUNS / 2], mHeap);
    }

    private static void writeRow(PrintWriter w, String wl, String var, String struct, int n, long time, Metrics m) {
        w.printf("%s,%s,%s,%d,%d,%d,%d,%d%n", wl, var, struct, n, time, m.steps, m.moves, m.comparisons);
    }
}