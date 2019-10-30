package workshop

import groovy.transform.Immutable

@Immutable
class Step1_StateWorkshop {

    String raw

    @Override
    String toString() {
        raw
    }
}
