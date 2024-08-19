package cartesien.international.clockify

import Subtareas
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.AdapterView
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var seconds = 0
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private lateinit var textViewChronometer: TextView
    private var isRunning = false
    private lateinit var spinnerTasks: Spinner

    private var taskList = mutableListOf(
        Subtareas("Task 1"),
        Subtareas("Task 2"),
        Subtareas("Task 3")
    )

    private var selectedTask: Subtareas? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        textViewChronometer = findViewById(R.id.textViewChronometer)
        spinnerTasks = findViewById(R.id.spinnerTasks)
        val startButton: Button = findViewById(R.id.startButton)
        val stopButton: Button = findViewById(R.id.stopButton)

        val adapter = MyAdapter(this, taskList)
        //val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, taskList.map { it.getTitlee() })
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTasks.adapter = adapter

        spinnerTasks.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            /*override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                selectedTask = taskList[position]
            }*/
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedTask = taskList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedTask = null
            }
        }

        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                if (isRunning) {
                    seconds++
                    updateTextView()
                    handler.postDelayed(this, 1000) // Actualiza cada segundo
                }
            }
        }

        startButton.setOnClickListener {
            if (!isRunning) {
                startChronometer()
            }
        }

        stopButton.setOnClickListener {
            if (isRunning) {
                stopChronometer()
                selectedTask?.let {
                    // Actualiza la duración de la tarea seleccionada
                    val updatedTask = it.editDuracion(seconds)
                    val index = taskList.indexOf(it)
                    taskList[index] = updatedTask

                    // Actualiza el Spinner para reflejar los cambios
                    (spinnerTasks.adapter as MyAdapter).notifyDataSetChanged()
                    //(spinnerTasks.adapter as ArrayAdapter<*>).notifyDataSetChanged()

                    //println("Duración de '${it.getTitlee()}' actualizada a ${it.getDuracionn()} segundos")
                }
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun updateTextView() {
        val minutes = seconds / 60
        val secs = seconds % 60
        textViewChronometer.text = String.format(Locale.US, "%02d:%02d", minutes, secs)
        //textViewChronometer.text = String.format("%02d:%02d", minutes, secs)
    }

    private fun startChronometer() {
        isRunning = true
        handler.post(runnable)
    }

    private fun stopChronometer() {
        isRunning = false
        handler.removeCallbacks(runnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopChronometer() // Detiene el cronómetro cuando la actividad es destruida
    }

}