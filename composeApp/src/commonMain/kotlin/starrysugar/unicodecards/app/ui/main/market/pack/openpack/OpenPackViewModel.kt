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
package starrysugar.unicodecards.app.ui.main.market.pack.openpack

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import starrysugar.unicodecards.app.ui.base.BaseViewModel
import starrysugar.unicodecards.appdata.database.table.QueryDataByIndexWithUserData
import starrysugar.unicodecards.appdata.models.pack.CardPack
import starrysugar.unicodecards.appdata.models.pack.CardPacks

/**
 * @author StarrySugar61
 * @create 2024/6/20
 */
class OpenPackViewModel(
    private val packID: Int,
    private val count: Int,
) : BaseViewModel() {

    var cardPack: CardPack? by mutableStateOf(null)
        private set

    var cardPackResult = emptyList<QueryDataByIndexWithUserData>()

    /**
     * Steps for opening the card pack!
     *
     * 1 - tear
     * 2 - remove package
     * 3 - move cards down
     * 4 - show cards one by one
     * 5 - show all cards obtained from this pack
     */
    var animatorStep by mutableStateOf(0)
        private set

    /**
     * The card displaying when animatorStep == 4
     */
    var animatorCardIndex by mutableStateOf(-1)
        private set

    /**
     * Steps for single card displaying!
     *
     * 1 - alpha
     * 2 - showing
     * 3 - fade away
     */
    var animatorCardStep by mutableStateOf(0)
        private set

    private var _animationJob: Job? = null

    init {
        cardPack = CardPacks.data[packID]
        cardPackResult = cardPack?.collectCards(count) ?: emptyList()
    }

    fun startAnimation() {
        _animationJob = viewModelScope.launch {
            animatorStep = 1
            delay(600)
            animatorStep = 2
            delay(600)
            animatorStep = 3
            delay(800)
            animatorStep = 4
            repeat(count) {
                animatorCardStep = 1
                delay(400)
                animatorCardIndex = it
                animatorCardStep = 2
                delay(1000)
                animatorCardStep = 3
                delay(200)
                animatorCardStep = 0
                delay(200)
            }
            animatorStep = 5
        }
    }

    fun skipAnimation() {
        _animationJob?.cancel()
        animatorCardIndex = count - 1
        animatorStep = 5
    }

    object PackIDKey : CreationExtras.Key<Int>
    object CountKey : CreationExtras.Key<Int>
}