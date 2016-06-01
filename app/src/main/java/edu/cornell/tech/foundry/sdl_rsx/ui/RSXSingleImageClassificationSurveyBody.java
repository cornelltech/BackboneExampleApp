package edu.cornell.tech.foundry.sdl_rsx.ui;

import edu.cornell.tech.foundry.sdl_rsx.step.RSXSingleImageClassificationSurveyStep;

import org.researchstack.backboneapp.R;

import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.researchstack.backbone.answerformat.ChoiceAnswerFormat;
import org.researchstack.backbone.model.Choice;
import org.researchstack.backbone.step.QuestionStep;
import org.researchstack.backbone.ui.step.body.SingleChoiceQuestionBody;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.result.StepResult;
import org.researchstack.backbone.ui.step.body.StepBody;
import org.researchstack.backbone.ui.step.body.BodyAnswer;

/**
 * Created by jk on 5/26/16.
 */

public class RSXSingleImageClassificationSurveyBody <T> implements StepBody {


    public interface OnSelectionListener <T> {

        public void onSelection(RSXSingleImageClassificationSurveyBody<T> body);

    }
    private RSXSingleImageClassificationSurveyStep step;
    private StepResult<T>      result;
    private ChoiceAnswerFormat format;
    private Choice<T>[]        choices;
    private T                  currentSelected;
    private OnSelectionListener <T> onSelectionListener;


    public RSXSingleImageClassificationSurveyBody(Step step, StepResult result, OnSelectionListener onSelectionListener) {
        this.step = (RSXSingleImageClassificationSurveyStep) step;
        this.result = result == null ? new StepResult<>(step) : result;
        this.format = (ChoiceAnswerFormat) this.step.getAnswerFormat();
        this.choices = format.getChoices();
        this.onSelectionListener = onSelectionListener;

        // Restore results
        T resultValue = this.result.getResult();
        if (resultValue != null) {
            for (Choice<T> choice : choices) {
                if (choice.getValue().equals(resultValue)) {
                    currentSelected = choice.getValue();
                }
            }
        }
    }

    public RSXSingleImageClassificationSurveyBody(Step step, StepResult result) {
        this(step, result, null);
    }

    public void setupBodyView(LayoutInflater inflater, ViewGroup parent) {
        this.initViewDefault(inflater, parent);
    }


    public View getBodyView(int viewType, LayoutInflater inflater, ViewGroup parent)
    {
        View view = getViewForType(viewType, inflater, parent);

//        Resources res = parent.getResources();
//        LinearLayout.MarginLayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
////        layoutParams.leftMargin = res.getDimensionPixelSize(org.researchstack.backbone.R.dimen.rsb_margin_left);
////        layoutParams.rightMargin = res.getDimensionPixelSize(org.researchstack.backbone.R.dimen.rsb_margin_right);
//        view.setLayoutParams(layoutParams);

        return view;
    }

    private View getViewForType(int viewType, LayoutInflater inflater, ViewGroup parent)
    {
        if(viewType == VIEW_TYPE_DEFAULT)
        {
            return initViewDefault(inflater, parent);
        }
        else if(viewType == VIEW_TYPE_COMPACT)
        {
            throw new IllegalArgumentException("Not Implemented");
        }
        else
        {
            throw new IllegalArgumentException("Invalid View Type");
        }
    }

    public void setOnSelectionListener(OnSelectionListener <T> onSelectionListener){
        this.onSelectionListener = onSelectionListener;
    }

    private void onSelection() {
        if (onSelectionListener != null) {
            onSelectionListener.onSelection(this);
        }
    }

    private View initViewDefault(LayoutInflater inflater, ViewGroup parent)
    {

//        LinearLayout buttonLayout = new LinearLayout(parent.getContext());
        LinearLayout buttonLayout = (LinearLayout) parent.findViewById(R.id.rsx_single_image_classification_button_container_view);
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);

        //remove all previous buttons
        buttonLayout.removeAllViews();

        for(int i=0; i<choices.length; i++) {
            Choice choice = choices[i];
            Button button = new AppCompatButton(parent.getContext());
            button.setText(choice.getText());
            button.setId(i);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f);

            button.setLayoutParams(layoutParams);



            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                    int i = v.getId();
                    Choice<T> choice = choices[i];
                    currentSelected = choice.getValue();
                    onSelection();
                }
            });

            buttonLayout.addView(button, i);
        }
        return buttonLayout;
    }

    public StepResult getStepResult(boolean skipped)
    {
        if(skipped)
        {
            result.setResult(null);
        }
        else
        {
            result.setResult(currentSelected);
        }

        return result;
    }

    public BodyAnswer getBodyAnswerState()
    {
        if (currentSelected == null)
        {
            return new BodyAnswer(false, org.researchstack.backbone.R.string.rsb_invalid_answer_choice);
        }
        else
        {
            return BodyAnswer.VALID;
        }
    }




}
