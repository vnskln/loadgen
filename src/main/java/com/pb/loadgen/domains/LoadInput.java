package com.pb.loadgen.domains;

public class LoadInput {

    private int loadPercentage;
    private int loadPercentageHigh;
    private int loadPercentageChangeStep;
    private int loadPercentageChangeFrequencyInSeconds;
    private int memoryLoadSizeMegaBytes;
    private LoadType loadType;

    public LoadInput () {}
    
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

    public int getLoadPercentageChangeFrequencyInSeconds() {
        return loadPercentageChangeFrequencyInSeconds;
    }

    public void setLoadPercentageChangeFrequencyInSeconds(int loadPercentageChangeFrequencyInSeconds) {
        this.loadPercentageChangeFrequencyInSeconds = loadPercentageChangeFrequencyInSeconds;
    }
    
    public int getMemoryLoadSizeMegaBytes() {
        return memoryLoadSizeMegaBytes;
    }
    
    public void setMemoryLoadSizeMegaBytes(int memoryLoadSizeMegaBytes) {
        this.memoryLoadSizeMegaBytes = memoryLoadSizeMegaBytes;
    }

    public LoadType getLoadType() {
        return loadType;
    }

    public void setLoadType(LoadType loadType) {
        this.loadType = loadType;
    }
    
}
