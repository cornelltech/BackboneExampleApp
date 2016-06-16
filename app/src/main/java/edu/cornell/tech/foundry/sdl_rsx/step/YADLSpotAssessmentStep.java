package edu.cornell.tech.foundry.sdl_rsx.step;

import org.researchstack.backbone.answerformat.AnswerFormat;

import edu.cornell.tech.foundry.sdl_rsx.model.RSXMultipleImageSelectionSurveyOptions;
import edu.cornell.tech.foundry.sdl_rsx.ui.RSXMultipleImageSelectionSurveyLayout;
import edu.cornell.tech.foundry.sdl_rsx.ui.RSXSingleImageClassificationSurveyBody;
import edu.cornell.tech.foundry.sdl_rsx.ui.RSXSingleImageClassificationSurveyLayout;
import edu.cornell.tech.foundry.sdl_rsx.ui.YADLSpotAssessmentBody;
import edu.cornell.tech.foundry.sdl_rsx.ui.YADLSpotAssessmentLayout;

/**
 * Created by jk on 6/1/16.
 */
public class YADLSpotAssessmentStep extends RSXMultipleImageSelectionSurveyStep {

    @Override
    public Class getStepLayoutClass()
    {
        return YADLSpotAssessmentLayout.class;
    }

    @Override
    public Class<?> getStepBodyClass()
    {
        return YADLSpotAssessmentBody.class;
    }

    public YADLSpotAssessmentStep(
            String identifier,
            String title,
            AnswerFormat answerFormat,
            RSXMultipleImageSelectionSurveyOptions options
    )
    {
        super(identifier, title, answerFormat, options);
    }
}
