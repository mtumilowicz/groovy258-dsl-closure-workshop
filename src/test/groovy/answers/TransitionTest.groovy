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
            stateFlow == StateFlow.of(from_state, to_state)
        }
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
            stateFlow == StateFlow.of(_fromState, _toState)
        }
    }

}
