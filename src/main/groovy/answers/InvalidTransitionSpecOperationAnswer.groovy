package answers

/**
 * Created by mtumilowicz on 2018-10-16.
 */
class InvalidTransitionSpecOperationAnswer extends RuntimeException {
    InvalidTransitionSpecOperationAnswer(String message) {
        super("Operation: ${message} is invalid according to transition specification")
    }
}
