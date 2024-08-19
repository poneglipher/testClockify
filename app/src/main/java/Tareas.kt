class Tareas {

    val taskList: MutableList<Subtareas> = mutableListOf()

    fun addSubtask(title: String){
        val subtask = Subtareas(title)
        taskList.add(subtask)
    }

    fun getSubtasksList(): List<Subtareas>{
        return taskList.toList()
    }

    fun delSubtask(title: String){
        val subtask = taskList.find { it.title == title }
        if (subtask != null){
            taskList.remove(subtask)
        }
    }

    fun actTitleSubtask(title: String, newTitle: String){
        val subtask = taskList.find { it.title == title }
        if (subtask != null){
            val index = taskList.indexOf(subtask)
            taskList[index] = subtask.editTitle(newTitle)
        }
    }

    fun actDurationSubtask(title: String, newDuracion: Int){
        val subtask = taskList.find { it.title == title }
        if (subtask != null){
            val index = taskList.indexOf(subtask)
            taskList[index] = subtask.editDuracion(newDuracion)
        }
    }



}