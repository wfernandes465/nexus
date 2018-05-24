package mist.nexus.com.nexus.ajuda;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class ValidarEntrada {
    private Context context;

    /**
     * Método Construtor
     *
     * @param context
     */
    public ValidarEntrada(Context context) {
        this.context = context;
    }

    /**
     * Método de verificação de EditText preenchido.
     *
     * @param textInputEditText
     * @param textInputLayout
     * @param mensagem
     * @return
     */
    public boolean EditTextPreenchido(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String mensagem) {
        String valor = textInputEditText.getText().toString().trim();
        if (valor.isEmpty()) {
            textInputLayout.setError(mensagem);
            hideKeyboardFrom(textInputEditText);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }

        return true;
    }


    /**
     * Metodo para verificar se o email é valido.
     *
     * @param textInputEditText
     * @param textInputLayout
     * @param mensagem
     * @return
     */
    public boolean entradaEditTextEmail(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String mensagem) {
        String valor = textInputEditText.getText().toString().trim();
        if (valor.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(valor).matches()) {
            textInputLayout.setError(mensagem);
            hideKeyboardFrom(textInputEditText);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    public boolean entradaEditTextcorresponde(TextInputEditText textInputEditText1, TextInputEditText textInputEditText2, TextInputLayout textInputLayout, String mensagem) {
        String valor1 = textInputEditText1.getText().toString().trim();
        String valor2 = textInputEditText2.getText().toString().trim();
        if (!valor1.contentEquals(valor2)) {
            textInputLayout.setError(mensagem);
            hideKeyboardFrom(textInputEditText2);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * Método para ocultar o teclado
     *
     * @param view
     */
    private void hideKeyboardFrom(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
