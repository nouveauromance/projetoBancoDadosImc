package br.gov.sp.cps.bancoDados;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    TextView textResultado;
    Button btnConsultar, btnDeletar, btnAtualizar, btnGravar;

    DataBaseHelper dbHelper;

    TextInputEditText textNome, textIdade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        dbHelper = new DataBaseHelper(this);

        textResultado = findViewById(R.id.textResultado);
        btnDeletar = findViewById(R.id.btnDeletar);
        btnAtualizar = findViewById(R.id.btnAtualizar);
        btnConsultar = findViewById(R.id.btnConsultar);
        btnGravar = findViewById(R.id.btnGravar);
        textNome = findViewById(R.id.textNome);
        textIdade = findViewById(R.id.textIdade);


        btnGravar.setOnClickListener( v -> {

            //Entrada de dados
            String nome = textNome.getText().toString();
            String idade = textIdade.getText().toString();

            //Validação
            if(nome.isEmpty() || idade.isEmpty()){
                Toast.makeText( MainActivity.this, "Preencha os dados",
                        Toast.LENGTH_LONG).show();
                return;
            }

            //Caso seja bem sucedido(apresentar a idade nome)
            try {
                //Converte idade para numérico
                int numIdade = Integer.parseInt(idade);

                //Chama db e metodo inserir dados
                if (dbHelper.inserirDados(nome, numIdade)){
                    //caso a gravação seja realizada com sucesso
                    Toast.makeText( MainActivity.this, "Dados inserido com sucesso",
                            Toast.LENGTH_LONG).show();
                } else {
                    //caso a gravação não tenha sido realizada com sucesso
                    Toast.makeText( MainActivity.this, "Erro ao inserir dados",
                            Toast.LENGTH_LONG).show();
                }

            } catch (NumberFormatException e){
                Toast.makeText(MainActivity.this,
                        "Erro ao inserir dados 2",
                        Toast.LENGTH_LONG).show();
            }
        });

        btnConsultar.setOnClickListener( v -> {

            String nome = textNome.getText().toString();

            if (nome.isEmpty()){
                Toast.makeText(MainActivity.this, "Preencher",
                        Toast.LENGTH_LONG).show();
            }

            Cursor cursor = dbHelper.obterIdadePorNome(nome);
            if(cursor != null && cursor.moveToFirst()){
                String idade = cursor.getString(0);
                textResultado.setText("Idade:" + idade);
            } else {
                Toast.makeText(MainActivity.this, "Nome não encontrado",
                        Toast.LENGTH_LONG).show();
            }

        });

        btnAtualizar.setOnClickListener( v -> {
            String nome = textNome.getText().toString();
            String idade = textIdade.getText().toString();

            //Validaçaõ de dados
            if (nome.isEmpty() || idade.isEmpty()){
                Toast.makeText(MainActivity.this, "Preencher todos os dados",
                        Toast.LENGTH_LONG).show();
                return;
            }

            try {
                int numIdade = Integer.parseInt(idade);
                if(dbHelper.atualizarDados(nome,numIdade)){
                    Toast.makeText(MainActivity.this, "Dados atualizados com sucesso!",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Nome não encontrado",
                            Toast.LENGTH_LONG).show();
                }
            } catch (NumberFormatException e){
                Toast.makeText(MainActivity.this, "Idade inválida",
                        Toast.LENGTH_LONG).show();
            }
        });

        btnDeletar.setOnClickListener( v -> {

            String nome = textNome.getText().toString();

            if (nome.isEmpty()){
                Toast.makeText( MainActivity.this, "Digite seu nome",
                        Toast.LENGTH_LONG).show();
                return;
            }

            if (dbHelper.deletarDados(nome)){
                Toast.makeText( MainActivity.this, "Dados deletados com sucesso",
                        Toast.LENGTH_LONG).show();
                return;
            } else {
                Toast.makeText( MainActivity.this, "Nome não encontrado",
                        Toast.LENGTH_LONG).show();
            }

        });


    }




}