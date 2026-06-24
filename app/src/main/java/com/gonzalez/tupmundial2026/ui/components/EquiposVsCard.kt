package com.gonzalez.tupmundial2026.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gonzalez.tupmundial2026.ui.Dorado
import com.gonzalez.tupmundial2026.ui.VerdeMedio
import com.gonzalez.tupmundial2026.ui.VerdeOscuro

// Card con los dos equipos enfrentados que aparece tanto en
// DetallesScreen como en CompraTicketScreen.

@Composable
fun EquiposVsCard(equipo1: String, equipo2: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Brush.verticalGradient(listOf(VerdeOscuro, VerdeMedio)))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(equipo1, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(
                "VS",
                color = Dorado,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(vertical = 6.dp)
            )
            Text(equipo2, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
    }
}