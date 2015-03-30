import java.util.function.Function;
import java.util.function.Predicate;

public interface Queue {
    void enqueue(Object o); // добавить элемент в очередь;

    Object element();       // первый элемент в очереди;

    Object dequeue();       // удалить и вернуть первый элемент в очереди;

    int size();             // текущий размер очереди;

    boolean isEmpty();      // является ли очередь пустой;

    void clear();           // удалить все элементы из очереди.

    Queue filter(Predicate<Object> predicate);

    Queue map(Function<Object, Object> function);
}
