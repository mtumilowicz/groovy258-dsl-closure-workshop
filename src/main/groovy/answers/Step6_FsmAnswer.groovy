package answers

import groovy.transform.Immutable
import groovy.transform.ToString

/**
 * Created by mtumilowicz on 2018-10-16.
 */
@Immutable
@ToString
class Step6_FsmAnswer {

    Map<String, Step2_StateFlowAnswer> transitions
    Step1_StateAnswer initial
    Step1_StateAnswer state

    static Step6_FsmAnswer create(Closure fsmRecipe) {
        Step7_FsmSpecAnswer.buildUsing fsmRecipe
    }

    def returnToInitialState() {
        new Step6_FsmAnswer(transitions, initial, initial)
    }

    def fire(event) {
        getTransitionFor(event)
                .filter { it.from == state }
                .map { new Step6_FsmAnswer(transitions, initial, it.into) }
                .orElseGet { this }
    }

    private def getTransitionFor(event) {
        Optional.ofNullable(transitions[event])
    }
}
