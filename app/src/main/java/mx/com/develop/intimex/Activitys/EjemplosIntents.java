package mx.com.develop.intimex.Activitys;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import mx.com.develop.intimex.R;

public class EjemplosIntents extends AppCompatActivity implements View.OnClickListener ,AdapterView.OnItemSelectedListener,SeekBar.OnSeekBarChangeListener {

    Button  btn_obtener_datos,
            btn_compartir_datos,
            btn_mostrar_geo;
    EditText et_compartir_datos;
    Spinner spinner_latitud,spinner_longitud,spinner_categoria;
    SeekBar seekbar_zoom;
    TextView tv_label_seekbar;
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
        tv_label_seekbar.setText(seekbar_zoom.getProgress()+"/"+seekbar_zoom.getMax());

    }


    private void inicializarComponentesUI() {
        btn_obtener_datos = (Button) findViewById(R.id.btn_obtener_datos);
        btn_compartir_datos = (Button) findViewById(R.id.btn_compartir_datos);
        btn_mostrar_geo = (Button) findViewById(R.id.btn_mostrar_geo);
        et_compartir_datos = (EditText) findViewById(R.id.et_compartir_datos);
        // Spinner element
        spinner_latitud = (Spinner) findViewById(R.id.spinner_latitud);
        spinner_longitud = (Spinner) findViewById(R.id.spinner_longitud);
        spinner_categoria = (Spinner) findViewById(R.id.spinner_categoria);
        seekbar_zoom = (SeekBar) findViewById(R.id.seekbar_zoom);
        tv_label_seekbar = (TextView) findViewById(R.id.tv_label_seekbar);


    }

    private void inicializarSetOnClickListener() {
        btn_obtener_datos.setOnClickListener(this);
        btn_compartir_datos.setOnClickListener(this);
        btn_mostrar_geo.setOnClickListener(this);
    }

    private void inicializarSetOnItemSelectedListener() {
        // Spinner click listener
        spinner_latitud.setOnItemSelectedListener(this);
        spinner_longitud.setOnItemSelectedListener(this);
        spinner_categoria.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i=null;
        switch (v.getId()) {

            case R.id.btn_obtener_datos:
                et_compartir_datos.setText(getObtenerDatos());
                break;

            case R.id.btn_compartir_datos:
                compartirDatos(i);
                break;

            case R.id.btn_mostrar_geo:
                String latitud=spinner_latitud.getSelectedItem().toString();
                String longitud=spinner_longitud.getSelectedItem().toString();
                String zoom=String.valueOf(seekbar_zoom.getProgress());
                String categoria=spinner_categoria.getSelectedItem().toString();

                if (validaCampos(latitud,longitud,categoria)){
                    String url=String.format("geo: %s,%s ?z=%s&q=%s",latitud,longitud,zoom,categoria);
                    Uri intentUri = Uri.parse(url);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, intentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");

                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(mapIntent);
                    }

                }else{}
                break;
        }
    }

    private boolean validaCampos(String latitud, String longitud,String categoria) {

        if (latitud.equals("Selecciona la Latitud")==true){
            Toast.makeText(this, "Selecciona la latitud", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (longitud.equals("Selecciona la Longitud")==true){
            Toast.makeText(this, "Selecciona la Longitud", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (categoria.equals("Selecciona la categoria")==true){
            Toast.makeText(this, "Selecciona la categoria", Toast.LENGTH_SHORT).show();
            return false;
        }
        return  true;
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
            verificarActividadApropiada(i,"Abrir con:");

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

    public boolean verificarActividadApropiada(Intent intent,String mensaje) {

        // Verificamos que exista alguna actividad apropiada
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean isIntentSafe = activities.size() > 0;

        // Si es seguro, iniciamos la actividad
        if (isIntentSafe) {
            startActivity(Intent.createChooser(intent,mensaje));
            return true;
        } else {
            return false;
        }
    }

    private String getObtenerDatos() {

        TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String numberSerial,deviceId,deviceSoftwareVersion,countryIso,operator,simOperator,datos;

        numberSerial=tMgr.getSimSerialNumber();
        deviceId=tMgr.getDeviceId();
        deviceSoftwareVersion=tMgr.getDeviceSoftwareVersion();
        countryIso=tMgr.getNetworkCountryIso();
        operator=tMgr.getNetworkOperator();
        simOperator=tMgr.getSimOperator();

        datos=String.format("Serial: %1s\n" +
                            "Id device: %2s\n" +
                            "Versión Software:%3s\n" +
                            "Iso Country:%4s\n" +
                            "Operador:%5s\n" +
                            "Sim Operador:%6s\n",
                             numberSerial,deviceId,deviceSoftwareVersion,countryIso,operator,simOperator);

        return datos;

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
        progressChangedValue=progress;
        tv_label_seekbar.setText(progressChangedValue+"/"+seekBar.getMax());
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        tv_label_seekbar.setText(progressChangedValue+"/"+seekBar.getMax());
    }
    //</editor-fold>

    //</editor-fold>
}
