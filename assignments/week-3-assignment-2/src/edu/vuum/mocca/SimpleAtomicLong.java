package edu.vuum.mocca;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

/**
 * @class SimpleAtomicLong
 *
 * @brief This class implements a subset of the
 *        java.util.concurrent.atomic.SimpleAtomicLong class using a
 *        ReentrantReadWriteLock to illustrate how they work.
 */
class SimpleAtomicLong
{
    /**
     * The value that's manipulated atomically via the methods.
     */
    private long mValue;
    
    /**
     * The ReentrantReadWriteLock used to serialize access to mValue.
     */
<<<<<<< HEAD

    // TODO -- you fill in here by replacing the null with an
    // initialization of ReentrantReadWriteLock.
    private ReentrantReadWriteLock mRWLock = new ReentrantReadWriteLock();
=======
    // TODO - add the implementation
>>>>>>> upstream/master

    /**
     * Creates a new SimpleAtomicLong with the given initial value.
     */
    public SimpleAtomicLong(long initialValue)
    {
    	mValue = initialValue;
    }

    /**
     * @brief Gets the current value.
     * 
     * @returns The current value
     */
    public long get()
    {
    	try {
    		mRWLock.readLock().lock();
    		long value = mValue;
    		return value;
    	}
    	finally {
    		mRWLock.readLock().unlock();
    	}
    }
    
    /**
     * Helper function to change mValue within a write lock
     * @param delta
     * @return
     */
    private long changeValue(long delta, boolean returnValueAfterChange) {
    	try {
    		mRWLock.writeLock().lock();
    		long oldValue = mValue;
    		mValue += delta;
    		if (returnValueAfterChange) {
    			long newValue = mValue;
    			return newValue;
    		}
    		else {
    			return oldValue;
    		}
    	}
    	finally {
    		mRWLock.writeLock().unlock();
    	}
    }

    /**
     * @brief Atomically decrements by one the current value
     *
     * @returns the updated value
     */
    public long decrementAndGet()
    {
    	return changeValue(-1, true);
    }

    /**
     * @brief Atomically increments by one the current value
     *
     * @returns the previous value
     */
    public long getAndIncrement()
    {
    	return changeValue(1, false);
    }

    /**
     * @brief Atomically decrements by one the current value
     *
     * @returns the previous value
     */
    public long getAndDecrement()
    {
    	return changeValue(-1, false);
    }

    /**
     * @brief Atomically increments by one the current value
     *
     * @returns the updated value
     */
    public long incrementAndGet()
    {
    	return changeValue(1, true);
    }
}

