package answers

import groovy.transform.Immutable
import groovy.transform.ToString


@Immutable
@ToString
class Fsm {

    Map<String, StateFlow> transitions
    State initial
    State current

    static Fsm create(Closure fsmRecipe) {
        FsmSpec.buildUsing fsmRecipe
    }

    def state() {
        current
    }

    def returnToInitialState() {
        new Fsm(transitions, initial, initial)
    }

    def fire(event) {
        getTransitionFor(event)
                .filter { it.from == current }
                .map { new Fsm(transitions, initial, it.into) }
                .orElseGet { this }
    }

    private def getTransitionFor(event) {
        Optional.ofNullable(transitions[event])
    }
}
