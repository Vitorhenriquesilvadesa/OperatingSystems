package org.helius.writer_reader;

import java.util.concurrent.CyclicBarrier;

class ReaderWriterBarrier {
    private final CyclicBarrier barrier;

    public ReaderWriterBarrier(int parties) {
        this.barrier = new CyclicBarrier(parties);
    }

    public void accessResource() throws Exception {
        System.out.println(Thread.currentThread().getName() + " waiting at barrier");
        barrier.await();
        System.out.println(Thread.currentThread().getName() + " accessing resource");
    }
}
