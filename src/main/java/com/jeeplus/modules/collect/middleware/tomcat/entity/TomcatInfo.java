package com.jeeplus.modules.collect.middleware.tomcat.entity;

/**
 * Created by Le on 2017/11/28.
 */
public class TomcatInfo {
    private boolean status = false;
    private long currentTime = -1L;
    private long lastTime = -1L;
    private long freeMemory = -1L;
    private long totalMemory = -1L;
    private long usedMemory = -1L;
    private long processingTime = -1L;
    private long currentRequestCount = -1L;
    private long currentBytesReceived = -1L;
    private long currentBytesSent = -1L;
    private long lastRequestCount = -1L;
    private long lastBytesReceived = -1L;
    private long lastBytesSent = -1L;
    private long responseTime = -1L;

    public TomcatInfo() {}

    public boolean isStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public long getCurrentTime() {
        return this.currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public long getLastTime() {
        return this.lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public long getFreeMemory() {
        return this.freeMemory;
    }

    public void setFreeMemory(long freeMemory) {
        this.freeMemory = freeMemory;
    }

    public long getTotalMemory() {
        return this.totalMemory;
    }

    public void setTotalMemory(long totalMemory) {
        this.totalMemory = totalMemory;
    }

    public long getUsedMemory() {
        return this.usedMemory;
    }

    public void setUsedMemory(long usedMemory) {
        this.usedMemory = usedMemory;
    }

    public long getProcessingTime() {
        return this.processingTime;
    }

    public void setProcessingTime(long processingTime) {
        this.processingTime = processingTime;
    }

    public long getCurrentRequestCount() {
        return this.currentRequestCount;
    }

    public void setCurrentRequestCount(long currentRequestCount) {
        this.currentRequestCount = currentRequestCount;
    }

    public long getCurrentBytesReceived() {
        return this.currentBytesReceived;
    }

    public void setCurrentBytesReceived(long currentBytesReceived) {
        this.currentBytesReceived = currentBytesReceived;
    }

    public long getCurrentBytesSent() {
        return this.currentBytesSent;
    }

    public void setCurrentBytesSent(long currentBytesSent) {
        this.currentBytesSent = currentBytesSent;
    }

    public long getLastRequestCount() {
        return this.lastRequestCount;
    }

    public void setLastRequestCount(long lastRequestCount) {
        this.lastRequestCount = lastRequestCount;
    }

    public long getLastBytesReceived() {
        return this.lastBytesReceived;
    }

    public void setLastBytesReceived(long lastBytesReceived) {
        this.lastBytesReceived = lastBytesReceived;
    }

    public long getLastBytesSent() {
        return this.lastBytesSent;
    }

    public void setLastBytesSent(long lastBytesSent) {
        this.lastBytesSent = lastBytesSent;
    }

    public long getResponseTime() {
        return this.responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }
}
