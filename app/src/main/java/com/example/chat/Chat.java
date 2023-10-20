package com.example.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class Chat extends AppCompatActivity implements View.OnClickListener {

    private String URL = "http://a22kevburcacchat.dam.inspedralbes.cat:3270";
    private String user="";


    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(URL);
        } catch (URISyntaxException e) {}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        user = getIntent().getStringExtra("user");

        Toast toast = Toast.makeText(this,"Usuario: "+user,Toast.LENGTH_SHORT);
        toast.show();

        mSocket.connect();

        Button boton = (Button) findViewById(R.id.enviar);
        boton.setOnClickListener(this);

        mSocket.on("chat message", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                //Immprimim el missatge per el log i la consola
                String message = args[0].toString();
                Log.d("Socket.io", "Received message: " + message);
                System.out.println(message);

                //Creem un nou textView i definim el text al missatge rebut
                TextView newTextView = new TextView(Chat.this);
                newTextView.setText(message);

                //Agafem el id del scrollable
                LinearLayout parentLayout = findViewById(R.id.scroll2);

                //Li diem que faci el run al UiThread perque no deixa afegir objectes si no es desde el thread creador
                runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        //Afegim el textView al Scroll
                        parentLayout.addView(newTextView);
                    }
                });
            }

        });}

    @Override
    public void onClick(View v) {
        //Agafem el text del edittext
        EditText et = findViewById(R.id.txtInput);
        String missatge = et.getText().toString();

        String userM = user+": "+missatge;
        //Netejem el EditText
        et.setText("");
        //Enviem el emit
        mSocket.emit("chat message", userM);
    }
}