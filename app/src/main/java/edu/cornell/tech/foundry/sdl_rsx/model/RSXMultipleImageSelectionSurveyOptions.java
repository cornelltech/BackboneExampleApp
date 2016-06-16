package edu.cornell.tech.foundry.sdl_rsx.model;

import android.graphics.Color;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by jk on 6/1/16.
 */
public class RSXMultipleImageSelectionSurveyOptions implements Serializable {

    private int somethingSelectedButtonColor;
    private int nothingSelectedButtonColor;
    private int itemCellSelectedColor;
    private String itemCellSelectedOverlayImageTitle;
    private int itemCellTextBackgroundColor;
    private int itemCollectionViewBackgroundColor;
    private int itemsPerRow;
    private int itemMinSpacing;


    public RSXMultipleImageSelectionSurveyOptions(JSONObject json) {
        try {
            if (json.has("somethingSelectedButtonColor")) {
                this.somethingSelectedButtonColor = Color.parseColor(json.getString("somethingSelectedButtonColor"));
            }
            if (json.has("nothingSelectedButtonColor")) {
                this.nothingSelectedButtonColor = Color.parseColor(json.getString("nothingSelectedButtonColor"));
            }
            if (json.has("itemCellSelectedColor")) {
                this.itemCellSelectedColor = Color.parseColor(json.getString("itemCellSelectedColor"));
            }
            if (json.has("itemCellSelectedOverlayImageTitle")) {
                this.itemCellSelectedOverlayImageTitle = json.getString("itemCellSelectedOverlayImageTitle");
            }
            if (json.has("itemCellTextBackgroundColor")) {
                this.itemCellTextBackgroundColor = Color.parseColor(json.getString("itemCellTextBackgroundColor"));
            }
            if (json.has("itemCollectionViewBackgroundColor")) {
                this.itemCollectionViewBackgroundColor = Color.parseColor(json.getString("itemCollectionViewBackgroundColor"));
            }
            if (json.has("itemsPerRow")) {
                this.itemsPerRow = json.getInt("itemsPerRow");
            }
            if (json.has("itemMinSpacing")) {
                this.itemMinSpacing = json.getInt("itemMinSpacing");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getSomethingSelectedButtonColor() {
        return this.somethingSelectedButtonColor;
    }

    public int getNothingSelectedButtonColor() {
        return this.nothingSelectedButtonColor;
    }

    public int getItemCellSelectedColor() {
        return this.itemCellSelectedColor;
    }

    public String getItemCellSelectedOverlayImageTitle() {
        return this.itemCellSelectedOverlayImageTitle;
    }

    public int getItemCellTextBackgroundColor() {
        return this.itemCellTextBackgroundColor;
    }

    public int getItemCollectionViewBackgroundColor() {
        return this.itemCollectionViewBackgroundColor;
    }

    public int getItemsPerRow() {
        return this.itemsPerRow;
    }

    public int getItemMinSpacing() {
        return this.itemMinSpacing;
    }


}
