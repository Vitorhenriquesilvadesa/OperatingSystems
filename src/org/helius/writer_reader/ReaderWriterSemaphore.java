package org.helius.writer_reader;

import java.util.concurrent.Semaphore;

class ReaderWriterSemaphore {
    private int readers = 0;
    private final Semaphore resource = new Semaphore(1);
    private final Semaphore mutex = new Semaphore(1);

    public void startRead() throws InterruptedException {
        mutex.acquire();
        readers++;
        if (readers == 1) {
            resource.acquire();
        }
        mutex.release();
    }

    public void endRead() throws InterruptedException {
        mutex.acquire();
        readers--;
        if (readers == 0) {
            resource.release();
        }
        mutex.release();
    }

    public void startWrite() throws InterruptedException {
        resource.acquire();
    }

    public void endWrite() {
        resource.release();
    }
}
