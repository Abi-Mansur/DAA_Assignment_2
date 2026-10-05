public class DynamicArray {
    private int[] data;
    private int size;
    private Metrics metrics;

    public DynamicArray(Metrics metrics) {
        this.data = new int[10];
        this.size = 0;
        this.metrics = metrics;
    }

    public void add(int x) {
        if (size == data.length) {
            grow();
        }
        data[size++] = x;
        metrics.moves++;
    }