package com.pb.loadgen.domains;

public class LoadInput {

    private int loadPercentage;
    private LoadType loadType;

    public LoadInput () {}

    public int getLoadPercentage() {
        return loadPercentage;
    }

    public void setLoadPercentage(int loadPercentage) {
        this.loadPercentage = loadPercentage;
    }

    public LoadType getLoadType() {
        return loadType;
    }

    public void setLoadType(LoadType loadType) {
        this.loadType = loadType;
    }
}
