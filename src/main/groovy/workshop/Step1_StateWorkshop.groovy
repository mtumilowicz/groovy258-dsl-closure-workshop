package workshop

import groovy.transform.Immutable

/**
 * Created by mtumilowicz on 2018-10-16.
 */
@Immutable
class Step1_StateWorkshop {

    String raw

    @Override
    String toString() {
        raw
    }
}
