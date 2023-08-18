package com.example.insightx.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.insightx.R
import com.example.insightx.adapters.RecordAdapter
import com.example.insightx.data.retrofit.NetworkResult
import com.example.insightx.data.retrofit.model.Record
import com.example.insightx.databinding.FragmentHomeBinding
import com.example.insightx.util.DataStoreRepository
import com.example.insightx.viewmodel.MachineRecordViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    //UI
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //Navigation
    private lateinit var navController: NavController

    //Recycler View
    private lateinit var recordAdapter: RecordAdapter

    //Data
    @Inject
    lateinit var dataStoreRepo: DataStoreRepository
    private val viewModel by viewModels<MachineRecordViewModel>()
    var recordList: MutableList<Record>? = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authenticationStatus()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)
        navController = Navigation.findNavController(view)
        recordAdapter = RecordAdapter(requireContext()) {
            val action = RecordFragmentDirections.actionRecordFragmentToDashboardFragment(it)
            navController.navigate(action)
        }

        binding.refreshContainer.setOnRefreshListener {
            viewModel.fetchMachineRecords()
        }

        binding.fabRecord.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_recordFragment)
        }

        init()
        callbackInit()
//        subscribeToData()
    }

    private fun callbackInit() {
        val itemTouchHelperCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ) = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val removedItem = recordList!!.removeAt(position)
                    recordAdapter.notifyItemRemoved(position)

                    Snackbar.make(
                        binding.recordRecyclerView, "Record deleted", Snackbar.LENGTH_SHORT
                    ).setAction("Undo") {
                        recordList!!.add(position, removedItem)
                        recordAdapter.notifyItemInserted(position)
                    }.addCallback(object : Snackbar.Callback() {
                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                            super.onDismissed(transientBottomBar, event)
                            if (event == DISMISS_EVENT_TIMEOUT
                                || event == DISMISS_EVENT_CONSECUTIVE
                                || event == DISMISS_EVENT_SWIPE
                            ) {
                                deleteRequest(position, removedItem)
                            }
                        }
                    }).show()

                }
            }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recordRecyclerView)
    }

    private fun deleteRequest(pos: Int, removedItem: Record) {
        viewModel.deleteRecord(removedItem)
        viewModel.delReqStatus.observe(viewLifecycleOwner) {
            when (it) {
                "ERROR" -> {
                    if (!recordList!!.contains(removedItem)) {
                        Toast.makeText(
                            requireContext(),
                            "Failed to delete record",
                            Toast.LENGTH_SHORT
                        ).show()
                        recordList!!.add(pos, removedItem)
                        recordAdapter.notifyItemInserted(pos)
                    }

                }
                "LOADING" -> {
                    Log.d("TAG", "deleteRequest: LOADING......")
                }
                "SUCCESS" -> {
                    Log.d("TAG", "deleteRequest: DELETED!!!")
                }
            }
        }

    }


    @SuppressLint("NotifyDataSetChanged")
    private fun init() {

        binding.apply {
            recordRecyclerView.apply {
                adapter = recordAdapter
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }
        }

        fetchRecords()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchRecords() {
        viewModel.records.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Error -> {
                    binding.progressBarRecords.visibility = View.INVISIBLE
                    binding.refreshContainer.isRefreshing = false
                    Toast.makeText(context, "Error Occurred", Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    binding.progressBarRecords.visibility = View.VISIBLE
                }
                is NetworkResult.Success -> {
                    binding.progressBarRecords.visibility = View.INVISIBLE
                    binding.refreshContainer.isRefreshing = false
                    recordList = it.data as MutableList<Record>?
                    recordAdapter.submitList(it.data)
                    recordAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun authenticationStatus() {
        lifecycleScope.launch {
            if (dataStoreRepo.getPass() == null || dataStoreRepo.getUser() == null)
                navController.navigate(
                    R.id.action_homeFragment_to_loginFragment
                )
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}