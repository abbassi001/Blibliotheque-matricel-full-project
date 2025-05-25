package Align;
public class IrregularSysLinException extends Exception {
    public IrregularSysLinException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "IrregularSysLinException: " + getMessage();
    }
}