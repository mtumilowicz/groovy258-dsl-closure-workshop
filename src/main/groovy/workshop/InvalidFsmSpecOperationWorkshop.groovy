package workshop

/**
 * Created by mtumilowicz on 2018-10-16.
 */
class InvalidFsmSpecOperationWorkshop extends RuntimeException {
    InvalidFsmSpecOperationWorkshop(String message) {
        super("Operation: ${message} is invalid according to fsm specification")
    }
}
