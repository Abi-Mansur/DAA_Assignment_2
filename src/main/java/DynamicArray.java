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

    public void add(int index, int x) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        if (size == data.length) grow();

        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
            metrics.steps++;
            metrics.moves++;
        }
        data[index] = x;
        metrics.moves++;
        size++;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();

        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
            metrics.steps++;
            metrics.moves++;
        }
        size--;
    }

    public int get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        metrics.steps++;
        return data[index];
    }

    public boolean contains(int x) {
        for (int i = 0; i < size; i++) {
            metrics.steps++;
            metrics.comparisons++;
            if (data[i] == x) return true;
        }
        return false;
    }

    private void grow() {
        int[] newData = new int[data.length * 2];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
            metrics.steps++;
            metrics.moves++;
        }
        data = newData;
    }

    public int size() {
        return size;
    }
}