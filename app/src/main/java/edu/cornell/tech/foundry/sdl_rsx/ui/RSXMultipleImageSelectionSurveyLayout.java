package edu.cornell.tech.foundry.sdl_rsx.ui;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.researchstack.backbone.result.StepResult;
import org.researchstack.backbone.step.QuestionStep;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.ui.callbacks.StepCallbacks;
import org.researchstack.backbone.ui.step.body.BodyAnswer;
import org.researchstack.backbone.ui.step.body.StepBody;
import org.researchstack.backbone.ui.step.layout.StepLayout;
import org.researchstack.backbone.ui.step.layout.SurveyStepLayout;
import org.researchstack.backbone.ui.views.SubmitBar;
import org.researchstack.backbone.utils.LogExt;
import org.researchstack.backbone.utils.ResUtils;
import org.researchstack.backbone.utils.TextUtils;
import org.researchstack.backboneapp.R;

import java.lang.reflect.Constructor;
import java.util.Set;

import edu.cornell.tech.foundry.sdl_rsx.model.RSXMultipleImageSelectionSurveyOptions;
import edu.cornell.tech.foundry.sdl_rsx.step.RSXMultipleImageSelectionSurveyStep;
import edu.cornell.tech.foundry.sdl_rsx.step.RSXSingleImageClassificationSurveyStep;

/**
 * Created by jk on 6/2/16.
 */
abstract public class RSXMultipleImageSelectionSurveyLayout extends FrameLayout implements StepLayout {


    public static final String TAG = RSXMultipleImageSelectionSurveyLayout.class.getSimpleName();


    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // Data used to initializeLayout and return
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //This is also declared private by superclass, but no getter has been defined
    private QuestionStep questionStep;

    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // Communicate w/ host
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private StepCallbacks callbacks;

    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // Child Views
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//    private LinearLayout container;
    private RSXMultipleImageSelectionSurveyBody stepBody;
    private Button somethingSelectedButton;
    private Button nothingSelectedButton;

    //Getters and Setters
    public Step getStep()
    {
        return this.questionStep;
    }



    public StepBody getStepBody() { return this.stepBody; }

    //Constructors
    public RSXMultipleImageSelectionSurveyLayout(Context context)
    {
        super(context);
    }

    public RSXMultipleImageSelectionSurveyLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public RSXMultipleImageSelectionSurveyLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    //Step Layout Methods
    @Override
    public void initialize(Step step, StepResult result)
    {
        if(! (step instanceof RSXMultipleImageSelectionSurveyStep))
        {
            throw new RuntimeException("Step being used in RSXMultipleImageSelectionSurveyStep is not a RSXMultipleImageSelectionSurveyStep");
        }

        this.questionStep = (QuestionStep) step;
        this.initializeStep((RSXMultipleImageSelectionSurveyStep) step, result);
    }

    @Override
    public View getLayout()
    {
        return this;
    }

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


    //Init Methods
    public void initializeStep(RSXMultipleImageSelectionSurveyStep step, StepResult result)
    {
        this.initStepLayout(step);
        this.initStepBody(step, result);
        this.updateUI();
    }

    public void initStepLayout(RSXMultipleImageSelectionSurveyStep step) {

        RSXMultipleImageSelectionSurveyOptions options = step.getOptions();

        // Init root
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.rsx_multiple_image_selection_survey, this, true);

        TextView questionTextView = (TextView) findViewById(R.id.question_text_view);
        TextView additionalTextview = (TextView) findViewById(R.id.additional_text_view);
//        SubmitBar submitBar = (SubmitBar) findViewById(org.researchstack.backbone.R.id.rsb_submit_bar);
//        submitBar.getPositiveActionView().setVisibility(View.GONE);
//        submitBar.getNegativeActionView().setVisibility(View.GONE);

        this.somethingSelectedButton = (Button) findViewById(R.id.something_selected_button);
        this.somethingSelectedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                somethingSelectedButtonPressed();
            }
        });

        this.somethingSelectedButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        if (options.getSomethingSelectedButtonColor() != 0) {
            this.somethingSelectedButton.setTextColor(options.getSomethingSelectedButtonColor());
        }

        this.nothingSelectedButton = (Button) findViewById(R.id.nothing_selected_button);
        this.nothingSelectedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                nothingSelectedButtonPressed();
            }
        });
        this.nothingSelectedButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        if (options.getNothingSelectedButtonColor() != 0) {
            this.nothingSelectedButton.setTextColor(options.getNothingSelectedButtonColor());
        }


        if (step != null) {

            String additionalText = this.getAdditionalText();
            if (!TextUtils.isEmpty(additionalText) && additionalTextview != null) {
                additionalTextview.setText(additionalText);
            }
            else {
                additionalTextview.setVisibility(View.GONE);
            }


            String questionText = this.getQuestionText();
            if (!TextUtils.isEmpty(questionText) && questionTextView != null) {
                questionTextView.setText(questionText);
            }

        }

    }

    private void updateUI() {

        Set<?> selectedAnswers = ((RSXMultipleImageSelectionSurveyBody)this.getStepBody()).getCurrentSelected();
        if (selectedAnswers != null) {
            this.somethingSelectedButton.setText(this.getSomethingSelectedButtonText());
            this.nothingSelectedButton.setText(this.getNothingSelectedButtonText());

            this.somethingSelectedButton.setVisibility((selectedAnswers.size() > 0) ? View.VISIBLE : View.INVISIBLE);
            this.nothingSelectedButton.setVisibility((selectedAnswers.size() > 0) ? View.INVISIBLE : View.VISIBLE);
        }

        RSXMultipleImageSelectionSurveyBody body = (RSXMultipleImageSelectionSurveyBody)this.getStepBody();
        body.updateUI();

    }

    private void onSelection() {
        if (this.transitionOnSelection()) {
            this.onNextClicked();
        }
        else {
            this.updateUI();
        }
    }

    @NonNull
    private RSXMultipleImageSelectionSurveyBody createStepBody(QuestionStep questionStep, StepResult result)
    {
        try
        {
            Class cls = questionStep.getStepBodyClass();
            Constructor constructor = cls.getConstructor(Step.class, StepResult.class);
            RSXMultipleImageSelectionSurveyLayout self = this;
            return (RSXMultipleImageSelectionSurveyBody) constructor.newInstance(questionStep, result);
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public void initStepBody(RSXMultipleImageSelectionSurveyStep step, StepResult result)
    {

        LogExt.i(getClass(), "initStepBody()");

        LayoutInflater inflater = LayoutInflater.from(getContext());
        RSXMultipleImageSelectionSurveyLayout self = this;
        RSXMultipleImageSelectionSurveyBody surveyBody = createStepBody(questionStep, result);
        surveyBody.setOnSelectionListener( new RSXMultipleImageSelectionSurveyBody.OnSelectionListener() {
            public void onSelection() {
                self.onSelection();
            }
        });

        surveyBody.setupBodyView(inflater, this);
        this.stepBody = surveyBody;



//        View body = stepBody.getBodyView(StepBody.VIEW_TYPE_DEFAULT, inflater, this);

//        if(body != null)
//        {
//            View oldView = container .findViewById(org.researchstack.backbone.R.id.rsx_single_image_classification_button_container_view);
//            int bodyIndex = container.indexOfChild(oldView);
//            container.removeView(oldView);
//            container.addView(body, bodyIndex);
//            body.setId(org.researchstack.backbone.R.id.rsb_survey_step_body);
//        }
    }


    public String getQuestionText() {
        return this.questionStep.getTitle();
    }

    public String getAdditionalText() {
        return "";
    }

    /**
     * Method allowing a step to consume a back event.
     *
     * @return
     */


//    @Override
//    public int getContentResourceId()
//    {
//        return R.layout.rsx_multiple_image_selection_survey;
//    }

    @Override
    public Parcelable onSaveInstanceState()
    {
        callbacks.onSaveStep(StepCallbacks.ACTION_NONE, getStep(), this.getStepBody().getStepResult(false));
        return super.onSaveInstanceState();
    }

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

    public String getString(@StringRes int stringResId)
    {
        return getResources().getString(stringResId);
    }

    abstract protected boolean transitionOnSelection();
    abstract protected String getSomethingSelectedButtonText();
    abstract protected String getNothingSelectedButtonText();
    abstract protected void somethingSelectedButtonPressed();
    abstract protected void nothingSelectedButtonPressed();

    protected int getAdditionalTextViewHeight() {
        return 0;
    }

    protected String getAdditionalTextViewText() {
        return null;
    }
}
