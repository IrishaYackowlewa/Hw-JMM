public class Main {

    public static void main(String[] args) {
	    /*
	    Task<Integer> task = new Task (() -> 13*13/3);
	    try {
            for (int i = 0; i < 5; i++) {
                Thread thread = new Thread(new RunThread(task, i));
                thread.start();
                thread.join();
            }
        } catch (InterruptedException e) {
            System.out.println("Какой-то поток прервал текущий");
        }
	     */
	    ExecutionManager executionManager = new ExecutionManagerImpl();
        Context context = executionManager.execute(() -> System.out.println("ВСЕ ЗАДАНИЯ ВЫПОЛНЕНЫ"),
                () -> System.out.println("Работа Runnable task1"),
                () -> System.out.println("Работа Runnable task2"),
                () -> System.out.println("Работа Runnable task3"),
                () -> System.out.println("Работа Runnable task4"),
                () -> System.out.println("Работа Runnable task5"),
                () -> System.out.println("Работа Runnable task6"),
                () -> System.out.println("Работа Runnable task7"),
                () -> System.out.println("Работа Runnable task8"),
                () -> System.out.println("Работа Runnable task9"),
                () -> System.out.println("Работа Runnable task10"),
                () -> System.out.println("Работа Runnable task11"),
                () -> System.out.println("Работа Runnable task12"),
                () -> System.out.println("Работа Runnable task13"),
                () -> System.out.println("Работа Runnable task14")
        );

        System.out.println("Успешно выполненные задания " + context.getCompletedTaskCount());
        System.out.println("Упавшие с ошибкой задания " + context.getFailedTaskCount());
        System.out.println("Прерванные задания " + context.getInterruptedTaskCount());
    }

    private static class RunThread implements Runnable {
        private final Task task;
        private static volatile Exception MyExceptions = null;
        int n;

        private RunThread(Task task, int n) {
            this.task = task;
            this.n = n;
        }

        @Override
        public void run() {
            try {
                if (MyExceptions != null) {
                    throw new TaskException();
                }
                System.out.println(task.get());
                System.out.println("Отработал поток №" + n);
            } catch (TaskException e) {
                if (MyExceptions == null) {
                    MyExceptions = e;
                }
                System.out.println("Отловили TaskException");
            } catch (Exception e) {
                System.out.println("Произошла ошибка не связанная с get()");
                e.printStackTrace();
            }
        }
    }
}
