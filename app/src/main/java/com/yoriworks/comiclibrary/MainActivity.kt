package com.yoriworks.comiclibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yoriworks.comiclibrary.ui.theme.ComicsLibraryTheme
import com.yoriworks.comiclibrary.view.CharacterBottomNav
import com.yoriworks.comiclibrary.view.CharacterDetailScreen
import com.yoriworks.comiclibrary.view.CollectionScreen
import com.yoriworks.comiclibrary.view.LibraryScreen
import dagger.hilt.android.AndroidEntryPoint

sealed class Destination(val route:String){
    object Library:Destination("library")
    object Collection:Destination("collection")
    object CharacterDetail:Destination("character/{characterId}"){
        fun createRoute(characterId:Int?) ="character/$characterId"
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComicsLibraryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    CharactersScaffold(navController=navController)
                    
                }
            }
        }
    }
}

@Composable
fun CharactersScaffold(navController: NavHostController){
   val scaffoldState = rememberScaffoldState()
    
    Scaffold(scaffoldState = scaffoldState, bottomBar = { CharacterBottomNav(navController = navController)}) { _ ->
        NavHost(navController = navController,startDestination = Destination.Library.route){
            composable(Destination.Library.route){
                LibraryScreen()
            }
            composable(Destination.Collection.route){
                CollectionScreen()
            }
            composable(Destination.CharacterDetail.route){
            
            }
        }
    }
}
