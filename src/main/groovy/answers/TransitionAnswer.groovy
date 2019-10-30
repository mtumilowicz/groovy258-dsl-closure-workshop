package answers

import groovy.transform.Immutable
import groovy.transform.PackageScope

/**
 * Created by mtumilowicz on 2018-10-16.
 */
@PackageScope
@Immutable
class TransitionAnswer {

    String transitionEvent = ""
    StateFlowAnswer stateFlow = new StateFlowAnswer()

    static TransitionAnswer make(Closure transitionRecipe) {
        TransitionSpecAnswer.make transitionRecipe
    }

    @Override
    String toString() {
        "$transitionEvent: $stateFlow"
    }
}
