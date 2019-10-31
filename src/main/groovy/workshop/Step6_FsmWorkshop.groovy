package workshop

// immutable, toString
class Step6_FsmWorkshop {

    // fsm in the simplest form should contain: transitions map, initial state, and current state

    static Step6_FsmWorkshop create(fsmRecipe) {
        // invoke buildUsing method from Step7_FsmSpecWorkshop
    }

    Step6_FsmWorkshop returnToInitialState() {
        // copy of this with initial state as a current state
    }

    Step6_FsmWorkshop fire(event) {
        // if transition is possible (stateFrom is equal to current state) move to stateInto
        // otherwise do nothing
    }

    private Optional<Step2_StateFlowWorkshop> getTransitionFor(String event) {
        // return stateFlow for given event (wrapped in Optional)
    }
}
