package workshop

/**
 * Created by mtumilowicz on 2018-10-16.
 */
class Step8_InvalidFsmSpecOperationWorkshop extends RuntimeException {
    Step8_InvalidFsmSpecOperationWorkshop(String message) {
        super("Operation: ${message} is invalid according to fsm specification")
    }
}
