package workshop

class Step8_InvalidFsmSpecOperationWorkshop extends RuntimeException {
    Step8_InvalidFsmSpecOperationWorkshop(String message) {
        super("Operation: ${message} is invalid according to fsm specification")
    }
}
