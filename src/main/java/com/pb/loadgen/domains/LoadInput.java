package com.pb.loadgen.domains;

public class LoadInput {

    private int loadPercentage;
    private int indecisiveness;
    private int speed;
    private LoadType loadType;
    private int memoryLoadSizeMegaBytes;

    public LoadInput () {}
    
        public int getMemoryLoadSizeMegaBytes() {
        return memoryLoadSizeMegaBytes;
    }

    public void setMemoryLoadSizeMegaBytes(int memoryLoadSizeMegaBytes) {
        this.memoryLoadSizeMegaBytes = memoryLoadSizeMegaBytes;
    }

    public int getLoadPercentage() {
        return loadPercentage;
    }

    public void setLoadPercentage(int loadPercentage) {
        this.loadPercentage = loadPercentage;
    }

    public int getIndecisiveness() {
        return indecisiveness;
    }

    public void setIndecisiveness(int indecisiveness) {
        this.indecisiveness = indecisiveness;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public LoadType getLoadType() {
        return loadType;
    }

    public void setLoadType(LoadType loadType) {
        this.loadType = loadType;
    }


}
