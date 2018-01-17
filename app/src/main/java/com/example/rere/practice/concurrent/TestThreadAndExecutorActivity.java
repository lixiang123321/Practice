package com.example.rere.practice.concurrent;

import com.example.rere.practice.base.activity.TestBaseActivity;
import com.example.rere.practice.base.utils.TagLog;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * test Thread and Executors
 *
 * Created by rere on 2017/2/7.
 */

public class TestThreadAndExecutorActivity extends TestBaseActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, TestThreadAndExecutorActivity.class));
    }

    @Override
    protected void addViews(LinearLayout layout) {

        getButton(layout, "runnable thread", v -> {
            // runnable thread
            Runnable task = () -> {
                String threadName = Thread.currentThread().getName();
                TagLog.i(TAG, " threadName = " + threadName + ",");
            };

            task.run();// in main thread

            Thread thread = new Thread(task);// in new thread
            thread.start();

            TagLog.i(TAG, "done");

            // the result maybe :
            //      threadName = main,
            //      threadName = Thread - 0
            //      done

            // or
            //      threadName = main,
            //      done
            //      threadName = Thread - 0

            // remember the non-deterministic in concurrent programming
            // the second result appear more, i think the thread start needs more init time.
        });


        getButton(layout, "sleep", v -> {
            // sleep

            Runnable runnable = () -> {
                try {
                    String threadName = Thread.currentThread().getName();
                    TagLog.i(TAG, " threadName = " + threadName + ",");
                    TagLog.i(TAG, "foo");
                    TimeUnit.SECONDS.sleep(1);
                    TagLog.i(TAG, "bar");
                } catch (InterruptedException e) {
                    TagLog.e(TAG, e.getMessage());
                }
            };

            new Thread(runnable).start();

            // result :
            // threadName = thread-0
            // foo
            // (one second after)
            // bar

        });

        getButton(layout, "executors newSingleThreadExecutor", v -> {
            // executors newSingleThreadExecutor
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.submit(() -> {
                String threadName = Thread.currentThread().getName();
                TagLog.i(TAG, " threadName = " + threadName + ",");
            });

            // threadName = pool-1-thread-1

            // remember : executor never stop

            try {
                TagLog.i(TAG, "attempt to shutdown executor");
                executor.shutdown();
                executor.awaitTermination(5, TimeUnit.SECONDS);// block if all task completed, or timeOut, or interrupted
            } catch (InterruptedException e) {
                TagLog.e(TAG, e.getMessage());
            } finally {
                if (!executor.isTerminated()) {//if all tasks have completed following shut down, remember always false if have not call shutdown
                    TagLog.e(TAG, "cancel non-finished tasks");
                }
                executor.shutdownNow();
                TagLog.i(TAG, "shutdown finished");
            }
        });

        getButton(layout, "Callable", v -> {
            // Callable
            Callable<Integer> task = () -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    return 123;
                } catch (InterruptedException e) {
                    TagLog.e(TAG, e.getMessage());
                    throw new IllegalStateException("task interrupted", e);
                }
            };

            ExecutorService executor = Executors.newFixedThreadPool(1);
            Future<Integer> future = executor.submit(task);

            TagLog.i(TAG, " (future.isDone()) = " + (future.isDone()) + ",");//false, because the task will sleep 1 second

            Integer result = null;
            try {
                result = future.get();// will block current thread until get the result
            } catch (InterruptedException | ExecutionException e) {
                TagLog.e(TAG, e.getMessage());
            }

            TagLog.i(TAG, " (future.isDone()) = " + (future.isDone()) + ",");//true when no exception print
            TagLog.i(TAG, " result = " + result + ",");

            executor.shutdownNow();

        });

        getButton(layout, "Callable shutdown them get future", v -> {
            // Callable shutdown them get future
            Callable<Integer> task = () -> {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    return 123;
                } catch (InterruptedException e) {
                    TagLog.e(TAG, e.getMessage());
                    throw new IllegalStateException("task interrupted", e);
                }
            };

            ExecutorService executor = Executors.newFixedThreadPool(1);
            Future<Integer> future = executor.submit(task);

            executor.shutdownNow();// shutdown before the task complete

            try {
                future.get();// will throw exception,
            } catch (InterruptedException e) {
                TagLog.e(TAG, "InterruptedException : " + e.getMessage());
            } catch (ExecutionException e) {
                TagLog.e(TAG, "ExecutionException : " + e.getMessage());// IllegalStateException, task interrupted
            }

        });

        getButton(layout, "future get with time", v -> {
            // future get with time
            Callable<Integer> task = () -> {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    return 1;
                } catch (InterruptedException e) {
                    TagLog.e(TAG, e.getMessage());
                    throw new IllegalStateException("task interrupted", e);
                }
            };

            ExecutorService executor = Executors.newFixedThreadPool(1);
            Future<Integer> future = executor.submit(task);

            try {
                future.get(1, TimeUnit.SECONDS);
                // throw the timeoutException, because the task need 2 seconds to execute
                // but we want to get the result in 1 second
            } catch (InterruptedException e) {
                TagLog.e(TAG, "InterruptedException : " + e.getMessage());
            } catch (ExecutionException e) {
                TagLog.e(TAG, "ExecutionException : " + e.getMessage());
            } catch (TimeoutException e) {
                TagLog.e(TAG, "TimeoutException : " + e.getMessage());// the message is null.....
            }

        });

        getButton(layout, "executor invokeAll", v -> {
            // executor invokeAll
            ExecutorService executor = Executors.newWorkStealingPool();
            List<Callable<String>> callables = Arrays.asList(
                    () -> "task1",
                    () -> "task2",
                    () -> "task3"
            );

            try {
                executor.invokeAll(callables)
                        .stream()
                        .map(stringFuture -> {
                            try {
                                return stringFuture.get();
                            } catch (Exception e) {
                                throw new IllegalStateException(e);
                            }
                        })
                        .forEach(str -> TagLog.i(TAG, " str = " + str + ","));
            } catch (Exception e) {
                TagLog.e(TAG, e.getMessage());
            }


        });

        getButton(layout, "executor invokeAny", v -> {
            // executor invokeAny
            ExecutorService executor = Executors.newWorkStealingPool();

            List<Callable<String>> callables = Arrays.asList(
                    callable("task1", 2),
                    callable("task2", 1),
                    callable("task3", 3)
            );

            try {
                String stringResult = executor.invokeAny(callables);
                TagLog.i(TAG, " stringResult = " + stringResult + ",");// task2, because it finish first
            } catch (Exception e) {
                TagLog.e(TAG, e.getMessage());
            }

            List<Runnable> runnablesNeverExecutor = executor.shutdownNow();
            TagLog.i(TAG, " (runnablesNeverExecutor.size()) = " + (runnablesNeverExecutor.size()) + ",");
            // 0, result the runnable never executor

            // TODO confused, why the other 2 callable did not throw any exception.
        });

        getButton(layout, "Scheduled Executor", v -> {
            // Scheduled Executor
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

            Runnable task = () -> TagLog.i(TAG, "Scheduling : " + System.nanoTime());

            ScheduledFuture<?> scheduledFuture = executor.schedule(task, 3, TimeUnit.SECONDS);
            // executor after 3 seconds

            try {
                TimeUnit.MILLISECONDS.sleep(1337);
            } catch (InterruptedException e) {
                TagLog.e(TAG, e.getMessage());
            }

            long remainDelay = scheduledFuture.getDelay(TimeUnit.MILLISECONDS);
            TagLog.i(TAG, " remainDelay = " + remainDelay + ",");
            // 3000 - 1337 = 1763, get the remain deley
            // the actual result is 1661

            List<Runnable> runnablesNeverExecutor = executor.shutdownNow();
            TagLog.i(TAG, " (runnablesNeverExecutor.size()) = " + (runnablesNeverExecutor.size()) + ",");
            // 1
        });

        getButton(layout, "Executor schedule at fixed rate", v -> {
            // Executor schedule at fixed rate
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

            Runnable task = () -> TagLog.i(TAG, "Scheduling : " + System.nanoTime());

            int initialDelay = 0;
            int period = 1;
            executor.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
            // every period seconds execute one time, after initialDelay start the first one.
            // task start 1 ~~~period~~~ task start 2 ~~~period~~~

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                TagLog.e(TAG, e.getMessage());
            } finally {
                List<Runnable> remainUnExecutedRunnables = executor.shutdownNow();
                TagLog.i(TAG, " (remainUnExecutedRunnables.size()) = " + (remainUnExecutedRunnables.size()) + ",");
            }

            // the result is :
            // 6 Scheduling : nanoTime()
            // (remainUnExecutedRunnables.size()) = 1 or 0

        });

        getButton(layout, "Executor schedule at fixed rate, with shutdown and await", v -> {
            // Executor schedule at fixed rate
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

            Runnable task = () -> TagLog.i(TAG, "Scheduling : " + System.nanoTime());

            int initialDelay = 0;
            int period = 1;
            executor.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
            // every period seconds execute one time, after initialDelay start the first one.
            // task start 1 ~~~period~~~ task start 2 ~~~period~~~

            try {
                TimeUnit.SECONDS.sleep(1);
                executor.shutdown();
                executor.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                TagLog.e(TAG, e.getMessage());
            } finally {
                if (!executor.isTerminated()) {
                    TagLog.i(TAG, "cancel non-finished tasks");
                }
                List<Runnable> remainUnExecutedRunnables = executor.shutdownNow();
                TagLog.i(TAG, " (remainUnExecutedRunnables.size()) = " + (remainUnExecutedRunnables.size()) + ",");
            }

            // the result is :
            // 1 Scheduling : nanoTime()
            // (remainUnExecutedRunnables.size()) = 0 or 1

            // i think the awaityTermination is not valid for scheduled task
        });

        getButton(layout, "Executor schedule with fixed delay", v -> {
            // Executor schedule with fixed delay
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

            Runnable task = () -> {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    TagLog.i(TAG, "scheduling : " + System.nanoTime());
                } catch (InterruptedException e) {
                    TagLog.e(TAG, "task interrupted : " + e.getMessage());
                }
            };

            executor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);
            // initial delay then start the first task execution
            // after the last task finish + 1 second period, start the next task

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                TagLog.e(TAG, e.getMessage());
            } finally {
                List<Runnable> remainUnExecutedRunnables = executor.shutdownNow();
                TagLog.i(TAG, " (remainUnExecutedRunnables.size()) = " + (remainUnExecutedRunnables.size()) + ",");
            }

            // the result is :
            // 1 Scheduling : nanoTime() and one task be interrupted
            // (remainUnExecutedRunnables.size()) = 0 or 1

        });

    }

    Callable<String> callable(String result, long sleepSeconds) {
        return () -> {
            try {
                TagLog.i(TAG, " (Thread.currentThread().getName()) = " + (Thread.currentThread().getName()) + ",");
                TimeUnit.SECONDS.sleep(sleepSeconds);
                TagLog.i(TAG, " (Thread.currentThread().getName()) = " + (Thread.currentThread().getName()) + ", sleep finish.");
                return result;
            } catch (Exception e) {
                TagLog.e(TAG, e.getMessage());
                throw new IllegalStateException(e);
            }
        };
    }


}
