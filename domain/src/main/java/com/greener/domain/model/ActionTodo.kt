package com.greener.domain.model

enum class ActionTodo(val actionId: Int, val activity: String, val color: String, val size: Int) {
    COMPLETE_ALL(0, "complete_all", "gray400", 5),
    WATERING(1, "watering", "blue100", 1),
    RE_POT(2, "repot", "brown100", 3),
    PRUNING(3, "pruning", "green300", 4),
    NUTRITION(4, "nutrition", "red100", 4),
    VENTILATION(5, "ventilation", "grayish100", 2),
}
