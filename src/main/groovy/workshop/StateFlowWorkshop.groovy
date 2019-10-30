package workshop

import groovy.transform.Immutable

/**
 * Created by mtumilowicz on 2018-10-16.
 */
@Immutable
class StateFlowWorkshop {

    StateWorkshop from
    StateWorkshop into

    static def of(from, into) {
        new StateFlowWorkshop(new StateWorkshop(from), new StateWorkshop(into))
    }

    @Override
    String toString() {
        "$from->$into"
    }
}
