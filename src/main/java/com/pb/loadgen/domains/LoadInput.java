//! Class for storage and transport of load parameters between views and application logic
package com.pb.loadgen.domains;

public class LoadInput {

    //! All possible load parameters
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

    //! Constructor
    public LoadInput () {}
    
    //! Getter
    public String getUniqueID() {
        return uniqueID;
    }

    //! Setter
    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    //! Getter
    public int getThreadNumber() {
        return threadNumber;
    }

    //! Setter
    public void setThreadNumber(int threadNumber) {
        this.threadNumber = threadNumber;
    }
    
    //! Getter
    public int getLoadPercentage() {
        return loadPercentage;
    }

    //! Setter
    public void setLoadPercentage(int loadPercentage) {
        this.loadPercentage = loadPercentage;
    }

    //! Getter
    public int getLoadPercentageHigh() {
        return loadPercentageHigh;
    }

    //! Setter
    public void setLoadPercentageHigh(int loadPercentageHigh) {
        this.loadPercentageHigh = loadPercentageHigh;
    }

    //! Getter
    public int getLoadPercentageChangeStep() {
        return loadPercentageChangeStep;
    }

    //! Setter
    public void setLoadPercentageChangeStep(int loadPercentageChangeStep) {
        this.loadPercentageChangeStep = loadPercentageChangeStep;
    }

    //! Getter
    public int getLoadPercentageChangeFrequencyS() {
        return loadPercentageChangeFrequencyS;
    }

    //! Setter
    public void setLoadPercentageChangeFrequencyS(int loadPercentageChangeFrequencyS) {
        this.loadPercentageChangeFrequencyS = loadPercentageChangeFrequencyS;
    }
    
    //! Getter
    public int getMemoryLoadSizeMiB() {
        return memoryLoadSizeMiB;
    }
    
    //! Setter
    public void setMemoryLoadSizeMiB(int memoryLoadSizeMiB) {
        this.memoryLoadSizeMiB = memoryLoadSizeMiB;
    }
    
    //! Getter
    public int getMemoryLoadSizeMiBHigh() {
        return memoryLoadSizeMiBHigh;
    }

    //! Setter
    public void setMemoryLoadSizeMiBHigh(int memoryLoadSizeMiBHigh) {
        this.memoryLoadSizeMiBHigh = memoryLoadSizeMiBHigh;
    }

    //! Getter
    public int getMemoryLoadSizeMiBChangeStep() {
        return memoryLoadSizeMiBChangeStep;
    }

    //! Setter
    public void setMemoryLoadSizeMiBChangeStep(int memoryLoadSizeMiBChangeStep) {
        this.memoryLoadSizeMiBChangeStep = memoryLoadSizeMiBChangeStep;
    }

    //! Getter
    public int getMemoryLoadChangeFrequencyS() {
        return memoryLoadChangeFrequencyS;
    }

    //! Setter
    public void setMemoryLoadChangeFrequencyS(int memoryLoadChangeFrequencyS) {
        this.memoryLoadChangeFrequencyS = memoryLoadChangeFrequencyS;
    }

    //! Getter
    public int getHanoiSize() {
        return hanoiSize;
    }

    //! Setter
    public void setHanoiSize(int hanoiSize) {
        this.hanoiSize = hanoiSize;
    }
    
    //! Getter
    public boolean isHanoiForeground() {
        return hanoiForeground;
    }

    //! Setter
    public void setHanoiForeground(boolean hanoiForeground) {
        this.hanoiForeground = hanoiForeground;
    }
    
    //! Getter
    public LoadType getLoadType() {
        return loadType;
    }

    //! Setter
    public void setLoadType(LoadType loadType) {
        this.loadType = loadType;
    }
    
}
