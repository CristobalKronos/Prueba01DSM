package com.example.prueba01dsm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.prueba01dsm.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private String mail, user, url;
    private WebView wv1;
    SharedPreferences pref = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
    String email = pref.getString("email", "empty");

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState

    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Previous
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Guardar
                setContentView(R.layout.activity_web);
                wv1= (WebView) findViewById(R.id.wv1);
                String url=getIntent().getStringExtra("url");
                wv1.setWebViewClient(new WebViewClient());
                Intent i= new Intent(this.getContext(), ActivityWeb.class);
                i.putExtra("url",txt_search.getText().toString());
            }
        });
        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Buscar
            }
        });
        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Reproducir Canción
            }
        });
        binding.btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navegar por URL
            }
        });
    }

    public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

        public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            //VAMOS A CREAR LA TABLA
            sqLiteDatabase.execSQL("create table products(code int primary key, description text, price real)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}