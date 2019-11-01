package answers

class Step6_InvalidTransitionSpecOperationAnswer extends RuntimeException {
    Step6_InvalidTransitionSpecOperationAnswer(operation) {
        super("Operation: ${operation} is invalid according to transition specification")
    }
}
