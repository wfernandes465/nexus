package mist.nexus.com.nexus.Telas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import android.content.Intent;

import mist.nexus.com.nexus.Comentarios.BerserkComentarios;
import mist.nexus.com.nexus.Constants;
import mist.nexus.com.nexus.R;

public class TelaBerserk extends YouTubeBaseActivity {

    Button button;
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    Button adicionar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.berserk_tela);


        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.view2);
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean button) {
                youTubePlayerView.initialize(Constants.licenseYouTube, onInitializedListener);
                youTubePlayer.loadVideo(Constants.urlVideoBerserk);
                youTubePlayer.play();

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }

        };
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                youTubePlayerView.initialize("54596426350-58e6vg7f6s9u4ojpi2irnkunp7nn3sjv.apps.googleusercontent.com", onInitializedListener);

            }
        });

    }

    public void chamarComentario(View view){
        adicionar = (Button) findViewById(R.id.adicionar);
        startActivity(new Intent(this, BerserkComentarios.class));
    }

}


