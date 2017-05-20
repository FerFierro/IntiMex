package mx.com.develop.intimex.Activitys;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import mx.com.develop.intimex.R;

public class Contactar extends AppCompatActivity implements View.OnClickListener {

    Button btn_redirigir_web,
            btn_llamar,
            btn_enviar_email;
    EditText et_asunto, et_Texto_correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactar);
        inicializarComponentesUI();
        inicializarSetOnClickListener();
    }

    private void inicializarComponentesUI() {
        btn_redirigir_web = (Button) findViewById(R.id.btn_redirigir_web);
        btn_llamar = (Button) findViewById(R.id.btn_llamar);
        btn_enviar_email = (Button) findViewById(R.id.btn_enviar_email);
        et_asunto = (EditText) findViewById(R.id.et_asunto);
        et_Texto_correo = (EditText) findViewById(R.id.et_Texto_correo);
    }

    private void inicializarSetOnClickListener() {
        btn_redirigir_web.setOnClickListener(this);
        btn_llamar.setOnClickListener(this);
        btn_enviar_email.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Boolean escampoVacioAsunto, escampoVacioTCorreo;
        Intent i;
        switch (v.getId()) {

            case R.id.btn_redirigir_web:
                //Abrir una ventana del navegador con una URL
                Uri webpage = Uri.parse("http://develop.com.mx/");
                i = new Intent(Intent.ACTION_VIEW, webpage);
                verificarActividadApropiada(i);
                break;

            case R.id.btn_llamar:
                //Realiza una llamada a un número telefonico.
                //Se necesita el permiso <uses-permission android:name="android.permission.CALL_PHONE"/> en AndroidManifest.xml
                i = new Intent(android.content.Intent.ACTION_CALL, Uri.parse("tel:(55) 53952791"));
                try {
                    verificarActividadApropiada(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.btn_enviar_email:
                //Mandar un correo
                String asunto = et_asunto.getText().toString().trim();
                String TextoDelcorreo = et_Texto_correo.getText().toString().trim();

                if (validaCampos(asunto, TextoDelcorreo) == true) {
                    String email = "certificaciones@develop.com.mx";
                    i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, asunto);
                    i.putExtra(Intent.EXTRA_TEXT, TextoDelcorreo);
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                    if (verificarActividadApropiada(i)) {
                        et_asunto.setText("");
                        et_Texto_correo.setText("");
                    } else {
                    }
                } else {
                }
                break;

        }
    }

    public boolean validaCampos(String asunto, String mensaje) {
        if (esVacio(asunto) == true) {
            et_asunto.setError("Campo vacío");
            return false;
        }

        if (esVacio(mensaje) == true) {
            et_Texto_correo.setError("Campo vacío");
            return false;
        }
        return true;
    }

    public Boolean esVacio(String texto) {
        if (texto.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verificarActividadApropiada(Intent intent) {
        // Verificamos que exista alguna actividad apropiada
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean isIntentSafe = activities.size() > 0;

        // Si es seguro, iniciamos la actividad
        if (isIntentSafe) {
            startActivity(Intent.createChooser(intent, "Abrir con :"));
            return true;
        } else {
            return false;
        }
    }

}
