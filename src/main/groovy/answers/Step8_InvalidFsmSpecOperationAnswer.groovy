package answers

/**
 * Created by mtumilowicz on 2018-10-16.
 */
class Step8_InvalidFsmSpecOperationAnswer extends RuntimeException {
    Step8_InvalidFsmSpecOperationAnswer(String message) {
        super("Operation: ${message} is invalid according to fsm specification")
    }
}
