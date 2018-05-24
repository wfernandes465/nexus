package mist.nexus.com.nexus.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import mist.nexus.com.nexus.modelo.Usuario;

public class DatabaseHelper extends SQLiteOpenHelper {
    //Versão do Database
    private static final int DATABASE_VERSAO = 1;

    //Nome do Database
    private static final String DATABASE_NOME = "UserManager.db";

    //Nome da Tabela do Usuário
    private static final String TABELA_USUARIO = "Usuario";

    //Nome das Colunas da Tabela do Usuario
    private static final String COLUMN_USUARIO_ID = "usuario_id";
    private static final String COLUMN_USUARIO_NOME = "usuario_nome";
    private static final String COLUMN_USUARIO_EMAIL = "usuario_email";
    private static final String COLUMN_USUARIO_SENHA = "usuario_senha";

    //Criando a Tabela SQL Query
    private String CREATE_TABELA_USUARIO = "CREATE TABLE " + TABELA_USUARIO + "("
            + COLUMN_USUARIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USUARIO_NOME + " TEXT,"
            + COLUMN_USUARIO_EMAIL + " TEXT," + COLUMN_USUARIO_SENHA + " TEXT" + ")";

    //Derrubando a Tabela SQL Query
    private String DROP_TABELA_USUARIO = "DROP TABLE IF EXISTS " + TABELA_USUARIO;

    /**
     * Método Construtor
     *
     * @param context
     */
    public DatabaseHelper(Context context){
        super(context, DATABASE_NOME, null, DATABASE_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABELA_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Derrubando a tabela se ela existir
        db.execSQL(DROP_TABELA_USUARIO);

        //Criando a Tabela Novamente
        onCreate(db);
    }

    /**
     * Este método serve pra criar um novo usuario.
     *
     * @param usuario
     */
    public void addUsuario(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USUARIO_NOME, usuario.getNome());
        values.put(COLUMN_USUARIO_EMAIL, usuario.getEmail());
        values.put(COLUMN_USUARIO_SENHA, usuario.getSenha());

        // Inserting Row
        db.insert(TABELA_USUARIO, null, values);
        db.close();
    }

    /**
     * Esse método serve pra trazer os usuarios já cadastrados.
     *
     * @return lista
     */
    public List<Usuario> getAllUsuarios() {
        // Fazer a busca da matriz de colunas
        String[] columns = {
                COLUMN_USUARIO_ID,
                COLUMN_USUARIO_EMAIL,
                COLUMN_USUARIO_NOME,
                COLUMN_USUARIO_SENHA
        };
        // Ordem de Classificação
        String sortOrder = COLUMN_USUARIO_NOME + " ASC";
        List<Usuario> listaUsuario = new ArrayList<Usuario>();

        SQLiteDatabase db = this.getReadableDatabase();

        // consulta a tabela de usuário
        /**
         * Aqui a função de consulta é usada para buscar registros da tabela usuario.
         * esta função funciona quando nós usamos uma consulta sql, e funciona da seguinte forma
         * SELECT usuario_id,usuario_nome,usuario_email,usuario_senha FROM usuario ORDER BY usuario_nome;
         */
        Cursor cursor = db.query(TABELA_USUARIO, //Tabela de Consulta
                columns,    //retornar para as colunas
                null,        //colunas para a cláusula WHERE
                null,        //Os valores para a cláusula WHERE
                null,       //agrupar as linhas
                null,       //Filtrar por grupos de linhas
                sortOrder); //A ordem de classificação


        // Aqui está percorrendo todas as linhas e depois adicionando na lista.
        if (cursor.moveToFirst()) {
            do {
                Usuario usuario = new Usuario();
                usuario.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USUARIO_ID))));
                usuario.setNome(cursor.getString(cursor.getColumnIndex(COLUMN_USUARIO_NOME)));
                usuario.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USUARIO_EMAIL)));
                usuario.setSenha(cursor.getString(cursor.getColumnIndex(COLUMN_USUARIO_SENHA)));
                // Adicionando o usuario na lista
                listaUsuario.add(usuario);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // retorna a lista de usuario
        return listaUsuario;
    }

    /**
     * Esse método vai fazer a atualização das informações do usuario.
     *
     * @param usuario
     */
    public void atualizarUsuario(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USUARIO_NOME, usuario.getNome());
        values.put(COLUMN_USUARIO_EMAIL, usuario.getEmail());
        values.put(COLUMN_USUARIO_SENHA, usuario.getSenha());

        // Atualizando a linha
        db.update(TABELA_USUARIO, values, COLUMN_USUARIO_ID + " = ?",
                new String[]{String.valueOf(usuario.getId())});
        db.close();
    }

    /**
     * Esse método serve pra excluir um usuario.
     *
     * @param usuario
     */
    public void deletarUsuario(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        // excluindo o usuario pelo id.
        db.delete(TABELA_USUARIO, COLUMN_USUARIO_ID + " = ?",
                new String[]{String.valueOf(usuario.getId())});
        db.close();
    }

    /**
     * Esse método verifica se o usuario existe, e verifica se o email informado possui uma conta.
     *
     * @param email
     * @return true/false
     */
    public boolean verificarUsuario(String email) {

        // Buscar matriz de colunas
        String[] columns = {
                COLUMN_USUARIO_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // Critério de seleção
        String selection = COLUMN_USUARIO_EMAIL + " = ?";

        // Selecionando os argumentos
        String[] selectionArgs = {email};

        //consulta a tabela de usuario com condição
        /**
         * Aqui a função de consulta é usada para buscar registros da tabela usuario.
         * esta função funciona quando nós usamos uma consulta sql, e funciona da seguinte forma
         * SELECT usuario_id FROM usurio WHERE usuario_email = 'lucas@teste.com';
         */
        Cursor cursor = db.query(TABELA_USUARIO, //Tabela de Consulta
                columns,                    //retornar para as colunas
                selection,                  //colunas para a cláusula WHERE
                selectionArgs,              //Os valores para a cláusula WHERE
                null,              //agrupar as linhas
                null,               //Filtrar por grupos de linhas
                null);             //A ordem de classificação
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    /**
     * Este método verifica se o usuario existe, se existe, ele verifica se o email e a senha bate com
     * o que está salvo.
     *
     * @param email
     * @param senha
     * @return true/false
     */

    public boolean verificarUsuario(String email, String senha) {

        // Buscar matriz de colunas
        String[] columns = {
                COLUMN_USUARIO_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // Criterio de Seleção
        String selection = COLUMN_USUARIO_EMAIL + " = ?" + " AND " + COLUMN_USUARIO_SENHA + " = ?";

        // Selecionando os argumentos
        String[] selectionArgs = {email, senha};

        // query user table with conditions
        /**
         * Aqui a função de consulta é usada para buscar registros da tabela usuario.
         * esta função funciona quando nós usamos uma consulta sql, e funciona da seguinte forma
         * SELECT usuario_id FROM usuario WHERE usuario_email = 'lucas@teste.com' AND usuario_senha = 'teste';
         */
        Cursor cursor = db.query(TABELA_USUARIO, //Tabela de Consulta
                columns,                    //Retorna para as colunas
                selection,                  //colunas para a cláusula WHERE
                selectionArgs,              //Os valores para a cláusula WHERE
                null,              //agrupar as linhas
                null,               //Filtrar por grupos de linhas
                null);             //A ordem de classificação

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }
}
