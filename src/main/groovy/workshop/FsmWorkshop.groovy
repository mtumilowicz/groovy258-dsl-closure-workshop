package workshop

import groovy.transform.Immutable
import groovy.transform.ToString

/**
 * Created by mtumilowicz on 2018-10-16.
 */
@Immutable
@ToString
class FsmWorkshop {

    Map<String, StateFlowWorkshop> transitions
    StateWorkshop initial
    StateWorkshop state

    static FsmWorkshop create(Closure fsmRecipe) {
        FsmSpecWorkshop.buildUsing fsmRecipe
    }

    def returnToInitialState() {
        new FsmWorkshop(transitions, initial, initial)
    }

    def fire(event) {
        getTransitionFor(event)
                .filter { it.from == state }
                .map { new FsmWorkshop(transitions, initial, it.into) }
                .orElseGet { this }
    }

    private def getTransitionFor(event) {
        Optional.ofNullable(transitions[event])
    }
}
