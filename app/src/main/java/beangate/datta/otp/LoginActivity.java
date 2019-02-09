package beangate.datta.otp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateMobileNo(editText.getText().toString()))
                {
                    sendVerificationCode(editText.getText().toString());

                }
                else{
                    Toast.makeText(LoginActivity.this, "Enter right no", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d("jfdkbvkdfb", "onVerificationCompleted:" + credential);
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w("jfdkbvkdfb", "onVerificationFailed", e);
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(getApplicationContext(),"Invalid Mobile Number",Toast.LENGTH_SHORT).show();

                } else if (e instanceof FirebaseTooManyRequestsException) {

                    Toast.makeText(getApplicationContext(),"Quate Exceed",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                Log.d("jfdkbvkdfb", "onCodeSent:" + verificationId);

                Intent i = new Intent(LoginActivity.this,OtpVerificationActivity.class);
                i.putExtra("verificationId",verificationId);
                i.putExtra("resendToken",token);
                i.putExtra("mobileno",editText.getText().toString());

                startActivity(i);


            }
        };



    }

    private void init() {
        editText = findViewById(R.id.number);
        button = findViewById(R.id.btn);
    }

    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull final Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }
                else{

                }
            }
        });

    }

    public void sendVerificationCode(String text) {
        Log.d("mdbsd", "sendVerificationCode: Verification Code Sent");

        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+text.toString()
                ,60
                , TimeUnit.SECONDS
                ,this
                ,mCallback);
    }

    private boolean validateMobileNo(String text) {

        if(text.length()<10 || text.length()>10)
        {
            return false;
        }
        return true;
    }


}
