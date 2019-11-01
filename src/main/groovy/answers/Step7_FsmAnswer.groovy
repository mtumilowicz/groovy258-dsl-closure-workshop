package answers

import groovy.transform.Immutable
import groovy.transform.ToString

@Immutable
@ToString
class Step7_FsmAnswer {

    Map<Step3_EventAnswer, Step2_StateFlowAnswer> transitions
    Step1_StateAnswer initial
    Step1_StateAnswer state

    static Step7_FsmAnswer create(fsmRecipe) {
        Step8_FsmSpecAnswer.buildUsing fsmRecipe
    }

    Step7_FsmAnswer returnToInitialState() {
        new Step7_FsmAnswer(transitions, initial, initial)
    }

    Step7_FsmAnswer fire(event) {
        getTransitionFor(event)
                .filter { it.from == state }
                .map { new Step7_FsmAnswer(transitions, initial, it.into) }
                .orElseGet { this }
    }

    def getAt(String event) {
        transitions[new Step3_EventAnswer(event)]
    }

    private Optional<Step2_StateFlowAnswer> getTransitionFor(event) {
        Optional.ofNullable(getAt(event))
    }
}
