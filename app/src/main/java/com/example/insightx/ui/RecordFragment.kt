package com.example.insightx.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.insightx.R
import com.example.insightx.databinding.FragmentRecordBinding
import com.example.insightx.viewmodel.MachineRecordViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecordFragment : Fragment(R.layout.fragment_record) {
    //UI
    private var _binding: FragmentRecordBinding? = null
    private val binding get() = _binding!!

    //Navigation
    private lateinit var navController: NavController

    //Data
    private val viewModel by viewModels<MachineRecordViewModel>()

    //0-> Balanced Bagging | 1-> Balanced Random Forest
    private var model: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRecordBinding.bind(view)

        val items = listOf("Low", "Medium", "High")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        navController = Navigation.findNavController(view)

        setObserver()

        binding.quality.setAdapter(adapter)

        binding.modelToggle.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                model = if (checkedId == R.id.balanced_bagging) "0"
                else "1"
            }

        }

        binding.predictBtn.setOnClickListener {
            if (!validateInput()) {
                Snackbar.make(view, "Enter all fields", Snackbar.LENGTH_SHORT).show()
            } else {
                val machineName = binding.machineName.text.toString()
                val airTemp = binding.airTemp.text.toString().toDouble()
                val processTemp = binding.processTemp.text.toString().toDouble()
                val quality = if (binding.quality.text.toString() == items[0]) 0
                else if (binding.quality.text.toString() == items[1]) 1
                else 2
                val rotationalSpeed = binding.rotationalSpeed.text.toString().toDouble()
                val torque = binding.torque.text.toString().toDouble()
                val toolWear = binding.toolWear.text.toString().toDouble()

                viewModel.createMachineRecord(
                    machineName,
                    model,
                    quality,
                    airTemp,
                    processTemp,
                    rotationalSpeed,
                    torque,
                    toolWear
                )
                binding.predictProgress.visibility = View.VISIBLE
            }

        }

    }

    private fun setObserver() {
        viewModel.createReqStatus.observe(this) {
            if (it == null) {
                Log.d("TAG", "setObserver: NULL is received")
            } else if (it::class.simpleName == "Record") {
                binding.predictProgress.visibility = View.INVISIBLE
                val action = RecordFragmentDirections.actionRecordFragmentToDashboardFragment(it)
                navController.navigate(action)
            }
        }
    }

    private fun validateInput(): Boolean {
        if (binding.machineName.text!!.isEmpty() || binding.quality.text!!.isEmpty() || binding.airTemp.text!!.isEmpty() || binding.processTemp.text!!.isEmpty() || binding.rotationalSpeed.text!!.isEmpty() || binding.torque.text!!.isEmpty() || binding.toolWear.text!!.isEmpty() || model == null) {

            Log.d(
                "TAG", "validateInput: ${
                    binding.machineName.text!!.isEmpty()
                }"
            )
            Log.d(
                "TAG", "validateInput: ${
                    binding.quality.text!!.isEmpty()

                }"
            )
            Log.d(
                "TAG", "validateInput: ${
                    binding.airTemp.text!!.isEmpty()
                }"
            )
            Log.d(
                "TAG", "validateInput: ${
                    binding.processTemp.text!!.isEmpty()

                }"
            )
            Log.d(
                "TAG", "validateInput: ${
                    binding.torque.text!!.isEmpty()
                }"
            )
            Log.d(
                "TAG", "validateInput: $model"
            )
            return false
        } else return true
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}