package answers

/**
 * Created by mtumilowicz on 2018-10-16.
 */
class InvalidFsmSpecOperationAnswer extends RuntimeException {
    InvalidFsmSpecOperationAnswer(String message) {
        super("Operation: ${message} is invalid according to fsm specification")
    }
}
