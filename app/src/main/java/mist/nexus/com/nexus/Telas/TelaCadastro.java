package mist.nexus.com.nexus.Telas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import mist.nexus.com.nexus.R;
import mist.nexus.com.nexus.ajuda.ValidarEntrada;
import mist.nexus.com.nexus.SQL.DatabaseHelper;
import mist.nexus.com.nexus.modelo.Usuario;

public class TelaCadastro extends AppCompatActivity implements View.OnClickListener{

    private final AppCompatActivity tela = TelaCadastro.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout txtNomeCad;
    private TextInputLayout txtEmailCad;
    private TextInputLayout txtSenhaCad;
    private TextInputLayout txtSenhaConfCad;

    private TextInputEditText editNomeCad;
    private TextInputEditText editEmailCad;
    private TextInputEditText editSenhaCad;
    private TextInputEditText editSenhaConfCad;

    private AppCompatButton btnCadastro;
    private AppCompatTextView txtLoginCriado;

    private ValidarEntrada validarEntrada;
    private DatabaseHelper databaseHelper;
    private Usuario usuario;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.tela_cadastro);

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        txtNomeCad = (TextInputLayout) findViewById(R.id.txtNomeCad);
        txtEmailCad = (TextInputLayout) findViewById(R.id.txtEmailCad);
        txtSenhaCad = (TextInputLayout) findViewById(R.id.txtSenhaCad);
        txtSenhaConfCad = (TextInputLayout) findViewById(R.id.txtSenhaConfCad);

        editNomeCad = (TextInputEditText) findViewById(R.id.editNomeCad);
        editEmailCad = (TextInputEditText) findViewById(R.id.editEmailCad);
        editSenhaCad = (TextInputEditText) findViewById(R.id.editSenhaCad);
        editSenhaConfCad = (TextInputEditText) findViewById(R.id.editSenhaConfCad);

        btnCadastro = (AppCompatButton) findViewById(R.id.btnCadastro);

        txtLoginCriado = (AppCompatTextView) findViewById(R.id.txtLoginCriado);
    }

    private void initListeners() {
        btnCadastro.setOnClickListener(this);
        txtLoginCriado.setOnClickListener(this);
    }

    private void initObjects() {
        validarEntrada = new ValidarEntrada(tela);
        databaseHelper = new DatabaseHelper(tela);
        usuario = new Usuario();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCadastro:
                postDataToSQLite();
                break;
            case R.id.txtLoginCriado:
                finish();
                break;
        }
    }

    private void postDataToSQLite() {
        if (!validarEntrada.EditTextPreenchido(editNomeCad, txtNomeCad, getString(R.string.mensagemErro_nome))) {
            return;
        }
        if (!validarEntrada.EditTextPreenchido(editEmailCad, txtEmailCad, getString(R.string.mensagemErro_email))) {
            return;
        }
        if (!validarEntrada.entradaEditTextEmail(editEmailCad, txtEmailCad, getString(R.string.mensagemErro_email))) {
            return;
        }
        if (!validarEntrada.EditTextPreenchido(editSenhaCad, txtSenhaCad, getString(R.string.mensagemErro_senha))) {
            return;
        }
        if (!validarEntrada.entradaEditTextcorresponde(editSenhaCad, editSenhaConfCad,
                txtSenhaConfCad, getString(R.string.senhasDiferentes))) {
            return;
        }

        if (!databaseHelper.verificarUsuario(editEmailCad.getText().toString().trim())) {

            usuario.setNome(editNomeCad.getText().toString().trim());
            usuario.setEmail(editEmailCad.getText().toString().trim());
            usuario.setSenha(editSenhaCad.getText().toString().trim());

            databaseHelper.addUsuario(usuario);

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(nestedScrollView, getString(R.string.mensagemSucesso), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();


        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollView, getString(R.string.emailJaCadastrado), Snackbar.LENGTH_LONG).show();
        }
    }

    private void emptyInputEditText() {
        editNomeCad.setText(null);
        editEmailCad.setText(null);
        editSenhaCad.setText(null);
        editSenhaConfCad.setText(null);
    }
}
