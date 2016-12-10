package com.example.jpery.dentalclinic.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jpery.dentalclinic.R;
import com.example.jpery.dentalclinic.model.User;
import com.example.jpery.dentalclinic.services.UsersService;
import com.example.jpery.dentalclinic.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    static final String KEY_USERID = "userID";
    static final String KEY_USERNAME = "username";
    static final String KEY_PASSWORD = "password";
    Button mLoginButton;
    TextView mEmailText;
    TextView mPasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        String username = prefs.getString(KEY_USERNAME, "");
        String password = prefs.getString(KEY_PASSWORD, "");
        int userID = prefs.getInt(KEY_USERID,-1);
        if(userID!=-1 && !username.equals("") && !password.equals("")) {
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            intent.putExtra(Constants.API_USER_ID, userID);
            startActivity(intent);
        }
        setContentView(R.layout.activity_login);
        mLoginButton = (Button) findViewById(R.id.login_button);
        mEmailText = (TextView) findViewById(R.id.emailText);
        mPasswordText = (TextView) findViewById(R.id.passwordText);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.API_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                UsersService service = retrofit.create(UsersService.class);
                final String email = mEmailText.getText().toString();
                if (email.length() > 0) {
                    final String password = mPasswordText.getText().toString();
                    if (password.length() > 0) {
                        User user = new User(email, password);
                        Call<User> call = service.loginUser(user);
                        call.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (response.code() == 200) {
                                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                    intent.putExtra(Constants.API_USER_ID, response.body().getId());
                                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                                    SharedPreferences.Editor ed = prefs.edit();
                                    ed.putInt(KEY_USERID,response.body().getId());
                                    ed.putString(KEY_USERNAME, email);
                                    ed.putString(KEY_PASSWORD, password);
                                    ed.apply();
                                    mEmailText.setText("");
                                    mPasswordText.setText("");
                                    startActivity(intent);
                                }
                                else {
                                    Snackbar.make(view, R.string.wrong_username_password, Snackbar.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Snackbar.make(view, R.string.internet_problem, Snackbar.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        Snackbar.make(view, R.string.empty_password, Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    Snackbar.make(view, R.string.empty_email, Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }
}
