package com.dmegyesi.vcipher;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class VCipherActivityTest {

    @Rule
    public ActivityTestRule<VCipherActivity> mActivityTestRule = new ActivityTestRule<>(VCipherActivity.class);

    @Test
    public void vCipherActivityTestQuickRedFoxEncrypt() {

        vCipherActivityBaseAppTemplate("The quick Red fox.", "LazyDoggy",
                true, "Ehd oxwiq Ppd ema.");
    }

    @Test
    public void vCipherActivityTestQuickRedFoxDecrypt() {

        vCipherActivityBaseAppTemplate("Ehd oxwiq Ppd ema.", "LazyDoggy",
                false, "The quick Red fox.");
    }

    @Test
    public void vCipherActivityTestHalfAlphabetEncrypt() {

        vCipherActivityBaseAppTemplate("abcdefghijkl", "abAB",
                true, "acceeggiikkm");
    }

    @Test
    public void vCipherActivityTestHalfAlphabetDecrypt() {

        vCipherActivityBaseAppTemplate("acceeggiikkm", "abAB",
                false, "abcdefghijkl");
    }

    @Test
    public void vCipherActivityTestFullAlphabetEncrypt() {

        vCipherActivityBaseAppTemplate("abcdefghijklmnopqrstuvwxyz", "lemon",
                true, "lforrqktwwvpybbaudggfzillk");
    }

    @Test
    public void vCipherActivityTestFullAlphabetDecrypt() {

        vCipherActivityBaseAppTemplate("lforrqktwwvpybbaudggfzillk", "lemon",
                false, "abcdefghijklmnopqrstuvwxyz");
    }

    @Test
    public void vCipherActivityTestCryptographyEncrypt() {

        vCipherActivityBaseAppTemplate("CryptoStands4Cryptography", "abCD",
                true, "CsastpUwaofv4Csastpiuaqjb");
    }

    @Test
    public void vCipherActivityTestCryptographyDecrypt() {

        vCipherActivityBaseAppTemplate("CsastpUwaofv4Csastpiuaqjb", "abCD",
                false, "CryptoStands4Cryptography");
    }

    @Test
    public void vCipherActivityTestJaberwockyEncrypt() {

        vCipherActivityBaseAppTemplate("Beware the Jabberwock, my son!", "VIGENERECIPHER",
                true, "Wmceei klg Rpifvmeugx, qp wqv!");
    }

    @Test
    public void vCipherActivityTestJaberwockyDecrypt() {

        vCipherActivityBaseAppTemplate("Wmceei klg Rpifvmeugx, qp wqv!", "VIGENERECIPHER",
                false, "Beware the Jabberwock, my son!");
    }

    /**
     * Private method that performs the actual ViewInteraction and validation.
     *
     * @param textPhrase Text that is to be decrypted.
     * @param keyPhrase  Alphabetical keyphrase that is used to encrypt/decrypt the text.
     * @param encryption  Boolean that determines if text is to be encrypted (True) or decrypted (False)
     * @param resultMessage  Final result (answer) of the operation. Uneditable field
     */
    private void vCipherActivityBaseAppTemplate(String textPhrase, String keyPhrase,
                                                boolean encryption, String resultMessage) {

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.text), isDisplayed()));
        appCompatEditText.perform(replaceText(textPhrase), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.keyphrase), isDisplayed()));
        appCompatEditText2.perform(replaceText(keyPhrase), closeSoftKeyboard());

        if (encryption) {
            ViewInteraction appCompatRadioButton = onView(
                    allOf(withId(R.id.encrypt), withText("Encrypt"), isDisplayed()));
            appCompatRadioButton.perform(click());
        } else {
            ViewInteraction appCompatRadioButton = onView(
                    allOf(withId(R.id.decrypt), withText("Decrypt"), isDisplayed()));
            appCompatRadioButton.perform(click());
        }

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.runButton), withText("Run"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.answer), withText(resultMessage), isDisplayed()));
        editText.check(matches(withText(resultMessage)));
    }
}
