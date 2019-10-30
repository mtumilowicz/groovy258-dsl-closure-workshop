package workshop


import groovy.transform.PackageScope

/**
 * Created by mtumilowicz on 2018-10-16.
 */
@PackageScope
class TransitionSpecWorkshop {

    String transitionEvent
    String stateFrom
    String stateTo

    static TransitionWorkshop make(Closure transitionRecipe) {
        def transitionSpec = new TransitionSpecWorkshop()
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
        new TransitionWorkshop(transitionEvent: transitionEvent, stateFlow: StateFlowWorkshop.of(stateFrom, stateTo))
    }

    def methodMissing(String methodName, args) {
        throw new InvalidTransitionSpecOperationWorkshop(methodName)
    }

    def propertyMissing(String name) {
        throw new InvalidTransitionSpecOperationWorkshop(name)
    }

    def propertyMissing(String name, def arg) {
        throw new InvalidTransitionSpecOperationWorkshop(name)
    }
}
