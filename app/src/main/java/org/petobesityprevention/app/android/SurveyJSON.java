package org.petobesityprevention.app.android;

import org.json.JSONObject;

public class SurveyJSON extends JSONObject {

    // We store the id key of the survey to avoid computing it multiple times
    private String id;

    public SurveyJSON(String id) {
        super();
        this.id  = id;
    }

    public String getID() {
        return id;
    }
}
