package com.pindad.aftersalespindadmekanik.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pindad.aftersalespindadmekanik.MainActivity;
import com.pindad.aftersalespindadmekanik.Model.Users;
import com.pindad.aftersalespindadmekanik.R;
import com.pindad.aftersalespindadmekanik.RestAPI.ApiClient;
import com.pindad.aftersalespindadmekanik.RestAPI.ApiInterface;
import com.pindad.aftersalespindadmekanik.SaveSharedPreference;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    ApiInterface mApiInterface;
    private EditText username, password;
    List<Users> KontakList;

    public LoginFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        final TextView textView = (TextView) rootView.findViewById(R.id.title_text);
        username = (EditText) rootView.findViewById(R.id.username);
        password = (EditText) rootView.findViewById(R.id.password);
        Button login = (Button) rootView.findViewById(R.id.login);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
            }
        });
        return rootView;
    }

    public void refresh() {
        Call<List<Users>> kontakCall = mApiInterface.putLogin(username.getText().toString(), password.getText().toString());
        kontakCall.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                KontakList = response.body();
                try {
                    SaveSharedPreference.setUserName(getActivity(), username.getText().toString(), KontakList.get(0).getId_customer());
                    MainActivity activity = (MainActivity) getActivity();
                    activity.signIn();
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}