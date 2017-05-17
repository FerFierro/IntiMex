package mx.com.develop.intimex.Activitys;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;
import mx.com.develop.intimex.R;

public class Buscador extends AppCompatActivity implements View.OnClickListener {

    Button btn_buscar_uri,
            btn_buscar_palabra;
    EditText et_url,
            et_buscar_palabra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);
        inicializarComponentesUI();
        inicializarSetOnClickListener();
    }

    private void inicializarComponentesUI() {
        btn_buscar_uri = (Button) findViewById(R.id.btn_buscar_uri);
        btn_buscar_palabra = (Button) findViewById(R.id.btn_buscar_palabra);
        et_url = (EditText) findViewById(R.id.et_url);
        et_buscar_palabra = (EditText) findViewById(R.id.et_buscar_palabra);
    }

    private void inicializarSetOnClickListener() {
        btn_buscar_uri.setOnClickListener(this);
        btn_buscar_palabra.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        Uri webpage;
        Boolean escampoVacio;
        switch (v.getId()) {

            case R.id.btn_buscar_uri:
                //Abrir una ventana del navegador con una URL
                String uri = et_url.getText().toString().trim();
                escampoVacio = esVacio(uri);
                if (!escampoVacio){
                    Boolean isValidUri = isUrl(uri);
                    if (isValidUri){
                        webpage = Uri.parse(uri);
                        i = new Intent(Intent.ACTION_VIEW, webpage);
                        verificarActividadApropiada(i);
                    }else{et_url.setError("Url incorrecto");}
                }else{ et_url.setError("Campo Vacío");}
                break;

            case R.id.btn_buscar_palabra:
                //Busca en el navegador la palabra especificada
                String buscarPalabra = et_buscar_palabra.getText().toString().trim();
                escampoVacio = esVacio( buscarPalabra);
                if (!escampoVacio){
                    webpage = Uri.parse("https://www.google.com.mx/?gfe_rd=cr&ei=_bYbWZ6wGurX8gfS-yk#safe=active&q=" + buscarPalabra);
                    i = new Intent(Intent.ACTION_VIEW, webpage);
                    verificarActividadApropiada(i);
                }else{
                    et_buscar_palabra.setError("Campo Vacío");
                }
                break;
        }
    }

    public Boolean esVacio( String texto) {
        if (texto.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public void verificarActividadApropiada(Intent intent){
        // Verificamos que exista alguna actividad apropiada
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean isIntentSafe = activities.size() > 0;

       // Si es seguro, iniciamos la actividad
        if (isIntentSafe) {
            startActivity(Intent.createChooser(intent, "Buscar con :"));
        }
    }

    private static boolean isUrl(String s) {
            return Patterns.WEB_URL.matcher(s).matches();
    }
}
