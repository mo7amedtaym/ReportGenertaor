package model

sealed class TrafficUnit(val displayName: String) {

    // القاهرة
    object CairoNasrCity : TrafficUnit("مدينة نصر")
    object CairoHeliopolis : TrafficUnit("مصر الجديدة")
    object CairoElNozha : TrafficUnit("النزهة")
    object CairoElZaytoun : TrafficUnit("الزيتون")
    object CairoMaadi : TrafficUnit("المعادي")
    object CairoElBasateen : TrafficUnit("البساتين")
    object CairoSayedaZeinab : TrafficUnit("السيدة زينب")
    object CairoAbdeen : TrafficUnit("عابدين")
    object CairoNewCapital : TrafficUnit("العاصمة الإدارية")

    // الجيزة
    object GizaDokki : TrafficUnit("الدقي")
    object GizaImbaba : TrafficUnit("إمبابة")
    object GizaHaram : TrafficUnit("الهرم")
    object GizaWarraq : TrafficUnit("الوراق")
    object GizaBulaq : TrafficUnit("بولاق الدكرور")
    object GizaOctober : TrafficUnit("6 أكتوبر")
    object GizaSheikhZayed : TrafficUnit("الشيخ زايد")

    // الإسكندرية
    object AlexandriaMontaza : TrafficUnit("المنتزه")
    object AlexandriaAttarin : TrafficUnit("العطارين")
    object AlexandriaBabSharq : TrafficUnit("باب شرقي")
    object AlexandriaDekheila : TrafficUnit("الدخيلة")

    // القليوبية
    object QalyubiaBanha : TrafficUnit("بنها")
    object QalyubiaQalyub : TrafficUnit("قليوب")
    object QalyubiaShubra : TrafficUnit("شبرا الخيمة")

    // المنوفية
    object MenoufiaShebin : TrafficUnit("شبين الكوم")
    object MenoufiaAshmoun : TrafficUnit("أشمون")

    // الشرقية
    object SharqiaZagazig : TrafficUnit("الزقازيق")
    object Sharqia10Ramadan : TrafficUnit("العاشر من رمضان")

    // الدقهلية
    object DakahliaMansoura : TrafficUnit("المنصورة")
    object DakahliaTalkha : TrafficUnit("طلخا")

    // الغربية
    object GharbiaTanta : TrafficUnit("طنطا")
    object GharbiaMahalla : TrafficUnit("المحلة الكبرى")

    // الفيوم
    object FayoumCenter : TrafficUnit("الفيوم")

    // بني سويف
    object BeniSuefCenter : TrafficUnit("بني سويف")

    // المنيا
    object MinyaCenter : TrafficUnit("المنيا")

    // أسيوط
    object AssiutCenter : TrafficUnit("أسيوط")

    // سوهاج
    object SohagCenter : TrafficUnit("سوهاج")

    // قنا
    object QenaCenter : TrafficUnit("قنا")

    // الأقصر
    object LuxorCenter : TrafficUnit("الأقصر")

    // أسوان
    object AswanCenter : TrafficUnit("أسوان")

    // البحر الأحمر
    object RedSeaHurghada : TrafficUnit("الغردقة")

    // شمال سيناء
    object NorthSinaiArish : TrafficUnit("العريش")

    // جنوب سيناء
    object SouthSinaiSharm : TrafficUnit("شرم الشيخ")

    // مرسى مطروح
    object MatruhCenter : TrafficUnit("مرسى مطروح")

    // الوادي الجديد
    object NewValleyCenter : TrafficUnit("الوادي الجديد")

    // كفر الشيخ
    object KafrElSheikhCenter : TrafficUnit("كفر الشيخ")

    // دمياط
    object DamiettaCenter : TrafficUnit("دمياط")

    // البحيرة
    object BeheiraDamanhour : TrafficUnit("دمنهور")

    // الإسماعيلية
    object IsmailiaCenter : TrafficUnit("الإسماعيلية")

    // السويس
    object SuezCenter : TrafficUnit("السويس")

    // بورسعيد
    object PortSaidCenter : TrafficUnit("بورسعيد")

    // Custom وحدة مخصصة
    class Custom(displayName: String) : TrafficUnit(displayName)

    override fun toString(): String = displayName

    companion object {
        val defaultUnits = listOf(
            // الجيزة
            GizaBulaq, GizaDokki, GizaHaram, GizaImbaba, GizaWarraq, GizaOctober, GizaSheikhZayed,
            // القاهرة
            CairoNasrCity, CairoHeliopolis, CairoElNozha, CairoElZaytoun, CairoMaadi, CairoElBasateen, CairoSayedaZeinab, CairoAbdeen, CairoNewCapital,
            // باقي المحافظات
            AlexandriaMontaza, AlexandriaAttarin, AlexandriaBabSharq, AlexandriaDekheila,
            QalyubiaBanha, QalyubiaQalyub, QalyubiaShubra,
            MenoufiaShebin, MenoufiaAshmoun,
            SharqiaZagazig, Sharqia10Ramadan,
            DakahliaMansoura, DakahliaTalkha,
            GharbiaTanta, GharbiaMahalla,
            FayoumCenter, BeniSuefCenter, MinyaCenter, AssiutCenter, SohagCenter, QenaCenter,
            LuxorCenter, AswanCenter,
            RedSeaHurghada, NorthSinaiArish, SouthSinaiSharm,
            MatruhCenter, NewValleyCenter, KafrElSheikhCenter, DamiettaCenter, BeheiraDamanhour,
            IsmailiaCenter, SuezCenter, PortSaidCenter
        )
    }
}
