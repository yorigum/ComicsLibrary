package com.yoriworks.comiclibrary.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.yoriworks.comiclibrary.CharacterImage
import com.yoriworks.comiclibrary.Destination
import com.yoriworks.comiclibrary.comicsToString
import com.yoriworks.comiclibrary.ui.theme.BlackBackground
import com.yoriworks.comiclibrary.ui.theme.PurpleActions
import com.yoriworks.comiclibrary.ui.theme.PurpleDisable
import com.yoriworks.comiclibrary.ui.theme.gradientCustom
import com.yoriworks.comiclibrary.viewmodel.CollectionDbViewModel
import com.yoriworks.comiclibrary.viewmodel.LibraryApiViewModel

@Composable
fun CharacterDetailScreen(
    lvm: LibraryApiViewModel,
    cvm: CollectionDbViewModel,
    paddingValues: PaddingValues,
    navController: NavHostController
) {
    val character = lvm.characterDetails.value
    val collection by cvm.collection.collectAsState()
    val inCollection = collection.map { it.apiId }.contains(character?.id)

    if (character == null) {
        navController.navigate(Destination.Library.route) {
            popUpTo(Destination.Library.route)
            launchSingleTop = true
        }
    }

    LaunchedEffect(key1 = Unit) {
        cvm.setCurrentCharacterId(character?.id!!)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = paddingValues.calculateBottomPadding())
            .verticalScroll(
                rememberScrollState()
            ), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imageUrl = character?.thumbnail?.path + "." + character?.thumbnail?.extension
        val title = character?.name ?: "No Name"
        val comics =
            character?.comics?.items?.mapNotNull { it.name }?.comicsToString() ?: "No Comics"
        val description = character?.description ?: "No description"

        Box(modifier = Modifier.weight(1f,true)){
            CharacterImage(
                url = imageUrl, modifier = Modifier
                    .fillMaxSize(1f)
            )
            Column(modifier = Modifier
                .fillMaxSize(1f)
                .background(gradientCustom(BlackBackground)), verticalArrangement = Arrangement.Bottom) {
    
                Text(text = title, style = MaterialTheme.typography.h1, modifier = Modifier.padding(horizontal = 4.dp))
                Text(text = description, style = MaterialTheme.typography.h3, modifier = Modifier.padding(4.dp))
        
                Button(onClick = {
                    if (!inCollection && character != null) cvm.addCharacter(character)
                }, modifier = Modifier.padding(vertical = 8.dp), colors = ButtonDefaults.buttonColors(backgroundColor = PurpleActions, disabledBackgroundColor = PurpleDisable), shape = RoundedCornerShape(30.dp)) {
                    if (!inCollection) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null)
                            Text(text = "Add to collection")
                        }
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null)
                            Text(text = "Added")
                        }
                    }
                }
            }
            
        }

       

    }

}