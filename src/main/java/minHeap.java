public class MinHeap {
    private int[] heap;
    private int size;
    private Metrics metrics;

    public MinHeap(int capacity, Metrics metrics) {
        this.heap = new int[Math.max(capacity, 10)];
        this.size = 0;
        this.metrics = metrics;
    }

    public void insert(int x) {
        if (size == heap.length) {
            grow();
        }
        heap[size] = x;
        metrics.moves++;
        bubbleUp(size);
        size++;
    }

    public int peekMin() {
        if (size == 0) throw new IllegalStateException();
        metrics.steps++;
        return heap[0];
    }