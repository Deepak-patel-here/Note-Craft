package com.deepakjetpackcompose.ainotes.view

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.deepakjetpackcompose.ainotes.model.database.Notes

import com.deepakjetpackcompose.ainotes.ui.theme.AiNotes
import com.deepakjetpackcompose.ainotes.ui.theme.UbuntuFont
import com.deepakjetpackcompose.ainotes.viewmodel.NotesViewmodel

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AddOrEditScreen(
    notesViewmodel: NotesViewmodel,
    id:Int?,
    title1:String,
    desc1:String,
    type:Int,
    navController: NavController,
    modifier: Modifier = Modifier
) {

    val decodeTitle= Uri.decode(title1)
    val decodeContent= Uri.decode(desc1)
    var title by remember { mutableStateOf(decodeTitle) }
    var content by remember { mutableStateOf(decodeContent) }
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(horizontal = 16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .size(40.dp)
                    .clickable(onClick = {
                        navController.popBackStack()
                    })
            )

            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .size(40.dp)
                    .clickable(onClick = {
                        if (title.isNotEmpty() && content.isNotEmpty()) {
                            if(type==1){
                                notesViewmodel.addNotes(
                                    notes = Notes(
                                        title = title,
                                        content = content,
                                        timeStamp = System.currentTimeMillis()
                                    )
                                )
                            }
                            else{
                                notesViewmodel.updateNotes(
                                    notes = Notes(
                                        id = id?:0,
                                        title = title,
                                        content = content,
                                        timeStamp = System.currentTimeMillis()
                                    )
                                )
                            }

                        } else {
                            Toast.makeText(context, "Please fill the fields", Toast.LENGTH_SHORT)
                                .show()
                        }
                        navController.popBackStack()
                    })
            )
        }
        Spacer(Modifier.height(5.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            placeholder = {
                Text(
                    "Note Title", fontFamily = UbuntuFont, fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
                focusedTextColor = MaterialTheme.colorScheme.onSurface
            ),
            textStyle = TextStyle(
                fontSize = 26.sp,
                fontFamily = UbuntuFont,
                fontWeight = FontWeight.Bold
            )
        )

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            placeholder = {
                Text(
                    "Note Description", fontFamily = UbuntuFont, fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            },
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
                focusedTextColor = MaterialTheme.colorScheme.onSurface
            ),
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontFamily = UbuntuFont,
                fontWeight = FontWeight.Normal
            )
        )
    }


}

fun formatTime(time: Long): String {
    val date = Date(time)
    val formatter = SimpleDateFormat("h:mm a", Locale.getDefault())
    return formatter.format(date)
}