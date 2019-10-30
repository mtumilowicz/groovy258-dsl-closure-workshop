package workshop

class Step5_InvalidTransitionSpecOperationWorkshop extends RuntimeException {
    Step5_InvalidTransitionSpecOperationWorkshop(String message) {
        super("Operation: ${message} is invalid according to transition specification")
    }
}
