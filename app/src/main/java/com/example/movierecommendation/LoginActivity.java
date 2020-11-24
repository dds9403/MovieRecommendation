//package com.example.movierecommendation;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.util.Patterns;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.common.SignInButton;
//import com.google.android.gms.common.api.ApiException;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//
//public class LoginActivity extends AppCompatActivity {
//    private String TAG = "MainActivity";
//    private int RC_SIGN_IN = 101;
//    EditText userName, password;
//    FirebaseAuth mAuth;
//    Button signInGoogle, signIn, signUp;
//    private GoogleSignInClient mGoogleSignInClient;
//    private SignInButton signInButton;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        mAuth = FirebaseAuth.getInstance();
//
//        FirebaseUser firebaseUser = mAuth.getCurrentUser();
//
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//
//        if (firebaseUser != null || account != null) {
//            Intent intent1 = new Intent(this, MainActivity.class);
//            startActivity(intent1);
//            String user = firebaseUser==null?account.getEmail():firebaseUser.getEmail();
//            Toast.makeText(this,"Signed in as "+user,Toast.LENGTH_SHORT).show();
//            finish();
//        }
//
//        userName = (EditText) findViewById(R.id.username);
//        password = (EditText) findViewById(R.id.password);
//
////      signInGoogle=(Button) findViewById(R.id.signInGoogle);
//        signInButton = findViewById(R.id.signInGoogle);
//        signInButton.setSize(SignInButton.SIZE_STANDARD);
//        signIn = (Button) findViewById(R.id.signIn);
//        signUp = (Button) findViewById(R.id.signUp);
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.client_id))
//                .requestEmail()
//                .build();
//
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//
//        signIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loginUser(view);
//            }
//        });
//
//        signInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                signIn();
//            }
//        });
//
//        signUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//    }
//
//    public void signUp(View view) {
//        Intent intent = new Intent(this, SignUpActivity.class);
//        startActivity(intent);
//    }
//
//    public void loginUser(View view) {
//        String user = userName.getText().toString().trim();
//        String pass = password.getText().toString().trim();
//
//        if (user.isEmpty()) {
//            userName.setError("Username required");
//            userName.requestFocus();
//            return;
//        }
//
//        if (!Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
//            userName.setError("Please enter a valid email");
//            userName.requestFocus();
//            return;
//        }
//
//        if (pass.isEmpty()) {
//            password.setError("Password required");
//            password.requestFocus();
//            return;
//        }
//
//        mAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                }
//            }
//        });
//
//
//    }
//
//
//    private void signIn() {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            // The Task returned from this call is always completed, no need to attach
//            // a listener.
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            handleSignInResult(task);
//        }
//    }
//
//    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
//        try {
//            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
//            Toast.makeText(LoginActivity.this, "Successfully logedd in!", Toast.LENGTH_LONG).show();
//            //firebaseAuthWithGoogle(account.getIdToken());
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(intent);
//        } catch (ApiException e) {
//            // The ApiException status code indicates the detailed failure reason.
//            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.d("Signin Failed", e.getMessage() + " " + e.getStatusCode());
//            Toast.makeText(LoginActivity.this, "Please try again", Toast.LENGTH_LONG).show();
//        }
//    }
//
////    private void firebaseAuthWithGoogle(String idToken) {
////        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
////        mAuth.signInWithCredential(credential)
////                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
////                    @Override
////                    public void onComplete(@NonNull Task<AuthResult> task) {
////                        if (task.isSuccessful()) {
////                            // Sign in success, update UI with the signed-in user's information
////                            Log.d(TAG, "signInWithCredential:success");
////                            FirebaseUser user = mAuth.getCurrentUser();
////                            updateUI(user);
////                        } else {
////                            // If sign in fails, display a message to the user.
////                            Log.w(TAG, "signInWithCredential:failure", task.getException());
////                            updateUI(null);
////                        }
////
////                        // ...
////                    }
////                });
////    }
////
////    private void updateUI(FirebaseUser user) {
////        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
////        try {
////
////            String username = account.getDisplayName();
////            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
////            intent.putExtra("USERNAME", username);
////            startActivity(intent);
////
////        } catch (NullPointerException e) {
////            Log.d("Null Pointer",e.toString());
////            Toast.makeText(MainActivity.this, "Log in failed!Please try again", Toast.LENGTH_LONG).show();
////
////        }
////    }
//}


package com.example.movierecommendation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {
    private String TAG="MainActivity";
    private int RC_SIGN_IN = 101;
    EditText userName, password;
    FirebaseAuth mAuth;
    Button signInGoogle,signIn,signUp;
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        userName = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        signInButton = findViewById(R.id.signInGoogle);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signIn = (Button) findViewById(R.id.signIn);
        signUp = (Button) findViewById(R.id.signUp);

        //Initialize the Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(view);
            }
        });

        //Onclick listener for Google Sign In button
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInForGoogle();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        //Check if the google account if there is any account that is logged in the phone.
        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            //Check if the user exists in Firebase
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser!=null) {
                Log.i("Username",currentUser.getEmail()+" in Onstart of Google Signin");
                String username = currentUser.getEmail();
                Uri photoUrl = currentUser.getPhotoUrl();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("Username",username);
                intent.putExtra("PhotoURL",photoUrl.toString());
                Log.i("PhotoURL",photoUrl.toString());
                startActivity(intent);
            }
            //else{
            //Save the account to Firebase and update UI accordingly.
            //}
        }else{
        FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser != null) {
                Log.i("Username in", currentUser.getEmail() + "  in Onstart of Firebase Signin");
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("Username", currentUser.getEmail());
                startActivity(intent);
            }
        }


        // what should you do if the user is not already signed in?


    }

    public void signUp(View view) {
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }

    public void loginUser(View view) {
        String user = userName.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if (user.isEmpty()) {
            userName.setError("Username required");
            userName.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
            userName.setError("Please enter a valid email");
            userName.requestFocus();
            return;
        }

        if (pass.isEmpty()) {
            password.setError("Password required");
            password.requestFocus();
            return;
        }


        mAuth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.i("Username",mAuth.getCurrentUser().getEmail());
                    Intent intent = new Intent(LoginActivity.this,GenreActivity.class);
                    intent.putExtra("Username",mAuth.getCurrentUser().getEmail());
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this,"Invalid username/password!Please try again",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    //Start google Sign in Intent if the Signin Button is clicked
    private void signInForGoogle(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    //Result after the Google Sign-in Activity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Toast.makeText(LoginActivity.this,"Successfully logedd in!",Toast.LENGTH_LONG).show();
            firebaseAuthWithGoogle(account.getIdToken());
//            Intent intent= new Intent(MainActivity.this,GenreActivity.class);
//            startActivity(intent);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d("Signin Failed", e.getMessage() + " "+e.getStatusCode());
            Toast.makeText(LoginActivity.this,"Please try again",Toast.LENGTH_LONG).show();
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        try {

            String username = account.getEmail();
            String photoURL = account.getPhotoUrl().toString();
            Log.i("Username",username);
            Intent intent = new Intent(LoginActivity.this, GenreActivity.class);
            Intent mainActivityIntent = new Intent(LoginActivity.this, MainActivity.class);
            mainActivityIntent.putExtra("Username",username);
            mainActivityIntent.putExtra("PhotoURL",photoURL);
            startActivity(intent);


        } catch (NullPointerException e) {
            Log.d("Null Pointer",e.toString());
            Toast.makeText(LoginActivity.this, "Log in failed!Please try again", Toast.LENGTH_LONG).show();

        }
    }
}

