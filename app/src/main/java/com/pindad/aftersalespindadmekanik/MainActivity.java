package com.pindad.aftersalespindadmekanik;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pindad.aftersalespindadmekanik.Fragment.LoginFragment;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView txtRegId, txtMessage;
    public FragmentManager fragmentManager;
    public Toolbar toolbar;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

//        txtRegId = (TextView) findViewById(R.id.txt_reg_id);
//        txtMessage = (TextView) findViewById(R.id.txt_push_message);
//        Button btnNext = (Button) findViewById(R.id.BtnNext);
//        btnNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i =  new Intent (MainActivity.this, EmailListActivity.class);
//                startActivity(i);
//            }
//        });

        fragmentManager = getSupportFragmentManager();
//        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//
//                // checking for type intent filter
//                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
//                    // gcm successfully registered
//                    // now subscribe to `global` topic to receive app wide notifications
//                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
//
//                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
//                    // new push notification is received
//
//                    String message = intent.getStringExtra("message");
//
//                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
//
//                    txtMessage.setText(message);
//                }
//            }
//        };
    }

    @Override
    public void onStart() {
        super.onStart();
        try{
            if(SaveSharedPreference.getUserName(MainActivity.this).length() == 0)
            {
                // call Login Activity
            }
            signIn();
        }catch (NullPointerException e){
            LoginFragment loginFragment = new LoginFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.loginContainer, loginFragment)
                    .commit();
        }// Check if user is signed in (non-null) and update UI accordingly.
    }


    public void signIn() {
//        FrameLayout loginContainer = (FrameLayout) findViewById(R.id.loginContainer);
//        loginContainer.setVisibility(GONE);
//        toolbar.setVisibility(View.GONE);
        Intent intent = new Intent(MainActivity.this,EmailListActivity.class);
        startActivity(intent);
//        CatalogueFragment catalogueFragment = new CatalogueFragment();
//        fragmentManager.beginTransaction()
//                .replace(R.id.catalogueContainer, catalogueFragment)
//                .commit();
    }

    private void updateUI() {
        if (true) {
//            FrameLayout loginContainer = (FrameLayout) findViewById(R.id.loginContainer);
//            loginContainer.setVisibility(GONE);
            Intent intent = new Intent(MainActivity.this,EmailListActivity.class);
            startActivity(intent);
        }else{
            LoginFragment loginFragment = new LoginFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.loginContainer, loginFragment)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
//        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
//                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
//        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
//                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
//        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
}
