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
package starrysugar.unicodecards.app.ui.main.settings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * Setting Column!
 *
 * @author StarrySugar61
 * @create 2024/7/6
 */
@Composable
fun SettingColumn(
    modifier: Modifier = Modifier,
    dataStore: DataStore<Preferences>,
    content: LazySettingColumnScope.() -> Unit
) {
    val scope = rememberCoroutineScope()
    LazyColumn(
        modifier = modifier,
        content = {
            toLazySettingColumnScope(
                dataStore,
                scope,
            ).content()
        }
    )
}

fun LazySettingColumnScope.switcherRow(
    key: Preferences.Key<Boolean>,
    default: () -> Boolean = { false },
    labelRes: StringResource,
    content: (@Composable (Boolean) -> Unit)? = null,
) {
    var value by mutableStateOf(default())
    scope.launch {
        dataStore.data.collect {
            value = it[key] ?: default()
        }
    }
    item {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier
                        .weight(1F)
                        .padding(end = 8.dp),
                    text = stringResource(
                        resource = labelRes,
                    ),
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Switch(
                    checked = value,
                    onCheckedChange = {
                        this@switcherRow.scope.launch {
                            this@switcherRow.dataStore.edit {
                                it[key] = !value
                            }
                        }
                    }
                )
            }
            content?.invoke(value)
        }
    }
}


interface LazySettingColumnScope : LazyListScope {
    val dataStore: DataStore<Preferences>
    val scope: CoroutineScope
}

private fun LazyListScope.toLazySettingColumnScope(
    dataStore: DataStore<Preferences>,
    scope: CoroutineScope,
) = object : LazySettingColumnScope {
    override val dataStore: DataStore<Preferences> = dataStore
    override val scope: CoroutineScope = scope

    override fun item(
        key: Any?,
        contentType: Any?,
        content: @Composable LazyItemScope.() -> Unit,
    ) = this@toLazySettingColumnScope.item(
        key = key,
        contentType = contentType,
        content = content,
    )

    override fun items(
        count: Int,
        key: ((index: Int) -> Any)?,
        contentType: (index: Int) -> Any?,
        itemContent: @Composable LazyItemScope.(index: Int) -> Unit
    ) = this@toLazySettingColumnScope.items(
        count = count,
        key = key,
        contentType = contentType,
        itemContent = itemContent,
    )

    @ExperimentalFoundationApi
    override fun stickyHeader(
        key: Any?,
        contentType: Any?,
        content: @Composable LazyItemScope.() -> Unit,
    ) = this@toLazySettingColumnScope.stickyHeader(
        key = key,
        contentType = contentType,
        content = content,
    )
}