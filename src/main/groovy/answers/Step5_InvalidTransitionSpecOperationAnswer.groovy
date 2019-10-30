package answers

/**
 * Created by mtumilowicz on 2018-10-16.
 */
class Step5_InvalidTransitionSpecOperationAnswer extends RuntimeException {
    Step5_InvalidTransitionSpecOperationAnswer(String message) {
        super("Operation: ${message} is invalid according to transition specification")
    }
}
