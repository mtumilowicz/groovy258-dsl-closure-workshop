package workshop


import groovy.transform.Immutable
import groovy.transform.PackageScope

/**
 * Created by mtumilowicz on 2018-10-16.
 */
@PackageScope
@Immutable
class Transition {

    String transitionEvent = ""
    StateFlow stateFlow = new StateFlow()

    static Transition make(Closure transitionRecipe) {
        TransitionSpec.make transitionRecipe
    }

    @Override
    String toString() {
        "$transitionEvent: $stateFlow"
    }
}
