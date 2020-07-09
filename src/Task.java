import java.util.concurrent.Callable;

public class Task<T> {
    private final Callable<? extends T> callable;

    public Task(Callable<? extends T> callable) {
        this.callable = callable;
    }

    public T get() throws Exception{
        return callable.call();
    }

    private class RunThread implements Runnable {

        @Override
        public void run() {

        }
    }
}

