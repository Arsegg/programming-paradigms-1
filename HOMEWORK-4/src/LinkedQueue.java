public class LinkedQueue extends AbstractQueue {
    private Node head; // = null
    private Node tail; // = null
    protected int size;

    @Override
    public void enqueue(Object o) {
        assert o != null;

        if (size == 0) {
            head = new Node(o, tail);
            tail = head;
        } else if (size == 1) {
            tail = new Node(o);
            head.next = tail;
        } else {
            tail.next = new Node(o);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public Object element() {
        assert size > 0;

        return head.value;
    }


    public int size() {
        return size;
    }

    @Override
    public Object dequeue() {
        assert size > 0;

        Object o = element();
        head = head.next;
        size--;

        return o;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    private class Node {
        Object value;
        Node next;

        public Node(Object value) {
            this.value = value;
        }

        public Node(Object value, Node next) {
            this.value = value;
            this.next = next;
        }
    }
}
