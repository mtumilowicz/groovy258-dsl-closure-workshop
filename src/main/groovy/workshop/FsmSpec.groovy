package workshop


import groovy.transform.PackageScope

/**
 * Created by mtumilowicz on 2018-10-16.
 */
@PackageScope
class FsmSpec {

    private List<Transition> transitions = []
    private State initialState

    static Fsm buildUsing(Closure fsmRecipe) {
        def fsmBuilder = new FsmSpec()
        def fsm = fsmRecipe.rehydrate(fsmBuilder, this, fsmBuilder)
        fsm.resolveStrategy = Closure.DELEGATE_ONLY
        fsm()
        fsmBuilder.build()
    }

    def initialState(String state) {
        initialState = new State(state)
        this
    }

    def add(Closure transitionRecipe) {
        transitions << Transition.make(transitionRecipe)
        this
    }

    def build() {
        def map = transitions.collectEntries {
            [(it.transitionEvent): it.stateFlow]
        }
        new Fsm(map, initialState, initialState)
    }

    def methodMissing(String name, def args) {
        throw new InvalidFsmSpecOperation(name)
    }

    def propertyMissing(String name) {
        throw new InvalidFsmSpecOperation(name)
    }

    def propertyMissing(String name, def arg) {
        throw new InvalidFsmSpecOperation(name)
    }
}
