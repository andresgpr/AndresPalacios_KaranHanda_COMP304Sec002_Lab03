package com.assignmnet.andrespalacios_karanhanda_comp304sec002_lab03

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assignmnet.andrespalacios_karanhanda_comp304sec002_lab03.databinding.FullScheduleFragmentBinding
import kotlinx.coroutines.launch
import com.assignmnet.andrespalacios_karanhanda_comp304sec002_lab03.viewmodels.AirLineViewModel
import com.assignmnet.andrespalacios_karanhanda_comp304sec002_lab03.viewmodels.AirLineListViewModelFactory

class FullScheduleFragment: Fragment() {

    private var _binding: FullScheduleFragmentBinding? = null

    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private val viewModel: AirLineViewModel by activityViewModels {
        AirLineListViewModelFactory(
            (activity?.application as AirLineApplication).database.scheduleDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FullScheduleFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val busStopAdapter = AirLineAdapter({
            val action = FullScheduleFragmentDirections
                .actionFullScheduleFragmentToStopScheduleFragment(
                    airlineName = it.airlineName
                )
            view.findNavController().navigate(action)
        })
        recyclerView.adapter = busStopAdapter
        lifecycle.coroutineScope.launch {
            viewModel.fullSchedule().collect() {
                busStopAdapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}