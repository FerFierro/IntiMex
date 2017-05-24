package mx.com.develop.intimex.Activitys;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
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
            btn_crear_temporizador,
            btn_mostrar_alarmas,
            btn_agregar_evento,
            btn_imagen_detenida,
            btn_modo_video,
            btn_abrir_configuracion,
            btn_insertar_contacto;
    EditText et_etiqueta,
            et_segundos,
            et_etiqueta_segundos;
    Spinner spinner_latitud,
            spinner_longitud,
            spinner_categoria,
            spinner_configuracion;
    SeekBar seekbar_zoom;
    TextView tv_label_seekbar,tv_compartir_datos;
    TimePicker timepiker_alarma;
    CheckBox checkbox_vibrar;
    CalendarView calendar_end,calendar_begin;
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
        btn_mostrar_alarmas = (Button) findViewById(R.id.btn_mostrar_alarmas);
        btn_agregar_evento = (Button) findViewById(R.id.btn_agregar_evento);
        btn_imagen_detenida = (Button) findViewById(R.id.btn_imagen_detenida);
        btn_modo_video = (Button) findViewById(R.id.btn_modo_video);
        btn_abrir_configuracion = (Button) findViewById(R.id.btn_abrir_configuracion);
        btn_insertar_contacto = (Button) findViewById(R.id.btn_insertar_contacto);

        // Spinner
        spinner_latitud = (Spinner) findViewById(R.id.spinner_latitud);
        spinner_longitud = (Spinner) findViewById(R.id.spinner_longitud);
        spinner_categoria = (Spinner) findViewById(R.id.spinner_categoria);
        spinner_configuracion = (Spinner) findViewById(R.id.spinner_configuracion);

        //seekBars
        seekbar_zoom = (SeekBar) findViewById(R.id.seekbar_zoom);
        //TextViews
        tv_label_seekbar = (TextView) findViewById(R.id.tv_label_seekbar);
        tv_compartir_datos = (TextView) findViewById(R.id.et_compartir_datos);
        //Editexts
        et_etiqueta = (EditText) findViewById(R.id.et_etiqueta);
        et_segundos = (EditText) findViewById(R.id.et_segundos);
        et_etiqueta_segundos = (EditText) findViewById(R.id.et_etiqueta_segundos);
        //TimePicker
        timepiker_alarma = (TimePicker) findViewById(R.id.timepiker_alarma);
        //CheckBox
        checkbox_vibrar = (CheckBox) findViewById(R.id.checkbox_vibrar);
        //CalendarView
        calendar_begin = (CalendarView) findViewById(R.id.calendar_begin);
        calendar_end = (CalendarView) findViewById(R.id.calendar_end);


    }

    private void inicializarSetOnClickListener() {
        btn_obtener_datos.setOnClickListener(this);
        btn_compartir_datos.setOnClickListener(this);
        btn_mostrar_geo.setOnClickListener(this);
        btn_mostrar_contactos.setOnClickListener(this);
        btn_interfaz_llamada.setOnClickListener(this);
        btn_crear_alarma.setOnClickListener(this);
        btn_crear_temporizador.setOnClickListener(this);
        btn_mostrar_alarmas.setOnClickListener(this);
        btn_agregar_evento.setOnClickListener(this);
        btn_imagen_detenida.setOnClickListener(this);
        btn_modo_video.setOnClickListener(this);
        btn_abrir_configuracion.setOnClickListener(this);
        btn_insertar_contacto.setOnClickListener(this);

    }

    private void inicializarSetOnItemSelectedListener() {
        // Spinner click listener
        spinner_latitud.setOnItemSelectedListener(this);
        spinner_longitud.setOnItemSelectedListener(this);
        spinner_categoria.setOnItemSelectedListener(this);
        spinner_configuracion.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {

            case R.id.btn_obtener_datos:
                tv_compartir_datos.setText(getObtenerDatos());
                break;

            case R.id.btn_compartir_datos:
                i = new Intent();
                compartirDatos(i);
                break;

            case R.id.btn_mostrar_contactos:
                i = new Intent();
                mostrarContactos(i);
                break;

            case R.id.btn_mostrar_geo:
                i = new Intent();
                mostrarGeolocalizacion(i);
                break;

            case R.id.btn_interfaz_llamada:
                i = new Intent();
                confirmarLlamada(i);
                break;

            case R.id.btn_crear_alarma:
                i = new Intent();
                crearAlarma(i);
                break;

            case R.id.btn_crear_temporizador:
                i = new Intent();
                crearTemporizador(i);
                break;

            case R.id.btn_mostrar_alarmas:
                i = new Intent();
                mostrarAlarmas(i);
                break;

            case R.id.btn_agregar_evento:
                i = new Intent();
                agregarEventoCalendario(i);
                break;

            case R.id.btn_imagen_detenida:
                i = new Intent();
                mostrarImagenDetenida(i);
                break;

            case R.id.btn_modo_video:
                i = new Intent();
                mostrarModoVideo(i);
                break;

            case R.id.btn_abrir_configuracion:
                i = new Intent();
                abrirConfiguracion(i);
                break;
            case R.id.btn_insertar_contacto:
                i = new Intent();
                insertarContacto(i);
                break;
        }
    }

    private void insertarContacto(Intent i) {
        i.setAction(Intent.ACTION_INSERT);
        i.setType(ContactsContract.Contacts.CONTENT_TYPE);
        i.putExtra(ContactsContract.Intents.Insert.NAME, "Jose Angel Fierro");
        i.putExtra(ContactsContract.Intents.Insert.PHONE, "5518845418");
        i.putExtra(ContactsContract.Intents.Insert.POSTAL, "56619");
        i.putExtra(ContactsContract.Intents.Insert.COMPANY, "Develop Talent & Technology");
        i.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, "Instructor for Android");
        i.putExtra(ContactsContract.Intents.Insert.EMAIL,"ing_angelfierro@hotmail.com");
        if (i.resolveActivity(getPackageManager()) != null) {
            startActivity(i);
        }
    }

    private void abrirConfiguracion(Intent i) {
        String configuracion = spinner_configuracion.getSelectedItem().toString();
        if (configuracion.equals("Selecciona la configuración")){
            Toast.makeText(this, "Selecciona la configuraion para abrir", Toast.LENGTH_SHORT).show();
        }else{
            String accion;
            switch (configuracion){
                case"ACTION_ACCESSIBILITY_SETTINGS":
                    //Accesibilidad
                    accion=Settings.ACTION_ACCESSIBILITY_SETTINGS;
                    iniciarAccionConfig(accion,i);
                    break;
                case"ACTION_AIRPLANE_MODE_SETTINGS":
                    //Redes inalámbricas y redes
                    accion=Settings.ACTION_AIRPLANE_MODE_SETTINGS;
                    iniciarAccionConfig(accion,i);
                    break;
                case"ACTION_APN_SETTINGS":
                    //APN
                    accion=Settings.ACTION_APN_SETTINGS;
                    iniciarAccionConfig(accion,i);
                    break;
                case"ACTION_APPLICATION_DEVELOPMENT_SETTINGS":
                    //Opciones del desarrollador
                    accion=Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS;
                    iniciarAccionConfig(accion,i);
                    break;
                case"ACTION_BLUETOOTH_SETTINGS":
                    //Bluetooth
                    accion=Settings.ACTION_BLUETOOTH_SETTINGS;
                    iniciarAccionConfig(accion,i);
                    break;
                case"ACTION_APPLICATION_SETTINGS":
                    //Aplicaciones
                    accion=Settings.ACTION_APPLICATION_SETTINGS;
                    iniciarAccionConfig(accion,i);
                    break;
                case"ACTION_LOCALE_SETTINGS":
                    //Idioma
                    accion=Settings.ACTION_LOCALE_SETTINGS;
                    iniciarAccionConfig(accion,i);
                    break;
                case"ACTION_INPUT_METHOD_SETTINGS":
                    //Teclado e Idionma
                    accion=Settings.ACTION_INPUT_METHOD_SETTINGS;
                    iniciarAccionConfig(accion,i);
                    break;
                case"ACTION_DISPLAY_SETTINGS":
                    //Pantalla
                    accion=Settings.ACTION_DISPLAY_SETTINGS;
                    iniciarAccionConfig(accion,i);
                    break;
                case"ACTION_SECURITY_SETTINGS":
                    //Seguridad
                    accion=Settings.ACTION_SECURITY_SETTINGS;
                    iniciarAccionConfig(accion,i);
                    break;
                case"ACTION_LOCATION_SOURCE_SETTINGS":
                    //Acceso a la ubicación
                    accion=Settings.ACTION_LOCATION_SOURCE_SETTINGS;
                    iniciarAccionConfig(accion,i);
                    break;
                case"ACTION_INTERNAL_STORAGE_SETTINGS":
                    //Configuración de almacenamiento
                    accion=Settings.ACTION_INTERNAL_STORAGE_SETTINGS;
                    iniciarAccionConfig(accion,i);
                    break;
                case"ACTION_MEMORY_CARD_SETTINGS":
                    //Configuración de almacenamiento
                    accion=Settings.ACTION_MEMORY_CARD_SETTINGS;
                    iniciarAccionConfig(accion,i);
                    break;

                case "ACTION_DATA_ROAMING_SETTINGS":
                    //Configuración de red móvil
                    accion=Settings.ACTION_DATA_ROAMING_SETTINGS;
                    iniciarAccionConfig(accion,i);
                    break;
                case "ACTION_DATE_SETTINGS":
                    //Configuración de fecha y hora
                    accion=Settings.ACTION_DATE_SETTINGS;
                    iniciarAccionConfig(accion,i);
                    break;
                case "ACTION_DEVICE_INFO_SETTINGS":
                    //Estado del dispositivo
                    accion=Settings.ACTION_DEVICE_INFO_SETTINGS;
                    iniciarAccionConfig(accion,i);
                    break;
                case "ACTION_INPUT_METHOD_SUBTYPE_SETTINGS":
                    //Teclado de android (AOSP)
                    accion=Settings.ACTION_INPUT_METHOD_SUBTYPE_SETTINGS;
                    iniciarAccionConfig(accion,i);
                    break;
                case "ACTION_MANAGE_APPLICATIONS_SETTINGS":
                    //Aplicaciones>Descargado
                    accion=Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS;
                    iniciarAccionConfig(accion,i);
                    break;
                case "ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS":
                    //Aplicaciones>Todos
                    accion=Settings.ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS;
                    iniciarAccionConfig(accion,i);
                    break;
                case "ACTION_NETWORK_OPERATOR_SETTINGS":
                    //Redes disponibles
                    accion=Settings.ACTION_NETWORK_OPERATOR_SETTINGS;
                    iniciarAccionConfig(accion,i);
                    break;
                case "ACTION_PRIVACY_SETTINGS":
                    //Copia de seguridad y restablecer
                    accion=Settings.ACTION_PRIVACY_SETTINGS;
                    iniciarAccionConfig(accion,i);
                    break;
                case "ACTION_WIFI_SETTINGS":
                    //Wi-Fi
                    accion=Settings.ACTION_WIFI_SETTINGS;
                    iniciarAccionConfig(accion,i);
                    break;
                case "ACTION_WIFI_IP_SETTINGS":
                    //Configuración de IP
                    accion=Settings.ACTION_WIFI_IP_SETTINGS;
                    iniciarAccionConfig(accion,i);
                    break;
                case "ACTION_USER_DICTIONARY_SETTINGS":
                    //Diccionario del usuario
                    accion=Settings.ACTION_USER_DICTIONARY_SETTINGS;
                    iniciarAccionConfig(accion,i);
                    break;
            }

        }

    }

    private void iniciarAccionConfig(String accion,Intent i) {
        i.setAction(accion);
        if (i.resolveActivity(getPackageManager()) != null) {
            startActivity(i);
        }
    }



    private void mostrarModoVideo(Intent i) {
        i.setAction(MediaStore.INTENT_ACTION_VIDEO_CAMERA);
        if (i.resolveActivity(getPackageManager()) != null) {
            startActivity(i);
        }
    }

    private void mostrarImagenDetenida(Intent i) {
        i.setAction(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        if (i.resolveActivity(getPackageManager()) != null) {
            startActivity(i);
        }
    }

    private void agregarEventoCalendario(Intent i) {
        Long begin=calendar_begin.getDate();
        Long end=calendar_end.getDate();
        i.setAction(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME,end)
                .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)
                .putExtra(CalendarContract.Events.TITLE, "Curso de Android")
                .putExtra(CalendarContract.Events.DESCRIPTION, "Asistir a la capacitación de Android Certified")
                .putExtra(CalendarContract.Events.EVENT_LOCATION, "Aulas de Develop")
                .putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");
        if (i.resolveActivity(getPackageManager()) != null) {
            startActivity(i);
        }
        //solo si se desea mostrar la UI de agregar un evento
       /* Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.dir/event");
        startActivity(intent);*/
    }

    private void mostrarAlarmas(Intent i) {
        i.setAction(AlarmClock.ACTION_SHOW_ALARMS);
        if (i.resolveActivity(getPackageManager()) != null) {
            startActivity(i);
        }
    }

    private void crearTemporizador(Intent i) {
        String seconds=et_segundos.getText().toString().trim();
        String message=et_etiqueta_segundos.getText().toString().trim();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            if (validaCamposTemporizador(et_segundos,et_etiqueta_segundos,seconds,message)){
                        i.setAction(AlarmClock.ACTION_SET_TIMER)
                        .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                        .putExtra(AlarmClock.EXTRA_LENGTH, seconds);
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
        int hour;
        int minutes;
        Boolean vibrar=checkbox_vibrar.isChecked();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            hour = timepiker_alarma.getHour();
            minutes = timepiker_alarma.getMinute();
        } else {
            hour = timepiker_alarma.getCurrentHour();
            minutes = timepiker_alarma.getCurrentMinute();
        }
        if (!esVacio(message)){
            i.setAction(AlarmClock.ACTION_SET_ALARM)
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

        i.setAction(android.content.Intent.ACTION_VIEW);
        i.setData(ContactsContract.Contacts.CONTENT_URI);
        verificarActividadApropiada(i, "Abrir con:");

        //Segunda forma larga
        //“content://contacts..” para realizar la operación de visualización de contactos del directorio
        //startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("content://contacts/people/")));
    }

    private boolean validaCampos(String latitud, String longitud, String categoria) {

        if (latitud.equals("Selecciona la Latitud")) {
            Toast.makeText(this, "Selecciona la latitud", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (longitud.equals("Selecciona la Longitud")) {
            Toast.makeText(this, "Selecciona la Longitud", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (categoria.equals("Selecciona la categoria")) {
            Toast.makeText(this, "Selecciona la categoria", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validaCamposTemporizador(EditText et1,EditText et2, String seconds,String message) {

        if (esVacio(seconds)) {
            et1.setError("Campo Vacio");
            return false;
        }

        if (esVacio(message)) {
            et2.setError("Campo Vacio");
            return false;
        }

        return true;
    }

    private void compartirDatos(Intent i) {

        String numeroTelefonico = tv_compartir_datos.getText().toString().trim();
        if (!(esVacio(numeroTelefonico))) {
            // Compartir  a través de las redes sociales disponibles en el móvil.
            String textoParaPonerEnLaRedSocial = "Mi datos del móvil";
            i.setAction(Intent.ACTION_SEND);
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
            i.setAction(Intent.ACTION_VIEW );
            i.setData(intentUri);
            i.setPackage("com.google.android.apps.maps");

            if (i.resolveActivity(getPackageManager()) != null) {
                startActivity(i);
            }

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
