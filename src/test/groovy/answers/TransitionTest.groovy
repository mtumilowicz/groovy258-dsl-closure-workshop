package answers

import spock.lang.Specification

/**
 * Created by mtumilowicz on 2018-08-29.
 */
class TransitionTest extends Specification {

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
