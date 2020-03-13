package bset.hyun.designpattern

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bset.hyun.designpattern.Pattern.FACTORY
import kotlinx.android.synthetic.main.activity_pattern_select.*

enum class Pattern {
    FACTORY
}

class PatternSelectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pattern_select)


        btn_factory.setOnClickListener { showPattern(FACTORY) }
    }

    private fun showPattern(pattern:Pattern) {
        when(pattern) {
            FACTORY -> { startActivity(Intent(this, FactoryPatternActivity::class.java)) }
        }
    }
}
