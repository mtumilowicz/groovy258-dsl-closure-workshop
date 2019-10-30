package answers

import groovy.transform.PackageScope

@PackageScope
class Step4_TransitionSpecAnswer {

    String event
    String stateFrom
    String stateTo

    static Step3_TransitionAnswer make(Closure transitionRecipe) {
        def transitionSpec = new Step4_TransitionSpecAnswer()
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

    def build() {
        new Step3_TransitionAnswer(event: event, stateFlow: Step2_StateFlowAnswer.of(stateFrom, stateTo))
    }

    def methodMissing(String methodName, args) {
        throw new Step5_InvalidTransitionSpecOperationAnswer(methodName)
    }

    def propertyMissing(String name) {
        throw new Step5_InvalidTransitionSpecOperationAnswer(name)
    }
}
