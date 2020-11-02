package org.petobesityprevention.app.android;

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
        assertEquals(KeyFactory.makeUserKey("ORG", "ID", "ANDROID"), "USER_ORG_ID_ANDROID");
    }
    @Test
    public void _isCorrect() {
        assertEquals(true, true);
    }

    @Test
    public void password_isDifferent() { assertNotEquals("password", KeyFactory.hashPassword("password")); }

    @Test
    public void surveyKey_isHex() {
        String key = KeyFactory.makeSurveyKey("Fido", "ORG", "ID", LocalDateTime.now().toString());

        String pattern = "[0123456789abcdef]{40}";

        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(key);

        assertTrue(matcher.find());
    }
}