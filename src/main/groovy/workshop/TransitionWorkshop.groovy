package workshop


import groovy.transform.Immutable
import groovy.transform.PackageScope

/**
 * Created by mtumilowicz on 2018-10-16.
 */
@PackageScope
@Immutable
class TransitionWorkshop {

    String transitionEvent = ""
    StateFlowWorkshop stateFlow = new StateFlowWorkshop()

    static TransitionWorkshop make(Closure transitionRecipe) {
        TransitionSpecWorkshop.make transitionRecipe
    }

    @Override
    String toString() {
        "$transitionEvent: $stateFlow"
    }
}
