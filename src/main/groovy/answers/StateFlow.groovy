package answers

import groovy.transform.Immutable

/**
 * Created by mtumilowicz on 2018-10-16.
 */
@Immutable
class StateFlow {

    State from
    State into

    static def of(from, into) {
        new StateFlow(new State(from), new State(into))
    }

    @Override
    String toString() {
        "$from->$into"
    }
}
