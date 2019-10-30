package workshop

import groovy.transform.Immutable

/**
 * Created by mtumilowicz on 2018-10-16.
 */
@Immutable
class State {

    String raw

    @Override
    String toString() {
        raw
    }
}
