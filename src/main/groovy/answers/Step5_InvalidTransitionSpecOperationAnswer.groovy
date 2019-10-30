package answers

class Step5_InvalidTransitionSpecOperationAnswer extends RuntimeException {
    Step5_InvalidTransitionSpecOperationAnswer(String message) {
        super("Operation: ${message} is invalid according to transition specification")
    }
}
