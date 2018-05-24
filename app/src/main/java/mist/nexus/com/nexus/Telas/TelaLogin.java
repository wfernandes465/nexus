package mist.nexus.com.nexus.Telas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import mist.nexus.com.nexus.LobbyActivity;
import mist.nexus.com.nexus.R;
import mist.nexus.com.nexus.SQL.DatabaseHelper;
import mist.nexus.com.nexus.ajuda.ValidarEntrada;

public class TelaLogin extends AppCompatActivity implements View.OnClickListener  {
    private final AppCompatActivity tela = TelaLogin.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout txtEmail;
    private TextInputLayout txtSenha;

    private TextInputEditText editEmail;
    private TextInputEditText editSenha;

    private AppCompatButton btnLogin;

    private AppCompatTextView txtNovoCadastro;

    private ValidarEntrada validarEntrada;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.tela_login);

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews() {

        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        txtEmail = (TextInputLayout) findViewById(R.id.txtEmail);
        txtSenha = (TextInputLayout) findViewById(R.id.txtSenha);

        editEmail = (TextInputEditText) findViewById(R.id.editEmail);
        editSenha = (TextInputEditText) findViewById(R.id.editSenha);

        btnLogin = (AppCompatButton) findViewById(R.id.btnLogin);

        txtNovoCadastro = (AppCompatTextView) findViewById(R.id.txtNovoCadastro);
    }

    private void initListeners() {
        btnLogin.setOnClickListener(this);
        txtNovoCadastro.setOnClickListener(this);
    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(tela);
        validarEntrada = new ValidarEntrada(tela);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                verifyFromSQLite();
                break;
            case R.id.txtNovoCadastro:
                Intent intentRegister = new Intent(getApplicationContext(), TelaCadastro.class);
                startActivity(intentRegister);
                break;
        }
    }

    private void verifyFromSQLite() {
        if (!validarEntrada.EditTextPreenchido(editEmail, txtEmail, getString(R.string.mensagemErro_email))) {
            return;
        }
        if (!validarEntrada.entradaEditTextEmail(editEmail, txtEmail, getString(R.string.mensagemErro_email))) {
            return;
        }
        if (!validarEntrada.EditTextPreenchido(editSenha, txtSenha, getString(R.string.mensagemErro_senha))) {
            return;
        }

        if (databaseHelper.verificarUsuario(editEmail.getText().toString().trim()
                , editSenha.getText().toString().trim())) {


            Intent accountsIntent = new Intent(tela, LobbyActivity.class);
            accountsIntent.putExtra("EMAIL", editEmail.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountsIntent);


        } else {
            Snackbar.make(nestedScrollView, getString(R.string.emailSenhaInvalidos), Snackbar.LENGTH_LONG).show();
        }
    }

    private void emptyInputEditText() {
        editEmail.setText(null);
        editSenha.setText(null);
    }
}
