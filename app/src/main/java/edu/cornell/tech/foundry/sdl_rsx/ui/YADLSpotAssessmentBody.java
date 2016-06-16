package edu.cornell.tech.foundry.sdl_rsx.ui;

import org.researchstack.backbone.result.StepResult;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.ui.step.body.BodyAnswer;

import java.util.Arrays;
import java.util.HashSet;

import edu.cornell.tech.foundry.sdl_rsx.answerformat.RSXImageChoiceAnswerFormat;
import edu.cornell.tech.foundry.sdl_rsx.step.RSXMultipleImageSelectionSurveyStep;

/**
 * Created by jk on 6/10/16.
 */
public class YADLSpotAssessmentBody <T> extends RSXMultipleImageSelectionSurveyBody {

    public YADLSpotAssessmentBody(Step step, StepResult result, OnSelectionListener onSelectionListener) {
        super(step, result, onSelectionListener);
    }
    public YADLSpotAssessmentBody(Step step, StepResult result) {
        super(step, result);
    }

    protected boolean supportsMultipleSelection() {
        return true;
    }

    @Override
    public BodyAnswer getBodyAnswerState()
    {
        return BodyAnswer.VALID;
    }
}
