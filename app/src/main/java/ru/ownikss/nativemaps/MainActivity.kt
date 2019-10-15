package ru.ownikss.nativemaps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import ru.ownikss.nativemaps.models.MainViewModel
import ru.ownikss.nativemaps.models.MarkersFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        ViewModelProviders.of(this).get(MainViewModel::class.java)
        MarkersFactory.initialize(this)
    }
}
