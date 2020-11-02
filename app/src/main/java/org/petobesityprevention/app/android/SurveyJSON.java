package org.petobesityprevention.app.android;

import org.json.JSONObject;

public class SurveyJSON extends JSONObject {

    // We store the key of the survey to avoid computing it multiple times
    private String key;

    public SurveyJSON(String key) {
        super();
        this.key  = key;
    }

    public String getKey() {
        return key;
    }
}
