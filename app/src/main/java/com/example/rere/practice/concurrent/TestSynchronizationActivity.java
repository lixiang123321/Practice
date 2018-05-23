package com.example.rere.practice.concurrent;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;

import com.example.rere.practice.base.activity.TestBaseActivity;
import com.example.rere.practice.base.utils.TagLog;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * test synchronization
 *
 * Created by rere on 2017/2/8.
 */

public class TestSynchronizationActivity extends TestBaseActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, TestSynchronizationActivity.class));
    }

    private int count;

    @Override
    protected void addViews(LinearLayout layout) {
        getButton(layout, "race condition", v -> {
            // race condition
            count = 0;
            ExecutorService executor = Executors.newFixedThreadPool(2);

            /*IntStream.range(0, 100000)
                    .forEach(i -> executor.submit(this::increment));
                    //.forEach(i -> executor.submit(() -> increment()));*/

            stopExecutor(executor, 60);

            TagLog.i(TAG, " count = " + count + ",");// 99994, 99981
            // when doing count = count + 1, there are 3 steps
            // 1. read count current value
            // 2. add 1 to the current value
            // 3. put (current value + 1) to count variable

            // so, when two thread doing the count = count + 1 in parallel,
            // when both thread perform step 1, get the value of count, for example 100
            // then two thread perform step 2,3, then the result will be 101, but we expect it to be 102.

            // thus the result smaller than 100000
        });

        getButton(layout, "synchronized the increment method", v -> {
            // race condition
            count = 0;
            ExecutorService executor = Executors.newFixedThreadPool(2);

            /*IntStream.range(0, 100000)
                    .forEach(i -> executor.submit(this::incrementSync));
                    //.forEach(i -> executor.submit(this::incrementWithSyncBlock));*/

            stopExecutor(executor, 60);

            TagLog.i(TAG, " count = " + count + ",");// 100000
        });

        getButton(layout, "synchronized 2 methods", v -> {
            // synchronized method
            ExecutorService executor = Executors.newFixedThreadPool(2);
            List<Callable<String>> runnableList = Arrays.asList(
                    () -> {
                        syncSleep(2);
                        return "finish sleep";
                    },
                    () -> {
                        syncPrintLog("print log");
                        return "finish print log";
                    }
            );

            try {
                executor.invokeAll(runnableList);
            } catch (InterruptedException e) {
                TagLog.e(TAG, "invoke all interrupted" + e.getMessage());
            }

            stopExecutor(executor, 5);

            // if the syncSleep(2) call first
            // the print log callable will wait until the sleep callable finish
            // because the synchronized method actually share the same object (monitor)

        });

        getButton(layout, "synchronized 2 methods using sync block", v -> {
            // synchronized method
            ExecutorService executor = Executors.newFixedThreadPool(2);
            List<Callable<String>> runnableList = Arrays.asList(
                    () -> {
                        sleepSyncWithBlock(2);
                        return "finish sleep";
                    },
                    () -> {
                        printLogSyncWithBlock("print log");
                        return "finish print log";
                    }
            );

            try {
                executor.invokeAll(runnableList);
            } catch (InterruptedException e) {
                TagLog.e(TAG, "invoke all interrupted" + e.getMessage());
            }

            stopExecutor(executor, 5);

            // the two callable will perform parallel.

        });



    }


    private Object syncObject1 = new Object();
    private void sleepSyncWithBlock(int sleepSeconds) {
        synchronized (syncObject1) {
            sleep(sleepSeconds);
        }
    }

    private synchronized void syncSleep(int sleepSeconds) {
        sleep(sleepSeconds);
    }

    private void sleep(int sleepSeconds) {
        TagLog.i(TAG, "ThreadName : "
                + Thread.currentThread().getName()
                + " try sleep " + sleepSeconds + " seconds");

        try {
            TimeUnit.SECONDS.sleep(sleepSeconds);
            TagLog.i(TAG, "ThreadName : "
                    + Thread.currentThread().getName()
                    + " sleep " + sleepSeconds + " seconds finish.");

        } catch (InterruptedException e) {
            TagLog.e(TAG, "InterruptedException (when sleep "
                    + sleepSeconds + " seconds)"
                    + e.getMessage());
        }
    }

    private Object syncObject2 = new Object();
    private void printLogSyncWithBlock(String msg) {
        synchronized (syncObject2) {
            printLog(msg);
        }
    }

    private synchronized void syncPrintLog(String msg) {
        printLog(msg);
    }

    private void printLog(String msg) {
        TagLog.i(TAG, " msg = " + msg + ",");
    }

    private void increment() {
        count = count + 1;
    }
    private synchronized void incrementSync() {
        count = count + 1;
    }

    /*private void incrementWithSyncBlock() {
        synchronized (this) {
            count = count + 1;
        }
    }*/

    private void stopExecutor(ExecutorService executor, int waitingSeconds) {
        try {
            executor.shutdown();
            executor.awaitTermination(waitingSeconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            TagLog.e(TAG, e.getMessage());
        } finally {
            if (!executor.isTerminated()) {
                TagLog.i(TAG, "cancel non-executor tasks");
            }
            List<Runnable> runnableList = executor.shutdownNow();
            TagLog.i(TAG, "executor shutdown with unExecutor task size = " + runnableList.size());
        }
    }
}
