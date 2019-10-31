package workshop

// package scope
// it is a Step3_TransitionWorkshop builder
class Step4_TransitionSpecWorkshop {

    // hint: three fields - event, stateFrom, stateTo

    // build Step3_TransitionWorkshop
    static Step3_TransitionWorkshop make(transitionRecipe) {
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

    Step3_TransitionWorkshop build() {
        // build Step3_TransitionWorkshop
    }

    def methodMissing(String methodName, args) {
        // throw appropriate exception: Step5_InvalidTransitionSpecOperationWorkshop
    }

    def propertyMissing(String propertyName) {
        // throw appropriate exception: Step5_InvalidTransitionSpecOperationWorkshop
    }
}
