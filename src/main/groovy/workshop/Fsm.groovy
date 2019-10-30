package workshop

import groovy.transform.Immutable
import groovy.transform.ToString

/**
 * Created by mtumilowicz on 2018-10-16.
 */
@Immutable
@ToString
class Fsm {

    Map<String, StateFlow> transitions
    State initial
    State state

    static Fsm create(Closure fsmRecipe) {
        FsmSpec.buildUsing fsmRecipe
    }

    def returnToInitialState() {
        new Fsm(transitions, initial, initial)
    }

    def fire(event) {
        getTransitionFor(event)
                .filter { it.from == state }
                .map { new Fsm(transitions, initial, it.into) }
                .orElseGet { this }
    }

    private def getTransitionFor(event) {
        Optional.ofNullable(transitions[event])
    }
}
