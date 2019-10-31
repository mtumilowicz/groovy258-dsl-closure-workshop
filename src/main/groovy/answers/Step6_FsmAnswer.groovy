package answers

import groovy.transform.Immutable
import groovy.transform.ToString

@Immutable
@ToString
class Step6_FsmAnswer {

    Map<String, Step2_StateFlowAnswer> transitions
    Step1_StateAnswer initial
    Step1_StateAnswer state

    static Step6_FsmAnswer create(fsmRecipe) {
        Step7_FsmSpecAnswer.buildUsing fsmRecipe
    }

    Step6_FsmAnswer returnToInitialState() {
        new Step6_FsmAnswer(transitions, initial, initial)
    }

    Step6_FsmAnswer fire(event) {
        getTransitionFor(event)
                .filter { it.from == state }
                .map { new Step6_FsmAnswer(transitions, initial, it.into) }
                .orElseGet { this }
    }

    private Optional<Step2_StateFlowAnswer> getTransitionFor(event) {
        Optional.ofNullable(transitions[event])
    }
}
