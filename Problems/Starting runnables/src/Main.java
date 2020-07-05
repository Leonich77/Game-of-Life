class Starter {

    public static void startRunnables(Runnable[] runnables) {
        // implement the method
        Thread[] thrs = new Thread[runnables.length];
        for (int i = 0; i < runnables.length; i++) {
            thrs[i] = new Thread(runnables[i]);
            thrs[i].start();
        }
    }
}