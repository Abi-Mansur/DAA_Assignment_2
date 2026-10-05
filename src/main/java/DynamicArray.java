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

        if (index == size) {
            add(x);
            return;
        }

        Node newNode = new Node(x);
        metrics.moves++;
        if (index == 0) {
            newNode.next = head;
            head = newNode;
            metrics.moves++;
        } else {
            Node current = head;
            metrics.steps++;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
                metrics.steps++;
            }
            newNode.next = current.next;
            current.next = newNode;
            metrics.moves += 2;
        }
        size++;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();

        if (index == 0) {
            head = head.next;
            if (size == 1) tail = null;
            metrics.moves++;
        } else {
            Node current = head;
            metrics.steps++;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
                metrics.steps++;
            }
            current.next = current.next.next;
            if (index == size - 1) tail = current;
            metrics.moves++;
        }
        size--;
    }

}