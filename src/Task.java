import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class Task<T> {
    private final Callable<? extends T> callable;
    private volatile T objectCallable;
    private Map<Callable,T> resultsGet = new HashMap<>();

    public Task(Callable<? extends T> callable) {
        this.callable = callable;
    }

    public T get() {
        objectCallable = isHaveResultGet(callable);
        if (objectCallable == null) {
            synchronized (this) {
                if (objectCallable == null) {
                    try {
                        objectCallable = callable.call();
                        resultsGet.put(callable,objectCallable);
                        System.out.println("Зашел");
                    } catch (Exception e) {
                        System.out.println("Нельзя вычислить операцию");
                        throw new TaskException();
                    }
                }
            }
        }
        return objectCallable;
    }

    private T isHaveResultGet (Callable<? extends T> callable) {
        if (resultsGet.containsKey(callable))
            return resultsGet.get(callable);
        return null;
    }
}

