package answers

import groovy.transform.PackageScope

/**
 * Created by mtumilowicz on 2018-10-16.
 */
@PackageScope
class Step7_FsmSpecAnswer {

    private List<Step3_TransitionAnswer> transitions = []
    private Step1_StateAnswer initialState

    static Step6_FsmAnswer buildUsing(Closure fsmRecipe) {
        def fsmBuilder = new Step7_FsmSpecAnswer()
        def fsm = fsmRecipe.rehydrate(fsmBuilder, this, fsmBuilder)
        fsm.resolveStrategy = Closure.DELEGATE_ONLY
        fsm()
        fsmBuilder.build()
    }

    def initialState(String state) {
        initialState = new Step1_StateAnswer(state)
        this
    }

    def add(Closure transitionRecipe) {
        transitions << Step3_TransitionAnswer.make(transitionRecipe)
        this
    }

    def build() {
        def map = transitions.collectEntries {
            [(it.transitionEvent): it.stateFlow]
        }
        new Step6_FsmAnswer(map, initialState, initialState)
    }

    def methodMissing(String name, def args) {
        throw new Step8_InvalidFsmSpecOperationAnswer(name)
    }

    def propertyMissing(String name) {
        throw new Step8_InvalidFsmSpecOperationAnswer(name)
    }

    def propertyMissing(String name, def arg) {
        throw new Step8_InvalidFsmSpecOperationAnswer(name)
    }
}
