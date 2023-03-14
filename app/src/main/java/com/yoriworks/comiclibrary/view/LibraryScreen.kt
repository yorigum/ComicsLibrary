package com.yoriworks.comiclibrary.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.yoriworks.comiclibrary.AttributionText
import com.yoriworks.comiclibrary.CharacterImage
import com.yoriworks.comiclibrary.Destination
import com.yoriworks.comiclibrary.R
import com.yoriworks.comiclibrary.model.CharacterResult
import com.yoriworks.comiclibrary.model.CharacterThumbnail
import com.yoriworks.comiclibrary.model.CharactersApiResponse
import com.yoriworks.comiclibrary.model.api.NetworkResult
import com.yoriworks.comiclibrary.model.connectivity.ConnectivityObservable
import com.yoriworks.comiclibrary.ui.theme.BlackBackground
import com.yoriworks.comiclibrary.ui.theme.DarkGrey
import com.yoriworks.comiclibrary.ui.theme.gradientCustom
import com.yoriworks.comiclibrary.viewmodel.LibraryApiViewModel

@Composable
fun LibraryScreen(
    navController: NavHostController, vm: LibraryApiViewModel, paddingValues: PaddingValues
) {
    val result by vm.result.collectAsState()
    val text = vm.queryText.collectAsState()
    val networkAvailable = vm.networkAvailable.observe()
        .collectAsState(initial = ConnectivityObservable.Status.Available)
    val showAsList = remember { mutableStateOf(true) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackBackground)
            .padding(bottom = paddingValues.calculateBottomPadding()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (networkAvailable.value == ConnectivityObservable.Status.Unavailable) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Red),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Network unavailable",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        
        SearchCharacterBar(vm = vm, text)
        PickLibraryShow(showAsList)
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (result) {
                is NetworkResult.Initial -> {
                    Text(text = "Search for a character")
                }
                
                is NetworkResult.Success -> {
                    ShowCharacters(result, navController, showAsList)
                }
                
                is NetworkResult.Loading -> {
                    CircularProgressIndicator()
                }
                
                is NetworkResult.Error -> {
                    Text(text = "Error: ${result.message}")
                }
            }
        }
        
    }
}

@Composable
fun ShowCharacters(
    result: NetworkResult<CharactersApiResponse>,
    navController: NavHostController,
    showAsList: MutableState<Boolean>
) {
    if (showAsList.value) {
        LazyColumnMarvel(result, navController)
    } else {
        LazyVerticalMarvel(result, navController)
    }
    
}

@Composable
fun LazyVerticalMarvel(
    result: NetworkResult<CharactersApiResponse>, navController: NavHostController
) {
    
    val list = result.data?.data?.results
    
    LazyVerticalGrid(columns = GridCells.Fixed(2),
        
        // content padding
        contentPadding = PaddingValues(
            12.dp
        ), content = {
            list?.let {
                items(it.size) { index ->
                    val character = list[index]

                    val id = character.id
    
                    val context = LocalContext.current
                    Card(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                            .clickable {
                                if (id != null) navController.navigate(
                                    Destination.CharacterDetail.createRoute(
                                        id
                                    )
                                )
                                else Toast
                                    .makeText(
                                        context, "Character id is null", Toast.LENGTH_SHORT
                                    )
                                    .show()
                            },
                        elevation = 0.dp,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        GridCharacterItem(character)
                    }
                }
            }
            
        })
}
@Preview
@Composable
fun GridCharacterItem(character: CharacterResult = CharacterResult(0,"Title","subtitle","",null,
    CharacterThumbnail("","jpg"),null
)
) {
    val imageUrl = character.thumbnail?.path + "." + character.thumbnail?.extension
    val title = character.name
    val description = character.description
    Box(modifier = Modifier.fillMaxSize(1f)){
        CharacterImage(
            url = imageUrl,
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(240.dp),
        )
        Column(verticalArrangement = Arrangement.Bottom, modifier = Modifier.fillMaxSize(1f).align(
            Alignment.BottomStart).background(gradientCustom(Color.Black)).clip(RoundedCornerShape(12.dp))) {
            Text(
                text = "$title" ?: "",
                fontSize = 14.sp,
                style = MaterialTheme.typography.h1,
                modifier = Modifier.padding(4.dp).fillMaxSize(1f)
            )
        }
       
    }
}

@Composable
fun LazyColumnMarvel(data: NetworkResult<CharactersApiResponse>, navController: NavHostController) {
    LazyColumn(
        verticalArrangement = Arrangement.Top
    ) {
        data.data?.attributionText?.let {
            item {
                AttributionText(text = it)
            }
        }
        val characters = data.data?.data?.results
        
        items(characters!!.size) { index ->
            val character = characters[index]
            val imageUrl = character.thumbnail?.path + "." + character.thumbnail?.extension
            val title = character.name
            val description = character.description
            val context = LocalContext.current
            val id = character.id
            
            Column(modifier = Modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(DarkGrey)
                .padding(4.dp)
                .fillMaxWidth()
                .clickable {
                    if (character.id != null) navController.navigate(
                        Destination.CharacterDetail.createRoute(
                            id
                        )
                    )
                    else Toast
                        .makeText(context, "Character id is null", Toast.LENGTH_SHORT)
                        .show()
                }) {
                
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                    
                        CharacterImage(
                            url = imageUrl,
                            modifier = Modifier
                                .padding(4.dp)
                                .width(150.dp)
                                .height(192.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                    Column(modifier = Modifier.padding(4.dp)) {
                        Text(text = title ?: "", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Text(text = description ?: "", maxLines = 4, fontSize = 14.sp)
                    }
                }
            }
        }
    }
    
}

//@Preview
@Composable
fun SearchCharacterBar(vm: LibraryApiViewModel, text: State<String>) {
    Column(Modifier.padding(top = 8.dp)) {
        TextField(
            value = text.value,
            onValueChange = vm::onQueryUpdate,
            shape = RoundedCornerShape(24.dp),
            singleLine = true,
            trailingIcon = {
                Icon(Icons.Filled.Search, "", tint = Color.DarkGray)
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = DarkGrey,
                focusedIndicatorColor = Color.Transparent, //hide the indicator
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) //hide the indicator )
    }
}

@Preview(showBackground = true)
@Composable
fun PickLibraryShow(showAsList: MutableState<Boolean> = mutableStateOf(true)) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 0.dp),
        horizontalArrangement = Arrangement.End
    ) {
        
        IconButton(onClick = { showAsList.value = true }) {
            Icon(
                Icons.Filled.List,
                contentDescription = "",
                tint = Color.LightGray,
                modifier = Modifier.width(24.dp)
            )
        }
        IconButton(onClick = { showAsList.value = false }) {
            Icon(
                painterResource(id = R.drawable.ic_grid),
                contentDescription = "",
                tint = Color.LightGray,
                modifier = Modifier.width(24.dp)
            )
        }
    }
}
