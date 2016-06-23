package edu.cornell.tech.foundry.sdl_rsx.ui;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.researchstack.backbone.result.StepResult;

import edu.cornell.tech.foundry.sdl_rsx.choice.RSXImageChoice;
import edu.cornell.tech.foundry.sdl_rsx.step.RSXMultipleImageSelectionSurveyStep;

/**
 * Created by jk on 6/23/16.
 */
public class MEDLAdapter <T> extends RSXMultipleImageSelectionSurveyAdapter {

    public MEDLAdapter(
            RSXMultipleImageSelectionSurveyStep step,
            StepResult<T[]> result) {
        super(step, result);

    }

    @Override
    protected LinearLayout configureCellForImageChoice(LinearLayout missCell, RSXImageChoice imageChoice, ViewGroup parent) {

        LinearLayout cell = super.configureCellForImageChoice(missCell, imageChoice, parent);

        return cell;
    }
}
