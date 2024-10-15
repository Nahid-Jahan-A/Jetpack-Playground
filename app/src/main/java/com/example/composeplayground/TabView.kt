package com.example.composeplayground

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.composeplayground.data.dataSource.local.TabItem

@Composable
fun TabView() {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabItems = listOf(
        TabItem(
            title = "Videos",
            unSelectedColor = colorResource(R.color.grey_600),
            selectedColor = colorResource(R.color.black)
        ),
        TabItem(
            title = "Playlists",
            unSelectedColor = colorResource(R.color.grey_600),
            selectedColor = colorResource(R.color.black)
        )
    )

    val pagerState = rememberPagerState { tabItems.size }

    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress)
            selectedTabIndex = pagerState.currentPage
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth(0.5f),
            divider = {},
//            indicator = { tabPositions ->
//                TabRowDefaults.PrimaryIndicator(
//                    color = colorResource(R.color.black),
//                    modifier = Modifier
//                        .tabIndicatorOffset(tabPositions[selectedTabIndex])
//                        .width(10.dp)
//                )
//            }
            indicator = { tabPositions ->
                TabRowDefaults.PrimaryIndicator(
                    color = colorResource(R.color.black),
                    width = 50.dp,
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex]))
            }
            ) {
            tabItems.forEachIndexed { index, tabItem ->
                Tab(
                    text = {
                        Text(
                            text = tabItem.title,
                            color = if (selectedTabIndex == index) tabItem.selectedColor else tabItem.unSelectedColor
                        )
                    },
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        Log.d("TabRow", "TabView: logging tab index on tab row --> $index")
                    }
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(colorResource(R.color.grey_300))
        )

        HorizontalPager(
            verticalAlignment = Alignment.Top,
            beyondViewportPageCount = selectedTabIndex,
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .weight(0.5f)
        ) { index ->
            when (index) {
                0 -> VideosTabContent()
                1 -> PlaylistsTabContent()
            }
        }
    }
}
