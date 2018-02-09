package com.pindad.aftersalespindadmekanik.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pindad.aftersalespindadmekanik.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    ApiInterface mApiInterface;
    private EditText username, password;
    List<Customer> KontakList;

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
        Call<List<Customer>> kontakCall = mApiInterface.putLogin(username.getText().toString(), password.getText().toString());
        kontakCall.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                KontakList = response.body();
                try {
                    SaveSharedPreference.setUserName(getActivity(), username.getText().toString(), KontakList.get(0).getId_customer());
                    MenuActivity activity = (MenuActivity) getActivity();
                    activity.signIn();
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}