package com.example.composeplayground

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composeplayground.ui.theme.ComposePlaygroundTheme

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    private val viewmodel by viewModels<DemoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePlaygroundTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = { FloatingActionButtonComposable() }
                ) { innerPadding ->
                    navController = rememberNavController()
//                    ChangeBgWithVM(viewmodel, modifier = Modifier.padding(innerPadding))
                    SetUpNavGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

const val DETAILS_ARGUMENT_KEY = "id"
const val DETAILS_ARGUMENT_KEY2 = "Name"

sealed class Screen(val route: String) {
    data object Home : Screen("home_screen")
    data object Details : Screen("details_screen?id={$DETAILS_ARGUMENT_KEY}") {
        fun passId(id: Int = 0): String = "details_screen?id=$id"
    }

    data object About : Screen("about_screen")
}

@Composable
fun SetUpNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) { HomeScreen(navController = navController) }
        composable(
            route = Screen.Details.route,
            arguments = listOf(
                navArgument(name = DETAILS_ARGUMENT_KEY) {
                    type = NavType.IntType
                    defaultValue = 0
                },
//                navArgument(name = DETAILS_ARGUMENT_KEY2) {
//                    type = NavType.StringType
//                }
            )) {
            Log.d("Args", "SetUpNavGraph: ${it.arguments?.getInt(DETAILS_ARGUMENT_KEY).toString()}")
            DetailsScreen(navController = navController)
        }
        composable(Screen.About.route) { AboutScreen(navController = navController) }
    }
}

@Composable
fun NavigationComposable(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "first_screen") {
        composable("first_screen") { HomeScreen(navController = navController) }
        composable("second_screen") { DetailsScreen(navController = navController) }
    }

}

@Composable
fun HomeScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "This is Home Screen",
                color = MaterialTheme.colorScheme.primary,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                fontWeight = MaterialTheme.typography.headlineLarge.fontWeight
            )
            Spacer(modifier = Modifier.size(15.dp))
            Button(onClick = {
                navController.navigate(
                    route = Screen.Details.passId()
                )
            }) {
                Text(text = "Go to Details Screen")
            }
        }

    }
}

@Composable
fun DetailsScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "This is Details Screen",
                color = Color.Red,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                fontWeight = MaterialTheme.typography.headlineLarge.fontWeight
            )
            Spacer(modifier = Modifier.size(15.dp))
            Button(onClick = {
                navController.navigate(Screen.About.route) {
                    popUpTo(Screen.About.route) {
                        inclusive = true
                    }
                }
            }) {
                Text(text = "Go to About Screen")
            }
            Spacer(modifier = Modifier.size(15.dp))
            Button(onClick = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route) {
                        inclusive = true
                    }
                }
            }) {
                Text(text = "Go back to Home Screen")
            }
        }

    }
}

@Composable
fun AboutScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "This is About Screen",
                color = Color.Red,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                fontWeight = MaterialTheme.typography.headlineLarge.fontWeight
            )
            Spacer(modifier = Modifier.size(15.dp))
            Button(onClick = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route) {
                        inclusive = true
                    }
                }
            }) {
                Text(text = "Go to Next Screen")
            }
        }

    }
}

@Composable
fun ChangeBgWithVM(viewModel: DemoViewModel, modifier: Modifier = Modifier) {
    val bgColor = viewModel.bgColor
    val colorName = if (viewModel.bgColor == Color.Gray) "Gray" else "Dark Gray"
    var isClicked by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
    ) {
        Text(text = "Current color is $colorName")
        Spacer(modifier = Modifier.size(12.dp))
        Button(onClick = {
            isClicked = !isClicked
            viewModel.changeBgColor(isClicked)
        }) {
            Text(text = "Change Color")
        }
    }
}

@Composable
fun TextComposable() {
    Text(
        text = "This is a basic text",
        modifier = Modifier
            .padding(10.dp)
            .background(Color.Cyan)
    )
}

@Composable
fun FloatingActionButtonComposable() {
    FloatingActionButton(onClick = { /*TODO*/ }) {
        Icon(Icons.Filled.Add, "Floating Action Button")
    }
}

@Composable
fun LazyListTypeComposable() {
    LazyColumn {
        items(count = 10) {
            Text(text = "item --> " + (1 + it), modifier = Modifier.padding(10.dp))
        }
    }
}

@Composable
fun CardListComposable(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var cardCount by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row {
            Button(onClick = {
                if (cardCount > 0) {
                    cardCount--
                } else {
                    Toast.makeText(context, "No item/s left to remove", Toast.LENGTH_SHORT).show()
                }
            }) {
                Icon(imageVector = Icons.Rounded.Close, contentDescription = null)
                Text(text = "Decrease")
            }
            Button(onClick = { cardCount++ }) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
                Text(text = "Increase")
            }
        }
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(cardCount) {
                Box(
                    modifier = Modifier.fillParentMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.coffee),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(200.dp),
                    )
                }
            }
        }
    }
}

@Composable
fun TextEditFieldComposable(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        TextField(
            value = text,
            onValueChange = { value ->
                text = value
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Rounded.AccountBox,
                    contentDescription = null
                )
            },
            shape = CircleShape,
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,

                ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    Log.d("EditableCompose", "TextEditFieldComposable: It's done")
                }
            ),
        )
    }
}

@Composable
fun GridComposable() {
    LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2)) {
        items(10) {
            val isEven = it % 2 == 0
//            Box(modifier = Modifier
//                .size(if (isEven) 200.dp else 100.dp)
//                .padding(10.dp)
//                .background(Color.Blue)
//            )
//            Icon(imageVector = Icons.Default.AccountBox, contentDescription = "Profile_image",
//                modifier = Modifier
//                    .size(if (isEven) 200.dp else 100.dp)
//                    .padding(10.dp)
//            )
            Image(
                painter = painterResource(id = R.drawable.coffee), contentDescription = "coffee",
                modifier = Modifier
                    .size(if (isEven) 200.dp else 100.dp)
                    .padding(10.dp)
            )
        }
    }
}

@Composable
fun ButtonComposable(modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(false) }
    var bgColor by remember { mutableStateOf(Color.Cyan) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Button")
            }
            OutlinedButton(onClick = { /*TODO*/ }) {
                Text(text = "outline Button")
            }
            FilledTonalButton(onClick = { /*TODO*/ }) {
                Text(text = "Filled tonal Button")
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
            OutlinedIconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
            FilledIconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
            Switch(
                checked = checked,
                onCheckedChange = {
                    checked = it
//                    bgColor = if(checked) Color.Gray else Color.Cyan
                    bgColor = if (checked) Color.Gray else Color.Cyan
                },
                thumbContent = if (checked) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                        )
                    }
                } else {
                    null
                }
            )
        }
    }
}

@Composable
fun ImageComposable() {
//    Icon(imageVector = Icons.Default.AccountBox, contentDescription = "Profile_image")
    Box(
        modifier = Modifier
            .size(500.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.coffee),
            contentDescription = "coffee",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}


@Composable
fun SizeAndAlignmentComposable() {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "Text 1")
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = "Text 2")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Sup $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun MyComposablePreview() {
    ComposePlaygroundTheme {
        ButtonComposable()
    }
}