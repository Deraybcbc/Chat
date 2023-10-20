package com.example.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

private EditText NOM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NOM = (EditText)findViewById(R.id.editTextText);

        Button boto = (Button) findViewById(R.id.acceder);
        boto.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,Chat.class);
        String nom = NOM.getText().toString();
        intent.putExtra("user",nom);
        startActivity(intent);
    }
}