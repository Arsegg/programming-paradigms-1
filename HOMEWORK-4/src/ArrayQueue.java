public class ArrayQueue extends AbstractQueue {
    private Object[] elements = new Object[10];
    private int head; // = 0
    private int tail; // = 0
    protected int size; // = 0

    public int size() {
        return size;
    }

    public void enqueue(Object o) {
        ensureCapacity(size + 1);
        elements[tail] = o;
        tail = (tail + 1) % elements.length;
        size++;
    }

    public Object element() {
        return elements[head];
    }

    public Object dequeue() {
        Object o = element();
        elements[head] = null;
        head = (head + 1) % elements.length;
        size--;
        return o;
    }

    public void clear() {
        head = 0;
        tail = 0;
        size = 0;
    }

    public void push(Object o) {
        ensureCapacity(size + 1);
        head = (head - 1 + elements.length) % elements.length;
        elements[head] = o;
        size++;
    }

    public Object peek() {
        return elements[(tail - 1 + elements.length) % elements.length];
    }

    public Object remove() {
        Object o = peek();
        elements[tail] = null;
        tail = (tail - 1 + elements.length) % elements.length;
        size--;
        return o;
    }

    public void ensureCapacity(int capacity) {
        if (capacity <= elements.length)
            return;
        Object[] newElements = new Object[2 * capacity];

        for (int i = 0; i < size; i++) {
            newElements[i] = elements[(head + i) % elements.length];
        }
        head = 0;
        tail = size;
        elements = newElements;
    }

    protected Queue create() {
        return new ArrayQueue();
    }
}
