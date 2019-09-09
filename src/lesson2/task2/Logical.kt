@file:Suppress("UNUSED_PARAMETER")

package lesson2.task2

import lesson1.task1.sqr
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * Пример
 *
 * Лежит ли точка (x, y) внутри окружности с центром в (x0, y0) и радиусом r?
 */
fun pointInsideCircle(x: Double, y: Double, x0: Double, y0: Double, r: Double) =
    sqr(x - x0) + sqr(y - y0) <= sqr(r)

/**
 * Простая
 *
 * Четырехзначное число назовем счастливым, если сумма первых двух ее цифр равна сумме двух последних.
 * Определить, счастливое ли заданное число, вернуть true, если это так.
 */
fun isNumberHappy(number: Int): Boolean{
    val ans:Boolean
    val n1:Int
    val n2:Int
    val n3:Int
    val n4:Int
    n1=number/1000
    n2=number/100-n1*10
    n3=number/10-n2*10-n1*100
    n4=number%10
    ans= n1+n2==n3+n4

    return ans
}

/**
 * Простая
 *
 * На шахматной доске стоят два ферзя (ферзь бьет по вертикали, горизонтали и диагоналям).
 * Определить, угрожают ли они друг другу. Вернуть true, если угрожают.
 * Считать, что ферзи не могут загораживать друг друга.
 */
fun queenThreatens(x1: Int, y1: Int, x2: Int, y2: Int): Boolean {
    val ans:Boolean
    val x= abs(x1-x2)
    val y= abs(y1-y2)
    ans=x1==x2 || y1==y2 || x==y
    return ans
}


/**
 * Простая
 *
 * Дан номер месяца (от 1 до 12 включительно) и год (положительный).
 * Вернуть число дней в этом месяце этого года по григорианскому календарю.
 */
fun daysInMonth(month: Int, year: Int): Int =when {
    month==1 -> 31
    month==2 && (((year%4==0) && (year%100!=0)) || (year%400==0)) ->29
    month==3 -> 31
    month==4 -> 30
    month==5 -> 31
    month==6 -> 30
    month==7 -> 31
    month==8 -> 31
    month==9 -> 30
    month==10 -> 31
    month==11 -> 30
    month==12 -> 31
    else -> 28
}

/**
 * Средняя
 *
 * Проверить, лежит ли окружность с центром в (x1, y1) и радиусом r1 целиком внутри
 * окружности с центром в (x2, y2) и радиусом r2.
 * Вернуть true, если утверждение верно
 */
fun circleInside(
    x1: Double, y1: Double, r1: Double,
    x2: Double, y2: Double, r2: Double
): Boolean{
    val ans:Boolean
    val l:Double=sqrt(sqr(y2-y1)+sqr(x2-x1))
    ans=l+r1<=r2

    return ans
}

/**
 * Средняя
 *
 * Определить, пройдет ли кирпич со сторонами а, b, c сквозь прямоугольное отверстие в стене со сторонами r и s.
 * Стороны отверстия должны быть параллельны граням кирпича.
 * Считать, что совпадения длин сторон достаточно для прохождения кирпича, т.е., например,
 * кирпич 4 х 4 х 4 пройдёт через отверстие 4 х 4.
 * Вернуть true, если кирпич пройдёт
 */
fun brickPasses(a: Int, b: Int, c: Int, r: Int, s: Int): Boolean{
    val ans:Boolean
    val hMin:Int
    val hMax:Int
    val bMin1:Int
    val bMin2:Int

    if (r<s){
        hMin=r
        hMax=s
    }else{
        hMin=s
        hMax=r
    }

    if (a<=b && a<=c){
        bMin1=a
        if (b<c) bMin2=b else bMin2=c
    }else{
        if (b<a && b<=c){
            bMin1=b
            if (a<c) bMin2=a else bMin2=c
        }else{
            bMin1=c
            if (a<b) bMin2=a else bMin2=b
        }
    }

    ans=(bMin1<=hMin) && (bMin2<=hMax)

    return ans
}
