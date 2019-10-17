package answers

import groovy.transform.PackageScope

@PackageScope
class FsmBuilder {

    private Map<String, StateFlow> map = [:]
    private State initialState

    static Fsm buildUsing(Closure fsmRecipe) {
        def fsmBuilder = new FsmBuilder()
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
        addTransition(Transition.make(transitionRecipe))
        this
    }

    def addTransition(Transition transition) {
        map[transition.transitionEvent] = transition.stateFlow
    }

    def build() {
        new Fsm(map, initialState, initialState)
    }

    def methodMissing(String name, def args) {
        throw new BadlyFormattedFsmRecipe(name)
    }

    def propertyMissing(String name) {
        throw new BadlyFormattedFsmRecipe(name)
    }

    def propertyMissing(String name, def arg) {
        throw new BadlyFormattedFsmRecipe(name)
    }
}
