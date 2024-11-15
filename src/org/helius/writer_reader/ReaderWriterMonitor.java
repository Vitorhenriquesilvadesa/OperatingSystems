package org.helius.writer_reader;

class ReaderWriterMonitor {
    private int readers = 0;
    private boolean writing = false;

    public synchronized void startRead() throws InterruptedException {
        while (writing) {
            wait();
        }
        readers++;
    }

    public synchronized void endRead() {
        readers--;
        if (readers == 0) {
            notifyAll();
        }
    }

    public synchronized void startWrite() throws InterruptedException {
        while (writing || readers > 0) {
            wait();
        }
        writing = true;
    }

    public synchronized void endWrite() {
        writing = false;
        notifyAll();
    }
}