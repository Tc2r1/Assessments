package com.tc2r1.fiskerassessment

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tc2r1.fiskerassessment.databinding.ActivityMainBinding


// Write an algorithm that takes as input a string and returns
// the substring that represents the longest sequential chain.  Sequences stop at ‘z’ and do not roll over.  So, for instance, ‘xyzab’ is 2 sequences ‘xyz’ and ‘ab.’ If there are more than one occurrence with longest chain return the first occurrence. Only lowercase letters are considered.

class MainActivity : AppCompatActivity() {

    // Contains all the views
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.resultSeq.observe(this, Observer { resultSequence ->
            Toast.makeText(this, resultSequence, Toast.LENGTH_LONG).show()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}