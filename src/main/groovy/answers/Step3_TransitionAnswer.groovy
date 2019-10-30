package answers

import groovy.transform.Immutable
import groovy.transform.PackageScope

@PackageScope
@Immutable
class Step3_TransitionAnswer {

    String event = ''
    Step2_StateFlowAnswer stateFlow = new Step2_StateFlowAnswer()

    static Step3_TransitionAnswer make(Closure transitionRecipe) {
        Step4_TransitionSpecAnswer.make transitionRecipe
    }

    @Override
    String toString() {
        "$event: $stateFlow"
    }
}
