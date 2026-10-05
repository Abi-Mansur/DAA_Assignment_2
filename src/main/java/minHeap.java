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

    public int extractMin() {
        if (size == 0) throw new IllegalStateException();
        int min = heap[0];
        metrics.steps++;

        heap[0] = heap[size - 1];
        metrics.moves++;
        size--;

        bubbleDown(0);
        return min;
    }

    private void bubbleUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            metrics.steps += 2;
            metrics.comparisons++;

            if (heap[index] < heap[parent]) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }






}