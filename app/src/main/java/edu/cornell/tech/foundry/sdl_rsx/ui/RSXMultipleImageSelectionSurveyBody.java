package edu.cornell.tech.foundry.sdl_rsx.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import org.researchstack.backbone.answerformat.ChoiceAnswerFormat;
import org.researchstack.backbone.model.Choice;
import org.researchstack.backbone.result.StepResult;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.ui.step.body.BodyAnswer;
import org.researchstack.backbone.ui.step.body.StepBody;
import org.researchstack.backboneapp.R;

import java.util.Set;

import edu.cornell.tech.foundry.sdl_rsx.answerformat.RSXImageChoiceAnswerFormat;
import edu.cornell.tech.foundry.sdl_rsx.model.RSXMultipleImageSelectionSurveyOptions;
import edu.cornell.tech.foundry.sdl_rsx.step.RSXMultipleImageSelectionSurveyStep;

/**
 * Created by jk on 6/10/16.
 *
 */
abstract public class RSXMultipleImageSelectionSurveyBody <T> implements StepBody {

    public interface OnSelectionListener <T> {

        public void onSelection();

    }

    private RSXMultipleImageSelectionSurveyStep step;
    private StepResult<T[]> result;
    private ChoiceAnswerFormat format;
    private Choice<T>[]        choices;
//    private Set<T> currentSelected;
    private RSXMultipleImageSelectionSurveyAdapter collectionAdapter;
    private OnSelectionListener <T> onSelectionListener;
    private GridView imagesGridView;

    public RSXMultipleImageSelectionSurveyBody(Step step, StepResult result, OnSelectionListener onSelectionListener) {
        this.step = (RSXMultipleImageSelectionSurveyStep) step;
        this.result = result == null ? new StepResult<>(step) : result;
        this.format = (RSXImageChoiceAnswerFormat) this.step.getAnswerFormat();
        this.choices = format.getChoices();
        this.onSelectionListener = onSelectionListener;

        this.collectionAdapter = new RSXMultipleImageSelectionSurveyAdapter(
                this.step,
                this.result,
                this.supportsMultipleSelection());

    }

    public RSXMultipleImageSelectionSurveyBody(Step step, StepResult result) {
        this(step, result, null);
    }

    public Set<T> getCurrentSelected() {
        return this.collectionAdapter.getCurrentSelected();
    }

    public void setOnSelectionListener(OnSelectionListener <T> onSelectionListener){
        this.onSelectionListener = onSelectionListener;
        if (this.imagesGridView != null && this.collectionAdapter != null) {
            this.imagesGridView.setOnItemClickListener(this.collectionAdapter.getOnItemClickListener(onSelectionListener));
        }
    }

    public void setupBodyView(LayoutInflater inflater, ViewGroup parent) {
        this.initViewDefault(inflater, parent);
    }

    private View initViewDefault(LayoutInflater inflater, ViewGroup parent)
    {
        RSXMultipleImageSelectionSurveyOptions options = this.step.getOptions();

        this.imagesGridView = (GridView) parent.findViewById(R.id.images_grid_view);

        if (options.getItemCollectionViewBackgroundColor() != 0) {
            this.imagesGridView.setBackgroundColor(options.getItemCollectionViewBackgroundColor());
        }

        this.collectionAdapter.setInflater(inflater);

        this.imagesGridView.setAdapter(this.collectionAdapter);
        //set on click listener
        this.imagesGridView.setOnItemClickListener(this.collectionAdapter.getOnItemClickListener(this.onSelectionListener));
        this.collectionAdapter.notifyDataSetChanged();


//        private int itemCellSelectedColor;
//        private String itemCellSelectedOverlayImage;
//        private int itemCellTextBackgroundColor;
//        private int itemsPerRow;
//        private int itemMinSpacing;



//        for(int i=0; i<choices.length; i++) {
//            Choice choice = choices[i];
//            Button button = new AppCompatButton(parent.getContext());
//            button.setText(choice.getText());
//            button.setId(i);
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                    0,
//                    LinearLayout.LayoutParams.WRAP_CONTENT,
//                    1.0f);
//
//            button.setLayoutParams(layoutParams);
//
//
//
//            button.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    // Perform action on click
//                    int i = v.getId();
//                    Choice<T> choice = choices[i];
//                    currentSelected = choice.getValue();
//                    onSelection();
//                }
//            });
//
//            buttonLayout.addView(button, i);
//        }



        return this.imagesGridView;
    }

    public void updateUI() {

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

    @Override
    public StepResult getStepResult(boolean skipped)
    {
        if(skipped)
        {
            this.collectionAdapter.clearCurrentSelected();
            result.setResult((T[]) this.collectionAdapter.getCurrentSelected().toArray());
        }
        else
        {
            result.setResult((T[]) this.collectionAdapter.getCurrentSelected().toArray());
        }
        return result;
    }

    @Override
    public BodyAnswer getBodyAnswerState()
    {
        if(this.collectionAdapter.getCurrentSelected().isEmpty())
        {
            return new BodyAnswer(false, org.researchstack.backbone.R.string.rsb_invalid_answer_choice);
        }
        else
        {
            return BodyAnswer.VALID;
        }
    }

    abstract protected boolean supportsMultipleSelection();

}
