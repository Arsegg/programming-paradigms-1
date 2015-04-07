// inv: size >= 0 && 0 <= head < elements.length && 0 <= tail < elements.length
public class ArrayQueueADT {
    private Object[] elements = new Object[10];
    private int head = 0;
    private int tail = 0;
    private int size = 0;

    // pre: true
    public static void enqueue(ArrayQueueADT arrayQueueADT, Object o) {
        assert o != null;

        ensureCapacity(arrayQueueADT, arrayQueueADT.size + 1);
        arrayQueueADT.elements[arrayQueueADT.tail] = o;
        arrayQueueADT.tail = (arrayQueueADT.tail + 1) % arrayQueueADT.elements.length;
        arrayQueueADT.size++;
    }
    // post: for i = head..tail: elements[i + head' - head]' = elements[i + head' - head]
    // && size' = size + 1 && (head' = head || head' = 0) && (tail' = (tail + 1) % elements.length || tail' = size + 1)

    // pre: size > 0
    public static Object element(ArrayQueueADT arrayQueueADT) {
        assert arrayQueueADT.size > 0;

        return arrayQueueADT.elements[arrayQueueADT.head];
    }
    // post: R = elements[head]

    // pre: size > 0
    public static Object dequeue(ArrayQueueADT arrayQueueADT) {
        assert arrayQueueADT.size > 0;

        Object o = element(arrayQueueADT);
        arrayQueueADT.elements[arrayQueueADT.head] = null;
        arrayQueueADT.head = (arrayQueueADT.head + 1) % arrayQueueADT.elements.length;
        arrayQueueADT.size--;

        return o;
    }
    // post: for i = head + 1..tail - 1: elements[i + head' - head]' = elements[i + head' - head]
    // && size' = size - 1 && head' = (head + 1) % elements.length && tail' = tail

    // pre: true
    public static int size(ArrayQueueADT arrayQueueADT) {
        return arrayQueueADT.size;
    }
    // post: R = size

    // pre: true
    public static boolean isEmpty(ArrayQueueADT arrayQueueADT) {
        return arrayQueueADT.size == 0;
    }
    // post: R = size == 0

    // pre: true
    public static void clear(ArrayQueueADT arrayQueueADT) {
        arrayQueueADT.head = 0;
        arrayQueueADT.tail = 0;
        arrayQueueADT.size = 0;
    }
    // post: head' = 0 && tail' = 0 && size' = 0

    // pre: true
    public static void push(ArrayQueueADT arrayQueueADT, Object o) {
        ensureCapacity(arrayQueueADT, arrayQueueADT.size + 1);
        arrayQueueADT.head = (arrayQueueADT.head - 1 + arrayQueueADT.elements.length) % arrayQueueADT.elements.length;
        arrayQueueADT.elements[arrayQueueADT.head] = o;
        arrayQueueADT.size++;
    }
    // post: for i = head..tail: elements[i + head' - head]' = elements[i + head' - head]
    // && size' = size + 1 && (head' = (head - 1 + elements.length) % elements.length || head' = elements.length - 2) && (tail' = tail || tail' = size)

    // pre: size > 0
    public static Object peek(ArrayQueueADT arrayQueueADT) {
        assert arrayQueueADT.size > 0;

        return arrayQueueADT.elements[(arrayQueueADT.tail - 1 + arrayQueueADT.elements.length) % arrayQueueADT.elements.length];
    }
    // post: R = elements[(tail - 1 + elements.length) % elements.length]

    // pre: size > 0
    public static Object remove(ArrayQueueADT arrayQueueADT) {
        assert arrayQueueADT.size > 0;

        Object o = peek(arrayQueueADT);
        arrayQueueADT.elements[(arrayQueueADT.tail - 1 + arrayQueueADT.elements.length) % arrayQueueADT.elements.length] = null;
        arrayQueueADT.tail = (arrayQueueADT.tail - 1 + arrayQueueADT.elements.length) % arrayQueueADT.elements.length;
        arrayQueueADT.size--;

        return o;
    }
    // post: for i = head..tail - 1: elements[i + head' - head]' = elements[i + head' - head]
    // && size' = size - 1 && head' = head && tail' = (tail - 1 + elements.length) % elements.length

    // pre: true
    private static void ensureCapacity(ArrayQueueADT arrayQueueADT, int capacity) {
        if (capacity <= arrayQueueADT.elements.length)
            return;
        Object[] newElements = new Object[2 * arrayQueueADT.elements.length];

        for (int i = 0; i < arrayQueueADT.size; i++) {
            newElements[i] = arrayQueueADT.elements[(arrayQueueADT.head + i) % arrayQueueADT.elements.length];
        }
        arrayQueueADT.head = 0;
        arrayQueueADT.tail = arrayQueueADT.size;
        arrayQueueADT.elements = newElements;
    }
    // post: (head' = head || head' = 0) && (tail' = tail || tail' = size) && size' = size && for i = head..tail: elements[i + head' - head]' = elements[i + head' - head]
}
