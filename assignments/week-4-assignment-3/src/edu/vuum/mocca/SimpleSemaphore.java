package edu.vuum.mocca;

import java.util.concurrent.locks.AbstractQueuedSynchronizer.ConditionObject;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;

/**
 * @class SimpleSemaphore
 * 
 * @brief This class provides a simple counting semaphore
 *        implementation using Java a ReentrantLock and a
 *        ConditionObject (which is accessed via a Condition). It must
 *        implement both "Fair" and "NonFair" semaphore semantics,
 *        just liked Java Semaphores.
 */
public class SimpleSemaphore {
    /**
     * Define a ReentrantLock to protect the critical section.
     */
    // DONE - you fill in here
	private ReentrantLock mLock;

    /**
     * Define a Condition that waits while the number of permits is 0.
     */
    // DONE - you fill in here
	private Condition mNonEmpty;

    /**
     * Define a count of the number of available permits.
     */
    // DONE - you fill in here.  Make sure that this data member will
    // ensure its values aren't cached by multiple Threads..
	private volatile int mPermits;

    public SimpleSemaphore(int permits, boolean fair) {
        // DONE - you fill in here to initialize the SimpleSemaphore,
        // making sure to allow both fair and non-fair Semaphore
        // semantics.
    	mLock = new ReentrantLock(fair);
    	mNonEmpty = mLock.newCondition();
    	mPermits = permits;
    }

    /**
     * Acquire one permit from the semaphore in a manner that can be
     * interrupted.
     */
    public void acquire() throws InterruptedException {
		mLock.lockInterruptibly();    	
    	try {
    		while (mPermits == 0) {
    			mNonEmpty.await();
    		}
    		--mPermits;
    	}
    	catch (InterruptedException ex) {
    		throw ex;
    	}
    	finally {
    		mLock.unlock();
    	}
    }

    /**
     * Acquire one permit from the semaphore in a manner that cannot be
     * interrupted.
     */
    public void acquireUninterruptibly() {
		mLock.lock();    	
    	try {
    		while (mPermits == 0) {
    			mNonEmpty.awaitUninterruptibly();
    		}
    		--mPermits;
    	}
    	finally {
    		mLock.unlock();
    	}
    }

    /**
     * Return one permit to the semaphore.
     */
    void release() {
    	mLock.lock();    	
        try {
        	++mPermits;
        	mNonEmpty.signal();
        }
        finally {
        	mLock.unlock();
        }
    }

    /**
     * Return the number of permits available.
     */
    public int availablePermits() {
		mLock.lock();    	
    	try {
    		int permits = mPermits;
    		return permits;
    	}
    	finally {
    		mLock.unlock();
    	}
    }
}
