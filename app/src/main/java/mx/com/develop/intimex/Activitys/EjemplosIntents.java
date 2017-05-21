package mx.com.develop.intimex.Activitys;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.AlarmClock;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.List;

import mx.com.develop.intimex.R;

public class EjemplosIntents extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, SeekBar.OnSeekBarChangeListener {

    Button btn_obtener_datos,
            btn_compartir_datos,
            btn_mostrar_geo,
            btn_mostrar_contactos,
            btn_interfaz_llamada,
            btn_crear_alarma,
            btn_crear_temporizador;
    EditText et_compartir_datos,
            et_etiqueta,
            et_segundos,
            et_etiqueta_segundos;
    Spinner spinner_latitud,
            spinner_longitud,
            spinner_categoria;
    SeekBar seekbar_zoom;
    TextView tv_label_seekbar;
    TimePicker timepiker_alarma;
    CheckBox checkbox_vibrar;
    int progressChangedValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejemplos_intents);

        inicializarComponentesUI();
        inicializarSetOnClickListener();
        inicializarSetOnItemSelectedListener();
        inicializaronSeekBarChangeListener();

    }

    private void inicializaronSeekBarChangeListener() {
        seekbar_zoom.setOnSeekBarChangeListener(this);
        tv_label_seekbar.setText(seekbar_zoom.getProgress() + "/" + seekbar_zoom.getMax());

    }


    private void inicializarComponentesUI() {
        //Botones
        btn_obtener_datos = (Button) findViewById(R.id.btn_obtener_datos);
        btn_compartir_datos = (Button) findViewById(R.id.btn_compartir_datos);
        btn_mostrar_geo = (Button) findViewById(R.id.btn_mostrar_geo);
        btn_mostrar_contactos = (Button) findViewById(R.id.btn_mostrar_contactos);
        btn_interfaz_llamada = (Button) findViewById(R.id.btn_interfaz_llamada);
        btn_crear_alarma = (Button) findViewById(R.id.btn_crear_alarma);
        btn_crear_temporizador = (Button) findViewById(R.id.btn_crear_temporizador);
        // Spinner
        spinner_latitud = (Spinner) findViewById(R.id.spinner_latitud);
        spinner_longitud = (Spinner) findViewById(R.id.spinner_longitud);
        spinner_categoria = (Spinner) findViewById(R.id.spinner_categoria);
        //seekBars
        seekbar_zoom = (SeekBar) findViewById(R.id.seekbar_zoom);
        //TextViews
        tv_label_seekbar = (TextView) findViewById(R.id.tv_label_seekbar);
        //Editexts
        et_compartir_datos = (EditText) findViewById(R.id.et_compartir_datos);
        et_etiqueta = (EditText) findViewById(R.id.et_etiqueta);
        et_segundos = (EditText) findViewById(R.id.et_segundos);
        et_etiqueta_segundos = (EditText) findViewById(R.id.et_etiqueta_segundos);
        //TimePicker
        timepiker_alarma = (TimePicker) findViewById(R.id.timepiker_alarma);
        //CheckBox
        checkbox_vibrar = (CheckBox) findViewById(R.id.checkbox_vibrar);

    }

    private void inicializarSetOnClickListener() {
        btn_obtener_datos.setOnClickListener(this);
        btn_compartir_datos.setOnClickListener(this);
        btn_mostrar_geo.setOnClickListener(this);
        btn_mostrar_contactos.setOnClickListener(this);
        btn_interfaz_llamada.setOnClickListener(this);
        btn_crear_alarma.setOnClickListener(this);
        btn_crear_temporizador.setOnClickListener(this);

    }

    private void inicializarSetOnItemSelectedListener() {
        // Spinner click listener
        spinner_latitud.setOnItemSelectedListener(this);
        spinner_longitud.setOnItemSelectedListener(this);
        spinner_categoria.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = null;
        switch (v.getId()) {

            case R.id.btn_obtener_datos:
                et_compartir_datos.setText(getObtenerDatos());
                break;

            case R.id.btn_compartir_datos:
                compartirDatos(i);
                break;

            case R.id.btn_mostrar_contactos:
                mostrarContactos(i);
                break;

            case R.id.btn_mostrar_geo:
                mostrarGeolocalizacion(i);
                break;

            case R.id.btn_interfaz_llamada:
                confirmarLlamada(i);
                break;

            case R.id.btn_crear_alarma:
                crearAlarma(i);
                break;

            case R.id.btn_crear_temporizador:
                crearTemporizador(i);
                break;
        }
    }

    private void crearTemporizador(Intent i) {
        String seconds=et_segundos.getText().toString().trim();
        String message=et_etiqueta_segundos.getText().toString().trim();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            if (validaCamposTemporizador(et_segundos,et_etiqueta_segundos,seconds,message)==true){
                i = new Intent(AlarmClock.ACTION_SET_TIMER)
                        .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                        .putExtra(AlarmClock.EXTRA_LENGTH, seconds)
                        .putExtra(AlarmClock.EXTRA_SKIP_UI, true);
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivity(i);
                }
            }
        }else{
            Toast.makeText(this, "En este dispositivo no se puede mostrar este ejemplo, se requiere android KITKAT para mostrar el ejemplo", Toast.LENGTH_SHORT).show();
        }

    }


    private void crearAlarma(Intent i) {
        /*Nota:
                Para invocar la intent ACTION_SET_ALARM, tu app debe tener el permiso SET_ALARM:
                <uses-permission android:name="com.android.alarm.permission.SET_ALARM" /> */

        String message = et_etiqueta.getText().toString().trim();
        int hour = 0;
        int minutes = 0;
        Boolean vibrar=checkbox_vibrar.isChecked();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            hour = timepiker_alarma.getHour();
            minutes = timepiker_alarma.getMinute();
        } else {
            hour = timepiker_alarma.getCurrentHour();
            minutes = timepiker_alarma.getCurrentMinute();
        }
        if (!esVacio(message)){
            i = new Intent(AlarmClock.ACTION_SET_ALARM)
                    .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                    .putExtra(AlarmClock.EXTRA_HOUR, hour)
                    .putExtra(AlarmClock.EXTRA_MINUTES, minutes)
                    .putExtra(AlarmClock.EXTRA_VIBRATE,vibrar);

            if (i.resolveActivity(getPackageManager()) != null) {
                startActivity(i);
            }
        }else{
            et_etiqueta.setError("Campo Vacío");
        }

    }

    private void confirmarLlamada(Intent i) {
        i = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:53952791"));
        verificarActividadApropiada(i, "Abrir con:");
    }

    private void mostrarContactos(Intent i) {
        //Primera forma larga
        i = new Intent();
        i.setAction(android.content.Intent.ACTION_VIEW);
        i.setData(ContactsContract.Contacts.CONTENT_URI);
        verificarActividadApropiada(i, "Abrir con:");

        //Segunda forma larga
        //“content://contacts..” para realizar la operación de visualización de contactos del directorio
        //startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("content://contacts/people/")));
    }


    private boolean validaCampos(String latitud, String longitud, String categoria) {

        if (latitud.equals("Selecciona la Latitud") == true) {
            Toast.makeText(this, "Selecciona la latitud", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (longitud.equals("Selecciona la Longitud") == true) {
            Toast.makeText(this, "Selecciona la Longitud", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (categoria.equals("Selecciona la categoria") == true) {
            Toast.makeText(this, "Selecciona la categoria", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validaCamposTemporizador(EditText et1,EditText et2, String seconds,String message) {

        if (esVacio(seconds) == true) {
            et1.setError("Campo Vacio");
            return false;
        }

        if (esVacio(message) == true) {
            et2.setError("Campo Vacio");
            return false;
        }

        return true;
    }

    private void compartirDatos(Intent i) {

        String numeroTelefonico = et_compartir_datos.getText().toString().trim();
        if (!(esVacio(numeroTelefonico))) {
            // Compartir  a través de las redes sociales disponibles en el móvil.
            String textoParaPonerEnLaRedSocial = "Mi datos del móvil";
            i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, textoParaPonerEnLaRedSocial);
            i.putExtra(Intent.EXTRA_TEXT, numeroTelefonico);
            verificarActividadApropiada(i, "Abrir con:");

        } else {
            Toast.makeText(this, "Obten tus datos del móvi primero", Toast.LENGTH_SHORT).show();
        }
    }

    public Boolean esVacio(String texto) {
        if (texto.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verificarActividadApropiada(Intent intent, String mensaje) {

        // Verificamos que exista alguna actividad apropiada
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean isIntentSafe = activities.size() > 0;

        // Si es seguro, iniciamos la actividad
        if (isIntentSafe) {
            startActivity(Intent.createChooser(intent, mensaje));
            return true;
        } else {
            return false;
        }
    }

    private String getObtenerDatos() {

        TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String numberSerial, deviceId, deviceSoftwareVersion, countryIso, operator, simOperator, datos;

        numberSerial = tMgr.getSimSerialNumber();
        deviceId = tMgr.getDeviceId();
        deviceSoftwareVersion = tMgr.getDeviceSoftwareVersion();
        countryIso = tMgr.getNetworkCountryIso();
        operator = tMgr.getNetworkOperator();
        simOperator = tMgr.getSimOperator();

        datos = String.format("Serial: %1s\n" +
                        "Id device: %2s\n" +
                        "Versión Software:%3s\n" +
                        "Iso Country:%4s\n" +
                        "Operador:%5s\n" +
                        "Sim Operador:%6s\n",
                numberSerial, deviceId, deviceSoftwareVersion, countryIso, operator, simOperator);

        return datos;

    }

    private void mostrarGeolocalizacion(Intent i) {

        String latitud = spinner_latitud.getSelectedItem().toString();
        String longitud = spinner_longitud.getSelectedItem().toString();
        String zoom = String.valueOf(seekbar_zoom.getProgress());
        String categoria = spinner_categoria.getSelectedItem().toString();

        if (validaCampos(latitud, longitud, categoria)) {
            String url = String.format("geo: %s,%s ?z=%s&q=%s", latitud, longitud, zoom, categoria);
            Uri intentUri = Uri.parse(url);
            i = new Intent(Intent.ACTION_VIEW, intentUri);
            i.setPackage("com.google.android.apps.maps");

            if (i.resolveActivity(getPackageManager()) != null) {
                startActivity(i);
            }

        } else {
        }
    }


    //<editor-fold desc="Métodos de AdapterView.OnItemSelectedListener para el Spinner">
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    //<editor-fold desc="Métodos de SeekBar.OnSeekBarChangeListener">
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        progressChangedValue = progress;
        tv_label_seekbar.setText(progressChangedValue + "/" + seekBar.getMax());
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        tv_label_seekbar.setText(progressChangedValue + "/" + seekBar.getMax());
    }
    //</editor-fold>

    //</editor-fold>
}
