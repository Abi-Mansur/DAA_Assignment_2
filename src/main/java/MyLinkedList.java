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



}