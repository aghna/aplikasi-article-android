package id.ac.uin_malang.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class grid extends Fragment {

    EditText editText1, editText2;
    Button button;
    String text1, text2;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grid, container, false);

        editText1 = (EditText) view.findViewById(R.id.username);
        editText2 = (EditText) view.findViewById(R.id.pass);
        button = (Button) view.findViewById(R.id.btnMasuk);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text1 = editText1.getText().toString();
                text2 = editText2.getText().toString();
                if ((text1.contains("admin"))&&(text2.contains("admin"))){
                    Toast.makeText(getContext(), "login success", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(getActivity(), list_admin.class);
                    getActivity().startActivity(intent);
                }
                else if ((text1.matches(""))&&(text2.matches(""))){
                    Toast.makeText(getContext(), "insert your username and password", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "login failed", Toast.LENGTH_SHORT).show();

                }
            }
        });
        return view;
    }

}
