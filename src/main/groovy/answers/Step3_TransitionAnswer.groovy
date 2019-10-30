package answers

import groovy.transform.Immutable
import groovy.transform.PackageScope

/**
 * Created by mtumilowicz on 2018-10-16.
 */
@PackageScope
@Immutable
class Step3_TransitionAnswer {

    String transitionEvent = ""
    Step2_StateFlowAnswer stateFlow = new Step2_StateFlowAnswer()

    static Step3_TransitionAnswer make(Closure transitionRecipe) {
        Step4_TransitionSpecAnswer.make transitionRecipe
    }

    @Override
    String toString() {
        "$transitionEvent: $stateFlow"
    }
}
