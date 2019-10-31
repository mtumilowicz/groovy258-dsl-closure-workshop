package workshop

// immutable, toString
// simplest fsm with three fields transitions map, initial state, and current state
class Step6_FsmWorkshop {

    static Step6_FsmWorkshop create(fsmRecipe) {
        // invoke buildUsing method from Step7_FsmSpecWorkshop, hint: Step7_FsmSpecWorkshop.buildUsing fsmRecipe
    }

    Step6_FsmWorkshop returnToInitialState() {
        // copy of this with initial state as a current state
    }

    Step6_FsmWorkshop fire(event) {
        // if transition is possible (stateFrom is equal to current state) move to stateInto
        // otherwise return this
        // hint: transition.filter.map.orElseGet
    }

    private Optional<Step2_StateFlowWorkshop> getTransitionFor(String event) {
        // return stateFlow for given event (wrapped in Optional)
    }
}
