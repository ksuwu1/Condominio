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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ita.condominio.BottomNavigationBar
import com.ita.condominio.CustomHeader
import com.ita.condominio.R


@Composable
fun AccountDetailsScreen(navController: NavHostController) {
    var isEditing by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("") }
    var lastNameP by remember { mutableStateOf("") }
    var lastNameM by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        // CustomHeader fijo en la parte superior
        CustomHeader(title = "Datos propietario")

        // Contenido desplazable con LazyColumn
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

            item { EditableField("Nombre", name, isEditing) { name = it } }
            item { EditableField("Apellido paterno", lastNameP, isEditing) { lastNameP = it } }
            item { EditableField("Apellido materno", lastNameM, isEditing) { lastNameM = it } }
            item { EditableField("Teléfono casa", phone, isEditing) { phone = it } }
            item { EditableField("Celular", mobile, isEditing) { mobile = it } }
            item { EditableField("Correo", email, isEditing) { email = it } }

            // Botones de acciones con íconos
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

        // BottomNavigationBar fijo en la parte inferior
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

@Preview(showBackground = true)
@Composable
fun PreviewAccountScreen() {
    val navController = rememberNavController() // Crea un controlador de navegación simulado
    AccountDetailsScreen(navController)
}
