package edu.cornell.tech.foundry.sdl_rsx.ui;

import edu.cornell.tech.foundry.sdl_rsx.step.RSXSingleImageClassificationSurveyStep;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.researchstack.backbone.ui.step.body.BodyAnswer;
import org.researchstack.backbone.utils.LogExt;
import org.researchstack.backbone.utils.TextUtils;
import org.researchstack.backboneapp.R;

import org.researchstack.backbone.result.StepResult;
import org.researchstack.backbone.step.QuestionStep;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.ui.callbacks.StepCallbacks;
import org.researchstack.backbone.ui.step.body.StepBody;
import org.researchstack.backbone.ui.step.layout.SurveyStepLayout;
import org.researchstack.backbone.ui.views.SubmitBar;

import java.lang.reflect.Constructor;

/**
 * Created by jk on 5/26/16.
 */
public class RSXSingleImageClassificationSurveyLayout extends SurveyStepLayout {


    public static final String TAG = RSXSingleImageClassificationSurveyLayout.class.getSimpleName();


    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // Data used to initializeLayout and return
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //This is also declared private by superclass, but no getter has been defined
    //probably shoudl remove this
//    private StepResult   stepResult;
    //need to keep this
    private QuestionStep questionStep;

    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // Communicate w/ host
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private StepCallbacks callbacks;

    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // Child Views
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private LinearLayout container;
    private StepBody     stepBody;


    //Getters and Setters

    @Override
    public Step getStep()
    {
        return this.questionStep;
    }

    public StepBody getStepBody() { return this.stepBody; }

    //Constructors
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

    //Init Methods

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
        this.initStepBody(step, result);
    }

//    @Override
    public void initStepLayout(RSXSingleImageClassificationSurveyStep step) {
        this.container = (LinearLayout) findViewById(R.id.rsx_single_image_classification_survey_content_container);
        ImageView imageView = (ImageView) findViewById(R.id.rsx_single_image_classification_survey_image_view);
        TextView itemDescriptionTextView = (TextView) findViewById(R.id.rsx_single_image_classification_item_description_text_view);
        TextView questionTextView = (TextView) findViewById(R.id.rsx_single_image_classification_question_text_view);
        SubmitBar submitBar = (SubmitBar) findViewById(org.researchstack.backbone.R.id.rsb_submit_bar);
//        submitBar.setVisibility(View.GONE);
//        submitBar.setPositiveAction(v -> onNextClicked());
        submitBar.getPositiveActionView().setVisibility(View.GONE);


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

            submitBar.setNegativeTitle(org.researchstack.backbone.R.string.rsb_step_skip);
            submitBar.setNegativeAction(v -> onSkipClicked());

        }
    }

    public void initStepBody(RSXSingleImageClassificationSurveyStep step, StepResult result)
    {
        LogExt.i(getClass(), "initStepBody()");

        LayoutInflater inflater = LayoutInflater.from(getContext());

        RSXSingleImageClassificationSurveyBody surveyBody = new RSXSingleImageClassificationSurveyBody<>(step, result);
        surveyBody.setOnSelectionListener( new RSXSingleImageClassificationSurveyBody.OnSelectionListener() {
            public void onSelection(RSXSingleImageClassificationSurveyBody body) {
                onNextClicked();
            }
        });
        surveyBody.setupBodyView(inflater, container);
        this.stepBody = surveyBody;

//        if(body != null)
//        {
//            View oldView = container.findViewById(R.id.rsx_single_image_classification_button_container_view);
//            int bodyIndex = container.indexOfChild(oldView);
//            container.removeView(oldView);
//            container.addView(body, bodyIndex);
//            body.setId(R.id.rsx_single_image_classification_button_container_view);
//        }

    }

    @NonNull
    private StepBody createStepBody(QuestionStep questionStep, StepResult result)
    {
        try
        {
            Class cls = questionStep.getStepBodyClass();
            Constructor constructor = cls.getConstructor(Step.class, StepResult.class);
            return (StepBody) constructor.newInstance(questionStep, result);
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private void setupBody(int viewType, LayoutInflater inflater, ViewGroup parent) {

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




    /**
     * Method allowing a step to consume a back event.
     *
     * @return
     */
    @Override
    public boolean isBackEventConsumed()
    {
        callbacks.onSaveStep(StepCallbacks.ACTION_PREV, this.getStep(), this.getStepBody().getStepResult(false));
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




    @Override
    public Parcelable onSaveInstanceState()
    {
        callbacks.onSaveStep(StepCallbacks.ACTION_NONE, getStep(), this.getStepBody().getStepResult(false));
        return super.onSaveInstanceState();
    }

    @Override
    protected void onNextClicked()
    {
        BodyAnswer bodyAnswer = this.getStepBody().getBodyAnswerState();

        if(bodyAnswer == null || ! bodyAnswer.isValid())
        {
            Toast.makeText(getContext(),
                    bodyAnswer == null
                            ? BodyAnswer.INVALID.getString(getContext())
                            : bodyAnswer.getString(getContext()),
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            callbacks.onSaveStep(StepCallbacks.ACTION_NEXT,
                    this.getStep(),
                    this.getStepBody().getStepResult(false));
        }
    }

    @Override
    public void onSkipClicked()
    {
        if(callbacks != null)
        {
            // empty step result when skipped
            callbacks.onSaveStep(StepCallbacks.ACTION_NEXT,
                    this.getStep(),
                    this.getStepBody().getStepResult(true));
        }
    }

    @Override
    public String getString(@StringRes int stringResId)
    {
        return getResources().getString(stringResId);
    }





}
