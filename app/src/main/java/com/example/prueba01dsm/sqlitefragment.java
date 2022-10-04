package com.example.conceptosmedios;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.conceptosmedios.databinding.FragmentSqliteBinding;


public class SqliteFragment extends Fragment {

    private FragmentSqliteBinding binding;

    private EditText txt_code, txt_description, txt_price;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSqliteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txt_code = (EditText) binding.txtCode;
        txt_description = (EditText) binding.txtDescription;
        txt_price = (EditText) binding.txtPrice;

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        binding.btnSearchP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });

        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit();
            }
        });

        binding.btnToLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SqliteFragment.this)
                        .navigate(R.id.action_sqliteFragment_to_tableLayoutFragment);
            }
        });

    }

    //Metodo para guardar los productos
    public void save() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this.getActivity(), "administration", null, 1);
        SQLiteDatabase database = admin.getWritableDatabase();

        String code = txt_code.getText().toString();
        String description = txt_description.getText().toString();
        String price = txt_price.getText().toString();

        if (!code.isEmpty() && !description.isEmpty() && !price.isEmpty()) {
            ContentValues register = new ContentValues();
            register.put("code", code);
            register.put("description", description);
            register.put("price", price);
            database.insert("products", null, register);
            database.close();
            cleanForm();
            Toast.makeText(this.getActivity(), "Registro exitoso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this.getActivity(), "Completar todos los datos", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para buscar productos
    public void search() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this.getActivity(), "administration", null, 1);
        SQLiteDatabase database = admin.getWritableDatabase();
        admin.getWritableDatabase();
        String code = txt_code.getText().toString();
        if (!code.isEmpty()) {
            Cursor row = database.rawQuery
                    ("select description, price  from products where code =" + code, null);
            if (row.moveToFirst()) {
                txt_description.setText(row.getString(0));
                txt_price.setText(row.getString(1));
                database.close();
            } else {
                Toast.makeText(this.getActivity(), "No se encontraron registros", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this.getActivity(), "Ingresar código", Toast.LENGTH_SHORT).show();
        }
    }

    //metodo para eliminar un producto
    public void delete() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this.getActivity(), "administration", null, 1);
        SQLiteDatabase database = admin.getWritableDatabase();
        admin.getWritableDatabase();
        String code = txt_code.getText().toString();
        if (!code.isEmpty()) {
            int count = database.delete("products", "code=" + code, null);
            if (count == 1) {
                database.close();
                cleanForm();
                Toast.makeText(this.getActivity(), "Eliminado exitosamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this.getActivity(), "El producto no existe", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this.getActivity(), "Ingresar código", Toast.LENGTH_SHORT).show();
        }
    }

    public void edit() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this.getActivity(), "administration", null, 1);
        SQLiteDatabase database = admin.getWritableDatabase();

        String code = txt_code.getText().toString();
        String description = txt_description.getText().toString();
        String price = txt_price.getText().toString();

        if (!code.isEmpty() && !description.isEmpty() && !price.isEmpty()) {
            ContentValues register = new ContentValues();
            register.put("code", code);
            register.put("description", description);
            register.put("price", price);
            int cant = database.update("products", register, "code=" + code, null);
            if (cant == 1) {
                database.close();
                cleanForm();
                Toast.makeText(this.getActivity(), "Actualizado exitosamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this.getActivity(), "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this.getActivity(), "Ingresar código", Toast.LENGTH_SHORT).show();
        }
    }

    public void cleanForm() {
        txt_code.setText("");
        txt_description.setText("");
        txt_price.setText("");
    }
}