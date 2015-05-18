import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AbstractQueue implements Queue {

    public boolean isEmpty() {
        return size() == 0;
    }

    public Queue filter(Predicate<Object> predicate) {
        Queue queue = create();
        for (int i = 0; i < size(); i++) {
            Object o = dequeue();
            enqueue(o);
            if (predicate.test(o)) {
                queue.enqueue(o);
            }
        }

        return queue;
    }

    public Queue map(Function<Object, Object> function) {
        Queue queue = create();

        for (int i = 0; i < size(); i++) {
            Object o = dequeue();
            enqueue(o);
            queue.enqueue(function.apply(o));
        }

        return queue;
    }

    protected abstract Queue create();
}
