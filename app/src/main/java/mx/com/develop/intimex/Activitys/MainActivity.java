package mx.com.develop.intimex.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import mx.com.develop.intimex.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button  btn_registrar_producto,
            btn_buscador,
            btn_contactar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarComponentesUI();
        inicializarSetOnClickListener();
    }


    private void inicializarComponentesUI() {
        btn_registrar_producto=(Button) findViewById(R.id.btn_registrar_producto);
        btn_buscador=(Button) findViewById(R.id.btn_buscador);
        btn_contactar=(Button) findViewById(R.id.btn_contactar);
    }

    private void inicializarSetOnClickListener() {
        btn_registrar_producto.setOnClickListener(this);
        btn_buscador.setOnClickListener(this);
        btn_contactar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){

            case R.id.btn_registrar_producto:
                i=new Intent(this,RegistrarProducto.class);
                startActivity(i);
                break;

            case R.id.btn_buscador:
                i=new Intent(this,Buscador.class);
                startActivity(i);
                break;

            case R.id.btn_contactar:
                i=new Intent(this,Contactar.class);
                startActivity(i);
                break;
        }
    }
}
