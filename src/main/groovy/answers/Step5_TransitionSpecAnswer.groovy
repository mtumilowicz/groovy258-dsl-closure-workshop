package answers

import groovy.transform.PackageScope

@PackageScope
class Step5_TransitionSpecAnswer {

    String event
    String stateFrom
    String stateTo

    static Step4_TransitionAnswer make(transitionRecipe) {
        def transitionSpec = new Step5_TransitionSpecAnswer()
        def transition = transitionRecipe.rehydrate(transitionSpec, this, transitionSpec)
        transition.resolveStrategy = Closure.DELEGATE_ONLY
        transition()
        transitionSpec.build()
    }

    def on(String event) {
        this.event = event
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

    Step4_TransitionAnswer build() {
        new Step4_TransitionAnswer(
                event: new Step3_EventAnswer(event),
                stateFlow: Step2_StateFlowAnswer.of(stateFrom, stateTo)
        )
    }

    def methodMissing(String methodName, args) {
        throw new Step6_InvalidTransitionSpecOperationAnswer(methodName)
    }

    def propertyMissing(String propertyName) {
        throw new Step6_InvalidTransitionSpecOperationAnswer(propertyName)
    }
}
