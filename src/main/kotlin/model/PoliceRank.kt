package model

enum class PoliceRank(val displayName: String) {
    MOHAAMY("ملازم"),
    MOHAAMY_AWEL("ملازم أول"),
    NAKIB("نقيب"),
    RAED("رائد"),
    MOQADDAM("مقدم"),
    AQID("عقيد"),
    AMID("عميد"),
    LOAA("لواء"), ;

    override fun toString(): String = displayName
}
