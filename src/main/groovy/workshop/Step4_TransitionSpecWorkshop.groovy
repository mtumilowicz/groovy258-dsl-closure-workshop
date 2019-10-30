package workshop

// package scope
class Step4_TransitionSpecWorkshop {

    // transition spec is as simple as it can be - it is a builder
    // fields: event, stateFrom, stateTo

    // builder method of Step3_TransitionWorkshop
    static Step3_TransitionWorkshop make(Closure transitionRecipe) {
        // create object of specification
        // rehydrate closure argument: delegate - specification, owner - this, this -> specification
        // set delegate_only strategy to rehydrated closure
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
        throw new Step5_InvalidTransitionSpecOperationWorkshop(methodName)
    }

    def propertyMissing(String name) {
        throw new Step5_InvalidTransitionSpecOperationWorkshop(name)
    }
}
