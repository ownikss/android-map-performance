package ru.ownikss.nativemaps.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import ru.ownikss.nativemaps.R
import ru.ownikss.nativemaps.databinding.StartLayoutBinding

class StartFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<StartLayoutBinding>(
            inflater,
            R.layout.start_layout,
            container,
            false
        )
        binding.clusterMapBtn.setOnClickListener { openMap(true) }
        binding.simpleMapBtn.setOnClickListener { openMap(false) }
        return binding.root
    }

    private fun openMap(useClusters: Boolean) {
        val bundle = Bundle()
        bundle.putBoolean("useClusters", useClusters)
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_startFragment_to_clustersFragment, bundle)
    }
}
