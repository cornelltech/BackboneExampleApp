package edu.cornell.tech.foundry.sdl_rsx.step;

import org.researchstack.backbone.answerformat.AnswerFormat;

import edu.cornell.tech.foundry.sdl_rsx.ui.RSXSingleImageClassificationSurveyBody;
import edu.cornell.tech.foundry.sdl_rsx.ui.RSXSingleImageClassificationSurveyLayout;

/**
 * Created by jk on 6/1/16.
 */
public class YADLSpotAssessmentStep extends RSXMultipleImageSelectionSurveyStep {

    @Override
    public Class getStepLayoutClass()
    {
        return RSXSingleImageClassificationSurveyLayout.class;
    }

    @Override
    public Class<?> getStepBodyClass()
    {
        return RSXSingleImageClassificationSurveyBody.class;
    }

    public YADLSpotAssessmentStep(
            String identifier,
            String title,
            AnswerFormat answerFormat
    )
    {
        super(identifier, title, answerFormat);
    }
}
