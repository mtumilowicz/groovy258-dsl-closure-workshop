package workshop

// immutable, toString
// simplest fsm with three fields transitions map, initial state, and current state
class Step7_FsmWorkshop {

    static Step7_FsmWorkshop create(fsmRecipe) {
        // invoke buildUsing method from Step8_FsmSpecWorkshop, hint: Step8_FsmSpecWorkshop.buildUsing fsmRecipe
    }

    Step7_FsmWorkshop returnToInitialState() {
        // copy of this with initial state as a current state
    }

    Step7_FsmWorkshop fire(event) {
        // if transition is possible (stateFrom is equal to current state) move to stateInto
        // otherwise return this
        // hint: transition.filter.map.orElseGet
    }

    // overload operator [] - hint: getAt(String event)

    private Optional<Step2_StateFlowWorkshop> getTransitionFor(String event) {
        // return stateFlow (wrapped in Optional) for given event
    }
}
