package httpServerTests.serverSocketLogicTests.testableObjects;

public class TestableThread extends Thread {

    private int callCountForStart = 0;
    public int getCallCountForStart() {
        return callCountForStart;
    }

    private Runnable runnablePassedToThread;
    public Runnable getRunnablePassedToThread() {
        return runnablePassedToThread;
    }

    public TestableThread() {}

    @Override
    public void start() {
        callCountForStart += 1;
        runnablePassedToThread.run();
    }

    public Thread establishWithRunnable(Runnable runnable) {
        this.runnablePassedToThread = runnable;
        return this;
    }

}
