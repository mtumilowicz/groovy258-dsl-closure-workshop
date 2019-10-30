package workshop

// package scope, immutable
class Step3_TransitionWorkshop {

    String transitionEvent = ''
    Step2_StateFlowWorkshop stateFlow = new Step2_StateFlowWorkshop()

    static Step3_TransitionWorkshop make(Closure transitionRecipe) {
        Step4_TransitionSpecWorkshop.make transitionRecipe
    }

    @Override
    String toString() {
        "$transitionEvent: $stateFlow"
    }
}
