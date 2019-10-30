package workshop

import groovy.transform.PackageScope

@PackageScope
class Step7_FsmSpecWorkshop {

    // fsm spec is as simple as it can be - it is a builder
    // fields: list of transitions, initial state

    // build Step6_FsmWorkshop
    static Step6_FsmWorkshop buildUsing(fsmRecipe) {
        // create object of specification
        // rehydrate closure argument: delegate - specification, owner - this, this -> specification
        // set delegate_only strategy to rehydrated closure
        // invoke rehydrated closure
        // build transition from specification
    }

    def initialState(state) {
        // set initial state, return this
    }

    def add(transitionRecipe) {
        // create transition and put it in transitions, return this
    }

    Step6_FsmWorkshop build() {
        // create map with entries (event, stateFlow)
        // return Step6_FsmWorkshop
    }

    def methodMissing(methodName, args) {
        throw new Step8_InvalidFsmSpecOperationWorkshop(methodName)
    }

    def propertyMissing(propertyName) {
        throw new Step8_InvalidFsmSpecOperationWorkshop(propertyName)
    }
}
