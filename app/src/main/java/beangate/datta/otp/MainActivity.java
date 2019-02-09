package beangate.datta.otp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout=(Button)findViewById(R.id.logout);

//        String phoneNumber = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
//        Toast.makeText(this, ""+phoneNumber, Toast.LENGTH_SHORT).show();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        //Checking User Is Logged In Or not;
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        if(mUser==null)
        {
            goToLoginPage();
        }


    }

    // Method For going on login page
    private void goToLoginPage() {

        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }


}
