package answers

import groovy.transform.Immutable
import groovy.transform.PackageScope

/**
 * Created by mtumilowicz on 2018-08-29.
 */
@PackageScope
@Immutable
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
        new Transition(transitionEvent: event, stateFlow: stateFlow)
    }

    def from(String state) {
        new Transition(transitionEvent: transitionEvent, stateFlow: new StateFlow(new State(state), stateFlow.into))
    }

    def into(String state) {
        new Transition(transitionEvent: transitionEvent, stateFlow: new StateFlow(stateFlow.from, new State(state)))
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
