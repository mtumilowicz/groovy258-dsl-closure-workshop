package workshop


import groovy.transform.PackageScope

/**
 * Created by mtumilowicz on 2018-10-16.
 */
@PackageScope
class FsmSpecWorkshop {

    private List<TransitionWorkshop> transitions = []
    private StateWorkshop initialState

    static FsmWorkshop buildUsing(Closure fsmRecipe) {
        def fsmBuilder = new FsmSpecWorkshop()
        def fsm = fsmRecipe.rehydrate(fsmBuilder, this, fsmBuilder)
        fsm.resolveStrategy = Closure.DELEGATE_ONLY
        fsm()
        fsmBuilder.build()
    }

    def initialState(String state) {
        initialState = new StateWorkshop(state)
        this
    }

    def add(Closure transitionRecipe) {
        transitions << TransitionWorkshop.make(transitionRecipe)
        this
    }

    def build() {
        def map = transitions.collectEntries {
            [(it.transitionEvent): it.stateFlow]
        }
        new FsmWorkshop(map, initialState, initialState)
    }

    def methodMissing(String name, def args) {
        throw new InvalidFsmSpecOperationWorkshop(name)
    }

    def propertyMissing(String name) {
        throw new InvalidFsmSpecOperationWorkshop(name)
    }

    def propertyMissing(String name, def arg) {
        throw new InvalidFsmSpecOperationWorkshop(name)
    }
}
