package com.thunder.arbooks.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.thunder.arbooks.databinding.FragmentHomeBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        val fab: FloatingActionButton = binding.FloatingActionButton

        fab.setOnClickListener { initScanner ()
        }

        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it


        }
        return root
    }

    private fun initScanner() {
       val integrator = IntentIntegrator(requireActivity())
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("Coloque su Codigo QR en el cuadrado de arriba")
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null){
            if (result.contents == null){
                Toast.makeText(requireContext(), "Cancelado", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(requireContext(), "El valor escaneado es: ${result.contents}",
                    Toast.LENGTH_SHORT).show()
            }

        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

