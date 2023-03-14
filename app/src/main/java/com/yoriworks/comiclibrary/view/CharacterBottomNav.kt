package com.yoriworks.comiclibrary.view

import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.yoriworks.comiclibrary.Destination
import com.yoriworks.comiclibrary.R
import com.yoriworks.comiclibrary.ui.theme.BlackBackground
import com.yoriworks.comiclibrary.ui.theme.PurpleActions

@Composable
fun CharacterBottomNav(navController: NavHostController) {
    BottomNavigation(elevation = 5.dp, backgroundColor = BlackBackground) {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry.value?.destination
        
        val iconLibrary = painterResource(id = R.drawable.ic_home)
        val iconCollection = painterResource(id = R.drawable.ic_bookmark)
        
        BottomNavigationItem(
            selected = currentDestination?.route == Destination.Library.route,
            onClick = {
                navController.navigate(Destination.Library.route) {
                    popUpTo(Destination.Library.route)
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    painter = iconLibrary,
                    contentDescription = null,
                    Modifier
                        .width(32.dp)
                )
            },
            label = {
                Text(
                    text = Destination.Library.route,
                    style = MaterialTheme.typography.body2, color = PurpleActions
                )
            },
            unselectedContentColor = Color.White,
            selectedContentColor = PurpleActions, alwaysShowLabel = false
        )
        
        BottomNavigationItem(
            selected = currentDestination?.route == Destination.Collection.route,
            onClick = {
                navController.navigate(Destination.Collection.route) {
                    popUpTo(Destination.Collection.route)
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    painter = iconCollection,
                    contentDescription = null,
                    Modifier
                        .width(32.dp)
                )
            },
            label = {
                Text(
                    text = Destination.Collection.route,
                    style = MaterialTheme.typography.body1, color = PurpleActions
                )
            },
            unselectedContentColor = Color.White,
            selectedContentColor = PurpleActions, alwaysShowLabel = false
        )
    }
}