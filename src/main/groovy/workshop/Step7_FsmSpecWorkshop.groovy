package workshop

import groovy.transform.PackageScope

@PackageScope
class Step7_FsmSpecWorkshop {

    private List<Step3_TransitionWorkshop> transitions = []
    private Step1_StateWorkshop initialState

    static Step6_FsmWorkshop buildUsing(Closure fsmRecipe) {
        def fsmBuilder = new Step7_FsmSpecWorkshop()
        def fsm = fsmRecipe.rehydrate(fsmBuilder, this, fsmBuilder)
        fsm.resolveStrategy = Closure.DELEGATE_ONLY
        fsm()
        fsmBuilder.build()
    }

    def initialState(String state) {
        initialState = new Step1_StateWorkshop(state)
        this
    }

    def add(Closure transitionRecipe) {
        transitions << Step3_TransitionWorkshop.make(transitionRecipe)
        this
    }

    def build() {
        def map = transitions.collectEntries {
            [(it.transitionEvent): it.stateFlow]
        }
        new Step6_FsmWorkshop(map, initialState, initialState)
    }

    def methodMissing(String name, def args) {
        throw new Step8_InvalidFsmSpecOperationWorkshop(name)
    }

    def propertyMissing(String name) {
        throw new Step8_InvalidFsmSpecOperationWorkshop(name)
    }
}
