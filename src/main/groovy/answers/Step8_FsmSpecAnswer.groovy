package answers

import groovy.transform.PackageScope

@PackageScope
class Step8_FsmSpecAnswer {

    private List<Closure> transitions = []
    private String initialState

    static Step7_FsmAnswer buildUsing(fsmRecipe) {
        def fsmBuilder = new Step8_FsmSpecAnswer()
        def fsm = fsmRecipe.rehydrate(fsmBuilder, this, fsmBuilder)
        fsm.resolveStrategy = Closure.DELEGATE_ONLY
        fsm()
        fsmBuilder.build()
    }

    def initialState(String state) {
        initialState = state
        this
    }

    def add(Closure transitionRecipe) {
        transitions << transitionRecipe
        this
    }

    Step7_FsmAnswer build() {
        def map = transitions.collect { Step4_TransitionAnswer.make(it) }
                .collectEntries {
                    [(it.event): it.stateFlow]
                }
        new Step7_FsmAnswer(transitions: map, initial: new Step1_StateAnswer(initialState), state: new Step1_StateAnswer(initialState))
    }

    def methodMissing(String methodName, args) {
        throw new Step9_InvalidFsmSpecOperationAnswer(methodName)
    }

    def propertyMissing(String propertyName) {
        throw new Step9_InvalidFsmSpecOperationAnswer(propertyName)
    }
}
