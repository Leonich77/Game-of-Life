class ThreadProcessor {
    public static void findAndStartThread(Thread... threads) throws InterruptedException {
        Thread thr = new Thread();
        for (Thread th : threads) {
            if (th.getState().equals(Thread.State.NEW)) {
                thr = th;
                thr.start();
                break;
            }
        }
        thr.interrupt();
        thr.join();
    }
}