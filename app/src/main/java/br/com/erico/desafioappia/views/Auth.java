package br.com.erico.desafioappia.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.erico.desafioappia.R;
import br.com.erico.desafioappia.controls.Connection;
import br.com.erico.desafioappia.models.User;

public class Auth extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail, editTextPassword;
    private Button buttonEnter;

    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_auth);
        startComponents();
        eventClick();
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Connection.getFirebaseAuth();
        user = Connection.getFirebaseUser();
        //checkLogged();
    }

    private void checkLogged() {
        if(user != null){
            Intent intent = new Intent(Auth.this, Dashboard.class);
            startActivity(intent);
            finish();
        }
    }


    private void startComponents() {
        editTextEmail = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonEnter = (Button) findViewById(R.id.buttonEnter);
    }

    private void eventClick() {
        buttonEnter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.buttonEnter){
            User user = new User();
            user.setUsername(editTextEmail.getText().toString().trim());
            user.setPassword(editTextPassword.getText().toString().trim());
            login(user);
        }
    }

    private void login(User user) {
        auth.signInWithEmailAndPassword(user.getUsername(), user.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(Auth.this, Dashboard.class);
                            startActivity(intent);
                            finish();
                        }else{
                            alert("E-mail ou Senha inválido");
                        }
                    }
                });
    }

    private void alert(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

}
