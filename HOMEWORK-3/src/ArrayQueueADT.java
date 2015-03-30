public class ArrayQueueADT {
    private Object[] elements = new Object[10000000];
    private int head; // = 0
    private int tail; // = 0
    private int size; // = 0

    public static void enqueue(ArrayQueueADT arrayQueueADT, Object o) {
        ensureCapacity(arrayQueueADT, arrayQueueADT.size + 1);
        arrayQueueADT.elements[arrayQueueADT.tail++] = o;
        arrayQueueADT.tail %= arrayQueueADT.elements.length;
        arrayQueueADT.size++;
    }

    public static Object element(ArrayQueueADT arrayQueueADT) {
        return arrayQueueADT.elements[arrayQueueADT.head];
    }

    public static Object dequeue(ArrayQueueADT arrayQueueADT) {
        Object o = element(arrayQueueADT);
        arrayQueueADT.head %= arrayQueueADT.elements.length;
        arrayQueueADT.size--;
        return o;
    }

    public static int size(ArrayQueueADT arrayQueueADT) {
        return arrayQueueADT.size;
    }

    public static boolean isEmpty(ArrayQueueADT arrayQueueADT) {
        return arrayQueueADT.size == 0;
    }

    public static void clear(ArrayQueueADT arrayQueueADT) {
        arrayQueueADT.head = 0;
        arrayQueueADT.tail = 0;
        arrayQueueADT.size = 0;
    }

    public static void push(ArrayQueueADT arrayQueueADT, Object o) {
        ensureCapacity(arrayQueueADT, arrayQueueADT.size + 1);
        arrayQueueADT.head = (arrayQueueADT.head - 1 + arrayQueueADT.elements.length) % arrayQueueADT.elements.length;
        arrayQueueADT.elements[arrayQueueADT.head] = o;
        arrayQueueADT.size++;
    }

    public static Object peek(ArrayQueueADT arrayQueueADT) {
        return arrayQueueADT.elements[(arrayQueueADT.tail - 1 + arrayQueueADT.elements.length) % arrayQueueADT.elements.length];
    }

    public static Object remove(ArrayQueueADT arrayQueueADT) {
        Object o = peek(arrayQueueADT);
        arrayQueueADT.tail = (arrayQueueADT.tail - 1 + arrayQueueADT.elements.length) % arrayQueueADT.elements.length;
        arrayQueueADT.size--;
        return o;
    }

    public static void ensureCapacity(ArrayQueueADT arrayQueueADT, int capacity) {
        if (capacity <= arrayQueueADT.elements.length)
            return;
        Object[] newElements = new Object[2 * capacity];

        for (int i = 0; i < arrayQueueADT.size; i++) {
            newElements[i] = arrayQueueADT.elements[(arrayQueueADT.head + i) % arrayQueueADT.elements.length];
        }
        arrayQueueADT.head = 0;
        arrayQueueADT.tail = arrayQueueADT.size;
        arrayQueueADT.elements = newElements;
    }
}
