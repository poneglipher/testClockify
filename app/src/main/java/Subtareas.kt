import androidx.room.util.copy

data class Subtareas(var title: String, var duracion: Int = 0) {

    fun getTitlee(): String {
        return this.title
    }
    fun editTitle(newTitle: String): Subtareas {
        return copy(title = newTitle)
    }
    fun getDuracionn(): Int {
        return this.duracion
    }
    fun editDuracion(newDuracion: Int): Subtareas{
        return copy(duracion = duracion + newDuracion)
    }

}