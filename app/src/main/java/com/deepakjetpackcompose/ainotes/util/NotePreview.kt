package com.deepakjetpackcompose.ainotes.util

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deepakjetpackcompose.ainotes.model.database.Notes

import com.deepakjetpackcompose.ainotes.ui.theme.AiNotes
import com.deepakjetpackcompose.ainotes.ui.theme.UbuntuFont




@Composable
fun NotePreview(onClick2:()->Unit,onClick:()-> Unit,notes: Notes, modifier: Modifier = Modifier) {
    val title = if(notes.title.length>25) notes.title.substring(0,26)+"..." else notes.title
    val content = if(notes.content.length>100) notes.content.substring(0,101)+"..." else notes.content
    val time=notes.timeStamp

    AiNotes {
        Surface (
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .clickable(onClick={onClick2()}),
            shape = RoundedCornerShape(12.dp)
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceContainerHighest)
                    .padding(10.dp),
            ) {
                Text(
                    title,
                    fontFamily = UbuntuFont,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
                Text(
                    content,
                    fontFamily = UbuntuFont,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .size(35.dp)
                            .clickable(onClick = {
                                onClick()
                            })
                    )

                    Text(
                        text = formatTime(notes.timeStamp),
                        fontFamily = UbuntuFont,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                }

            }
        }
    }
}

@Preview
@Composable
private fun normal() {
    NotePreview(onClick2 = {}, onClick = {},
        Notes(
            id = 1,
            title = "hello guys",
            content = "hello guys app log kaise ho mein toh theek hu asha krta hu aap sab bhi theek hinge.",
            timeStamp =2L
        )
    )
}