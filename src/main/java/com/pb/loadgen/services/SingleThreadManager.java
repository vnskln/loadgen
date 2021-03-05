package com.pb.loadgen.services;

public class SingleThreadManager implements Runnable{

    private int percentage;
    private boolean doStop = false;
    RandomDivider worker;
    Thread workerThread;

    public SingleThreadManager (RandomDivider worker, int percentage) throws Exception {
        if (percentage < 0 || percentage > 100)
            throw new Exception("Load percentage must be in range <0,100>");
        else
            this.percentage = percentage;
        this.worker = worker;
        workerThread = new Thread(worker);
        System.out.println("Worker prepared");
    }

    @Override
    public void run() {
        System.out.println("Manager started");
        workerThread.start();
    }

    public synchronized void doStop() {
        try {
            Thread.sleep(100L-percentage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        worker.doStop();
        System.out.println("Manager stopped");
    }
}
