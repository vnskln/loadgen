package com.pb.loadgen.domains;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class LoadInput {

    private int loadPercentage;
    private int loadPercentageHigh;
    private int loadPercentageChangeStep;
    private int loadPercentageChangeFrequencyS;
    private int memoryLoadSizeMiB;
    private int memoryLoadSizeMiBHigh;
    private int memoryLoadSizeMiBChangeStep;
    private int memoryLoadChangeFrequencyS;
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

    public LoadType getLoadType() {
        return loadType;
    }

    public void setLoadType(LoadType loadType) {
        this.loadType = loadType;
    }
    
}
