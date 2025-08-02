//
//import org.docx4j.jaxb.Context
//import org.docx4j.openpackaging.packages.WordprocessingMLPackage
//import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart
//import org.docx4j.wml.*
//import java.io.File
//import java.math.BigInteger
//
//fun createArabicDocxWithDocx4j(fileName: String, suspectName: String, otherDetails: String) {
//    // 1. إنشاء حزمة مستند Word جديدة
//    val wordMLPackage = WordprocessingMLPackage.createPackage()
//    val documentPart: MainDocumentPart = wordMLPackage.mainDocumentPart
//
//    // 2. الحصول على كائن ObjectFactory للمساعدة في إنشاء عناصر XML
//    val factory = Context.getWmlObjectFactory()
//
//    // --- العنوان ---
//    val titleP: P = factory.createP()
//    val titlePPr: PPr = factory.createPPr()
//    titleP.setPPr(titlePPr)
//
//    // تعيين المحاذاة للوسط
//    val titleJc: Jc = factory.createJc()
//    titleJc.setVal(JcEnumeration.CENTER)
//    titlePPr.setJc(titleJc)
//
//    // تفعيل خاصية Bidi (اليمين لليسار) للفقرة
//    val titleBidi: BooleanDefaultTrue = factory.createBooleanDefaultTrue()
//    titleBidi.setVal(true) // true for ON
//    titlePPr.setBidi(titleBidi)
//
//    val titleRun: R = factory.createR()
//    val titleText: Text = factory.createText()
//    titleText.setValue("محضر شرطة")
//    titleRun.getContent().add(titleText)
//
//    // تعيين الخط للعنوان (مهم لدعم العربية)
//    val titleRPr: RPr = factory.createRPr()
//    val rFonts = factory.createRFonts()
//    rFonts.setAscii("Arial")
//    rFonts.setHAnsi("Arial")
////    rFonts.setBidi("Arial") // الخط للغة ثنائية الاتجاه (العربية)
//    titleRPr.setRFonts(rFonts)
//    titleRPr.setSz(factory.createHpsMeasure()) // حجم الخط
//    titleRPr.getSz().setVal(BigInteger.valueOf(32)) // 16 * 2 (لأن الحجم في Docx4j يكون بنصف نقطة)
//    titleRPr.setB(factory.createBooleanDefaultTrue()) // خط عريض
//    titleRun.setRPr(titleRPr)
//
//    titleP.getContent().add(titleRun)
//    documentPart.getContent().add(titleP)
//
//    // --- اسم المتهم ---
//    val nameP: P = factory.createP()
//    val namePPr: PPr = factory.createPPr()
//    nameP.setPPr(namePPr)
//
//    // تعيين المحاذاة لليمين
//    val nameJc: Jc = factory.createJc()
//    nameJc.setVal(JcEnumeration.RIGHT)
//    namePPr.setJc(nameJc)
//
//    // تفعيل خاصية Bidi للفقرة
//    val nameBidi: BooleanDefaultTrue = factory.createBooleanDefaultTrue()
//    nameBidi.setVal(true)
//    namePPr.setBidi(nameBidi)
//
//    val nameRun: R = factory.createR()
//    val nameText: Text = factory.createText()
//    nameText.setValue("اسم المتهم: $suspectName")
//    nameRun.getContent().add(nameText)
//
//    // تعيين الخط
//    val nameRPr: RPr = factory.createRPr()
//    val nameRFonts = factory.createRFonts()
//    nameRFonts.setAscii("Arial")
//    nameRFonts.setHAnsi("Arial")
////    nameRFonts.setBidi("Arial")
//    nameRPr.setRFonts(nameRFonts)
//    nameRPr.setSz(factory.createHpsMeasure())
//    nameRPr.getSz().setVal(BigInteger.valueOf(24)) // 12 * 2
//    nameRun.setRPr(nameRPr)
//
//    nameP.getContent().add(nameRun)
//    documentPart.getContent().add(nameP)
//
//    // --- بيانات أخرى ---
//    val detailsP: P = factory.createP()
//    val detailsPPr: PPr = factory.createPPr()
//    detailsP.setPPr(detailsPPr)
//
//    // تعيين المحاذاة لليمين
//    val detailsJc: Jc = factory.createJc()
//    detailsJc.setVal(JcEnumeration.RIGHT)
//    detailsPPr.setJc(detailsJc)
//
//    // تفعيل خاصية Bidi للفقرة
//    val detailsBidi: BooleanDefaultTrue = factory.createBooleanDefaultTrue()
//    detailsBidi.setVal(true)
//    detailsPPr.setBidi(detailsBidi)
//
//    val detailsRun: R = factory.createR()
//    val detailsText: Text = factory.createText()
//    detailsText.setValue("بيانات أخرى: $otherDetails")
//    detailsRun.getContent().add(detailsText)
//
//    // تعيين الخط
//    val detailsRPr: RPr = factory.createRPr()
//    val detailsRFonts = factory.createRFonts()
//    detailsRFonts.setAscii("Arial")
//    detailsRFonts.setHAnsi("Arial")
//    //detailsRFonts.setBidi("Arial")
//    detailsRPr.setRFonts(detailsRFonts)
//    detailsRPr.setSz(factory.createHpsMeasure())
//    detailsRPr.getSz().setVal(BigInteger.valueOf(24))
//    detailsRun.setRPr(detailsRPr)
//
//    detailsP.getContent().add(detailsRun)
//    documentPart.getContent().add(detailsP)
//
//    // 3. حفظ الملف
//    val outputFile = File(fileName)
//    wordMLPackage.save(outputFile)
//}
//
//// مثال الاستخدام في Jetpack Compose desktop:
//// Button(onClick = { createArabicDocxWithDocx4j("محضر_شرطة_Docx4j.docx", "محمد أحمد", "تفاصيل إضافية هنا") }) {
////     Text("إنشاء المحضر (Docx4j)")
//// }