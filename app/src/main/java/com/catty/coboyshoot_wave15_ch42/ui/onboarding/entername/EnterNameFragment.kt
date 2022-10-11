package com.catty.coboyshoot_wave15_ch42.ui.onboarding.entername

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.catty.coboyshoot_wave15_ch42.databinding.FragmentEnterNameBinding
import com.catty.coboyshoot_wave15_ch42.ui.ingamestart.InGameStartActivity
import com.catty.coboyshoot_wave15_ch42.ui.onboarding.OnFinishNavigateListener

class EnterNameFragment : Fragment(), OnFinishNavigateListener {

    private lateinit var binding: FragmentEnterNameBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEnterNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onFinishNavigateListener() {
        val name = binding.etName.text.toString().trim()
        if(name.isEmpty()){
            Toast.makeText(requireContext(), "Please input your name !", Toast.LENGTH_SHORT).show()
        }else{
            navigateToMenu(name)
        }
    }

    private fun navigateToMenu(name : String) {
        val intent = Intent(context, InGameStartActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

        Toast.makeText(requireContext(), "Name : $name", Toast.LENGTH_SHORT).show()
    }
}

