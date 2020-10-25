package com.example.alertaumg.Funcionalidad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.alertaumg.Contenedor;
import com.example.alertaumg.Inicio.Login;
import com.example.alertaumg.Modelos.Departamento;
import com.example.alertaumg.Modelos.RespuestaAPI;
import com.example.alertaumg.Modelos.Usuario;
import com.example.alertaumg.R;
import com.example.alertaumg.Utilidades.APIUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroUsuario extends AppCompatActivity {

    EditText txtCorreo, txtPassword, txtNombre, txtApellido, txtDireccion, txtRepetirContrasena, txtTelefono;
    Spinner spinerDepartamento, spinerMunicipio;
    Button btnRegistrar;
    String direccionFinal;
    ImageView editarImagenPerfil;

    String[] arraySpinerDepartamento;

    ArrayAdapter<String> adapter;

    String[] munGuatemala, munElProgreso, munZacapa, munJalapa, munJutiapa, munAltaVerapaz, munBajaVerapaz,
            munQuetzaltenango, munSolola, munIzabal, munPeten, munQuihe, munSanMarcos, munSantaRosa, munTotonicapan,
            munChimaltenango, munChiquimula, munEscuintla, munHuehuetenango, munRetalhuleu, munSacatepequez, munSuchitepequez;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        reconocerObjetos();//Reconocemos todos las cajas de texto, botones y spinners
        txtNombre.setText(getIntent().getStringExtra("nombre"));
        txtApellido.setText(getIntent().getStringExtra("apellido"));
        txtCorreo.setText(getIntent().getStringExtra("correo"));

        try{
            Glide.with(this)
                    .load(getIntent().getStringExtra("fotografia") )
                    .into((ImageView) editarImagenPerfil);
        }catch (Exception e){

        }

        llenarDatos();//llenamos datos de departamentos y municipios

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar();
            }
        });

        spinerMunicipio.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) { //añadimos municipios dependiendo del departamento seleccionado
                llenarMunicipios();
                return false;
            }
        });

        //getSupportActionBar().hide();
    }

    private void reconocerObjetos(){
        txtCorreo = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtContrasena);
        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        spinerDepartamento = findViewById(R.id.sprDepartamento);
        spinerMunicipio = findViewById(R.id.sprMunicipio);
        txtDireccion = findViewById(R.id.txtDireccion);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        txtRepetirContrasena = findViewById(R.id.txtRepetirContrasena);
        txtTelefono = findViewById(R.id.txtTelefono);
        editarImagenPerfil = findViewById(R.id.editarImagenPerfil);
    }

    public void llenarDatos() {
        arraySpinerDepartamento = new String[]{"Guatemala", "El Progreso", "Zacapa", "Jalapa", "Jutiapa",
                "Alta Verapaz", "Baja Verapaz", "Quetzaltenango", "Sololá", "Izabal", "Petén", "Quiché", "San Marcos",
                "Santa Rosa", "Totonicapán", "Chimaltenango", "Chiquimula", "Escuintla", "Huehuetenango", "Retalhuleu", "Sacatepéquez", "Suchitepéquez"};

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinerDepartamento);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinerDepartamento.setAdapter(adapter);

        munGuatemala = new String[]{"Guatemala", "Santa Catarina Pinula", "San José Pinula", "San José del Golfo",
                "Palencia", "Chinautla", "San Pedro Ayampuc", "Mixco", "San Pedro Sacatepéquez", "San Juan Sacatepéquez",
                "San Raymundo", "Chuarrancho", "Fraijanes", "Amatitlán", "Villa Nueva", "Villa Canales", "Petapa"};


        munElProgreso = new String[]{"El Jícaro", "Guastatoya", "Morazán", "Sanarate", "Sansare", "San Agustín Acasaguastlán",
                "San Antonio La Paz", "San Cristóbal Acasaguastlán"};

        munZacapa = new String[]{"Cabañas", "Estanzuela", "Gualán", "Huité", "La Unión", "Río Hondo", "San Diego",
                "San Jorge", "Teculután", "Usumatlán", "Zacapa"};


        munJalapa = new String[]{"Jalapa", "Mataquescuintla", "Monjas", "San Carlos Alzatate", "San Luis Jilotepeque",
                "San Pedro Pinula", "San Manuel Chaparrón"};

        munJutiapa = new String[]{"Agua Blanca", "Asunción Mita", "Atescatempa", "Comapa", "Conguaco", "El Adelanto",
                "El Progreso", "Jalpatagua", "Jerez", "Jutiapa", "Moyuta", "Pasaco", "Quesada", "San José Acatempa",
                "Santa Catarina Mita", "Yupiltepeque", "Zapotitlán"};


        munAltaVerapaz = new String[]{"Cobán", "San Pedro Carchá", "San Juan Chamelco", "San Cristóbal Verapaz", "Tactic",
                "Tucuru", "Tamahú", "Panzós", "Senahú", "Cahabón", "Lanquín", "Chahal", "Fray Bartolomé de las Casas",
                "Chisec", "Santa Cruz Verapaz", "Santa Catalina La Tinta", "Raxruhá"};

        munBajaVerapaz = new String[]{"Cubulco", "Santa Cruz el Chol", "Granados", "Purulhá", "Rabinal", "Salamá",
                "San Miguel Chicaj", "San Jerónimo"};

        munQuetzaltenango = new String[]{"Almolonga", "Cabricán", "Cajolá", "Cantel", "Coatepeque", "Colomba",
                "Concepción Chiquirichapa", "El Palmar", "Flores Costa Cuca", "Génova", "Huitán", "La Esperanza",
                "Olintepeque", "Palestina de Los Altos", "Quetzaltenango", "Salcajá", "Écija", "San Carlos Écija",
                "San Juan Ostuncalco", "San Francisco La Unión", "San Martín Sacatepéquez", "San Mateo",
                "San Miguel Sigüilá", "Sibilia", "Zunil"};

        munSolola = new String[]{"Sololá", "Concepción", "Nahualá", "Panajachel", "San Andrés Semetabaj",
                "San Antonio Palopó", "San José Chacayá", "San Juan La Laguna", "San Lucas Tolimán", "San Marcos La Laguna",
                "San Pablo La Laguna", "San Pedro La Laguna", "Santa Catarina Ixtahuacán", "Santa Catarina Palopó",
                "Santa Clara La Laguna", "Santa Cruz La Laguna", "Santa Lucía Utatlán", "Santa María Visitación",
                "Santiago Atitlán"};

        munIzabal = new String[]{"Puerto Barrios", "Livingston", "El Estor", "Morales", "Los Amates"};

        munPeten = new String[]{"Dolores", "Flores", "Santa Elena de la Cruz", "La Libertad", "Melchor de Mencos",
                "Poptún", "San Andrés", "San Benito", "San Francisco", "San José", "San Luis", "Santa Ana",
                "Sayaxché", "Las Cruces", "El Chal"};

        munQuihe = new String[]{"Santa Cruz del Quiché", "Canillá", "Chajul", "Chicamán", "Chiché",
                "Chichicastenango", "Chinique", "Cunén", "Joyabaj", "Nebaj", "Pachalum", "Patzité",
                "Playa Grande-Ixcán", "Sacapulas", "San Andrés Sajcabajá", "San Antonio Ilotenango",
                "San Bartolomé Jocotenango", "San Juan Cotzal", "San Pedro Jocopilas", "Uspantán",
                "Zacualpa"};

        munSanMarcos = new String[]{"San Marcos (San Marcos)", "Ayutla", "Catarina", "Comitancillo",
                "Concepción Tutuapa", "El Quetzal", "El Rodeo", "El Tumbador", "Ixchiguán",
                "La Reforma", "Malacatán", "Nuevo Progreso", "Ocós", "Pajapita", "Esquipulas Palo Gordo",
                "San Antonio Sacatepéquez", "San Cristóbal Cucho", "San José Ojetenam", "San Lorenzo",
                "San Miguel Ixtahuacán", "San Pablo", "San Pedro Sacatepéquez", "San Rafael Pie de la Cuesta",
                "Sibinal", "Sipacapa", "Tacaná", "Tajumulco", "Tejutla", "Río Blanco", "La Blanca"};

        munSantaRosa = new String[]{"Ayarza", "Barberena", "Casillas", "Chiquimulilla", "Cuilapa", "Guazacapán",
                "Monterrico", "Nueva Santa Rosa", "Oratorio", "Pueblo Nuevo Viñas", "San Juan Tecuaco",
                "San Rafael Las Flores", "Santa Cruz Naranjo", "Santa María Ixhuatán", "Santa Rosa de Lima",
                "Taxisco"};

        munTotonicapan = new String[]{"Momostenango", "San Andrés Xecul", "San Bartolo", "San Cristóbal Totonicapán",
                "San Francisco El Alto", "Santa Lucía La Reforma", "Santa María Chiquimula", "Totonicapán"};

        munChimaltenango = new String[]{"Chimaltenango", "San José Poaquíl", "San Martín Jilotepeque",
                "San Juan Comalapa", "Santa Apolonia", "Tecpán Guatemala", "Patzún", "Pochuta", "Patzicía",
                "Santa Cruz Balanyá", "Acatenango", "San Pedro Yepocapa", "San Andrés Itzapa", "Parramos",
                "Zaragoza", "El Tejar"};

        munChiquimula = new String[]{"Chiquimula", "Jocotán", "Esquipulas", "Camotán", "Quezaltepeque",
                "Olopa", "Ipala", "San Juan Ermita", "Concepción Las Minas", "San Jacinto", "San José la Arada"};

        munEscuintla = new String[]{"Escuintla (municipio)", "Santa Lucía Cotzumalguapa", "La Democracia",
                "Siquinalá", "Masagua", "Tiquisate", "La Gomera", "Guaganazapa", "San José", "Iztapa",
                "Palín", "San Vicente Pacaya", "Nueva Concepción"};

        munHuehuetenango = new String[]{"Huehuetenango", "Chiantla", "Malacatancito", "Cuilco", "Nentón",
                "San Pedro Necta", "Jacaltenango", "Soloma", "Ixtahuacán", "Santa Bárbara", "La Libertad",
                "La Democracia", "San Miguel Acatán", "San Rafael La Independencia", "Todos Santos Cuchumatán",
                "San Juan Atitán", "Santa Eulalia", "San Mateo Ixtatán", "Colotenango", "San Sebastián Huehuetenango",
                "Tectitán", "Concepción Huista", "San Juan Ixcoy", "San Antonio Huista", "Santa Cruz Barillas",
                "San Sebastián Coatán", "Aguacatán", "San Rafael Petzal", "San Gaspar Ixchil", "Santiago Chimaltenango",
                "Santa Ana Huista", "Unión Cantinil"};

        munRetalhuleu = new String[]{"Champerico", "El Asintal", "Nuevo San Carlos", "Retalhuleu",
                "San Andrés Villa Seca", "San Felipe", "San Martín Zapotitlán", "San Sebastián",
                "Santa Cruz Muluá"};

        munSacatepequez = new String[]{"Alotenango", "La Antigua Guatemala", "Ciudad Vieja", "Jocotenango",
                "Magdalena Milpas Altas", "Pastores", "San Antonio Aguas Calientes", "San Bartolomé Milpas Altas",
                "San Lucas Sacatepéquez", "San Miguel Dueñas", "Santa Catarina Barahona", "Santa Lucía Milpas Altas",
                "Santa María de Jesús", "Santiago Sacatepéquez", "Santo Domingo Xenacoj", "Sumpango"};

        munSuchitepequez = new String[]{"Chicacao", "Cuyotenango", "Mazatenango", "Patulul", "Pueblo Nuevo", "Río Bravo",
                "Samayac", "San Antonio Suchitepéquez", "San Bernardino", "San Francisco Zapotitlán", "San Gabriel",
                "San José El Ídolo", "San José La Máquina", "San Juan Bautista", "San Lorenzo", "San Miguel Panán",
                "San Pablo Jocopilas", "Santa Bárbara", "Santo Domingo Suchitepéquez", "Santo Tomás La Unión",
                "Zunilito"};
    }

    public void llenarMunicipios() {

        spinerMunicipio.setAdapter(null);

        if (spinerDepartamento.getSelectedItem().toString().equals("Alta Verapaz")) {
            spinerMunicipio.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, munAltaVerapaz));
        }


        if (spinerDepartamento.getSelectedItem().toString().equals("Baja Verapaz")) {
            spinerMunicipio.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, munBajaVerapaz));
        }

        if (spinerDepartamento.getSelectedItem().toString().equals("Chimaltenango")) {
            spinerMunicipio.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, munChimaltenango));
        }

        if (spinerDepartamento.getSelectedItem().toString().equals("Chiquimula")) {
            spinerMunicipio.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, munChiquimula));
        }

        if (spinerDepartamento.getSelectedItem().toString().equals("El Progreso")) {
            spinerMunicipio.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, munElProgreso));
        }

        if (spinerDepartamento.getSelectedItem().toString().equals("Escuintla")) {
            spinerMunicipio.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, munEscuintla));
        }

        if (spinerDepartamento.getSelectedItem().toString().equals("Guatemala")) {
            spinerMunicipio.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, munGuatemala));
        }

        if (spinerDepartamento.getSelectedItem().toString().equals("Huehuetenango")) {
            spinerMunicipio.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, munHuehuetenango));
        }

        if (spinerDepartamento.getSelectedItem().toString().equals("Izabal")) {
            spinerMunicipio.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, munIzabal));
        }

        if (spinerDepartamento.getSelectedItem().toString().equals("Jalapa")) {
            spinerMunicipio.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, munJalapa));
        }

        if (spinerDepartamento.getSelectedItem().toString().equals("Jutiapa")) {
            spinerMunicipio.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, munJutiapa));
        }

        if (spinerDepartamento.getSelectedItem().toString().equals("Petén")) {
            spinerMunicipio.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, munPeten));
        }

        if (spinerDepartamento.getSelectedItem().toString().equals("Quetzaltenango")) {
            spinerMunicipio.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, munQuetzaltenango));
        }

        if (spinerDepartamento.getSelectedItem().toString().equals("Quiché")) {
            spinerMunicipio.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, munQuihe));
        }

        if (spinerDepartamento.getSelectedItem().toString().equals("Retalhuleu")) {
            spinerMunicipio.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, munRetalhuleu));
        }

        if (spinerDepartamento.getSelectedItem().toString().equals("Sacatepéquez")) {
            spinerMunicipio.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, munSacatepequez));
        }

        if (spinerDepartamento.getSelectedItem().toString().equals("San Marcos")) {
            spinerMunicipio.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, munSanMarcos));
        }

        if (spinerDepartamento.getSelectedItem().toString().equals("Santa Rosa")) {
            spinerMunicipio.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, munSantaRosa));
        }

        if (spinerDepartamento.getSelectedItem().toString().equals("Sololá")) {
            spinerMunicipio.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, munSolola));
        }

        if (spinerDepartamento.getSelectedItem().toString().equals("Suchitepéquez")) {
            spinerMunicipio.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, munSuchitepequez));
        }

        if (spinerDepartamento.getSelectedItem().toString().equals("Totonicapán")) {
            spinerMunicipio.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, munTotonicapan));
        }

        if (spinerDepartamento.getSelectedItem().toString().equals("Zacapa")) {
            spinerMunicipio.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, munZacapa));
        }
    }

    public void registrar(){
        //Intent intent = new Intent(getApplicationContext(), Login.class);
        direccionFinal = spinerDepartamento.getSelectedItem().toString() + ", "+spinerMunicipio.getSelectedItem().toString()
                +", "+txtDireccion.getText().toString();//Obtenemos dirección final

        String nombre = txtNombre.getText().toString().trim();
        String apellido = txtApellido.getText().toString().trim();
        String numero_telefono = txtTelefono.getText().toString().trim();
        String email = txtCorreo.getText().toString().trim();
        String contrasenia = txtPassword.getText().toString().trim();
        String contrasenia_confirm = txtRepetirContrasena.getText().toString().trim();
        String direccion = direccionFinal;

        String departamento = spinerDepartamento.getSelectedItem().toString();
        String municipio = spinerMunicipio.getSelectedItem().toString();
        String fotourl = getIntent().getStringExtra("fotografia");

        if(!nombre.isEmpty() && !apellido.isEmpty() && !numero_telefono.isEmpty() && !email.isEmpty()
                && !contrasenia.isEmpty() && !contrasenia_confirm.isEmpty() && !departamento.isEmpty()
                && !municipio.isEmpty() && !direccion.isEmpty()){

            try{
                Gson gson = new GsonBuilder().setLenient().create();
                APIUtils.getUsuarioService().registrarUsuario(nombre,apellido,numero_telefono,email,contrasenia,contrasenia_confirm,direccionFinal, fotourl).enqueue(new Callback<RespuestaAPI<String>>() {
                    @Override
                    public void onResponse(Call<RespuestaAPI<String>> call, Response<RespuestaAPI<String>> response) {
                        if (response.isSuccessful()) {
                            RespuestaAPI<String> respuesta = response.body();

                            if ( respuesta.getCodigo() == 1 ){
                                String objeto = respuesta.getData();
                                if ( objeto != null ){
                                    Toast.makeText(RegistroUsuario.this, respuesta.toString(), Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(RegistroUsuario.this, Contenedor.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(getApplicationContext(), "¡Registro exitoso!", Toast.LENGTH_SHORT).show();
                                }
                            }else if ( respuesta.getCodigo() == 0 ){

                                Toast.makeText(getApplicationContext(), respuesta.getMensaje(), Toast.LENGTH_SHORT).show();
                            }

                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Error en el servidor.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RespuestaAPI<String>> call, Throwable t) {

                    }
                });

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }


        }else{
            Toast.makeText(getApplicationContext(), "¡Verifique los campos!.", Toast.LENGTH_SHORT).show();
        }


        //startActivity(intent);
        //Toast.makeText(getApplicationContext(),"¡Usuario registrado con éxito!",Toast.LENGTH_SHORT).show();
        //finish();
    }


}