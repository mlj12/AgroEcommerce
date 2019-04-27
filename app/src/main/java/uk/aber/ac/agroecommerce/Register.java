package uk.aber.ac.agroecommerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    private EditText txtEmailAddress;
    private EditText txtPassword;
    private FirebaseAuth firebaseAuth;
    private Button registerBtn;
    private Button loginBtn;
    private Spinner spinner_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        txtEmailAddress = (EditText) findViewById(R.id.email);
        txtPassword = (EditText) findViewById(R.id.password);
        registerBtn = (Button) findViewById(R.id.register_button);

        firebaseAuth = FirebaseAuth.getInstance();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                createAccount();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {


                Intent i = new Intent(Register.this, LogIn.class);
                startActivity(i);

            }
        });

    }
    public void createAccount() {

        final ProgressDialog progressDialog = ProgressDialog.show(Register.this, "Please wait...", "Processing...", true);
        (firebaseAuth.createUserWithEmailAndPassword(txtEmailAddress.getText().toString(), txtPassword.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(Register.this, LogIn.class);
                            startActivity(i);
                        }
                        else
                        {
                            Log.e("ERROR", task.getException().toString());
                            Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}