package com.mikeyyds.library.executor

import android.os.Looper
import android.util.Log
import androidx.annotation.IntRange
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock


object MikeExecutor {
    private var isPaused: Boolean = false
    private var mikeExecutor: ThreadPoolExecutor
    private var lock: ReentrantLock
    private var pauseCondition: Condition
    private val mainHandler = android.os.Handler(Looper.getMainLooper())

    private val TAG: String = "MikeExecutor"

    init {
        lock = ReentrantLock();
        pauseCondition = lock.newCondition();

        val cpuCount = Runtime.getRuntime().availableProcessors()
        val corePoolSize = cpuCount + 1
        val maxPoolSize = cpuCount * 2 + 1
        val blockingQueue: PriorityBlockingQueue<out Runnable> = PriorityBlockingQueue()
        val keepAliveTime = 30L
        val unit = TimeUnit.SECONDS

        val seq = AtomicLong()
        val threadFactory = ThreadFactory {
            val thread = Thread(it)
            thread.name = "Mike-Executor-" + seq.getAndIncrement()
            return@ThreadFactory thread
        }

        mikeExecutor = object : ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            keepAliveTime,
            unit,
            blockingQueue as BlockingQueue<Runnable>,
            threadFactory
        ) {
            override fun beforeExecute(t: Thread?, r: Runnable?) {
                if (isPaused) {
                    lock.lock()
                    try {
                        pauseCondition.await()
                    } finally {
                        lock.unlock()
                    }
                }
            }

            override fun afterExecute(r: Runnable?, t: Throwable?) {
                Log.e(TAG, "afterExecute: " + (r as PriorityRunnable).priority)
            }

        }
    }

    @JvmOverloads
    fun execute(@IntRange(from = 0, to = 10) priority: Int = 0, runnable: Runnable) {
        mikeExecutor.execute(PriorityRunnable(priority, runnable))
    }

    @JvmOverloads
    fun execute(@IntRange(from = 0, to = 10) priority: Int = 0, runnable: Callable<*>) {
        mikeExecutor.execute(PriorityRunnable(priority, runnable))
    }

    @Synchronized
    fun pause() {
        isPaused = true
        Log.e(TAG, "pause: MikeExecutor")
    }

    @Synchronized
    fun resume() {
        isPaused = false
        lock.lock()
        try {
            pauseCondition.signalAll()
        } finally {
            lock.unlock()
        }
        Log.e(TAG, "resume: MikeExecutor")
    }

    class PriorityRunnable(val priority: Int, val runnable: Runnable) : Runnable,
        Comparable<PriorityRunnable> {
        override fun run() {
            runnable.run()
        }

        override fun compareTo(other: PriorityRunnable): Int {
            return if (this.priority < other.priority) 1 else if (this.priority > other.priority) -1 else 0
        }
    }

    abstract class Callable<T> : Runnable {
        override fun run() {
            mainHandler.post { onPrepare() }
            var t = onBackground()
            mainHandler.post { onCompleted(t) }
        }

        open fun onPrepare() {
        }

        abstract fun onBackground(): T
        abstract fun onCompleted(t: T)
    }
}