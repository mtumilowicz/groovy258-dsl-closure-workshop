package answers

import groovy.transform.Immutable

@Immutable
class Step1_StateAnswer {

    String raw

    @Override
    String toString() {
        raw
    }
}
