package answers

class BadlyFormattedFsmRecipe extends RuntimeException {
    BadlyFormattedFsmRecipe(String message) {
        super("Badly formatted fsm recipe: ${message}")
    }
}
