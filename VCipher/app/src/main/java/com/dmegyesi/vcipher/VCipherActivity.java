package com.dmegyesi.vcipher;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class VCipherActivity extends AppCompatActivity {

    private EditText text;
    private EditText keyphrase;
    private RadioButton encrypt;
    private RadioButton decrypt;
    private Button runButton;
    private EditText answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.dmegyesi.vcipher.R.layout.activity_vcipher);

        text = findViewById(com.dmegyesi.vcipher.R.id.text);
        keyphrase = findViewById(com.dmegyesi.vcipher.R.id.keyphrase);
        encrypt = findViewById(com.dmegyesi.vcipher.R.id.encrypt);
        decrypt = findViewById(com.dmegyesi.vcipher.R.id.decrypt);
        runButton = findViewById(com.dmegyesi.vcipher.R.id.runButton);
        answer = findViewById(com.dmegyesi.vcipher.R.id.answer);
    } // end of onCreate method.

    /**
     *
     * 'OnClick' method. Called when a view has been clicked on the SDP Vigenère cipher design.
     *
     * Implementation of the SDP Vigenère cipher, that shifts each letter in the original text by a number
     * of places that depends in the corresponding letter in teh keyphrase (0 for A, 1 for B, and so on), where the
     * keyphrase is repeated enough times to match the length of the (alphabetical) text.
     *
     * To illustrate with an example, we show inputs, numeric shift applied, and output for a case in which the
     * unencrypted yext is “CryptoStands4Cryptography” and the Key Phrase is “abCD”:
     *
     * Text:                  CryptoStands4Cryptography
     * Key Phrase (repeated): abCDabCDabCD abCDabCDabCD
     * Numeric shift:         012301230123 012301230123
     * Output:                CsastpUwaofv4Csastpiuaqjb
     *
     * Decoding works in an analogous way.
     *
     * Reference: http://www.sanfoundry.com/java-program-implement-vigenere-cypher/
     *
     * @param view  Name of the method in this View's context to invoke when the view is clicked.
     * @throws NullPointerException        If the current text is null or empty.
     * @throws IllegalArgumentException    If the current keyphrase contains a non-alphabetical character.
     */
    public void encryptOrDecryptUsingKeyphraseOnClick(View view) {

        // Local variables.
        String shiftedString;

        if (view.getId() == R.id.runButton) {

            // Close the soft keyboard when the user hits the run button.
            // Reference: https://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard/34972848
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            shiftedString = null;
            String textFromCipher = this.text.getText().toString();
            String keyphraseFromCipher = this.keyphrase.getText().toString();

            if (!checkForEmptyInvalidInput(textFromCipher, keyphraseFromCipher)) {
                // Input parameters are all correct.
                if (encrypt.isChecked()) {
                    // Encrypt option.
                    shiftedString = this.encryptAlgorithm(textFromCipher, keyphraseFromCipher);
                }
                if (decrypt.isChecked()) {
                    // Decrypt option.
                    shiftedString = this.decryptAlgorithm(textFromCipher, keyphraseFromCipher);
                }
                this.answer.setText(shiftedString.toString());
            }
        }
    } // end of encryptOrDecryptUsingKeyphrase method.

    /**
     * Private method that performs the actual encryption for the Vigenère cipher.
     *
     * @param text Text that is to be encrypted.
     * @param keyphrase  Alphabetical keyphrase that is used to encrypt/decrypt the text.
     * @return Modified (now encrypted) string.
     */
    private String encryptAlgorithm(String text, String keyphrase) {

        keyphrase = keyphrase.toUpperCase();
        StringBuilder sb = new StringBuilder(100);

        for (int i = 0, j = 0; i < text.length(); i++) {

            char upper = text.toUpperCase().charAt(i);
            char orig = text.charAt(i);

            if (Character.isAlphabetic(orig)) {
                if (Character.isUpperCase(orig)) {
                    sb.append((char)((upper + keyphrase.charAt(j) - 130) % 26 + 65));
                    ++j;
                    j %= keyphrase.length();
                } else {
                    sb.append(Character.toLowerCase((char)((upper + keyphrase.charAt(j) - 130) % 26 + 65)));
                    ++j;
                    j %= keyphrase.length();
                }
            } else {
                sb.append(orig);
            }
        }
        return sb.toString();
    } // end of encryptAlgorithm method.

    /**
     * Private method that performs the actual decryption for the Vigenère cipher.
     *
     * @param text Text that is to be decrypted.
     * @param keyphrase  Alphabetical keyphrase that is used to encrypt/decrypt the text.
     * @return Modified (now decrypted) string.
     */
    private String decryptAlgorithm(String text, String keyphrase) {

        keyphrase = keyphrase.toUpperCase();
        StringBuilder sb = new StringBuilder(100);

        for (int i = 0, j = 0; i < text.length(); i++) {

            char upper = text.toUpperCase().charAt(i);
            char orig = text.charAt(i);

            if (Character.isAlphabetic(orig)) {
                if (Character.isUpperCase(orig)) {
                    sb.append((char)((upper - keyphrase.charAt(j) + 26) % 26 + 65));
                    ++j;
                    j %= keyphrase.length();
                } else {
                    sb.append(Character.toLowerCase((char)((upper - keyphrase.charAt(j) + 26) % 26 + 65)));
                    ++j;
                    j %= keyphrase.length();
                }
            } else {
                sb.append(orig);
            }
        }
        return sb.toString();
    } // end of decryptAlgorithm method.

    /**
     * Private method that checks the input parameters for validity.
     *
     * @param text Text that is to be decrypted.
     * @param keyphrase  Alphabetical keyphrase that is used to encrypt/decrypt the text.
     * @return Boolean value that indicates if the input parameters are correct.
     */
    private boolean checkForEmptyInvalidInput(String text, String keyphrase) {

        boolean isError = false;

        // Current text has no alphabetical characters. Text must at least one alphabetical character.
        if (!text.matches(".*[a-zA-Z]+.*")) {
            this.text.setError("Nothing to encode/decode");
            isError = true;
        }

        // Current keyphrase is either null or empty.
        if (null == keyphrase || keyphrase.isEmpty()) {
            this.keyphrase.setError("Keyphrase required");
            isError = true;
        }

        // Non-alphabetical character(s) in keyphrase. Keyphrase must only contain alphabetical characters.
        boolean valid = this.checkIfKeyphraseValid(keyphrase);
        if (!valid) {
            // Non-alphabetical character(s) in keyphrase.
            this.keyphrase.setError("Non-alphabetical character(s) in keyphrase");
            isError = true;
        }
        return isError;
    } // end of checkForEmptyInvalidInput.

    /**
     * Private method that verifies the the keyphrase is valid (does not contain non-alphabetical characters).
     *
     * @param keyphrase  Alphabetical keyphrase that is used to encrypt/decrypt the text.
     * @return Boolean value that indicates if the input keyphrase is valid or not.
     */
    private boolean checkIfKeyphraseValid(String keyphrase) {

        boolean valid = true;

        for(int z = 0; z < keyphrase.length(); ++z) {
            char c = keyphrase.charAt(z);
            if (!Character.isAlphabetic(c)) {
                valid = false;
                break;
            }
        }
        return valid;
    } // end of checkIfKeyphraseValid.
}  // end of VCipherActivity class.
