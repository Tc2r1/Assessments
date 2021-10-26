package com.pilotflyingj.codechallenge.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pilotflyingj.codechallenge.R
import com.pilotflyingj.codechallenge.databinding.FragmentAboutBinding
import com.pilotflyingj.codechallenge.viewmodel.AboutViewModel
import com.pilotflyingj.codechallenge.viewmodel.AboutViewModelFactory


class AboutFragment : Fragment() {

    private lateinit var viewModelFactory: AboutViewModelFactory

    // Contains all the views
    private var _binding: FragmentAboutBinding? = null

    // This property is only valid between onCreateView and onDestoryView
    private val binding get() = _binding!!

    private val viewModel: AboutViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class.
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        binding.aboutViewModel = viewModel
        binding.tvAbout.text = getString(R.string.about_text)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // prevents memory leaks
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.about_menu, menu)

        // check if the activity resolves
        getShareIntent().resolveActivity(requireActivity().packageManager).let {
            // hide the share menu item.
            menu.findItem(R.id.shareMenuButton)?.setVisible(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shareMenuButton -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getShareIntent(): Intent {

        // Alternative way of creating intent for sharing.
        // val shareIntent = Intent(Intent.ACTION_SEND)
        // shareIntent.type = "text/plain"
        // shareIntent.putExtra(
        //      Intent.EXTRA_TEXT,
        //      getString(R.string.share_success_text, args.numCorrect, args.numQuestions)
        //  )
        // return shareIntent

        return ShareCompat.IntentBuilder.from(requireActivity())
            .setText(getString(R.string.share_success_text))
            .setText("text/plain")
            .intent
    }

    private fun shareSuccess() {
        startActivity(getShareIntent())

    }
}