import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutionManagerImpl implements ExecutionManager{

    public ExecutionManagerImpl () {}

    @Override
    public Context execute(Runnable callback, Runnable... tasks) {
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future> listTasks = new ArrayList<>();
        for (Runnable task: tasks) {
            listTasks.add(executor.submit(task));
        }

        Context context = new ContextImpl(callback, listTasks);
        while (!context.isFinished()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Ошибка в sleep");
            }
        }

        executor.shutdown();
        return context;
    }
}
