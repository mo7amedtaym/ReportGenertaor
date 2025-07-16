package model

enum class PoliceDepartment(val label: String) {
    F_OCT("أول أكتوبر"),
    S_OCT("ثاني أكتوبر"),
    T_OCT("ثالث أكتوبر"),
    AGOUZA("العجوزة"),
    DOKKI("الدقي"),
    GIZA("الجيزة"),
    IMBABA("إمبابة"),
    ZAID("الشيخ زايد"),
    KIRDASA("كرداسة"),
    MOHANDSEEN("المهندسين"),
    BULAQ_EL_DAKROR("بولاق الدكرور"),
    WARRAQ("الوراق"),
    OMRANIEYA("العمرانية"),
    FAYSAL("فيصل"),
    HARAM("الهرم"),
    TALBIYA("الطالبية"),
    MONEEB("المنيب"),
    BADRASHEEN("البدرشين"),
    EL_AYYAT("العياط"),
    AL_SAFF("الصف"),
    ATFEH("أطفيح"),
    SHABRAMANT("شبرامنت"),
    NAHIA("ناهيا"),;

    override fun toString(): String = label
}
