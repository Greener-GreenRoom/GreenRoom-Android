package com.greener.domain.model


enum class ActionTodo(val actionId: Int, val activity: String, val color: String) {
    COMPLETE_ALL(0, "complete_all", "gray400"),
    WATERING(1, "watering", "blue100"),
    RE_POT(2, "repot", "brown100"),
    PRUNING(3, "pruning", "green400"),
    NUTRITION(4, "nutrition", "red100"),
    VENTILATION(5, "ventilation", "grayish100")
}