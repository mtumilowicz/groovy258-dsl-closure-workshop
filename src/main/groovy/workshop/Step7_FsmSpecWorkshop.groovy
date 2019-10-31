package workshop

// package scope
// it is a Step6_FsmWorkshop builder, hint: two fields - list of transitions, initial state
class Step7_FsmSpecWorkshop {

    // build Step6_FsmWorkshop
    static Step6_FsmWorkshop buildUsing(fsmRecipe) {
        // create object of specification
        // rehydrate closure argument: delegate - specification, owner - this, this -> specification
        // set strategy of rehydrated closure to delegate_only
        // invoke rehydrated closure
        // build transition from specification
    }

    def initialState(String state) {
        // set initial state, return this
    }

    def add(Closure transitionRecipe) {
        // create transition and put it in transitions, return this
        // hint: <<, Step3_TransitionWorkshop.make
    }

    Step6_FsmWorkshop build() {
        // create map with entries (event, stateFlow), hint: transitions.collectEntries {}
        // return Step6_FsmWorkshop
    }

    def methodMissing(String methodName, args) {
        throw new Step8_InvalidFsmSpecOperationWorkshop(methodName)
    }

    def propertyMissing(String propertyName) {
        throw new Step8_InvalidFsmSpecOperationWorkshop(propertyName)
    }
}
