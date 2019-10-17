package answers

class BadlyFormattedTransitionRecipe extends RuntimeException {
    BadlyFormattedTransitionRecipe(String message) {
        super("Badly formatted transition recipe: ${message}")
    }
}
