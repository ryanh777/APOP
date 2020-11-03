package org.petobesityprevention.app.android;

import org.json.JSONException;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void userKey_isCorrect() {
        assertEquals(KeyFactory.makeUserID("ORG", "ID", "ANDROID"), "USER_ORG_ID_ANDROID");
    }
    @Test
    public void _isCorrect() {
        assertEquals(true, true);
    }

    @Test
    public void surveyJSON_isCorrect() {
        SurveyJSON sJSON = JSONFactory.makeSurveyJSON("", "", "", "",
                0, 0, "", "", 0, 0, 0, 0, "", "");

        String survey_id = "";
        try {
            survey_id = (String) sJSON.get("survey_id");
        } catch (JSONException e) {
            assertTrue(false);
        }
        assertEquals(survey_id, sJSON.getID());
    }

    @Test
    public void surveyID_isHex() {
        String id = KeyFactory.makeSurveyID("Fido", "ORG", "ID", LocalDateTime.now().toString());

        String pattern = "[0123456789abcdef]{40}";

        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(id);

        assertTrue(matcher.find());
    }

    @Test
    public void password_isDifferent() { assertNotEquals("password", KeyFactory.hashPassword("password")); }
}