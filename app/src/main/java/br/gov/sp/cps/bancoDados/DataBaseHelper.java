package br.gov.sp.cps.bancoDados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    //Constantes que representam dados e tabela
    public static final String DATABASE_NAME = "dados.db";
    public static final String TABLE_NAME = "pessoas";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NOME";
    public static final String COL_3 = "IDADE";

    //Construtor da classe que chma super classe
    public DataBaseHelper (Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    //Metodo que chama ao criar o DB
    // Cria tabela pessoas
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NOME TEXT, IDADE INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void  onUpgrade(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    //Metodos

    //Metodo gravar
    public boolean inserirDados(String nome, int idade) {
        SQLiteDatabase db = this.getWritableDatabase();

        //armazena os valores a serem inseridos
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, nome);
        contentValues.put(COL_3, idade);

        //Insere os dados e ar,azena o resultado
        long resultado = db.insert(TABLE_NAME, null, contentValues);

        //fecha o db
        db.close();

        //Retorna true se a inserção for bem sucedida
        return resultado != -1;
    }


    // metodo consultar
    public Cursor obterIdadePorNome(String nome){
        //db no modo leitura
        SQLiteDatabase db = this.getReadableDatabase();

        //Especificar coluna idade
        String[] columns = {COL_3};
        String selection  = COL_2 + " = ?";
        String[] selectionArgs = {nome};

        return db.query(TABLE_NAME,
                columns, selection, selectionArgs, null, null, null
        );
    }


    //Medoto Atualizar
    public boolean atualizarDados(String Nome, int novaIdade){
        SQLiteDatabase db = this.getWritableDatabase();

        //Armazena novo valor para idade
    ContentValues valores = new ContentValues();
    valores.put(COL_3, novaIdade);

    //Atualiza a tablea
    int linhasAfetas = db.update(
            TABLE_NAME, valores, COL_2
            + "=?",
            new String[]{}
    );

    //Fecha DB
    db.close();
    //Retorna true se uma linha for afeta
    return  linhasAfetas > 0;

    }


    //Metodo deletar
    public boolean deletarDados(String nome){
        SQLiteDatabase db = this.getWritableDatabase();

        //Deleta o registro pelo nome
        int linhasAfetas =db.delete(
                TABLE_NAME, COL_2 + "= ?", new String[]{nome}
        );

        db.close();

        return linhasAfetas > 0;
    }


}
