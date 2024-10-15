package com.example.composeplayground

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.composeplayground.data.dataSource.local.getFakeChannel
import com.example.composeplayground.data.dataSource.local.getFakeVideos
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
//                    floatingActionButton = { FloatingActionButtonComposable() }
                ) { innerPadding ->
//                    navController = rememberNavController()
//                    ChangeBgWithVM(viewmodel, modifier = Modifier.padding(innerPadding))
//                    SetUpNavGraph(
//                        navController = navController,
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                    VideoActivityScreen(name = "From Video Player", modifier = Modifier.padding(innerPadding))

                    ChannelActivityScreen(modifier = Modifier.padding(innerPadding))

                }
            }
        }
    }
}


@Composable
fun ChannelTabs() {
    val tabTitles = listOf("Videos", "Playlists")
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column {
        // Tab Row for Tabs
        Box(
            modifier = Modifier.fillMaxWidth(0.5f)
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                contentColor = colorResource(R.color.black),
                divider = {
                    colorResource(R.color.red_500)
                },
                indicator = { tabPositions ->
                    TabRowDefaults.PrimaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = colorResource(R.color.red_500)
                    )
                }
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }
        }

        // Tab content
        when (selectedTabIndex) {
            0 -> VideosTabContent()
            1 -> PlaylistsTabContent()
        }
    }
}

@Composable
fun VideosTabContent() {
    val videos = getFakeVideos()
    LazyColumn {
        items(videos.size) { index ->
            VideoItem(
                videoTitle = videos[index].title,
                views = "${index * 2}K views",
                duration = "2:$index",
                imageUrl = videos[index].highThumbnailUrl
            )
        }
    }
}

@Composable
fun PlaylistsTabContent() {
    val videoPlayLists = getFakeVideos()
    LazyColumn {
        items(videoPlayLists.size) { index ->
            PlayListItems(videoPlayLists[index].title, videoPlayLists[index].highThumbnailUrl, index)
        }
    }
}

@Composable
fun PlayListItems(playlistTitle: String, imageUrl: String, count: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(130.dp, 75.dp)
                .clip(shape = RoundedCornerShape(8.dp))
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp, 40.dp)
                    .offset(y = (-15).dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(colorResource(R.color.grey_400)),
                contentAlignment = Alignment.Center
            ) {
            }
            Box (
                modifier = Modifier
                    .size(130.dp, 70.dp)
                    .padding(top = 5.dp)
                    .clip(shape = RoundedCornerShape(5.dp))
                    .background(Color.Gray)
            ) {
                // Load image from URL using Coil
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(4.dp)
                        .background(Color.Black.copy(alpha = 0.7f), shape = RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 4.dp)  // Inner padding for the content
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Icon (e.g., a playlist icon)
                        Icon(
                            painter = painterResource(id = R.drawable.ic_filter),  // Replace with your actual icon resource
                            contentDescription = "Playlist Icon",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))

                        // Number (e.g., count of items in the playlist)
                        Text(
                            text = count.toString(),
                            color = Color.White,
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        )
                    }
                }
            }

        }
        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = playlistTitle,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

}

@Composable
fun VideoItem(videoTitle: String, views: String, duration: String, imageUrl: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Thumbnail (using a placeholder here)
        Box(
            modifier = Modifier
                .size(130.dp, 70.dp)
                .clip(shape = RoundedCornerShape(5.dp))
                .background(Color.Gray)
        ) {
            // Load image from URL using Coil
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Display the duration text at the bottom right
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(4.dp)
                    .background(
                        colorResource(R.color.black).copy(alpha = 0.7f),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = duration,
                    color = Color.White,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 10.sp)
                )
            }

        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = videoTitle,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = views, style = TextStyle(color = Color.Gray))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ChannelActivityScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            ChannelActivityHeader()
            TabView()
        }
    }
}

@Composable
fun ChannelActivityHeader(modifier: Modifier = Modifier) {
    val channelInfo = getFakeChannel()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.25f)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 30.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(colorResource(R.color.grey_400), shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.AccountBox,
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier.size(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxHeight(0.5f)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = channelInfo.title,
                            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(
                            text = channelInfo.customUrl,
                            style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(
                            text = channelInfo.subscriberCount.toString(),
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = colorResource(R.color.grey_600)
                            )
                        )
                    }
                }
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.grey_300),
                    contentColor = Color.Black
                )
            ) {
                Text(text = if (channelInfo.subscribed) "Unsubscribed" else "Subscribe")
            }
        }
    }
}

@Composable
fun VideoActivityScreen(name: String, modifier: Modifier = Modifier) {
    val likeCount = remember { mutableIntStateOf(10) }
    val liked = remember { mutableStateOf(false) }
    val disliked = remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .background(Color.Black)
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Hello $name!",
                style = TextStyle(background = Color.White),
                modifier = modifier
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        Box(
            modifier = Modifier.padding(12.dp)
        ) {
            Column {
                Text(
                    text = "Content Title Text",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "38.5K views",
                    style = TextStyle(fontSize = 12.sp, color = colorResource(R.color.grey_600))
                )
                Spacer(modifier = Modifier.size(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .height(80.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(colorResource(R.color.grey_200), shape = CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.AccountBox,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(40.dp)
                                    .align(Alignment.Center)
                            )
                        }
                        Spacer(modifier = Modifier.size(5.dp))
                        Column(
                            modifier = Modifier.size(180.dp, 40.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Rokomari.com",
                                style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.size(115.dp, 40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White
                        ),
                    ) {
                        Text(text = "Subscribe")
                    }
                }
                Row {
                    Surface(
                        modifier = Modifier
                            .height(40.dp)
                            .width(140.dp)
                            .clip(CircleShape),
                        shape = CircleShape,
                        color = colorResource(R.color.grey_200),
                        shadowElevation = 4.dp
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                        ) {
                            // Like Section
                            Row(
                                modifier = Modifier
                                    .weight(1.5f)
                                    .fillMaxHeight()
                                    .clickable {
                                        if (!liked.value) {
                                            liked.value = true
                                            disliked.value = false
                                            likeCount.value += 1
                                        } else {
                                            liked.value = false
                                            likeCount.value -= 1
                                        }
                                    },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = if (liked.value) R.drawable.ic_like_filled else R.drawable.ic_like),
                                    contentDescription = "Like",
                                    modifier = Modifier.size(24.dp)
                                )
                                Text(
                                    text = "${likeCount.intValue}K",
                                    color = Color.Black,
                                    modifier = Modifier.padding(start = 4.dp)
                                )
                            }

                            // Vertical Separator
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight(0.6f)
                                    .width(1.dp)
                                    .background(Color.Gray)
                            )

                            // Dislike Section
                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .fillMaxWidth()
                                    .clickable {
                                        if (!disliked.value && likeCount.intValue > 0) {
                                            disliked.value = true
                                            liked.value = false
                                            likeCount.value -= 1
                                        } else {
                                            disliked.value = false
                                            likeCount.value += 1
                                        }
                                    },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = if (disliked.value) R.drawable.ic_dislike_filled else R.drawable.ic_dislike),
                                    contentDescription = "Dislike",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.size(115.dp, 40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.grey_200),
                            contentColor = Color.Black
                        ),
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_share),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.size(5.dp))
                        Text(text = "Share")
                    }
                }

            }
        }
        Text(
            modifier = Modifier.padding(12.dp),
            text = "Comments",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
        )
        Box(
            modifier = Modifier.padding(12.dp)
        ) {
            LazyColumn {
                items(50) { count ->
                    CommentCard(count + 1)
                    Spacer(modifier = Modifier.size(10.dp))
                }
            }
        }
    }
}

@Composable
fun CommentCard(count: Int) {
    Box {
        Row {
            Box(
                modifier = Modifier
                    .size(25.dp)
                    .background(colorResource(R.color.grey_400), shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Rounded.AccountBox,
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.size(5.dp))
            Column {
                Text(
                    text = "@RokomariOfficial",
                    style = TextStyle(fontSize = 12.sp, color = colorResource(R.color.grey_600))
                )
                Text(text = "This is a very good comment! $count")
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
            )
        ) {
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
                Text(text = "Go Back to Home Screen")
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

@Composable
fun MyComposablePreview() {
    ComposePlaygroundTheme {
        ButtonComposable()
    }
}
