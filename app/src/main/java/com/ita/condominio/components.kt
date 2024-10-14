package com.ita.condominio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController

//import com.ita.condominio.screens.MainMenuScreen

import com.ita.condominio.screens.AccountScreen


@Composable
fun AccountOptionButton(text: String, iconRes: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 20.dp, vertical = 8.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFa9dfbf))
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(25.dp),
            tint = Color.Black
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, color = Color.Black)
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.verde_claro),
        contentColor = Color.Black
    ) {
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.reporte),
                    contentDescription = "Informes",
                    modifier = Modifier.size(30.dp)
                )
            },
            selected = false,
            onClick = { navController.navigate("MainMenu") }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = "Home",
                    modifier = Modifier.size(30.dp)
                )
            },
            selected = false,
            onClick = {navController.navigate("payments")}
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.chat),
                    contentDescription = "Chat",
                    modifier = Modifier.size(30.dp)
                )
            },
            selected = false,
            onClick = { navController.navigate("reports")  }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = "User",
                    modifier = Modifier.size(30.dp)
                )
            },
            selected = false,
            onClick = { navController.navigate("account") }
        )
    }
}


@Composable
fun CustomHeader(title: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth(), // Cambié fillMaxSize por fillMaxWidth
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Primera barra divisora con texto "Condominio"
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color(0xFF699C89)),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "Condominio",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp),
                color = Color.White
            )
        }

        // Segunda barra divisora con texto dinámico
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(Color(0xFFC4D9D2)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,  // Texto dinámico
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
        }
    }
}



