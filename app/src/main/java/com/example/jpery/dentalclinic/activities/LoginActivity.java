package com.example.jpery.dentalclinic.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jpery.dentalclinic.utils.Constants;
import com.example.jpery.dentalclinic.R;
import com.example.jpery.dentalclinic.model.User;
import com.example.jpery.dentalclinic.services.UsersService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    Button mLoginButton;
    TextView mEmailText;
    TextView mPasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                String email = mEmailText.getText().toString();
                if (email.length() > 0) {
                    String password = mPasswordText.getText().toString();
                    if (password.length() > 0) {
                        User user = new User(email, password);
                        Call<User> call = service.loginUser(user);
                        call.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (response.code() == 200) {
                                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                    intent.putExtra(Constants.API_USER_ID, response.body().getId());
                                    startActivity(intent);
                                    mEmailText.setText("");
                                    mPasswordText.setText("");
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
