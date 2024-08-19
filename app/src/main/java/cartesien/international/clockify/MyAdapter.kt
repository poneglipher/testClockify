package cartesien.international.clockify

import Subtareas
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class MyAdapter(context: Context, private val taskList: List<Subtareas>) :
    ArrayAdapter<Subtareas>(context, R.layout.spinner_item, taskList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false)
        val task = getItem(position) ?: return view

        val textViewTitle: TextView = view.findViewById(R.id.textViewTitle)
        val textViewDuration: TextView = view.findViewById(R.id.textViewDuration)

        textViewTitle.text = task.title
        textViewDuration.text = "${task.duracion} s"

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }
}
