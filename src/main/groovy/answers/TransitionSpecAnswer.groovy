package answers


import groovy.transform.PackageScope

/**
 * Created by mtumilowicz on 2018-10-16.
 */
@PackageScope
class TransitionSpecAnswer {

    String transitionEvent
    String stateFrom
    String stateTo

    static TransitionAnswer make(Closure transitionRecipe) {
        def transitionSpec = new TransitionSpecAnswer()
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
        new TransitionAnswer(transitionEvent: transitionEvent, stateFlow: StateFlowAnswer.of(stateFrom, stateTo))
    }

    def methodMissing(String methodName, args) {
        throw new InvalidTransitionSpecOperationAnswer(methodName)
    }

    def propertyMissing(String name) {
        throw new InvalidTransitionSpecOperationAnswer(name)
    }

    def propertyMissing(String name, def arg) {
        throw new InvalidTransitionSpecOperationAnswer(name)
    }
}
