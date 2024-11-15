package org.helius.writer_reader;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class ReaderWriterMessages {
    private final BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();

    public void startRead() throws InterruptedException {
        messageQueue.put("Reader");
    }

    public void endRead() throws InterruptedException {
        messageQueue.take();
    }

    public void startWrite() throws InterruptedException {
        while (!messageQueue.isEmpty()) {
            Thread.sleep(10);
        }
        messageQueue.put("Writer");
    }

    public void endWrite() throws InterruptedException {
        messageQueue.take();
    }
}
