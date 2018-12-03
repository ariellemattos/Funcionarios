package br.com.senaijandira.funcionarios;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.senaijandira.funcionarios.db.AppDatabase;
import br.com.senaijandira.funcionarios.model.Cargo;
import br.com.senaijandira.funcionarios.model.Funcionario;
import br.com.senaijandira.funcionarios.viewmodel.FuncionarioCargo;

public class MainActivity extends AppCompatActivity {

    ListView lstViewFuncionarios;
    FuncionariosAdapter adapter;
    AppDatabase appDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appDB = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "funcionario.db").fallbackToDestructiveMigration().allowMainThreadQueries().build();

        lstViewFuncionarios = findViewById(R.id.lstViewFuncionarios);
        adapter = new FuncionariosAdapter(this);
        lstViewFuncionarios.setAdapter(adapter);

    }

    public void carregarFuncionarios(View V){
       FuncionarioCargo[] funcionarios = appDB.daoFuncionario().selecionarFuncionarioCargo();
       adapter.addAll(funcionarios);

    }

    public void inserirDados(View v){

        //Cargos
       long idAnalista = appDB.daoCargo().inserir( new Cargo("Analista"));
       long idGer = appDB.daoCargo().inserir( new Cargo("Gerente"));


        //Funcionairios
        Funcionario f1 = new Funcionario("Joesley", (int)idAnalista);

        Funcionario f2 = new Funcionario("Steve", (int)idGer);

        Funcionario f3 = new Funcionario("Bill", (int)idAnalista);

        Funcionario f4 = new Funcionario("João", (int)idGer);

        appDB.daoFuncionario().inserir(f1);
        appDB.daoFuncionario().inserir(f2);
        appDB.daoFuncionario().inserir(f3);
        appDB.daoFuncionario().inserir(f4);


    }

    private class FuncionariosAdapter extends ArrayAdapter<FuncionarioCargo>{
        public FuncionariosAdapter(Context ctx){
            super(ctx, 0, new ArrayList<FuncionarioCargo>());

        }

        @NonNull
        @Override
        public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent){
            View v = convertView;
           if(v == null){
               v= LayoutInflater.from(getContext()).inflate(R.layout.list_item_funcionario,parent, false);
           }

           FuncionarioCargo func = getItem(position);

            TextView txtNomeFunc = v.findViewById(R.id.txtNomeFuncionario);
            TextView txtCargo = v.findViewById(R.id.txtTituloCargo);

            txtNomeFunc.setText(func.getFuncionario().getNome());
            txtCargo.setText(func.getCargo());

            return v;
        }
    }
}
