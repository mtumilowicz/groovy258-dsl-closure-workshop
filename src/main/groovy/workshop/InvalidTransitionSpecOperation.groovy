package workshop

/**
 * Created by mtumilowicz on 2018-10-16.
 */
class InvalidTransitionSpecOperation extends RuntimeException {
    InvalidTransitionSpecOperation(String message) {
        super("Operation: ${message} is invalid according to transition specification")
    }
}
