package br.com.erico.desafioappia.controls;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Connection {

    private static FirebaseAuth firebaseAuth;
    private static FirebaseAuth.AuthStateListener authStateListener;
    private static FirebaseUser firebaseUser;

    private Connection(){

    }

    public static FirebaseAuth getFirebaseAuth(){

        if(firebaseAuth == null){
            startFirebaseAuth();
        }

        return firebaseAuth;

    }

    private static void startFirebaseAuth() {

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){

                    firebaseUser = user;
                    Log.i("----------------------", "Aqui 1: " + firebaseUser.getEmail());
                }
            }
        };

        firebaseAuth.addAuthStateListener(authStateListener);

    }

    public static FirebaseUser getFirebaseUser(){
        //Log.i("----------------------", "Aqui 2: " + firebaseUser.getEmail());
        return firebaseUser;
    }

    public static void logOut(){
        firebaseAuth.signOut();
    }
}
