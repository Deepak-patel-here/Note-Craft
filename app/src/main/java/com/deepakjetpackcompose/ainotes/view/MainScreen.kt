package com.deepakjetpackcompose.ainotes.view

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.staggeredgrid.items
import com.deepakjetpackcompose.ainotes.model.database.Notes
import com.deepakjetpackcompose.ainotes.R
import com.deepakjetpackcompose.ainotes.navigation.NavigationDestination
import com.deepakjetpackcompose.ainotes.util.NotePreview

import com.deepakjetpackcompose.ainotes.util.TopBar
import com.deepakjetpackcompose.ainotes.viewmodel.NotesViewmodel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource

//STEP 1:first chnge the navigationdestion apply navigation using arguments,
//step 2:
const val FOR_ADD: Int=1
const val FOR_UPDATE: Int=2
const val idI=0
@Composable
fun MainScreen(notesViewmodel: NotesViewmodel,navController: NavController,modifier: Modifier = Modifier) {
    val allNotes=notesViewmodel.allTask.collectAsState(initial = emptyList<Notes>())
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("${NavigationDestination.AddOrUpdate.routes}?title=&description=&type=$FOR_ADD&id=$idI")
            },
                containerColor = MaterialTheme.colorScheme.primaryContainer) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        },
        topBar = {
            TopBar(notesViewmodel=notesViewmodel,modifier=Modifier.statusBarsPadding())
        }
    ) {innerPadding->
        Column (modifier=modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(horizontal = 20.dp)){
            if(allNotes.value.isEmpty()){
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Image(
                        painter = painterResource(R.drawable.empty),
                        contentDescription = null,
                        modifier = Modifier.size(80.dp).alpha(0.7f)
                    )
                }
            }else {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    verticalItemSpacing = 12.dp,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(allNotes.value) { note ->
                        val encodedTitle = Uri.encode(note.title)
                        val encodedDescription = Uri.encode(note.content)
                        NotePreview(onClick2 = {
                            navController.navigate("${NavigationDestination.AddOrUpdate.routes}?title=$encodedTitle&description=$encodedDescription&type=$FOR_UPDATE&id=${note.id}")
                        }, onClick = { notesViewmodel.deleteNotes(note) }, notes = note)
                    }
                }
            }

        }

    }

}

