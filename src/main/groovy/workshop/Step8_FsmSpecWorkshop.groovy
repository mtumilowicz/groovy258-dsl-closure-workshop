package workshop

// package scope
// it is a Step7_FsmWorkshop builder, hint: two fields - list of transitionsRecipe, initial state
class Step8_FsmSpecWorkshop {

    // build Step7_FsmWorkshop
    static Step7_FsmWorkshop buildUsing(fsmRecipe) {
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
        // hint: <<, Step4_TransitionWorkshop.make
    }

    Step7_FsmWorkshop build() {
        // create map with entries (event, stateFlow), hint: transitions.collect {}, Step4_TransitionAnswer.make, transitions.collectEntries {}
        // return Step7_FsmWorkshop
    }

    def methodMissing(String methodName, args) {
        throw new Step9_InvalidFsmSpecOperationWorkshop(methodName)
    }

    def propertyMissing(String propertyName) {
        throw new Step9_InvalidFsmSpecOperationWorkshop(propertyName)
    }
}
