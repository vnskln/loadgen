package com.pb.loadgen.domains;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class LoadInput {

    private String uniqueID;
    private int threadNumber;
    private int loadPercentage;
    private int loadPercentageHigh;
    private int loadPercentageChangeStep;
    private int loadPercentageChangeFrequencyS;
    private int memoryLoadSizeMiB;
    private int memoryLoadSizeMiBHigh;
    private int memoryLoadSizeMiBChangeStep;
    private int memoryLoadChangeFrequencyS;
    private int hanoiSize;
    private boolean hanoiForeground;
    private LoadType loadType;

    public LoadInput () {}
    
    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public int getThreadNumber() {
        return threadNumber;
    }

    public void setThreadNumber(int threadNumber) {
        this.threadNumber = threadNumber;
    }
    
    public int getLoadPercentage() {
        return loadPercentage;
    }

    public void setLoadPercentage(int loadPercentage) {
        this.loadPercentage = loadPercentage;
    }

    public int getLoadPercentageHigh() {
        return loadPercentageHigh;
    }

    public void setLoadPercentageHigh(int loadPercentageHigh) {
        this.loadPercentageHigh = loadPercentageHigh;
    }

    public int getLoadPercentageChangeStep() {
        return loadPercentageChangeStep;
    }

    public void setLoadPercentageChangeStep(int loadPercentageChangeStep) {
        this.loadPercentageChangeStep = loadPercentageChangeStep;
    }

    public int getLoadPercentageChangeFrequencyS() {
        return loadPercentageChangeFrequencyS;
    }

    public void setLoadPercentageChangeFrequencyS(int loadPercentageChangeFrequencyS) {
        this.loadPercentageChangeFrequencyS = loadPercentageChangeFrequencyS;
    }
    
    public int getMemoryLoadSizeMiB() {
        return memoryLoadSizeMiB;
    }
    
    public void setMemoryLoadSizeMiB(int memoryLoadSizeMiB) {
        this.memoryLoadSizeMiB = memoryLoadSizeMiB;
    }
    
    public int getMemoryLoadSizeMiBHigh() {
        return memoryLoadSizeMiBHigh;
    }

    public void setMemoryLoadSizeMiBHigh(int memoryLoadSizeMiBHigh) {
        this.memoryLoadSizeMiBHigh = memoryLoadSizeMiBHigh;
    }

    public int getMemoryLoadSizeMiBChangeStep() {
        return memoryLoadSizeMiBChangeStep;
    }

    public void setMemoryLoadSizeMiBChangeStep(int memoryLoadSizeMiBChangeStep) {
        this.memoryLoadSizeMiBChangeStep = memoryLoadSizeMiBChangeStep;
    }

    public int getMemoryLoadChangeFrequencyS() {
        return memoryLoadChangeFrequencyS;
    }

    public void setMemoryLoadChangeFrequencyS(int memoryLoadChangeFrequencyS) {
        this.memoryLoadChangeFrequencyS = memoryLoadChangeFrequencyS;
    }

    public int getHanoiSize() {
        return hanoiSize;
    }

    public void setHanoiSize(int hanoiSize) {
        this.hanoiSize = hanoiSize;
    }
    
    public boolean isHanoiForeground() {
        return hanoiForeground;
    }

    public void setHanoiForeground(boolean hanoiForeground) {
        this.hanoiForeground = hanoiForeground;
    }
    
    public LoadType getLoadType() {
        return loadType;
    }

    public void setLoadType(LoadType loadType) {
        this.loadType = loadType;
    }
    
}
