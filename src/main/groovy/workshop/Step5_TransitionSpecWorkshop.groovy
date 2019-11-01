package workshop

// package scope
// it is a Step4_TransitionWorkshop builder
class Step5_TransitionSpecWorkshop {

    // hint: three fields - event, stateFrom, stateTo

    // build Step4_TransitionWorkshop
    static Step4_TransitionWorkshop make(transitionRecipe) {
        // create object of specification
        // rehydrate closure argument: delegate - specification, owner - this, this -> specification
        // set strategy of rehydrated closure to delegate_only
        // invoke rehydrated closure
        // build transition from specification
    }

    def on(String event) {
        // set event, return this
    }

    def from(String state) {
        // set state, return this
    }

    def into(String state) {
        // set state, return this
    }

    Step4_TransitionWorkshop build() {
        // build Step4_TransitionWorkshop
    }

    def methodMissing(String methodName, args) {
        // throw appropriate exception: Step6_InvalidTransitionSpecOperationWorkshop
    }

    def propertyMissing(String propertyName) {
        // throw appropriate exception: Step6_InvalidTransitionSpecOperationWorkshop
    }
}
