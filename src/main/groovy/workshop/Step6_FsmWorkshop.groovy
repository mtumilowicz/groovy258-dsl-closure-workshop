package workshop

// immutable, toString
class Step6_FsmWorkshop {

    Map<String, Step2_StateFlowWorkshop> transitions
    Step1_StateWorkshop initial
    Step1_StateWorkshop state

    static Step6_FsmWorkshop create(Closure fsmRecipe) {
        Step7_FsmSpecWorkshop.buildUsing fsmRecipe
    }

    def returnToInitialState() {
        new Step6_FsmWorkshop(transitions, initial, initial)
    }

    def fire(event) {
        getTransitionFor(event)
                .filter { it.from == state }
                .map { new Step6_FsmWorkshop(transitions, initial, it.into) }
                .orElseGet { this }
    }

    private def getTransitionFor(event) {
        Optional.ofNullable(transitions[event])
    }
}
