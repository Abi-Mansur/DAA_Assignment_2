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

    private void bubbleDown(int index) {
        while (index < size) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < size) {
                metrics.steps += 2;
                metrics.comparisons++;
                if (heap[left] < heap[smallest]) smallest = left;
            }

            if (right < size) {
                metrics.steps += 2;
                metrics.comparisons++;
                if (heap[right] < heap[smallest]) smallest = right;
            }

            if (smallest != index) {
                swap(index, smallest);
                index = smallest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
        metrics.moves += 3;
    }

    private void grow() {
        int[] newHeap = new int[heap.length * 2];
        for (int i = 0; i < size; i++) {
            newHeap[i] = heap[i];
            metrics.steps++;
            metrics.moves++;
        }
        heap = newHeap;
    }

    public int size() {
        return size;
    }

}