package answers

import spock.lang.Specification

class FsmTest extends Specification {

    def "Fsm is immutable"() {

        given:
        def fsm = new Fsm("transitions": ["event": StateFlow.of("state1", "state2")], initial: new State("state0"))

        when:
        fsm.transitions["events2"] = StateFlow.of("state2", "state3")

        then:
        thrown(UnsupportedOperationException)
    }

    def "load() building transitions map with transition word"() {

        when:
        def fsm = Fsm.load {
            initialState _state0
            add { on _event1 from _state1 into _state2 }
            add { on _event2 from _state2 into _state3 }
        }

        then:
        fsm.transitions[_event1] == StateFlow.of(_state1, _state2)
        fsm.transitions[_event2] == StateFlow.of(_state2, _state3)

        where:
        _event1  | _event2  | _state0  | _state1  | _state2 | _state3
        "event1" | "event2" | "state0" | "state1" | "state2"| "state3"
    }

    def "load() order of method in load is not important" () {

        when:
        def fsm = Fsm.load {
            initialState _state0
            add { on _event1 from _state1 into _state2 }
            add { on _event2 from _state2 into _state3 }
        }
        and:
        def fsm1 = Fsm.load {
            add { on _event2 from _state2 into _state3 }
            initialState _state0
            add { on _event1 from _state1 into _state2 }
        }
        and:
        def fsm2 = Fsm.load {
            add { on _event1 from _state1 into _state2 }
            add { on _event2 from _state2 into _state3 }
            initialState _state0
        }

        then:
        fsm == fsm1
        fsm1 == fsm2
        fsm == fsm2

        where:
        _event1  | _event2  | _state0  | _state1  | _state2 | _state3
        "event1" | "event2" | "state0" | "state1" | "state2"| "state3"
    }

    def "fire(_event) -> state if _event in transitions and from _state == current"() {

        given:
        def fsm = Fsm.load {
            initialState _state0
            add { on _event1 from _state0 into _state2 }
        }

        when:
        fsm = fsm.fire(_event1)

        then:
        fsm.state() == new State(_state2)

        where:
        _event1  | _state0  | _state1  | _state2
        "event1" | "state0" | "state1" | "state2"
    }

    def "fire(_event) not change state if _event is in transitions but from _ state != current"() {

        given:
        def fsm = Fsm.load {
            initialState _state0
            add { on _event1 from _state1 into _state2 }
        }

        when:
        fsm = fsm.fire(_event1)

        then:
        fsm.state() == new State(_state0)


        where:
        _event1  | _state0  | _state1  | _state2
        "event1" | "state0" | "state1" | "state2"
    }

    def "fire(_event) not change state if _event is not in transitions"() {

        given:
        def fsm = Fsm.load {
            initialState _state0
            add { on _event1 from _state1 into _state2 }
        }

        when:
        fsm = fsm.fire(_event2)

        then:
        fsm.state() == new State(_state0)


        where:
        _event1  | _event2  | _state0  | _state1  | _state2
        "event1" | "event2" | "state0" | "state1" | "state2"
    }

    def "state() return currentState"() {
        when:
        def fsm = Fsm.load {
            initialState _state0
        }

        then:
        fsm.state() == fsm.current

        where:
        _state0  | _
        "state0" | _
    }


    def "clear() return to initial Fsm"() {

        given:
        def fsm = Fsm.load {
            initialState _state0
            add { on _event1 from _state0 into _state2 }
        }

        when:
        def fsm1 = fsm.fire(_event1)

        and:
        def fsm2 = fsm1.returnToInitialState()

        then:
        fsm == fsm2
        fsm1 != fsm2


        where:
        _event1  | _event2  | _state0  | _state1  | _state2
        "event1" | "event2" | "state0" | "state1" | "state2"
    }
}


