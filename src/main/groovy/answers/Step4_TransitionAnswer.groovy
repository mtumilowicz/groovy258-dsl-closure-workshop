package answers

import groovy.transform.Immutable
import groovy.transform.PackageScope

@PackageScope
@Immutable
class Step4_TransitionAnswer {

    Step3_EventAnswer event
    Step2_StateFlowAnswer stateFlow

    static Step4_TransitionAnswer make(transitionRecipe) {
        Step5_TransitionSpecAnswer.make transitionRecipe
    }

    @Override
    String toString() {
        "$event: $stateFlow"
    }
}
