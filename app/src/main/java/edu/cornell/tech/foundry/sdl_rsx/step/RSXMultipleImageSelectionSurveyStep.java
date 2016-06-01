package edu.cornell.tech.foundry.sdl_rsx.step;

import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.step.QuestionStep;

import edu.cornell.tech.foundry.sdl_rsx.ui.RSXSingleImageClassificationSurveyBody;
import edu.cornell.tech.foundry.sdl_rsx.ui.RSXSingleImageClassificationSurveyLayout;

/**
 * Created by jk on 6/1/16.
 */
abstract public class RSXMultipleImageSelectionSurveyStep extends QuestionStep {

    /**
     * Returns a new question step that includes the specified identifier, title, image, and answer
     * format.
     *
     * @param identifier The identifier of the step (a step identifier should be unique within the
     *                   task).
     * @param title      A localized string that represents the primary text of the question.
     * @param answerFormat The format in which the answer is expected.
     */
    public RSXMultipleImageSelectionSurveyStep(
            String identifier,
            String title,
            AnswerFormat answerFormat
    )
    {
        super(identifier, title, answerFormat);
    }
}
