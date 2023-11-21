package br.edu.ifsp.dmo.exemplofirestore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.firebase.Firebase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        Button insereButton = findViewById(R.id.button_insere);
        insereButton.setOnClickListener(view -> {
            Map<String, Object> aluno = new HashMap<>();
            aluno.put("nome", "Lucas");
            aluno.put("prontuario", 300338);
            aluno.put("nascimento", 1992);
            aluno.put("sexo", "M");
            aluno.put("CPF", "123.123.123-32");

//            Map<String, Object> aluno2 = new HashMap<>();
//            aluno2.put("nome", "André");
//            aluno2.put("prontuario", 1234);
//            aluno2.put("nascumento", 2000);
//            aluno2.put("curso", "ADS");

            db.collection("alunos")
                    .add(aluno)
                    .addOnSuccessListener(documentReference -> Log.v("DMO", "Aluno inserido"))
                    .addOnFailureListener(e -> Log.v("DMO", "Erro"));

//            db.collection("alunos")
//                    .add(aluno2)
//                    .addOnSuccessListener(documentReference -> Log.v("DMO", "Aluno inserido"))
//                    .addOnFailureListener(e -> Log.v("DMO", "Erro"));
        });

        Button recuperaButton = findViewById(R.id.button_recupera);
        recuperaButton.setOnClickListener(view -> {
            db.collection("alunos")
                    .get()
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            for (DocumentSnapshot document : task.getResult()){
                                Log.v("DMO", document.getId() + "->" + document.getData());
                                Log.v("DMO", "Nome: " + document.get("nome"));
                            }
                        }
                    });
        });

        CollectionReference disciplinaReference = db.collection("DISCIPLINAS");

        Button insereDisciplinasButton = findViewById(R.id.button_insere_disciplinas);
        insereDisciplinasButton.setOnClickListener(view -> {
            /*Map<String, Object> lpe = new HashMap<>();
            lpe.put("sigla", "LPES1");
            lpe.put("nome", "Linguagem de programação estruturada");
            lpe.put("aulas", 80);
            disciplinaReference.document("LPES1").set(lpe);

            Map<String, Object> dmo = new HashMap<>();
            dmo.put("sigla", "DMOS5");
            dmo.put("nome", "Desenvolvimento para dispositivos móveis e embarcados");
            dmo.put("aulas", 80);
            disciplinaReference.document("DMOS5").set(dmo);

            Disciplina dw1 = new Disciplina("DW1S5", "Desenvolviemnto WEB", 120);
            disciplinaReference.document("DW1S5").set(dw1);*/

            Disciplina d = new Disciplina("LPVS2", "Linguagem de Programação Visual", 80);
            db.collection("DISCIPLINAS").document(d.getSigla()).set(d);
        });

        Button recuperaLpeButton = findViewById(R.id.button_recupera_lpe);
        recuperaLpeButton.setOnClickListener(view -> {
            DocumentReference umaDisciplinaReference = disciplinaReference.document("LPVS2");
            umaDisciplinaReference.get().addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        Log.v("DMO", "Disciplina: " + document.getData());
                    }else{
                        Log.v("DMO", "Disciplina não encontrada");
                    }
                }
            });
        });

        Button recuperaDmoButton = findViewById(R.id.button_recupera_dmo);
        recuperaDmoButton.setOnClickListener(view -> {
            DocumentReference umaDisciplinaReference = disciplinaReference.document("DMOS5");
            umaDisciplinaReference.get().addOnSuccessListener(documentSnapshot -> {
                Disciplina disciplina = documentSnapshot.toObject(Disciplina.class);
                Log.v("DMO", disciplina.toString());
            });
        });

        findViewById(R.id.button_consulta).setOnClickListener(view -> {
            disciplinaReference
                    .whereEqualTo("aulas", 80)
                    .get()
                    .addOnCompleteListener(task -> {
                       if(task.isSuccessful()){
                           Disciplina disciplina;
                           for (QueryDocumentSnapshot document : task.getResult()){
                                disciplina = document.toObject(Disciplina.class);
                                Log.v("DMO", disciplina.toString());
                           }
                       }
                    });

            Query query = disciplinaReference.whereGreaterThan("aulas", 120);
            query.get().addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Disciplina disciplina;
                    for (QueryDocumentSnapshot document : task.getResult()){
                        disciplina = document.toObject(Disciplina.class);
                        Log.v("DMO", disciplina.toString());
                    }
                }
            });

            query = disciplinaReference.orderBy("nome");
            query.get().addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Disciplina disciplina;
                    for (QueryDocumentSnapshot document : task.getResult()){
                        disciplina = document.toObject(Disciplina.class);
                        Log.v("DMO", disciplina.toString());
                    }
                }
            });
        });
    }
}