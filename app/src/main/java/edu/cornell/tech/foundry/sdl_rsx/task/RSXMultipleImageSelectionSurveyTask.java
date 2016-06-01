package edu.cornell.tech.foundry.sdl_rsx.task;

import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.task.OrderedTask;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jk on 6/1/16.
 */
public class RSXMultipleImageSelectionSurveyTask extends OrderedTask {

    public RSXMultipleImageSelectionSurveyTask(String identifier, List<Step> steps) {
        super(identifier, steps);
    }

    public RSXMultipleImageSelectionSurveyTask(String identifier, Step... steps)
    {
        this(identifier, Arrays.asList(steps));
    }
}
