package answers

import groovy.transform.PackageScope

/**
 * Created by mtumilowicz on 2018-08-29.
 */
@PackageScope
class Transition {

    String transitionEvent = ""
    StateFlow stateFlow = new StateFlow()

    static Transition make(Closure transitionRecipe) {
        new Transition().loadFrom transitionRecipe
    }

    Transition loadFrom(Closure transitionRecipe) {
        def transition = transitionRecipe.rehydrate(this, this, this)
        transition.resolveStrategy = Closure.DELEGATE_ONLY
        transition()
    }

    def methodMissing(String methodName, args) {
        throw new BadlyFormattedTransitionRecipe(methodName)
    }

    def propertyMissing(String name) {
        throw new BadlyFormattedTransitionRecipe(name)
    }

    def propertyMissing(String name, def arg) {
        throw new BadlyFormattedTransitionRecipe(name)
    }

    def on(event) {
        transitionEvent = event
        this
    }

    def from(String state) {
        stateFlow.from = new State(state)
        this
    }

    def into(String state) {
        stateFlow.into = new State(state)
        this
    }

    def fromState() {
        stateFlow.from
    }

    def intoState() {
        stateFlow.into
    }

    @Override
    String toString() {
        "$transitionEvent: $stateFlow"
    }
}
