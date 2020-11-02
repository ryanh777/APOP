package org.petobesityprevention.app.android;

import org.junit.Test;

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
}