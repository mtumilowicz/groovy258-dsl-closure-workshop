package answers

import groovy.transform.EqualsAndHashCode
import groovy.transform.PackageScope

@EqualsAndHashCode
@PackageScope
class StateFlow {

    State from
    State into

    StateFlow() {
    }

    StateFlow(String from, String into) {
        this.from = new State(from)
        this.into = new State(into)
    }

    @Override
    String toString() {
        "$from->$into"
    }
}
