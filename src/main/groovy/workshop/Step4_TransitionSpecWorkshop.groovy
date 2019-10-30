package workshop


import groovy.transform.PackageScope

/**
 * Created by mtumilowicz on 2018-10-16.
 */
@PackageScope
class Step4_TransitionSpecWorkshop {

    String transitionEvent
    String stateFrom
    String stateTo

    static Step3_TransitionWorkshop make(Closure transitionRecipe) {
        def transitionSpec = new Step4_TransitionSpecWorkshop()
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
        new Step3_TransitionWorkshop(transitionEvent: transitionEvent, stateFlow: Step2_StateFlowWorkshop.of(stateFrom, stateTo))
    }

    def methodMissing(String methodName, args) {
        throw new Step5_InvalidTransitionSpecOperationWorkshop(methodName)
    }

    def propertyMissing(String name) {
        throw new Step5_InvalidTransitionSpecOperationWorkshop(name)
    }

    def propertyMissing(String name, def arg) {
        throw new Step5_InvalidTransitionSpecOperationWorkshop(name)
    }
}
