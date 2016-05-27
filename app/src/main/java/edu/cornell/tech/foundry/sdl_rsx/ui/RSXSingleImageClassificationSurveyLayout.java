package edu.cornell.tech.foundry.sdl_rsx.ui;

import edu.cornell.tech.foundry.sdl_rsx.step.RSXSingleImageClassificationSurveyStep;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;


import org.researchstack.backbone.utils.TextUtils;
import org.researchstack.backboneapp.R;

import org.researchstack.backbone.result.StepResult;
import org.researchstack.backbone.step.QuestionStep;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.ui.callbacks.StepCallbacks;
import org.researchstack.backbone.ui.step.body.StepBody;
import org.researchstack.backbone.ui.step.layout.SurveyStepLayout;

import java.lang.reflect.Constructor;

/**
 * Created by jk on 5/26/16.
 */
public class RSXSingleImageClassificationSurveyLayout extends SurveyStepLayout {

    //This is also declared private by superclass, but no getter has been defined
    //probably shoudl remove this
    private StepResult   stepResult;
    //need to keep this
    private QuestionStep questionStep;

    private StepCallbacks callbacks;

    private LinearLayout container;
    private StepBody     stepBody;

    public static final String TAG = RSXSingleImageClassificationSurveyLayout.class.getSimpleName();

    public RSXSingleImageClassificationSurveyLayout(Context context)
    {
        super(context);
    }

    public RSXSingleImageClassificationSurveyLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public RSXSingleImageClassificationSurveyLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initialize(Step step, StepResult result)
    {
        if(! (step instanceof RSXSingleImageClassificationSurveyStep))
        {
            throw new RuntimeException("Step being used in RSXSingleImageClassificationSurveyLayout is not a RSXSingleImageClassificationSurveyStep");
        }

        this.questionStep = (QuestionStep) step;
        this.initializeStep((RSXSingleImageClassificationSurveyStep) step, result);
    }

//    @Override
    public void initializeStep(RSXSingleImageClassificationSurveyStep step, StepResult result)
    {
        this.initStepLayout(step);
        this.initStepBody();
    }

//    @Override
    public void initStepLayout(RSXSingleImageClassificationSurveyStep step) {
        this.container = (LinearLayout) findViewById(R.id.rsx_single_image_classification_survey_content_container);
        ImageView imageView = (ImageView) findViewById(R.id.rsx_single_image_classification_survey_image_view);
        TextView itemDescriptionTextView = (TextView) findViewById(R.id.rsx_single_image_classification_item_description_text_view);
        TextView questionTextView = (TextView) findViewById(R.id.rsx_single_image_classification_question_text_view);
//        SubmitBar submitBar = (SubmitBar) findViewById(R.id.rsb_submit_bar);
//        submitBar.setPositiveAction(v -> onNextClicked());


        if(step != null) {
            if (!TextUtils.isEmpty(step.getTitle()) && itemDescriptionTextView!=null) {
                itemDescriptionTextView.setText(step.getTitle());
            }

            if (!TextUtils.isEmpty(step.getText())) {
                questionTextView.setText(step.getText());
            }

            if (!TextUtils.isEmpty(step.getImage())) {
                int resId = getContext().getResources().getIdentifier(step.getImage(), "drawable", getContext().getPackageName());
                if (resId != 0) {
                    imageView.setImageResource(resId);
                }
            }

        }
    }

    @Override
    public void initStepBody()
    {

    }

//    @NonNull
//    private StepBody createStepBody(QuestionStep questionStep, StepResult result)
//    {
//        try
//        {
//            Class cls = questionStep.getStepBodyClass();
//            Constructor constructor = cls.getConstructor(Step.class, StepResult.class);
//            return (StepBody) constructor.newInstance(questionStep, result);
//        }
//        catch(Exception e)
//        {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public Step getStep()
    {
        return this.questionStep;
    }



//    void initialize(Step step, StepResult result);
//
//    View getLayout();
//
//    /**
//     * Method allowing a step layout to consume a back event.
//     *
//     * @return
//     */
//    boolean isBackEventConsumed();
//
//    void setCallbacks(StepCallbacks callbacks);

    @Override
    public boolean isBackEventConsumed()
    {
        callbacks.onSaveStep(StepCallbacks.ACTION_PREV, getStep(), stepBody.getStepResult(false));
        return false;
    }

    @Override
    public void setCallbacks(StepCallbacks callbacks)
    {
        this.callbacks = callbacks;
    }

    @Override
    public int getContentResourceId()
    {
        return R.layout.rsx_single_image_classification_survey_layout;
    }



}
