package main;

public class Metrics {
    private long startTime;
    private long endTime;
    private int operationCount;
    private String operationName;

    public Metrics(String operationName) {
        this.operationName = operationName;
        this.operationCount = 0;
    }

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        endTime = System.nanoTime();
    }

    public void incrementOperations() {
        operationCount++;
    }

    public void incrementOperations(int count) {
        operationCount += count;
    }

    public long getTimeNanos() {
        return endTime - startTime;
    }

    public double getTimeMillis() {
        return (endTime - startTime) / 1_000_000.0;
    }

    public int getOperationCount() {
        return operationCount;
    }

    public String getReport() {
        return String.format("%s: %d operations, %.3f ms",
                operationName, operationCount, getTimeMillis());
    }
}