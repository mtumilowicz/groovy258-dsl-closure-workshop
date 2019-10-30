package workshop


import groovy.transform.PackageScope

/**
 * Created by mtumilowicz on 2018-10-16.
 */
@PackageScope
class TransitionSpec {

    String transitionEvent
    String stateFrom
    String stateTo

    static Transition make(Closure transitionRecipe) {
        def transitionSpec = new TransitionSpec()
        def transition = transitionRecipe.rehydrate(transitionSpec, this, transitionSpec)
        transition.resolveStrategy = Closure.DELEGATE_ONLY
        transition()
        transitionSpec.build()
    }

    def on(String event) {
        transitionEvent = event
        this
    }

    def from(String state) {
        stateFrom = state
        this
    }

    def into(String state) {
        stateTo = state
        this
    }

    def build() {
        new Transition(transitionEvent: transitionEvent, stateFlow: StateFlow.of(stateFrom, stateTo))
    }

    def methodMissing(String methodName, args) {
        throw new InvalidTransitionSpecOperation(methodName)
    }

    def propertyMissing(String name) {
        throw new InvalidTransitionSpecOperation(name)
    }

    def propertyMissing(String name, def arg) {
        throw new InvalidTransitionSpecOperation(name)
    }
}
