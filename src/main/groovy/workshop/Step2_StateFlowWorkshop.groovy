package workshop

import groovy.transform.Immutable

/**
 * Created by mtumilowicz on 2018-10-16.
 */
@Immutable
class Step2_StateFlowWorkshop {

    Step1_StateWorkshop from
    Step1_StateWorkshop into

    static def of(from, into) {
        new Step2_StateFlowWorkshop(new Step1_StateWorkshop(from), new Step1_StateWorkshop(into))
    }

    @Override
    String toString() {
        "$from->$into"
    }
}
