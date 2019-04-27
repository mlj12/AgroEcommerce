package uk.aber.ac.agroecommerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {

    private EditText txtEmailLogin;
    private EditText txtPwd;
    private FirebaseAuth firebaseAuth;
    private Button signInBtn;

    private TextView txt_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        txtEmailLogin = (EditText) findViewById(R.id.email_login);
        txtPwd = (EditText) findViewById(R.id.password_login);
        txt_register = (TextView) findViewById(R.id.create_acc_txt);
        signInBtn = (Button) findViewById(R.id.log_in_button);

        firebaseAuth = FirebaseAuth.getInstance();

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                signIn();
            }
        });

        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this,Register.class);
                startActivity(intent);
            }
        });

    }

    public void signIn() {
        final ProgressDialog progressDialog = ProgressDialog.show(LogIn.this, "Please wait...", "Proccessing...", true);

        (firebaseAuth.signInWithEmailAndPassword(txtEmailLogin.getText().toString(), txtPwd.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            Toast.makeText(LogIn.this, "Login successful", Toast.LENGTH_LONG).show();
                           Intent i = new Intent(LogIn.this,Home2.class);
                            i.putExtra("Email", firebaseAuth.getCurrentUser().getEmail());
                            startActivity(i);
                        } else {
                            Log.e("ERROR", task.getException().toString());
                            Toast.makeText(LogIn.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }

                    }
                });
    }
}