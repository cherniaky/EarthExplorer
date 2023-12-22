package sk.tuke.earthexplorer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthProvider;

import sk.tuke.earthexplorer.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText email_input;
    private EditText password_input;

    ActivitySignUpBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());

        email_input = findViewById(R.id.editTextTextEmailAddress);
        password_input = findViewById(R.id.editTextTextPassword);

        mAuth = FirebaseAuth.getInstance();

        ((Button) findViewById(R.id.signup_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

        ((Button) findViewById(R.id.to_login_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        ((Button) findViewById(R.id.github_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGitHub();
            }
        });

    }

    private void signInWithGitHub() {
        OAuthProvider.Builder provider = OAuthProvider.newBuilder("github.com");
        mAuth
                .startActivityForSignInWithProvider(/* activity= */ this, provider.build())
                .addOnSuccessListener(
                        new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                startActivity(
                                        new Intent(SignUpActivity.this, MainActivity.class)
                                );
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
    }

    private void signUp() {
        mAuth.createUserWithEmailAndPassword(email_input.getText().toString(), password_input.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}