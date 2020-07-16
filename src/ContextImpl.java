import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ContextImpl implements Context {
    private Runnable callback;
    private List<Future> listTasks = new ArrayList<>();
    private int failedTaskCount = 0;
    private int interruptedTaskCount = 0;
    private int completedTaskCount = 0;
    private boolean isFinished = false;

    public ContextImpl(Runnable callback, List<Future> tasks) {
        this.callback = callback;
        this.listTasks = tasks;
        runTasks();
    }

    private class RunTasks implements Runnable {

        @Override
        public void run() {
            for (Future task: listTasks) {
                try {
                    task.get();
                    completedTaskCount++;
                } catch (InterruptedException e) {
                    interruptedTaskCount++;
                    failedTaskCount++;
                } catch (ExecutionException e) {
                    failedTaskCount++;
                }
            }
            isFinished = true;
            callback.run();
        }
    }

    private void runTasks() {
        Thread thread = new Thread(new RunTasks());
        thread.start();
    }

    @Override
    public int getCompletedTaskCount() {//возвращает количество тасков, которые на текущий момент успешно выполнились.
        return completedTaskCount;
    }

    @Override
    public int getFailedTaskCount() {//возвращает количество тасков, при выполнении которых произошел Exception.
        return failedTaskCount;
    }

    @Override
    public int getInterruptedTaskCount() {//возвращает количество тасков, которые не были выполены из-за отмены (вызовом предыдущего метода).
        return interruptedTaskCount;
    }

    @Override
    public void interrupt() {//отменяет выполнения тасков, которые еще не начали выполняться.
        for (Future task: listTasks) {
            task.cancel(false);
        }
    }

    @Override
    public boolean isFinished() {//вернет true, если все таски были выполнены или отменены, false в противном случае.
        return isFinished;
    }
}
