package answers

import spock.lang.Specification

class FsmSpecTest extends Specification {

    def "apply() order of usage transition.on(), transition.from(), transition.to() method is not important"() {

        when:
        def fsm = new FsmSpec().add {
            on _event from _state1 into _state2
        }
                .initialState(_state0)
                .build()

        and:
        def fsm1 = new FsmSpec().add {
            from _state1 on _event into _state2
        }
                .initialState(_state0)
                .build()

        and:
        def fsm2 = new FsmSpec().add {
            into _state2 from _state1 on _event
        }
                .initialState(_state0)
                .build()


        then:
        fsm == fsm1
        fsm1 == fsm2

        where:
        _event  | _state0  | _state1  | _state2
        "event" | "state1" | "state1" | "state2"
    }

    def "build create Fsm"() {

        given:
        def builder = new FsmSpec()

        when:
        def fsm = builder.add {
            on _event from _state1 into _state2
        }.add {
            on _event1 from _state2 into _state3
        }
                .initialState(_state0)
                .build()

        then:
        fsm == new Fsm(initial: new State(_state0),
                transitions: [
                        (_event) : StateFlow.of(_state1, _state2),
                        (_event1): StateFlow.of(_state2, _state3)
                ],
                current: new State(_state0))

        where:
        _event  | _event1  | _state0  | _state1  | _state2  | _state3
        "event" | "event1" | "state0" | "state1" | "state2" | "state3"
    }

}

