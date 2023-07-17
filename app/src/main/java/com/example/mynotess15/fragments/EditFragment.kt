package com.example.mynotess15.fragments

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat.getRotation
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mynotess15.R
import com.example.mynotess15.database.Note
import com.example.mynotess15.databinding.FragmentEditBinding
import com.example.mynotess15.viewmodel.NotesViewModel
import java.text.SimpleDateFormat
import java.util.Date

class EditFragment : Fragment() {

    lateinit var binding: FragmentEditBinding
    val viewModel: NotesViewModel by activityViewModels()
    var color: String = "greyLight"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditBinding.inflate(layoutInflater, container, false)

        if (viewModel.getIsUpdate()) {
            val tempNote = viewModel.getTempNote()
            binding.editTextTitle.setText(tempNote.title)
            binding.editTextDescription.setText(tempNote.description)
            color = tempNote.color
            val startColor = ContextCompat.getColor(requireContext(), stringToColor(tempNote.color))
            val endColor = ContextCompat.getColor(requireContext(), R.color.white)
            setBackgroundGradient(binding.root,startColor,endColor)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.ivMore.setOnClickListener {
            if (binding.clMore.visibility == View.VISIBLE) {
                binding.clMore.visibility = View.GONE
            } else {
                binding.clMore.visibility = View.VISIBLE
            }

            val rotation = getRotation(binding.ivMore)
            if (rotation == 0f) {
                binding.ivMore.rotation = 90f
            } else {
                binding.ivMore.rotation = 0f
            }
        }

        binding.imageBack.setOnClickListener {
            saveNote()
            toHomeFragment()
        }
        binding.imageGallery.setOnClickListener() {
            Toast.makeText(requireContext(), "Coming Soon", Toast.LENGTH_SHORT).show()
        }
        binding.imageLink.setOnClickListener() {
            Toast.makeText(requireContext(), "Coming Soon", Toast.LENGTH_SHORT).show()
        }


        setColorToNote()




        binding.imageSave.setOnClickListener {
            saveNote()
            toHomeFragment()
        }


    }

    private fun saveNote(){
        val title = binding.editTextTitle.text.toString()
        val description = binding.editTextDescription.text.toString()
        val date = SimpleDateFormat("dd-MMM-yy").format(Date())
        val color = color

        if (title.isNotEmpty() || description.isNotEmpty()) {


            if (viewModel.getIsUpdate()) {
                //update
                val id = viewModel.getTempNote().id
                val note = Note(id, title, description, date, "null", color)
                viewModel.update(note)
                Toast.makeText(requireActivity(), "Note updated", Toast.LENGTH_SHORT).show()
                viewModel.setIsUpdate(false)
                Log.d("TAG", "onViewCreated: note updated : $note")
            } else {
                //create
                val note = Note(0, title, description, date, "null", color)
                viewModel.insert(note)
                Toast.makeText(requireActivity(), "Note inserted", Toast.LENGTH_SHORT).show()
                Log.d("TAG", "onViewCreated: note created : $note")
            }

        } else {
            Toast.makeText(requireActivity(), "Invalid Input", Toast.LENGTH_SHORT).show()
        }
    }

    private fun toHomeFragment() {
        findNavController().navigate(R.id.action_editFragment_to_homeFragment)
    }

    private fun setColorToNote() {
        Log.d("TAG", "setBackgroundColor: ")
        binding.lightGreyCircle.setOnClickListener {

            val startColor = ContextCompat.getColor(requireContext(), stringToColor("greyLight"))
            val endColor = ContextCompat.getColor(requireContext(), R.color.white)
            setBackgroundGradient(binding.root,startColor,endColor)
//            binding.root.setBackgroundColor(resources.getColor(R.color.white))
            color = "greyLight";
        }
        binding.redCircle.setOnClickListener {
            val startColor = ContextCompat.getColor(requireContext(), stringToColor("red"))
            val endColor = ContextCompat.getColor(requireContext(), R.color.white)
            setBackgroundGradient(binding.root,startColor,endColor)
//            binding.root.setBackgroundColor(resources.getColor(R.color.red))
            color = "red";
        }
        binding.greenCircle.setOnClickListener {
            val startColor = ContextCompat.getColor(requireContext(), stringToColor("green"))
            val endColor = ContextCompat.getColor(requireContext(), R.color.white)
            setBackgroundGradient(binding.root,startColor,endColor)
//            binding.root.setBackgroundColor(resources.getColor(R.color.green))
            color = "green";
        }
        binding.greyCircle.setOnClickListener {
            val startColor = ContextCompat.getColor(requireContext(), stringToColor("grey"))
            val endColor = ContextCompat.getColor(requireContext(), R.color.white)
            setBackgroundGradient(binding.root,startColor,endColor)
//            binding.root.setBackgroundColor(resources.getColor(R.color.grey))
            color = "grey";
        }
        binding.orangeCircle.setOnClickListener {
            val startColor = ContextCompat.getColor(requireContext(), stringToColor("orange"))
            val endColor = ContextCompat.getColor(requireContext(), R.color.white)
            setBackgroundGradient(binding.root,startColor,endColor)
//            binding.root.setBackgroundColor(resources.getColor(R.color.orange))
            color = "orange";
        }
        binding.pinkCircle.setOnClickListener {
            val startColor = ContextCompat.getColor(requireContext(), stringToColor("pink"))
            val endColor = ContextCompat.getColor(requireContext(), R.color.white)
            setBackgroundGradient(binding.root,startColor,endColor)
//            binding.root.setBackgroundColor(resources.getColor(R.color.pink))
            color = "pink";
        }
        binding.skyCircle.setOnClickListener {
            val startColor = ContextCompat.getColor(requireContext(), stringToColor("sky"))
            val endColor = ContextCompat.getColor(requireContext(), R.color.white)
            setBackgroundGradient(binding.root,startColor,endColor)
//            binding.root.setBackgroundColor(resources.getColor(R.color.sky))
            color = "sky";
        }
        binding.yellowCircle.setOnClickListener {
            val startColor = ContextCompat.getColor(requireContext(), stringToColor("yellow"))
            val endColor = ContextCompat.getColor(requireContext(), R.color.white)
            setBackgroundGradient(binding.root,startColor,endColor)
//            binding.root.setBackgroundColor(resources.getColor(R.color.yellow))
            color = "yellow";
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.setIsUpdate(false)
    }

    fun setBackgroundGradient(view: View, startColor: Int, endColor: Int) {
        Log.d("TAG", "setBackgroundGradient: startColor: ${startColor}   endColor $endColor")
        // Create a GradientDrawable with the two colors.
        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(startColor, endColor)
        )
        // Set the background of the view to the gradient.
        view.background = gradientDrawable
    }

    private fun stringToColor(color: String) : Int {
        return when(color){
            "red" -> R.color.red
            "green" -> R.color.green
            "grey" -> R.color.grey
            "orange" -> R.color.orange
            "pink" -> R.color.pink
            "sky" -> R.color.sky
            "yellow" -> R.color.yellow
            else -> {
                R.color.greyLight
            }
        }
    }



}