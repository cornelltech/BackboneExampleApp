package edu.cornell.tech.foundry.sdl_rsx.choice;

import org.researchstack.backbone.model.Choice;
import android.graphics.Color;
/**
 * Created by jk on 5/31/16.
 */
public class RSXTextChoiceWithColor <T> extends Choice {

    private Color color;

    /**
     * Creates a choice object with the provided text and value, color is null
     *
     * @param text  user-facing text representing the choice
     * @param value value of any type for this choice, type should match other choices in the step
     */
    public RSXTextChoiceWithColor(String text, T value)
    {
        this(text, value, null);
    }

    /**
     * Creates a choice object with the provided text, value, and color
     *
     * @param text       user-facing text representing the choice
     * @param value      value of any type for this choice, type should match other choices in the
     *                   step
     * @param color      color to display the choice
     */
    public RSXTextChoiceWithColor(String text, T value, Color color)
    {
        super(text, value);
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
