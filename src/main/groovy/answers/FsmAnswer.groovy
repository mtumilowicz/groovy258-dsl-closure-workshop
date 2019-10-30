package answers

import groovy.transform.Immutable
import groovy.transform.ToString

/**
 * Created by mtumilowicz on 2018-10-16.
 */
@Immutable
@ToString
class FsmAnswer {

    Map<String, StateFlowAnswer> transitions
    StateAnswer initial
    StateAnswer state

    static FsmAnswer create(Closure fsmRecipe) {
        FsmSpecAnswer.buildUsing fsmRecipe
    }

    def returnToInitialState() {
        new FsmAnswer(transitions, initial, initial)
    }

    def fire(event) {
        getTransitionFor(event)
                .filter { it.from == state }
                .map { new FsmAnswer(transitions, initial, it.into) }
                .orElseGet { this }
    }

    private def getTransitionFor(event) {
        Optional.ofNullable(transitions[event])
    }
}
