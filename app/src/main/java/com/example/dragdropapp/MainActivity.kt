package com.example.dragdropapp

import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Color
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dragdropapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        binding.image1.setOnLongClickListener {

            var clipText = "Drag drop text"
            val item = ClipData.Item(clipText)

            val mimeTpes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText,mimeTpes,item)

            val drawShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data,drawShadowBuilder,it,0)
            it.visibility = View.INVISIBLE

            true
        }

        binding.image2.setOnDragListener { v, event ->

            when(event.action){

                DragEvent.ACTION_DRAG_STARTED -> {
                    true
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    v.setBackgroundColor(Color.GREEN)
                    true
                }
                DragEvent.ACTION_DROP -> {

                    binding.image1.scaleX = 0.3f
                    binding.image1.scaleY = 0.3f
                    binding.image2.scaleX = 0.3f
                    binding.image2.scaleY = 0.3f


                    v.setBackgroundColor(Color.DKGRAY)

                    val draggedView = event.localState as ImageView
                    draggedView.visibility = View.VISIBLE

                    val targetImageViewDrawable = (v as ImageView).drawable

                    (v as ImageView).setImageDrawable(draggedView.drawable)
                    binding.image1.setImageDrawable(targetImageViewDrawable)



                    binding.image2.animate().scaleX(1f).scaleY(1f).duration = 1500
                    binding.image1.animate().scaleX(1f).scaleY(1f).duration = 1500




                    true
                }
                DragEvent.ACTION_DRAG_ENDED -> {

                    if(!event.result){
                        val draggedView = event.localState as View
                        draggedView.visibility = View.VISIBLE
                    }

                    true
                }
                else -> {
                    false
                }
            }

        }







    }
}