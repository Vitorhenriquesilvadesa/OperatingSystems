package org.helius.writer_reader;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escolha o mecanismo de sincronização:");
        System.out.println("1. ReaderWriterBarrier");
        System.out.println("2. ReaderWriterMessages");
        System.out.println("3. ReaderWriterMonitor");
        System.out.println("4. ReaderWriterMutex");
        System.out.println("5. ReaderWriterSemaphore");
        System.out.print("Opção: ");

        int option = scanner.nextInt();
        scanner.close();

        ExecutorService executor = Executors.newFixedThreadPool(10);

        switch (option) {
            case 1 -> {
                ReaderWriterBarrier barrier = new ReaderWriterBarrier(5);
                for (int i = 0; i < 5; i++) {
                    int id = i + 1;
                    executor.submit(() -> {
                        try {
                            barrier.accessResource();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
            case 2 -> {
                ReaderWriterMessages messages = new ReaderWriterMessages();
                for (int i = 0; i < 5; i++) {
                    int id = i + 1;
                    executor.submit(() -> {
                        try {
                            messages.startRead();
                            System.out.println("Reader " + id + " is reading");
                            Thread.sleep(1000); 
                            messages.endRead();
                            System.out.println("Reader " + id + " finished reading");
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    });
                }

                for (int i = 0; i < 2; i++) {
                    int id = i + 1;
                    executor.submit(() -> {
                        try {
                            messages.startWrite();
                            System.out.println("Writer " + id + " is writing");
                            Thread.sleep(1500); 
                            messages.endWrite();
                            System.out.println("Writer " + id + " finished writing");
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    });
                }
            }
            case 3 -> {
                ReaderWriterMonitor monitor = new ReaderWriterMonitor();
                executeReadersAndWriters(executor, monitor);
            }
            case 4 -> {
                ReaderWriterMutex mutex = new ReaderWriterMutex();
                executeReadersAndWriters(executor, mutex);
            }
            case 5 -> {
                ReaderWriterSemaphore semaphore = new ReaderWriterSemaphore();
                executeReadersAndWriters(executor, semaphore);
            }
            default -> System.out.println("Opção inválida. Por favor, escolha uma opção de 1 a 5.");
        }

        executor.shutdown();
    }

    private static void executeReadersAndWriters(ExecutorService executor, Object syncMechanism) {
        for (int i = 0; i < 5; i++) {
            int id = i + 1;
            executor.submit(() -> {
                try {
                    if (syncMechanism instanceof ReaderWriterMonitor monitor) {
                        monitor.startRead();
                        System.out.println("Reader " + id + " is reading");
                        Thread.sleep(1000); 
                        monitor.endRead();
                        System.out.println("Reader " + id + " finished reading");
                    } else if (syncMechanism instanceof ReaderWriterMutex mutex) {
                        mutex.startRead();
                        System.out.println("Reader " + id + " is reading");
                        Thread.sleep(1000); 
                        mutex.endRead();
                        System.out.println("Reader " + id + " finished reading");
                    } else if (syncMechanism instanceof ReaderWriterSemaphore semaphore) {
                        semaphore.startRead();
                        System.out.println("Reader " + id + " is reading");
                        Thread.sleep(1000); 
                        semaphore.endRead();
                        System.out.println("Reader " + id + " finished reading");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        for (int i = 0; i < 2; i++) {
            int id = i + 1;
            executor.submit(() -> {
                try {
                    if (syncMechanism instanceof ReaderWriterMonitor monitor) {
                        monitor.startWrite();
                        System.out.println("Writer " + id + " is writing");
                        Thread.sleep(1500); 
                        monitor.endWrite();
                        System.out.println("Writer " + id + " finished writing");
                    } else if (syncMechanism instanceof ReaderWriterMutex mutex) {
                        mutex.startWrite();
                        System.out.println("Writer " + id + " is writing");
                        Thread.sleep(1500); 
                        mutex.endWrite();
                        System.out.println("Writer " + id + " finished writing");
                    } else if (syncMechanism instanceof ReaderWriterSemaphore semaphore) {
                        semaphore.startWrite();
                        System.out.println("Writer " + id + " is writing");
                        Thread.sleep(1500); 
                        semaphore.endWrite();
                        System.out.println("Writer " + id + " finished writing");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }
}
