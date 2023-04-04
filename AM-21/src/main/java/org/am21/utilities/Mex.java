package org.am21.utilities;

public enum Mex {

    Neutral("None"),
    Failed("Failure"),
    Success("Match > Success"),
    Retry("Try again");


    private final String value;

    Mex(String message) {
        this.value = message;
    }
    public static void show(Mex in){
        System.out.println();

    }


}
