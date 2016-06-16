package edu.cornell.tech.foundry.sdl_rsx.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.cornell.tech.foundry.sdl_rsx.choice.PAMImageChoice;
import edu.cornell.tech.foundry.sdl_rsx.choice.RSXImageChoice;

/**
 * Created by jk on 6/1/16.
 */
public class RSXAffectItem extends RSXImageItem {


    private List<String> imageTitles;
    private JSONObject value;

    public RSXAffectItem(JSONObject json) {
        super(json);

        try {
            this.imageTitles = new ArrayList<String>();

            JSONArray imageTitlesJSON= json.getJSONArray("activities");

            for (int i = 0; i < imageTitlesJSON.length(); i++) {
                String imageTitle = imageTitlesJSON.getString(i);
                this.imageTitles.add(imageTitle);
            }

            this.value = json.getJSONObject("value");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public RSXImageChoice getImageChoice() {
        return new PAMImageChoice<>(this.imageTitles, this.value);
    }


}