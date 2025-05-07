package com.deepakjetpackcompose.ainotes.util

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import com.deepakjetpackcompose.ainotes.R
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.deepakjetpackcompose.ainotes.ui.theme.AiNotes
import com.deepakjetpackcompose.ainotes.ui.theme.UbuntuFont
import com.deepakjetpackcompose.ainotes.viewmodel.NotesViewmodel



@Composable
fun TopBar(notesViewmodel: NotesViewmodel,modifier: Modifier = Modifier) {
    val query=notesViewmodel.searchQuery.collectAsState()
    val isDark=notesViewmodel.isDark.collectAsState()
    var rotate by remember { mutableStateOf(false) }
    var isRotating by remember { mutableStateOf(false) }
    val darkImg=if(isDark.value){
        R.drawable.moon
    }else{
        R.drawable.sun
    }

    val rotation by animateFloatAsState(
        targetValue = if(rotate)360F else 0F,
        animationSpec = tween(durationMillis = 400),
        finishedListener = {
            notesViewmodel.toggleDarkMode()
            isRotating=false
        }
    )
    SetStatusBarColor(color = MaterialTheme.colorScheme.surfaceContainerLow)

        Column(
            modifier = modifier.fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Row (modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween){
                Text(
                    "My Notes",
                    fontFamily = UbuntuFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                IconButton(onClick = {
                    rotate= !rotate
                    isRotating=true
                }) {
                    Icon(
                        painter = painterResource(darkImg),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .graphicsLayer(rotationZ = rotation)
                            .size(30.dp)
                    )
                }

            }

            OutlinedTextField(
                value = query.value,
                onValueChange = notesViewmodel::updateSearchQuery,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 5.dp)
                    .height(50.dp),
                textStyle = TextStyle(
                    fontFamily = UbuntuFont
                ),
                singleLine = true,
                placeholder = {Text("Search notes")},
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
                    focusedIndicatorColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }


}