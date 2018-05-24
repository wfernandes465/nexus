package mist.nexus.com.nexus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import mist.nexus.com.nexus.Telas.TelaBerserk;
import mist.nexus.com.nexus.Telas.TelaDBZ;
import mist.nexus.com.nexus.Telas.TelaDarling;
import mist.nexus.com.nexus.Telas.TelaNanatsu;
import mist.nexus.com.nexus.Telas.TelaNaruto;
import mist.nexus.com.nexus.Telas.TelaSuperCampeoes;

public class LobbyActivity extends AppCompatActivity {

    Button adicionar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobby);
        getSupportActionBar().hide();
    }

    public void chamarBerserk(View view) {
        adicionar = (Button) findViewById(R.id.adicionar);
        startActivity(new Intent(this, TelaBerserk.class));
    }

    public void chamarNanatsu(View view) {
        adicionar = (Button) findViewById(R.id.adicionar);
        startActivity(new Intent(this, TelaNanatsu.class));
    }
    public void chamarNaruto(View view) {
        adicionar = (Button) findViewById(R.id.adicionar);
        startActivity(new Intent(this, TelaNaruto.class));
    }
    public void chamarDarling(View view) {
        adicionar = (Button) findViewById(R.id.adicionar);
        startActivity(new Intent(this, TelaDarling.class));
    }
    public void chamarDBZ(View view) {
        adicionar = (Button) findViewById(R.id.adicionar);
        startActivity(new Intent(this, TelaDBZ.class));
    }
    public void chamarSuperCampeoes(View view) {
        adicionar = (Button) findViewById(R.id.adicionar);
        startActivity(new Intent(this, TelaSuperCampeoes.class));
    }

}