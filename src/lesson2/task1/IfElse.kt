@file:Suppress("UNUSED_PARAMETER")

package lesson2.task1

import lesson1.task1.discriminant
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sqrt

/**
 * Пример
 *
 * Найти число корней квадратного уравнения ax^2 + bx + c = 0
 */
fun quadraticRootNumber(a: Double, b: Double, c: Double): Int {
    val discriminant = discriminant(a, b, c)
    return when {
        discriminant > 0.0 -> 2
        discriminant == 0.0 -> 1
        else -> 0
    }
}

/**
 * Пример
 *
 * Получить строковую нотацию для оценки по пятибалльной системе
 */
fun gradeNotation(grade: Int): String = when (grade) {
    5 -> "отлично"
    4 -> "хорошо"
    3 -> "удовлетворительно"
    2 -> "неудовлетворительно"
    else -> "несуществующая оценка $grade"
}

/**
 * Пример
 *
 * Найти наименьший корень биквадратного уравнения ax^4 + bx^2 + c = 0
 */
fun minBiRoot(a: Double, b: Double, c: Double): Double {
    // 1: в главной ветке if выполняется НЕСКОЛЬКО операторов
    if (a == 0.0) {
        if (b == 0.0) return Double.NaN // ... и ничего больше не делать
        val bc = -c / b
        if (bc < 0.0) return Double.NaN // ... и ничего больше не делать
        return -sqrt(bc)
        // Дальше функция при a == 0.0 не идёт
    }
    val d = discriminant(a, b, c)   // 2
    if (d < 0.0) return Double.NaN  // 3
    // 4
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    val y3 = max(y1, y2)       // 5
    if (y3 < 0.0) return Double.NaN // 6
    return -sqrt(y3)           // 7
}

/**
 * Простая
 *
 * Мой возраст. Для заданного 0 < n < 200, рассматриваемого как возраст человека,
 * вернуть строку вида: «21 год», «32 года», «12 лет».
 */
fun ageDescription(age: Int): String {
    if (age%100>4 && age%100<21){
        return "$age лет"
    }else{
        when {
            age%10==1 -> return "$age год"
            (age%10>1) && (age%10<5) -> return "$age года"
            else -> return "$age лет"
        }
    }
}

/**
 * Простая
 *
 * Путник двигался t1 часов со скоростью v1 км/час, затем t2 часов — со скоростью v2 км/час
 * и t3 часов — со скоростью v3 км/час.
 * Определить, за какое время он одолел первую половину пути?
 */
fun timeForHalfWay(
    t1: Double, v1: Double,
    t2: Double, v2: Double,
    t3: Double, v3: Double
): Double {
    val s1:Double=t1*v1
    val s2:Double=t2*v2
    val s3:Double=t3*v3
    val s:Double=(s1+s2+s3)/2
    var k=0
    val t:Double
    when {
        (s>0) && (s<=s1) -> k=1
        (s>s1) && (s<=s2+s1) -> k=2
        s>s2+s1 -> k=3
    }
    when (k){
        0-> t=0.0
        1-> t=s/v1
        2-> t=t1+ (s-s1)/v2
        else -> t=t1+t2+(s-s1-s2)/v3
    }
    return t
}

/**
 * Простая
 *
 * Нa шахматной доске стоят черный король и две белые ладьи (ладья бьет по горизонтали и вертикали).
 * Определить, не находится ли король под боем, а если есть угроза, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от первой ладьи, 2, если только от второй ладьи,
 * и 3, если угроза от обеих ладей.
 * Считать, что ладьи не могут загораживать друг друга
 */
fun whichRookThreatens(
    kingX: Int, kingY: Int,
    rookX1: Int, rookY1: Int,
    rookX2: Int, rookY2: Int
): Int{
    var ans=0
    if (kingX==rookX1 || kingY==rookY1){
        if (kingX==rookX2 || kingY==rookY2 ){
            ans=3
        }else{
            ans=1
        }
    }else{
        if (kingX==rookX2 || kingY==rookY2 ){
            ans=2
        }
    }
    return ans
}

/**
 * Простая
 *
 * На шахматной доске стоят черный король и белые ладья и слон
 * (ладья бьет по горизонтали и вертикали, слон — по диагоналям).
 * Проверить, есть ли угроза королю и если есть, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от ладьи, 2, если только от слона,
 * и 3, если угроза есть и от ладьи и от слона.
 * Считать, что ладья и слон не могут загораживать друг друга.
 */
fun rookOrBishopThreatens(
    kingX: Int, kingY: Int,
    rookX: Int, rookY: Int,
    bishopX: Int, bishopY: Int
): Int{
    var ans=0
    val x= abs(kingX-bishopX)
    val y= abs(kingY-bishopY)
    if (kingX==rookX || kingY==rookY){
        if (x==y ){
            ans=3
        }else{
            ans=1
        }
    }else{
        if (x==y){
            ans=2
        }
    }
    return ans
}

/**
 * Простая
 *
 * Треугольник задан длинами своих сторон a, b, c.
 * Проверить, является ли данный треугольник остроугольным (вернуть 0),
 * прямоугольным (вернуть 1) или тупоугольным (вернуть 2).
 * Если такой треугольник не существует, вернуть -1.
 */
fun triangleKind(a: Double, b: Double, c: Double): Int {
    var ans=-1
    if (a+b>c && a+c>b && b+c>a){
        val cosA:Double=(b*b+c*c-a*a)/(2*b*c)
        val cosB:Double=(a*a+c*c-b*b)/(2*a*c)
        val cosC:Double=(b*b+a*a-c*c)/(2*b*a)
        if (cosA==0.0 || cosB==0.0 || cosC==0.0){
            ans=1
        }else{
            if (cosA<0.0 || cosB<0.0 || cosC<0.0){
                ans=2
            }else{
                ans=0
            }
        }
    }
    return ans

}

/**
 * Средняя
 *
 * Даны четыре точки на одной прямой: A, B, C и D.
 * Координаты точек a, b, c, d соответственно, b >= a, d >= c.
 * Найти длину пересечения отрезков AB и CD.
 * Если пересечения нет, вернуть -1.
 */
fun segmentLength(a: Int, b: Int, c: Int, d: Int): Int{
    val sp1:Int
    val sp2:Int
    val ep1:Int
    val ep2:Int
    val l:Int

    if (a<b){
        sp1=a
        ep1=b
    }else{
        sp1=b
        ep1=a
    }

    if (c<d){
        sp2=c
        ep2=d
    }else{
        sp2=d
        ep2=c
    }

    when{
        (sp1>=sp2) && (sp1<=ep2) && (ep1<=ep2) -> l=ep1-sp1
        (sp1<sp2) && (ep1<=ep2) && (ep1>=sp2) -> l=ep1-sp2
        (sp1>=sp2) && (sp1<=ep2) && (ep1>ep2) -> l=ep2-sp1
        (sp1<sp2) && (ep1>ep2) -> l=ep2-sp2
        else -> l=-1
    }
    return l

}
