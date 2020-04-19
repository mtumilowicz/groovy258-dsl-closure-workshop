package workshop


import spock.lang.Specification

class Step7_FsmWorkshopTest extends Specification {

    def 'create empty fsm'() {

        when:
        def fsm = Step7_FsmWorkshop.create {
        }

        then:
        fsm.initial == new Step1_StateWorkshop()
        fsm.transitions.size() == 0
    }

    def 'create fsm with empty transition'() {

        when:
        def fsm = Step7_FsmWorkshop.create {
            add {}
        }

        then:
        fsm.transitions.size() == 1
        fsm.transitions[new Step3_EventWorkshop()] == Step2_StateFlowWorkshop.of(null, null)
    }

    def "create fsm with initial state and two transitions"() {

        when:
        def fsm = Step7_FsmWorkshop.create {
            initialState _state0
            add { on _event1 from _state1 into _state2 }
            add { on _event2 from _state2 into _state3 }
        }

        then:
        fsm.initial == new Step1_StateWorkshop(_state0)
        fsm.transitions.size() == 2
        fsm[_event1] == Step2_StateFlowWorkshop.of(_state1, _state2)
        fsm[_event2] == Step2_StateFlowWorkshop.of(_state2, _state3)

        where:
        _event1  | _event2  | _state0  | _state1  | _state2  | _state3
        "event1" | "event2" | "state0" | "state1" | "state2" | "state3"
    }

    def "order of initialState and add is not important"() {

        when: 'initialState is first'
        def fsm = Step7_FsmWorkshop.create {
            initialState _state0
            add { on _event1 from _state1 into _state2 }
            add { on _event2 from _state2 into _state3 }
        }
        and: 'initialState is second'
        def fsm1 = Step7_FsmWorkshop.create {
            add { on _event2 from _state2 into _state3 }
            initialState _state0
            add { on _event1 from _state1 into _state2 }
        }
        and: 'initialState is third'
        def fsm2 = Step7_FsmWorkshop.create {
            add { on _event1 from _state1 into _state2 }
            add { on _event2 from _state2 into _state3 }
            initialState _state0
        }

        then: 'outcome is always the same'
        fsm == fsm1
        fsm1 == fsm2
        fsm == fsm2

        where:
        _event1  | _event2  | _state0  | _state1  | _state2  | _state3
        "event1" | "event2" | "state0" | "state1" | "state2" | "state3"
    }

    def "order of on-from-into inside add is not important"() {

        given: "event1"
        def _event1 = 'event1'
        def _event1From = "${_event1}From"
        def _event1Into = "${_event1}Into"

        and: 'event2'
        def _event2 = 'event2'
        def _event2From = "${_event2}From"
        def _event2Into = "${_event2}Into"

        and: 'event3'
        def _event3 = 'event3'
        def _event3From = "${_event3}From"
        def _event3Into = "${_event3}Into"

        and: 'event4'
        def _event4 = 'event4'
        def _event4From = "${_event4}From"
        def _event4Into = "${_event4}Into"

        when: 'on-from-into'
        def fsm = Step7_FsmWorkshop.create {
            initialState 'initialState'
            add { on _event1 from _event1From into _event1Into } // on from into
            add { from _event2From on _event2 into _event2Into } // from on into
            add { from _event3From into _event3Into on _event3 } // from into on
            add { into _event4Into from _event4From on _event4 } // into from on
        }

        then: 'outcome is always the same'
        fsm[_event1] == Step2_StateFlowWorkshop.of(_event1From, _event1Into)
        fsm[_event2] == Step2_StateFlowWorkshop.of(_event2From, _event2Into)
        fsm[_event3] == Step2_StateFlowWorkshop.of(_event3From, _event3Into)
        fsm[_event4] == Step2_StateFlowWorkshop.of(_event4From, _event4Into)

    }

    def "if event is not defined in transitions, stay in current state"() {

        given:
        def _initialState = 'initialState'

        and: 'create fsm with empty transitions'
        def fsm = Step7_FsmWorkshop.create {
            initialState _initialState
        }

        when:
        fsm = fsm.fire('event')

        then:
        fsm.state == new Step1_StateWorkshop(_initialState)
    }

    def "if transition is not designed for current state - stay in current state"() {

        given:
        def _state = 'state'
        def _event = 'event'

        and: 'create fsm with single transition not designed for state'
        def fsm = Step7_FsmWorkshop.create {
            initialState _state
            add { on _event from 'stateFrom' into 'stateTo' }
        }

        when: 'transition is not designed for current state'
        fsm = fsm.fire(_event)

        then: 'stay in current state'
        fsm.state == new Step1_StateWorkshop(_state)
    }

    def "if transition is designed for current state - move to stateInto"() {

        given:
        def _state = 'state'

        and: 'define transition'
        def _event = 'event'
        def _stateTo = 'stateTo'

        and: 'create fsm with single transition designed for state'
        def fsm = Step7_FsmWorkshop.create {
            initialState _state
            add { on _event from _state into _stateTo }
        }

        when: 'transition is designed for current state'
        fsm = fsm.fire(_event)

        then: 'moved to stateTo'
        fsm.state == new Step1_StateWorkshop(_stateTo)
    }

    def "if we are in initial state then returning to initial state means returning exact copy"() {

        given:
        def fsm = Step7_FsmWorkshop.create {
            initialState 'state'
        }

        expect:
        fsm == fsm.returnToInitialState()
    }

    def "if we are in any state then returning to initial state means returning copy with current state set to initial state"() {

        given: 'create fsm with single transition'
        def fsm = Step7_FsmWorkshop.create {
            initialState 'initialState'
            add { on 'event' from 'initialState' into 'state2' }
        }

        and: 'state changes to state2'
        def fsmInState2 = fsm.fire('event')

        when: 'return to initial state'
        def fsmInInitialState = fsmInState2.returnToInitialState()

        then:
        with(fsmInInitialState) {
            initial == new Step1_StateWorkshop('initialState')
            transitions == fsmInState2.transitions
        }
    }

    def 'when operation is illegal according to fsm specification - error'() {
        when: 'wrongName is not defined '
        Step7_FsmWorkshop.create {
            wrongName
        }

        then:
        Step9_InvalidFsmSpecOperationWorkshop ex = thrown()
        ex.message == 'Operation: wrongName is invalid according to fsm specification'
    }

    def 'when operation and argument is illegal according to fsm specification - error'() {
        when: 'wrongName is not defined '
        Step7_FsmWorkshop.create {
            wrongName 1
        }

        then:
        Step9_InvalidFsmSpecOperationWorkshop ex = thrown()
        ex.message == 'Operation: wrongName is invalid according to fsm specification'
    }

    def 'when argument of the initialState is illegal according to fsm specification - error'() {
        when: 'initialState accepts only strings'
        Step7_FsmWorkshop.create {
            initialState 1
        }

        then:
        Step9_InvalidFsmSpecOperationWorkshop ex = thrown()
        ex.message == 'Operation: initialState is invalid according to fsm specification'
    }

    def 'when argument of the add is illegal according to fsm specification - error'() {
        when: 'add accepts only closures'
        Step7_FsmWorkshop.create {
            add 1
        }

        then:
        Step9_InvalidFsmSpecOperationWorkshop ex = thrown()
        ex.message == 'Operation: add is invalid according to fsm specification'
    }

    def 'when operation is illegal according to transition specification - error'() {
        when: 'wrongName is not defined'
        Step7_FsmWorkshop.create {
            add { wrongName }
        }

        then:
        Step6_InvalidTransitionSpecOperationWorkshop ex = thrown()
        ex.message == 'Operation: wrongName is invalid according to transition specification'
    }

    def 'when operation and argument is illegal according to transition specification - error'() {
        when: 'wrongName is not defined'
        Step7_FsmWorkshop.create {
            add { wrongName 1 }
        }

        then:
        Step6_InvalidTransitionSpecOperationWorkshop ex = thrown()
        ex.message == 'Operation: wrongName is invalid according to transition specification'
    }

    def 'when argument of the operation "on" is illegal according to transition specification - error'() {
        when: 'on accepts only strings'
        Step7_FsmWorkshop.create {
            add { on 1 }
        }

        then:
        Step6_InvalidTransitionSpecOperationWorkshop ex = thrown()
        ex.message == 'Operation: on is invalid according to transition specification'
    }

    def 'when argument of the operation "from" is illegal according to transition specification - error'() {
        when: 'from accepts only strings'
        Step7_FsmWorkshop.create {
            add { from 1 }
        }

        then:
        Step6_InvalidTransitionSpecOperationWorkshop ex = thrown()
        ex.message == 'Operation: from is invalid according to transition specification'
    }

    def 'when argument of the operation "into" is illegal according to transition specification - error'() {
        when: 'into accepts only strings'
        Step7_FsmWorkshop.create {
            add { into 1 }
        }

        then:
        Step6_InvalidTransitionSpecOperationWorkshop ex = thrown()
        ex.message == 'Operation: into is invalid according to transition specification'
    }
}


