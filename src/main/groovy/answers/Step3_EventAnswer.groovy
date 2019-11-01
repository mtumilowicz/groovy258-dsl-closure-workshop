package answers

import groovy.transform.Immutable

@Immutable
class Step3_EventAnswer {
    String raw

    @Override
    String toString() {
        raw
    }
}
