public class MyLinkedList {
    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    private Node head;
    private Node tail;
    private int size;
    private Metrics metrics;

    public MyLinkedList(Metrics metrics) {
        this.metrics = metrics;
    }

    public void add(int x) {
        Node newNode = new Node(x);
        metrics.moves++;
        if (head == null) {
            head = newNode;
            tail = newNode;
            metrics.moves++;
        } else {
            tail.next = newNode;
            tail = newNode;
            metrics.moves += 2;
        }
        size++;
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

    public int get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();

        Node current = head;
        metrics.steps++;
        for (int i = 0; i < index; i++) {
            current = current.next;
            metrics.steps++;
        }
        return current.value;
    }

    public boolean contains(int x) {
        Node current = head;
        metrics.steps++;
        while (current != null) {
            metrics.comparisons++;
            if (current.value == x) return true;
            current = current.next;
            metrics.steps++;
        }
        return false;
    }

    public int size() {
        return size;
    }
}