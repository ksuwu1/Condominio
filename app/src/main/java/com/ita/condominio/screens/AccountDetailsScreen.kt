
import android.app.Application
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.ita.condominio.BottomNavigationBar
import com.ita.condominio.CustomHeader2
import com.ita.condominio.R
import androidx.compose.material.Snackbar
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ita.condominio.Network.AccountDetailsViewModel

@Composable
fun AccountDetailsScreen(navController: NavHostController, viewModel: AccountDetailsViewModel = viewModel()) {
        val usuario by viewModel.usuario.observeAsState()

        // Usar valores directamente desde el usuario
    val id_user = usuario?.id_usuario ?: "Cargando..."
    val password = usuario?.password ?: "Cargando..."

    // Inicializa los valores solo si el usuario está disponible
    val nombre = usuario?.nombre ?: "Cargando..."
    val apellidoPat = usuario?.apellido_pat ?: "Cargando..."
    val apellidoMat = usuario?.apellido_mat ?: "Cargando..."
    val telCasa = usuario?.tel_casa ?: "Cargando..."
    val celular = usuario?.cel ?: "Cargando..."
    val correo = usuario?.correo ?: "Cargando..."
    val casa = usuario?.num_casa ?: "Cargando..."

    // Usa el estado mutable solo después de obtener los datos reales
    var nombreState by remember { mutableStateOf(nombre) }
    var apellidoPatState by remember { mutableStateOf(apellidoPat) }
    var apellidoMatState by remember { mutableStateOf(apellidoMat) }
    var telCasaState by remember { mutableStateOf(telCasa) }
    var celularState by remember { mutableStateOf(celular) }
    var correoState by remember { mutableStateOf(correo) }


        // Estado para indicar si los campos son editables o solo de lectura
    var isEditing by remember { mutableStateOf(false) }

    LaunchedEffect(usuario) {
        // Verifica que 'usuario' no sea null
        if (usuario != null) {
            nombreState = usuario?.nombre ?: nombreState
            apellidoPatState = usuario?.apellido_pat ?: apellidoPatState
            apellidoMatState = usuario?.apellido_mat ?: apellidoMatState
            telCasaState = usuario?.tel_casa ?: telCasaState
            celularState = usuario?.cel ?: celularState
            correoState = usuario?.correo ?: correoState
        }
    }


    Column(modifier = Modifier.fillMaxSize()) {
        // Encabezado de la pantalla
        CustomHeader2(navController = navController, title = "Datos propietario")

        // Lista de campos y botones
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Barra superior con icono de casa y número
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center, // Cambiado a Center
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.home),
                        contentDescription = "Casa",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Espacio entre icono y texto
                    Text(text = "Número de casa: " + casa, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }

            item {
                Text(
                    text = "Datos propietario",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            item { EditableField("Nombre", nombreState, isEditing) { nombreState = it } }
            item { EditableField("Apellido paterno", apellidoPatState, isEditing) { apellidoPatState = it } }
            item { EditableField("Apellido materno", apellidoMatState, isEditing) { apellidoMatState = it } }
            item { EditableField("Teléfono casa", telCasaState, isEditing) { telCasaState = it } }
            item { EditableField("Celular", celularState, isEditing) { celularState = it } }
            item { EditableField("Correo", correoState, isEditing) { correoState = it } }

            // Botones para modificar datos y cambiar contraseña
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Botón Modificar Datos
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.editar),
                            contentDescription = "Editar",
                            modifier = Modifier.size(24.dp)
                        )
                        Button(
                            onClick = {
                                if (isEditing) {
                                    viewModel.guardarCambios(
                                        nombre = nombreState,
                                        apellidoPat = apellidoPatState,
                                        apellidoMat = apellidoMatState,
                                        telCasa = telCasaState,
                                        celular = celularState,
                                        correo = correoState
                                    ) // Llamamos a guardar cambios
                                    isEditing = false // Cambiamos a solo lectura
                                } else {
                                    isEditing = true // Cambiamos a modo de edición
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC4DAD2))
                        ) {
                            Text(if (isEditing) "Guardar cambios" else "Modificar datos")
                        }
                    }

                    // Botón Cambiar Contraseña
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.pass),
                            contentDescription = "Cambiar contraseña",
                            modifier = Modifier.size(24.dp)
                        )
                        Button(
                            onClick = { navController.navigate("changePassword") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC4DAD2))
                        ) {
                            Text("Cambiar contraseña")
                        }
                    }
                }
            }
        }

        // Barra de navegación inferior
        BottomNavigationBar(navController)
    }
}

@Composable
fun EditableField(label: String, value: String, isEditable: Boolean, onValueChange: (String) -> Unit) {
    Column {
        Text(text = label, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(4.dp))
        if (isEditable) {
            BasicTextField(
                value = value,
                onValueChange = { newValue -> onValueChange(newValue) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(8.dp)
            )

            //Actualizar datos
        } else {
            Text(
                text = value,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Gray)
                    .padding(8.dp)
            )
        }
    }
}
