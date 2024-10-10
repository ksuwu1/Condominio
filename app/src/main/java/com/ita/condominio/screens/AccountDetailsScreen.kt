import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.ita.condominio.R

@Composable
fun AccountDetailsScreen(navController: NavHostController) {
    var isEditing by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("Nombre") }
    var lastNameP by remember { mutableStateOf("Apellido paterno") }
    var lastNameM by remember { mutableStateOf("Apellido materno") }
    var phone by remember { mutableStateOf("Teléfono de casa") }
    var mobile by remember { mutableStateOf("Celular") }
    var email by remember { mutableStateOf("Correo electrónico") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFA9DFBF))
            .padding(16.dp)
    ) {
        // Barra superior con icono de casa y número
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ajusta el tamaño del ícono aquí
            Icon(
                painter = painterResource(id = R.drawable.home),
                contentDescription = "Casa",
                modifier = Modifier.size(24.dp) // Tamaño ajustado
            )
            Text(text = "Número de casa: 10", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Título "Datos propietario"
        Text(
            text = "Datos propietario",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campos de datos
        EditableField("Nombre", name, isEditing) { name = it }
        EditableField("Apellido paterno", lastNameP, isEditing) { lastNameP = it }
        EditableField("Apellido materno", lastNameM, isEditing) { lastNameM = it }
        EditableField("Teléfono casa", phone, isEditing) { phone = it }
        EditableField("Celular", mobile, isEditing) { mobile = it }
        EditableField("Correo", email, isEditing) { email = it }

        Spacer(modifier = Modifier.height(16.dp))

        // Botones de acciones
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                isEditing = !isEditing
            }) {
                Text(if (isEditing) "Guardar cambios" else "Modificar datos")
            }

            Button(onClick = { /* Cambiar contraseña no funcional */ }) {
                Text("Cambiar contraseña")
            }
        }
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
