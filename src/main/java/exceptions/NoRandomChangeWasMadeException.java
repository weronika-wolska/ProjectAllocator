package exceptions;

public class NoRandomChangeWasMadeException extends Exception {
    // thrown by CandidateSolution class when undoRandomChange method is called without any change being made to the class beforehand
}
