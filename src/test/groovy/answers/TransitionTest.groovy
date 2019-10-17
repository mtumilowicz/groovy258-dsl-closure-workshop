package answers

import spock.lang.Specification

/**
 * Created by mtumilowicz on 2018-08-29.
 */
class TransitionTest extends Specification {

    def "test on"() {
        given:
        def transition_event = "test"
        def transition = new Transition()

        when:
        def transition_after_on = transition.on(transition_event)

        then:
        transition_after_on.transitionEvent == transition_event
    }

    def "test from"() {
        given:
        def from_state = "test"
        def transition = new Transition()

        when:
        def transition_after_from = transition.from(from_state)

        then:
        transition_after_from.fromState() == new State(from_state)
    }

    def "test to"() {
        given:
        def to_state = "test"
        def transition = new Transition()

        when:
        def transition_after_to = transition.into(to_state)

        then:
        transition_after_to.intoState() == new State(to_state)
    }

    def "test full tense: on().from().to()"() {
        given:
        def transition_state = "event"
        def from_state = "fromState"
        def to_state = "toState"

        def transition = new Transition()

        when:
        def transition_after_on_from_to = transition.on(transition_state).from(from_state).into(to_state)

        then:
        with(transition_after_on_from_to) {
            transitionEvent == transition_state
            fromState() == new State(from_state)
            intoState() == new State(to_state)
        }
    }

    def "if word appears many times, only the last one occurrence is important"() {
        when:
        def transition_multiple_from = new Transition().from("from1").from("from2")

        then:
        transition_multiple_from.fromState() == new State("from2")
    }

    def "test toString"() {
        given:
        def transition = new Transition(transitionEvent: "event", stateFlow: StateFlow.of("fromState", "toState"))

        expect:
        transition.toString() == "event: fromState->toState"
    }

    def "make form full packed closure"() {
        given:
        def _fromState = "fromState"
        def _toState = "toState"
        def _event = "event"

        when:
        def transition = Transition.make {
            from _fromState into _toState on _event
        }

        then:
        with(transition) {
            transitionEvent == _event
            fromState() == new State(_fromState)
            intoState() == new State(_toState)
        }
    }

    def "make with only toState"() {
        given:
        def _toState = "toState"

        when:
        def transition = Transition.make {
            into _toState
        }

        then:
        with(transition) {
            intoState() == new State(_toState)
        }
    }

    def "make with only fromState"() {
        given:
        def _fromState = "fromState"

        when:
        def transition = Transition.make {
            from _fromState
        }

        then:
        with(transition) {
            fromState() == new State(_fromState)
        }
    }

    def "make with only transitionEvent"() {
        given:
        def _transitionEvent = "transitionEvent"

        when:
        def transition = Transition.make {
            on _transitionEvent
        }

        then:
        with(transition) {
            transitionEvent == _transitionEvent
        }
    }

}
