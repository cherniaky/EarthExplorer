package sk.tuke.earthexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthProvider;

import sk.tuke.earthexplorer.databinding.ActivityLoginBinding;
import sk.tuke.earthexplorer.databinding.ActivitySignUpBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding = null;
    private FirebaseAuth mAuth;

    private EditText email_input;
    private EditText password_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        email_input = findViewById(R.id.editTextTextEmailAddress);
        password_input = findViewById(R.id.editTextTextPassword);

        mAuth = FirebaseAuth.getInstance();

        ((Button) findViewById(R.id.login_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        ((Button) findViewById(R.id.to_register_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
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
                                        new Intent(LoginActivity.this, MainActivity.class)
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

    private void login() {
        mAuth.signInWithEmailAndPassword(email_input.getText().toString(),
                        password_input.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
