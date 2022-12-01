package org.b07boys.walnut.timeline;

import java.util.ArrayList;

public class ValidTimelines {
    private ArrayList<Timeline> validTimelines;
    private static ValidTimelines instance;

    private ValidTimelines(){
        validTimelines = new ArrayList<>();
    }

    public ArrayList<Timeline> getValidTimelines(){
        return validTimelines;
    }

    public void setValidTimelines(ArrayList<Timeline> validTimelines){
        this.validTimelines = validTimelines;
    }

    public static ValidTimelines getInstance(){
        if(instance == null){
            new ValidTimelines();
        }
        return instance;
    }
}
