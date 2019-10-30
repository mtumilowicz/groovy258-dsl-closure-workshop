package answers

import groovy.transform.Immutable

/**
 * Created by mtumilowicz on 2018-10-16.
 */
@Immutable
class Step1_StateAnswer {

    String raw

    @Override
    String toString() {
        raw
    }
}
