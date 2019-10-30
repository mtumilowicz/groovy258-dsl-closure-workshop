package answers

import groovy.transform.Immutable

/**
 * Created by mtumilowicz on 2018-10-16.
 */
@Immutable
class StateFlowAnswer {

    StateAnswer from
    StateAnswer into

    static def of(from, into) {
        new StateFlowAnswer(new StateAnswer(from), new StateAnswer(into))
    }

    @Override
    String toString() {
        "$from->$into"
    }
}
