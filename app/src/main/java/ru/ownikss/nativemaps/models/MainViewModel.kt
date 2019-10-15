package ru.ownikss.nativemaps.models

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val count = ObservableField<Int>(100)
}
