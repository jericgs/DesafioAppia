package br.com.erico.desafioappia.controls;

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
                if(firebaseUser != null){
                    firebaseUser = user;
                }
            }
        };

        firebaseAuth.addAuthStateListener(authStateListener);

    }

    public static FirebaseUser getFirebaseUser(){
        return firebaseUser;
    }

    public static void logOut(){
        firebaseAuth.signOut();
    }
}
