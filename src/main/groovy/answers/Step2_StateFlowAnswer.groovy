package answers

import groovy.transform.Immutable

/**
 * Created by mtumilowicz on 2018-10-16.
 */
@Immutable
class Step2_StateFlowAnswer {

    Step1_StateAnswer from
    Step1_StateAnswer into

    static def of(from, into) {
        new Step2_StateFlowAnswer(new Step1_StateAnswer(from), new Step1_StateAnswer(into))
    }

    @Override
    String toString() {
        "$from->$into"
    }
}
