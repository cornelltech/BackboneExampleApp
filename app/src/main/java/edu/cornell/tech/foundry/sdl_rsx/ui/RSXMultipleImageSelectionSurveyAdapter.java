package edu.cornell.tech.foundry.sdl_rsx.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.researchstack.backbone.result.StepResult;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.utils.ResUtils;
import org.researchstack.backbone.utils.TextUtils;
import org.researchstack.backboneapp.R;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import edu.cornell.tech.foundry.sdl_rsx.answerformat.RSXImageChoiceAnswerFormat;
import edu.cornell.tech.foundry.sdl_rsx.choice.RSXImageChoice;
import edu.cornell.tech.foundry.sdl_rsx.step.RSXMultipleImageSelectionSurveyStep;

/**
 * Created by jk on 6/15/16.
 */
public class RSXMultipleImageSelectionSurveyAdapter <T> extends BaseAdapter {

    private Set<T> currentSelected;
    private RSXImageChoice[] imageChoices;
    private RSXMultipleImageSelectionSurveyStep step;
    private boolean supportsMultipleSelection;
    private LayoutInflater inflater;
    private int cellWidth;

    public RSXMultipleImageSelectionSurveyAdapter(
            RSXMultipleImageSelectionSurveyStep step,
            StepResult<T[]> result,
            boolean supportsMultipleSelection) {
        super();
        this.step = step;

        this.supportsMultipleSelection = supportsMultipleSelection;
        // Restore results
        currentSelected = new HashSet<>();

        T[] resultArray = result.getResult();
        if(resultArray != null && resultArray.length > 0)
        {
            currentSelected.addAll(Arrays.asList(resultArray));
        }

        RSXImageChoiceAnswerFormat answerFormat = (RSXImageChoiceAnswerFormat)this.step.getAnswerFormat();

        this.imageChoices = answerFormat.getImageChoices();
    }

    @Override
    public int getCount() {
        int length = this.imageChoices.length;
        return length;
    }

    @Override
    public Object getItem(int position) {
        return this.imageChoices[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected LinearLayout configureCellForImageChoice(LinearLayout missCell, RSXImageChoice<T> imageChoice, ViewGroup parent) {

        ImageView itemImageView = (ImageView) missCell.findViewById(R.id.item_image_view);
        ImageView checkImageView = (ImageView) missCell.findViewById(R.id.check_image_view);
        TextView primaryTextView = (TextView) missCell.findViewById(R.id.primary_text_label);
        TextView secondaryTextView = (TextView) missCell.findViewById(R.id.secondary_text_label);

        int itemResId = ResUtils.getDrawableResourceId(missCell.getContext(), imageChoice.getNormalStateImage());
        if (itemResId != 0) {
            itemImageView.setImageResource(itemResId);
        }
        if (this.getSelectedForValue((T) imageChoice.getValue())) {

            if(this.step.getOptions().getItemCellSelectedColor() != 0) {
                missCell.setBackgroundColor(this.step.getOptions().getItemCellSelectedColor());
            }

            if (!TextUtils.isEmpty( this.step.getOptions().getItemCellSelectedOverlayImageTitle() )) {
                int checkResId = ResUtils.getDrawableResourceId(missCell.getContext(), this.step.getOptions().getItemCellSelectedOverlayImageTitle());

                checkImageView.setVisibility(View.VISIBLE);
                if (checkResId != 0) {
                    checkImageView.setImageResource(checkResId);
                }
            }
        }
        else {
            missCell.setBackgroundColor(missCell.getResources().getColor(android.R.color.transparent));
            checkImageView.setVisibility(View.INVISIBLE);
        }

        primaryTextView.setVisibility(View.GONE);
        secondaryTextView.setVisibility(View.GONE);

        return missCell;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout cell;
        if (convertView == null) {
            assert(this.inflater != null);
            if (this.inflater == null) {
                return null;
            }

            cell = (LinearLayout) this.inflater.inflate(
                    R.layout.rsx_multiple_image_selection_survey_cell,
                    parent,
                    false
            );

        }
        else {
            cell = (LinearLayout) convertView;
        }

        return this.configureCellForImageChoice(cell, this.imageChoices[position], parent);
    }

    public Set<T> getCurrentSelected() {
        return this.currentSelected;
    }

    public void clearCurrentSelected() {
        this.currentSelected.clear();
    }

    //click listener
    public AdapterView.OnItemClickListener getOnItemClickListener(RSXMultipleImageSelectionSurveyLayout.OnSelectionListener listener) {

        return new AdapterView.OnItemClickListener() {
            public void onItemClick(
                    AdapterView<?> parent,
                    View v,
                    int position,
                    long id) {

                RSXImageChoice imageChoice = (RSXImageChoice) parent.getItemAtPosition(position);

                RSXMultipleImageSelectionSurveyAdapter adapter = (RSXMultipleImageSelectionSurveyAdapter) parent.getAdapter();
                if (adapter.supportsMultipleSelection) {
                    adapter.setSelectedForValue(imageChoice.getValue(), !adapter.getSelectedForValue(imageChoice.getValue()));
                }
                else {
                    //multiple selections not allowed
                    boolean currentSelectionSetting = adapter.getSelectedForValue(imageChoice.getValue());
                    adapter.clearCurrentSelected();
                    adapter.setSelectedForValue(imageChoice.getValue(), !currentSelectionSetting);
                }

                if (listener != null) {
                    listener.onSelection();
                }

                adapter.notifyDataSetChanged();

            }
        };
    }

    private void setSelectedForValue(T value, boolean selected) {
        //add or remove from hash based on selected
        if (selected) {
            this.currentSelected.add(value);
        }
        else {
            this.currentSelected.remove(value);
        }
    }

    private boolean getSelectedForValue(T value) {
        return this.currentSelected.contains(value);
    }

    public void setInflater(LayoutInflater i) {
        this.inflater = i;
    }

}
