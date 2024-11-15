package org.helius.writer_reader;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ReaderWriterMutex {
    private int readers = 0;
    private final Lock resourceLock = new ReentrantLock();
    private final Lock readersLock = new ReentrantLock();

    public void startRead() {
        readersLock.lock();
        try {
            readers++;
            if (readers == 1) {
                resourceLock.lock();
            }
        } finally {
            readersLock.unlock();
        }
    }

    public void endRead() {
        readersLock.lock();
        try {
            readers--;
            if (readers == 0) {
                resourceLock.unlock();
            }
        } finally {
            readersLock.unlock();
        }
    }

    public void startWrite() {
        resourceLock.lock();
    }

    public void endWrite() {
        resourceLock.unlock();
    }
}
