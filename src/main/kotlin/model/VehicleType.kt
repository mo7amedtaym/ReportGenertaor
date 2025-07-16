package model

enum class VehicleType(val displayName: String) {
    CAR("سيارة"),
    BICE("دراجة"),
    MICROBUS("مايكروباص"),
    TRANSPORT("سيارة نقل"),;

    override fun toString(): String = displayName

}