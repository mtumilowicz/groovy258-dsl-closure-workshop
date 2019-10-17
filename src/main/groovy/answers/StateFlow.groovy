package answers


import groovy.transform.Immutable
import groovy.transform.PackageScope

@Immutable
@PackageScope
class StateFlow {

    State from
    State into

    static def of(from, into) {
        new StateFlow(new State(from), new State(into))
    }

    @Override
    String toString() {
        "$from->$into"
    }
}
