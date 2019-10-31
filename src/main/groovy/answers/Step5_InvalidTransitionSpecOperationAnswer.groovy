package answers

class Step5_InvalidTransitionSpecOperationAnswer extends RuntimeException {
    Step5_InvalidTransitionSpecOperationAnswer(String operation) {
        super("Operation: ${operation} is invalid according to transition specification")
    }
}
