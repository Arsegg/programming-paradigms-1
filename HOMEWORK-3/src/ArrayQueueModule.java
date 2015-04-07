// inv: size >= 0 && 0 <= head < elements.length && 0 <= tail < elements.length
public class ArrayQueueModule {
    static private Object[] elements = new Object[10];
    static private int head = 0;
    static private int tail = 0;
    static private int size = 0;

    // pre: true
    public static void enqueue(Object o) {
        assert o != null;

        ensureCapacity(size + 1);
        elements[tail] = o;
        tail = (tail + 1) % elements.length;
        size++;
    }
    // post: for i = head..tail: elements[i + head' - head]' = elements[i + head' - head]
    // && size' = size + 1 && (head' = head || head' = 0) && (tail' = (tail + 1) % elements.length || tail' = size + 1)

    // pre: size > 0
    public static Object element() {
        assert size > 0;

        return elements[head];
    }
    // post: R = elements[head]

    // pre: size > 0
    public static Object dequeue() {
        assert size > 0;

        Object o = element();
        elements[head] = null;
        head = (head + 1) % elements.length;
        size--;

        return o;
    }
    // post: for i = head + 1..tail - 1: elements[i + head' - head]' = elements[i + head' - head]
    // && size' = size - 1 && head' = (head + 1) % elements.length && tail' = tail

    // pre: true
    public static int size() {
        return size;
    }
    // post: R = size

    // pre: true
    public static boolean isEmpty() {
        return size == 0;
    }
    // post: R = size == 0

    // pre: true
    public static void clear() {
        head = 0;
        tail = 0;
        size = 0;
    }
    // post: head' = 0 && tail' = 0 && size' = 0

    // pre: true
    public static void push(Object o) {
        ensureCapacity(size + 1);
        head = (head - 1 + elements.length) % elements.length;
        elements[head] = o;
        size++;
    }
    // post: for i = head..tail: elements[i + head' - head]' = elements[i + head' - head]
    // && size' = size + 1 && (head' = (head - 1 + elements.length) % elements.length || head' = elements.length - 2) && (tail' = tail || tail' = size)

    // pre: size > 0
    public static Object peek() {
        assert size > 0;

        return elements[(tail - 1 + elements.length) % elements.length];
    }
    // post: R = elements[(tail - 1 + elements.length) % elements.length]

    // pre: size > 0
    public static Object remove() {
        assert size > 0;

        Object o = peek();
        elements[(tail - 1 + elements.length) % elements.length] = null;
        tail = (tail - 1 + elements.length) % elements.length;
        size--;

        return o;
    }
    // post: for i = head..tail - 1: elements[i + head' - head]' = elements[i + head' - head]
    // && size' = size - 1 && head' = head && tail' = (tail - 1 + elements.length) % elements.length

    // pre: true
    private static void ensureCapacity(int capacity) {
        if (capacity <= elements.length)
            return;
        Object[] newElements = new Object[2 * elements.length];

        for (int i = 0; i < size; i++) {
            newElements[i] = elements[(head + i) % elements.length];
        }
        head = 0;
        tail = size;
        elements = newElements;
    }
    // post: (head' = head || head' = 0) && (tail' = tail || tail' = size) && size' = size && for i = head..tail: elements[i + head' - head]' = elements[i + head' - head]
}
