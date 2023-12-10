package com.example.pictroviews;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class verListas extends AppCompatActivity {

    private ListView lvDatos;

    private Button btnvlv;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_listas);

        lvDatos = (ListView) findViewById(R.id.lvDatos);
        btnvlv = (Button) findViewById(R.id.btnvlv);

        listarAlerta();

    }

    private void listarAlerta(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbref = db.getReference(Alerta.class.getSimpleName());

        ArrayList<Alerta> lisalert = new ArrayList <Alerta> ();
        ArrayAdapter<Alerta> ada = new ArrayAdapter <Alerta> (verListas.this, android.R.layout.simple_list_item_1, lisalert);
        lvDatos.setAdapter(ada);

        dbref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Alerta ale = snapshot.getValue(Alerta.class);
                lisalert.add(ale);
                ada.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                ada.notifyDataSetChanged();


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (error.getCode() == DatabaseError.NETWORK_ERROR) {
                    // Manejo de la excepción de conexión a Internet
                    Toast.makeText(verListas.this, "Error de conexión a Internet", Toast.LENGTH_SHORT).show();
                } else {
                    // Manejo de otras excepciones de Firebase Database
                    Toast.makeText(verListas.this, "Error de Firebase: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        lvDatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Alerta ale = lisalert.get(i);
                AlertDialog.Builder a = new AlertDialog.Builder(verListas.this);
                a.setCancelable(true);
                a.setTitle("DATOS ALERTA");

                String msg = "--" + ale.getTitulo()+"--" + "\n\n\n\n";
                msg += "" + ale.getDescripcion()+ "\n\n\n\n";
                msg += "Latitud: "+ale.getLatitud()+ "\n\n";
                msg += "Longitud: "+ale.getLongitud()+ "\n\n";
                msg += "Hora de Alerta: "+ale.getHora()+ "\n\n";

                a.setMessage(msg);
                a.show();

            }
        });

    }//fin listar persona

    public void openSecondActivity(View view) {
        Intent intent = new Intent(this, MenuApp.class);
        startActivity(intent);
    }
}