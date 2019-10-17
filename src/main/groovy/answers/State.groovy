package answers

import groovy.transform.Immutable

@Immutable
class State {

    String raw

    @Override
    String toString() {
        raw
    }
}
