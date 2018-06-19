package com.saipriyank.iread;

import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Locale;

public class Main extends AppCompatActivity {

    private TextToSpeech tts;
    private EditText et;
    private SeekBar sb_pitch;
    private SeekBar sb_speed;
    private Button bt;
    private Button bt2;
    private Button bt3;
    private Button bt4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        bt = findViewById(R.id.bt);
        bt2 = findViewById(R.id.bt2);
        bt3 = findViewById(R.id.bt3);
        bt4 = findViewById(R.id.bt4);


        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if(status == TextToSpeech.SUCCESS)
                {
                  tts.setLanguage(Locale.ENGLISH);
                sb_pitch.setEnabled(false);
                sb_speed.setEnabled(false);
                }

            }
        });

        et = findViewById(R.id.et);
        sb_pitch = findViewById(R.id.sb_pitch);
        sb_speed = findViewById(R.id.sb_speed);



        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"No Text Detected!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Press 'Stop' to end reading.",Toast.LENGTH_SHORT).show();
                    sb_pitch.setEnabled(false);
                    sb_speed.setEnabled(false);
                    speak();

                }
            }
        });


        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.stop();
                sb_pitch.setEnabled(false);
                sb_speed.setEnabled(false);
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sb_pitch.setEnabled(false);
                sb_speed.setEnabled(false);
                et.setText("");

                sb_pitch.setProgress(50);
                sb_speed.setProgress(50);

            }
        });

        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sb_pitch.setEnabled(true);
                sb_speed.setEnabled(true);
            }
        });

    }

    private void speak()
    {

        String text = et.getText().toString();

        float pitch = (float)sb_pitch.getProgress() / 50;
        if(pitch<0.1)pitch = 0.1f;

        float speed = (float)sb_speed.getProgress() / 50;
        if(speed<0.1)speed = 0.1f;

        tts.setPitch(pitch);
        tts.setSpeechRate(speed);

        tts.speak(text,TextToSpeech.QUEUE_FLUSH,null);

    }

    

    @Override
    protected void onDestroy() {

        if(tts!=null)
        {
            tts.stop();
            tts.shutdown();
        }

        super.onDestroy();
    }

}
