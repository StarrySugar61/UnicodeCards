/*
 * Copyright (C) 2024 StarrySugar
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the 
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see <https://www.gnu.org/licenses/>.
 */
package starrysugar.unicodecards.app.ui.common.paging

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * @author StarrySugar61
 * @create 2024/6/18
 */

@Composable
fun <T : Any> AppLazyPagingList(
    modifier: Modifier = Modifier,
    lazyPagingItems: LazyPagingItems<T>,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    itemKey: ((index: Int) -> Any)? = null,
    itemContentType: (index: Int) -> Any? = { null },
    itemContent: @Composable LazyItemScope.(index: Int, item: T) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        state = state,
        contentPadding = contentPadding,
        reverseLayout = reverseLayout,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
    ) {
        items(
            count = lazyPagingItems.itemCount,
            key = itemKey,
            contentType = itemContentType,
        ) { index ->
            lazyPagingItems[index]?.let { item ->
                itemContent(index, item)
            }
        }

    }
}

@Composable
fun <T : Any> AppLazyPagingVerticalGrid(
    modifier: Modifier = Modifier,
    columns: GridCells,
    lazyPagingItems: LazyPagingItems<T>,
    state: LazyGridState = rememberLazyGridState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    itemKey: ((index: Int) -> Any)? = null,
    itemContentType: (index: Int) -> Any? = { null },
    itemContent: @Composable LazyGridItemScope.(index: Int, item: T) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = columns,
        state = state,
        contentPadding = contentPadding,
        reverseLayout = reverseLayout,
        verticalArrangement = verticalArrangement,
        horizontalArrangement = horizontalArrangement,
    ) {
        items(
            count = lazyPagingItems.itemCount,
            key = itemKey,
            contentType = itemContentType,
        ) { index ->
            lazyPagingItems[index]?.let { item ->
                itemContent(index, item)
            }
        }

    }
}