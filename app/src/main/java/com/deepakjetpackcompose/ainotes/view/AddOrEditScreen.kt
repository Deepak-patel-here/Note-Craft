package com.deepakjetpackcompose.ainotes.view

import android.net.Uri
import android.speech.tts.TextToSpeech
import android.util.Log
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.deepakjetpackcompose.ainotes.model.database.Notes
import com.deepakjetpackcompose.ainotes.R
import com.deepakjetpackcompose.ainotes.ui.theme.AiNotes
import com.deepakjetpackcompose.ainotes.ui.theme.UbuntuFont
import com.deepakjetpackcompose.ainotes.util.LanguageDialog
import com.deepakjetpackcompose.ainotes.viewmodel.ApiState
import com.deepakjetpackcompose.ainotes.viewmodel.NotesViewmodel
import kotlinx.coroutines.selects.select

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.collections.get

@Composable
fun AddOrEditScreen(
    notesViewmodel: NotesViewmodel,
    id: Int?,
    title1: String,
    desc1: String,
    type: Int,
    navController: NavController,
    modifier: Modifier = Modifier
) {

    val decodeTitle = Uri.decode(title1)
    val decodeContent = Uri.decode(desc1)
    var title by remember { mutableStateOf(decodeTitle) }
    var content by remember { mutableStateOf(TextFieldValue(decodeContent)) }
    val context = LocalContext.current
    var tts: TextToSpeech? by remember { mutableStateOf(null) }
    val translatedText by notesViewmodel.translatedText
    val summaryText by notesViewmodel.summariesText
    val isLoading by notesViewmodel.fetch.collectAsState()
    val selectedText = remember { mutableStateOf("") }
    var isShow by remember { mutableStateOf(false) }
    val languages = listOf("Hindi", "English", "Spanish", "French","Japanese","Sanskrit","Korean")
    var selectedLanguage = remember { mutableStateOf(languages[0]) }


    DisposableEffect(Unit) {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale("en", "IN")
            }
        }
        onDispose {
            tts?.stop()
            tts?.shutdown()
        }
    }
    LaunchedEffect(summaryText) {
        if (summaryText.isNotEmpty()) {
            content = content.copy(
                text = content.text + "\n\n" + summaryText
            )
            notesViewmodel.summariesText.value=""
        }
    }

    LaunchedEffect(translatedText) {
        if (translatedText.isNotEmpty()) {
            content = content.copy(
                text = content.text + "\n\n" + translatedText
            )
            notesViewmodel.translatedText.value=""
        }
    }
    Scaffold (modifier = Modifier.fillMaxSize()){ innerPadding->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
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
                            if (content.text.isNotEmpty()) {
                                if (title.isEmpty()) {
                                    title =
                                        content.text.substring(0, content.text.length - (content.text.length - 5))
                                }
                                if (type == 1) {
                                    notesViewmodel.addNotes(
                                        notes = Notes(
                                            title = title,
                                            content = content.text,
                                            timeStamp = System.currentTimeMillis()
                                        )
                                    )
                                } else {
                                    notesViewmodel.updateNotes(
                                        notes = Notes(
                                            id = id ?: 0,
                                            title = title,
                                            content = content.text,
                                            timeStamp = System.currentTimeMillis()
                                        )
                                    )
                                }

                            } else {
                                Toast.makeText(
                                    context,
                                    "Please fill the fields",
                                    Toast.LENGTH_SHORT
                                )
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
            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = content,
                    onValueChange = { newVal->
                       content=newVal
                    } ,
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
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = 200.dp)
                )

                Column(
                    modifier = Modifier
                        .height(300.dp)
                        .clip(shape = RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(horizontal = 5.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Icon(
                        painter = painterResource(R.drawable.volume),
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable(onClick = {
                                tts?.speak(content.text, TextToSpeech.QUEUE_FLUSH, null, null)
                            }),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    if (isLoading == ApiState.Summary) {
                        CircularProgressIndicator(Modifier.size(30.dp))
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.tool),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                                .clickable(onClick = {
                                    try {
                                        val selected=content.selection
                                        if(selected.start != selected.end) {
                                            val selectedText = content.text.substring(selected.start, selected.end)
                                            notesViewmodel.getSummaries(content = selectedText)
                                        }
                                        else{
                                            Toast.makeText(
                                                context,
                                                "Please selected some text. ",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    } catch (e: Exception) {
                                        Toast.makeText(
                                            context,
                                            "not connected to Internet ",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    if (isLoading == ApiState.Translate) {
                        CircularProgressIndicator(Modifier.size(30.dp))
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.languageone),
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                                .clickable(onClick = {
                                    try {
                                        val selected=content.selection
                                        if(selected.start != selected.end) {
                                            val selectedText = content.text.substring(selected.start, selected.end)
                                            isShow=true
                                        Log.d("translate", selectedText.toString())
                                        }else{
                                            Toast.makeText(
                                                context,
                                                "Please selected some text. ",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                        Log.d("translate", translatedText)
                                    } catch (e: Exception) {
                                        Toast.makeText(
                                            context,
                                            "not connected to Internet ",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }


                }
            }

        }
    }


    if(isShow){
        val selected=content.selection
        if(selected.start != selected.end) {
            val selectedText = content.text.substring(selected.start, selected.end)
            LanguageDialog(onDismiss = {isShow = !isShow}, notesViewmodel = notesViewmodel, selectedLanguage = selectedLanguage, selectedText = selectedText, languages = languages)
        }
    }


}

fun formatTime(time: Long): String {
    val date = Date(time)
    val formatter = SimpleDateFormat("h:mm a", Locale.getDefault())
    return formatter.format(date)
}