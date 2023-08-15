package com.burnout.mhmburnout.model

import androidx.lifecycle.ViewModel
import com.burnout.mhmburnout.datasource.TaskSource

class HomeViewModel: ViewModel() {
    fun load(day: Int) = TaskSource().tasks.filter{it.day == day}
    fun getNegativeQuote() = TaskSource().negativeQuotes.shuffled()[0]
    fun getPositiveQuote() = TaskSource().positiveQuotes.shuffled()[0]
}