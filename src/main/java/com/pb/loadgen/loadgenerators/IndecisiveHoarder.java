package com.pb.loadgen.loadgenerators;

public class IndecisiveHoarder extends Hoarder {

    public int indecisiveness;
    public int speed;
    
    public IndecisiveHoarder(int loadSizeMegaBytes, int indecisiveness, int speed) {
        super(loadSizeMegaBytes);
        this.indecisiveness = indecisiveness;
        this.speed=speed;
    }

    @Override
    public void run() {
        
    }
}
