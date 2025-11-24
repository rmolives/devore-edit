package org.devore.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlin.system.exitProcess

fun main() {
    application {
        val state = rememberWindowState(
            placement = WindowPlacement.Floating,
            position = WindowPosition(Alignment.Center),
            size = DpSize(1300.dp, 950.dp)
        )
        Window(
            title = "Devore Edit",
            icon = painterResource("drawable/logo.png"),
            onCloseRequest = ::exitApplication,
            state = state,
            resizable = true,
            undecorated = true,
            transparent = true
        ) {
            WindowDraggableArea {
                Box(modifier = Modifier.fillMaxSize().clip(shape = RoundedCornerShape(20.dp)).background(Color.LightGray)) {
                    Column {
                        Framework(state)
                        TextEditor()
                    }
                }
            }
        }
    }
    exitProcess(0)
}

@Composable
fun Framework(state: WindowState) {
    Box(
        modifier = Modifier.fillMaxWidth().height(50.dp)
            .background(Color.White.copy(alpha = 0f))
            .clip(shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
    ) {
        Box(modifier = Modifier.fillMaxSize().background(Color.White.copy(alpha = 0.6f))) {
            Head(state)
        }
    }
}

@Composable
fun Head(state: WindowState) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = "Devore Edit",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
        )
        Operation(state)
    }
}

@Composable
fun Operation(state: WindowState) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(
            onClick = { state.isMinimized = state.isMinimized.not() },
            colors = ButtonDefaults.textButtonColors(contentColor = Color.Transparent)
        ) {
            Text(
                text = "—",
                color = Color.Black,
                style = TextStyle(
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        TextButton(
            onClick = { exitProcess(0) },
            colors = ButtonDefaults.textButtonColors(contentColor = Color.Transparent)
        ) {
            Text(
                text = "X",
                color = Color.Black,
                style = TextStyle(
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}