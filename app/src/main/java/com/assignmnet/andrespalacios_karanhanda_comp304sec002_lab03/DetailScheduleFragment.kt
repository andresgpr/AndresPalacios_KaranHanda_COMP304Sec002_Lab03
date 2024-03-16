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
import kotlinx.coroutines.launch
import com.assignmnet.andrespalacios_karanhanda_comp304sec002_lab03.viewmodels.AirLineViewModel
import com.assignmnet.andrespalacios_karanhanda_comp304sec002_lab03.viewmodels.AirLineListViewModelFactory
import com.assignmnet.andrespalacios_karanhanda_comp304sec002_lab03.databinding.DetailScheduleFragmentBinding

class DetailScheduleFragment: Fragment() {

    companion object {
        var AIRLINE_NAME = "airlineName"
    }

    private var _binding: DetailScheduleFragmentBinding? = null

    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private lateinit var airLineName: String

    private val viewModel: AirLineViewModel by activityViewModels {
        AirLineListViewModelFactory(
            (activity?.application as AirLineApplication).database.scheduleDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            airLineName = it.getString(AIRLINE_NAME).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailScheduleFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize the adapter with isStatusVisible set to true to show the status
        val busStopAdapter = AirLineAdapter(onItemClicked = { /* Handle item click if necessary */ }, statusDetails = true)

        recyclerView.adapter = busStopAdapter
        lifecycle.coroutineScope.launch {
            viewModel.scheduleForAirlineName(airLineName).collect { schedules ->
                busStopAdapter.submitList(schedules)
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
