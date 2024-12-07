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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ita.condominio.BottomNavigationBar
import com.ita.condominio.CustomHeader2
import com.ita.condominio.Models.AccountDetailsViewModel
import com.ita.condominio.Network.RetrofitInstance
import com.ita.condominio.Network.UserResponse
import com.ita.condominio.R
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun AccountDetailsScreen(navController: NavHostController, userId: String) {
    val accountDetailsViewModel: AccountDetailsViewModel = viewModel()
    val user by accountDetailsViewModel.user.observeAsState()

    var isEditing by remember { mutableStateOf(false) }

    // Variables to store user details
    var name by remember { mutableStateOf(user?.nombre ?: "") }
    var lastNameP by remember { mutableStateOf(user?.apellido_pat ?: "") }
    var lastNameM by remember { mutableStateOf(user?.apellido_mat ?: "") }
    var phone by remember { mutableStateOf(user?.tel_casa ?: "") }
    var mobile by remember { mutableStateOf(user?.cel ?: "") }
    var email by remember { mutableStateOf(user?.correo ?: "") }

    val scope = rememberCoroutineScope()
    var userResponseList by remember { mutableStateOf<List<UserResponse>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }

    // Get user data when screen is launched
    LaunchedEffect(Unit) {
        try {
            val response = RetrofitInstance.api.getUsers()
            if (response.isSuccessful) {
                userResponseList = response.body() ?: emptyList()
                loading = false
                val currentUser = userResponseList.find { it.id_usuario.toString() == userId }
                currentUser?.let {
                    name = it.nombre
                    lastNameP = it.apellido_pat
                    lastNameM = it.apellido_mat
                    phone = it.tel_casa
                    mobile = it.cel
                    email = it.correo
                } ?: run {
                    Log.e("AccountDetailsScreen", "User not found")
                    loading = false
                }
            } else {
                Log.e("AccountDetailsScreen", "Error getting user data: ${response.message()}")
                loading = false
            }
        } catch (e: Exception) {
            Log.e("AccountDetailsScreen", "Network error: ${e.message}")
            loading = false
        }
    }


    if (loading) {
        CircularProgressIndicator()
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            CustomHeader2(navController = navController, title = "Datos propietario")

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.home),
                            contentDescription = "Casa",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
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

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                painter = painterResource(id = R.drawable.editar),
                                contentDescription = "Editar",
                                modifier = Modifier.size(24.dp)
                            )
                            Button(
                                onClick = { isEditing = !isEditing },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC4DAD2))
                            ) {
                                Text(if (isEditing) "Guardar cambios" else "Modificar datos")
                            }
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
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

            BottomNavigationBar(navController)
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
    AccountDetailsScreen(navController, userId = "id_usuario")
}
