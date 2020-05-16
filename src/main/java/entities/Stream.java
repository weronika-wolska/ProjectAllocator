package entities;

public enum Stream{
    CS,
    DS,
    CSDS,
    commonTesting;

    public String getStreamString() {
        String string;
        if( this == Stream.CS) {
            string = "CS";
        }
        else if( this == Stream.DS || this == Stream.CSDS) {
            string = "CS+DS";
        }
        else if( this == Stream.commonTesting) {
            string = "COMMON";
        }
        else {
            string = "";
        }
        return string;
    }

}