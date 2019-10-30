package answers

import groovy.transform.PackageScope

/**
 * Created by mtumilowicz on 2018-10-16.
 */
@PackageScope
class FsmSpecAnswer {

    private List<TransitionAnswer> transitions = []
    private StateAnswer initialState

    static FsmAnswer buildUsing(Closure fsmRecipe) {
        def fsmBuilder = new FsmSpecAnswer()
        def fsm = fsmRecipe.rehydrate(fsmBuilder, this, fsmBuilder)
        fsm.resolveStrategy = Closure.DELEGATE_ONLY
        fsm()
        fsmBuilder.build()
    }

    def initialState(String state) {
        initialState = new StateAnswer(state)
        this
    }

    def add(Closure transitionRecipe) {
        transitions << TransitionAnswer.make(transitionRecipe)
        this
    }

    def build() {
        def map = transitions.collectEntries {
            [(it.transitionEvent): it.stateFlow]
        }
        new FsmAnswer(map, initialState, initialState)
    }

    def methodMissing(String name, def args) {
        throw new InvalidFsmSpecOperationAnswer(name)
    }

    def propertyMissing(String name) {
        throw new InvalidFsmSpecOperationAnswer(name)
    }

    def propertyMissing(String name, def arg) {
        throw new InvalidFsmSpecOperationAnswer(name)
    }
}
