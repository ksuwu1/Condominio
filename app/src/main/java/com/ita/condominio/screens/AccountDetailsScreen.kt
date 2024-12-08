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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ita.condominio.BottomNavigationBar
import com.ita.condominio.CustomHeader2
import com.ita.condominio.Models.AccountDetailsViewModel
import com.ita.condominio.R
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.material.Snackbar

@Composable
fun AccountDetailsScreen(navController: NavHostController, viewModel: AccountDetailsViewModel = viewModel()) {
    // Observa los detalles de usuario y los errores desde el ViewModel
    val userDetails by viewModel.userDetails.observeAsState()
    val error by viewModel.error.observeAsState()

    // Estado para indicar si los campos son editables o solo de lectura
    var isEditing by remember { mutableStateOf(false) }

    // Estados iniciales para los campos
    var nombre by remember { mutableStateOf("") }
    var apellido_pat by remember { mutableStateOf("") }
    var apellido_mat by remember { mutableStateOf("") }
    var tel_casa by remember { mutableStateOf("") }
    var cel by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }

    // Llamada para cargar los detalles del usuario al iniciar la pantalla
    LaunchedEffect(Unit) {
        viewModel.fetchUserDetails(correo)
    }

    // Mostrar una Snackbar si hay un error
    error?.let {
        Snackbar(
            modifier = Modifier.padding(8.dp),
            content = {
                Text(text = it, color = Color.White)
            }
        )
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
                    Text(text = "Número de casa: 10", fontSize = 18.sp, fontWeight = FontWeight.Bold)
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

            item { EditableField("Nombre", nombre, isEditing) { nombre = it } }
            item { EditableField("Apellido paterno", apellido_pat, isEditing) { apellido_pat = it } }
            item { EditableField("Apellido materno", apellido_mat, isEditing) { apellido_mat = it } }
            item { EditableField("Teléfono casa", tel_casa, isEditing) { tel_casa = it } }
            item { EditableField("Celular", cel, isEditing) { cel = it } }
            item { EditableField("Correo", correo, isEditing) { correo = it } }

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
                                isEditing = !isEditing
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
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(8.dp)
            )
        } else {
            Text(
                text = value,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(8.dp)
            )
        }
    }
}
