package com.example.rere.practice.concurrent;

import com.example.rere.practice.base.activity.TestBaseActivity;
import com.example.rere.practice.base.utils.TagLog;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * test locks
 *
 * ReentrantLock, ReadWriteLock, StampedLock,
 * Semaphores
 *
 * Created by rere on 2017/2/9.
 */

public class TestLocksActivity extends TestBaseActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, TestLocksActivity.class));
    }

    @Override
    protected void addViews(LinearLayout layout) {

        getButton(layout, "ReentrantLock", v -> {
            // ReentrantLock
            ExecutorService executor = Executors.newFixedThreadPool(2);

            ReentrantLock lock = new ReentrantLock();

            executor.submit(() -> {
                // when the lock is unlock or lock by current thread, lock it immediately
                // but when the lock is locked by other thread, wait until it is available.
                lock.lock();

                try {
                    sleep(1);
                } finally {
                    // if the lock is held by current thread. release it.
                    // if the lock is not held by current thread. will throw Exception.
                    lock.unlock();
                }
            });

            // let the first runnable executor first.
            layout.postDelayed(() -> {
                executor.submit(() -> {
                    TagLog.i(TAG, "Locked : " + lock.isLocked());
                    TagLog.i(TAG, "Held by me : " + lock.isHeldByCurrentThread());
                    boolean locked = lock.tryLock();// try lock,
                    TagLog.i(TAG, "try lock = " + locked + ",");
                    if (locked) {
                        lock.unlock();
                    }
                });

                stopExecutor(executor, 5);
            }, 100);


        });

        getButton(layout, "ReadWriteLock", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ReadWriteLock

                ExecutorService executor = Executors.newFixedThreadPool(3);
                Map<String, String> map = new HashMap<>();
                ReadWriteLock lock = new ReentrantReadWriteLock();
                final String key = "foo";

                Runnable writeTask = () -> {
                    TagLog.i(TAG, getThreadName() + "write Task");
                    lock.writeLock().lock();
                    try {
                        sleep(1);
                        map.put(key, "bar");
                        TagLog.i(TAG, "write finish.");
                    } finally {
                        lock.writeLock().unlock();
                    }
                };

                Runnable readTask = () -> {
                    TagLog.i(TAG, getThreadName() + "read Task");
                    lock.readLock().lock();
                    try {
                        TagLog.i(TAG, map.get(key));
                        sleep(1);
                    } finally {
                        lock.readLock().unlock();
                    }
                };

                Runnable runnable = new Runnable() {

                    @Override
                    public void run() {

                    }
                };

                executor.submit(runnable);

                executor.submit(writeTask);
                layout.postDelayed(() -> {
                    executor.submit(readTask);
                    executor.submit(readTask);
                    stopExecutor(executor, 5);
                }, 100);

                // read lock should wait for write lock release.
                // and read lock has no need to wait for read lock.
            }
        });


    }

    private String getThreadName() {
        return Thread.currentThread().getName();
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
