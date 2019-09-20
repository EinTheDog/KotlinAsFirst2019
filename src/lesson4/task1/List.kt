@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import lesson1.task1.sqr
import java.io.File.separator
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
    when {
        y < 0 -> listOf()
        y == 0.0 -> listOf(0.0)
        else -> {
            val root = sqrt(y)
            // Результат!
            listOf(-root, root)
        }
    }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double {
    var ans = 0.0
    for (i in v.indices) {
        ans += sqr(v[i])
    }
    return sqrt(ans)
}

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double {
    var ans = 0.0
    for (i in list.indices) ans += list[i]
    return if (list.isNotEmpty()) ans / list.size else 0.0
}

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    var mid = 0.0
    for (a in list) {
        mid += a
    }
    mid /= list.size
    for (i in list.indices) {
        list[i] -= mid
    }
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.
 */
fun times(a: List<Int>, b: List<Int>): Int {
    var c = 0
    for (i in a.indices) {
        c += a[i] * b[i]
    }
    return c
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0 при любом x.
 */
fun polynom(p: List<Int>, x: Int): Int {
    var a = 0
    for (i in p.indices) {
        a += p[i] * x.toDouble().pow(i).toInt()
    }
    return a
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Int>): MutableList<Int> {
    for (i in 1 until list.size) {
        list[i] += list[i - 1]
    }
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    var n1 = n
    val ans = mutableListOf<Int>()
    while (n1 > 1) {
        if (n1 % 2 == 0) {
            ans.add(2)
            n1 /= 2
            continue
        }
        var d = 3
        while (n1 % d != 0) {
            d += 2
        }
        ans.add(d)
        n1 /= d
    }
    return ans
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString(separator = "*")


/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    var n1 = n
    val ans = mutableListOf<Int>()
    while (n1 >= base) {
        ans.add(0, n1 % base)
        n1 /= base
    }
    ans.add(0, n1)
    return ans
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, n.toString(base) и подобные), запрещается.
 */
fun convertToString(n: Int, base: Int): String {
    val list = convert(n, base)
    var ans = ""
    for (o in list) {
        val s = if (o > 9) {
            (87 + o).toChar()
        } else {
            o
        }
        ans += s
    }
    return ans
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var ans = 0
    val s = digits.size - 1
    for (i in digits.indices) {
        ans += digits[i] * base.toDouble().pow(s - i).toInt()
    }
    return ans
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, str.toInt(base)), запрещается.
 */
fun decimalFromString(str: String, base: Int): Int {
    val list = str.toCharArray()
    var ans = 0
    val s = list.size - 1
    for (i in list.indices) {
        val n = if (list[i].toInt() > 86) {
            list[i].toInt() - 87
        } else {
            list[i].toString().toInt()
        }
        ans += n * base.toDouble().pow(s - i).toInt()
    }
    return ans
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {
    var n1 = n
    var ans = ""
    val listN = mutableListOf<Int>()
    val listC1 = listOf("M", "C", "X", "I")
    val listC2 = listOf("D", "L", "V")
    listN.add(n1 / 1000)
    n1 -= 1000 * listN[0]
    listN.add(n1 / 100)
    n1 -= 100 * listN[1]
    listN.add(n1 / 10)
    n1 -= 10 * listN[2]
    listN.add(n1)

    for (i in 3 downTo 1) {
        ans = when (listN[i]) {
            1 -> listC1[i] + ans
            2 -> listC1[i] + listC1[i] + ans
            3 -> listC1[i] + listC1[i] + listC1[i] + ans
            4 -> listC1[i] + listC2[i - 1] + ans
            5 -> listC2[i - 1] + ans
            6 -> listC2[i - 1] + listC1[i] + ans
            7 -> listC2[i - 1] + listC1[i] + listC1[i] + ans
            8 -> listC2[i - 1] + listC1[i] + listC1[i] + listC1[i] + ans
            9 -> listC1[i] + listC1[i - 1] + ans
            else -> ans
        }
    }
    for (i in 1..listN[0]) {
        ans = listC1[0] + ans
    }

    return ans
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String {
    var n1 = n
    var ans = ""
    val list1 = mutableListOf<Int>()
    val list2 = mutableListOf<Int>()
    var is10 = false
    for (i in 1..3) {
        list1.add(n1 % 10)
        n1 /= 10
    }
    for (i in 4..6) {
        list2.add(n1 % 10)
        n1 /= 10
    }
    fun write100(el: Int) {
        when (el) {
            1 -> ans += " сто"
            2 -> ans += " двести"
            3 -> ans += " триста"
            4 -> ans += " четыреста"
            5 -> ans += " пятьсот"
            6 -> ans += " шестьсот"
            7 -> ans += " семьсот"
            8 -> ans += " восемьсот"
            9 -> ans += " девятьсот"
        }
    }

    fun write10(el: Int) {
        when (el) {
            1 -> is10 = true
            2 -> ans += " двадцать"
            3 -> ans += " тридцать"
            4 -> ans += " сорок"
            5 -> ans += " пятьдесят"
            6 -> ans += " шестьдесят"
            7 -> ans += " семьдесят"
            8 -> ans += " восемьдесят"
            9 -> ans += " девяносто"
        }
    }

    write100(list2[2])
    write10(list2[1])

    if (is10) {
        when (list2[0]) {
            0 -> ans += " десять"
            1 -> ans += " одиннадцать"
            2 -> ans += " двенадцать"
            3 -> ans += " тринадцать"
            4 -> ans += " четырнадцать"
            5 -> ans += " пятнадцать"
            6 -> ans += " шестнадцать"
            7 -> ans += " семнадцать"
            8 -> ans += " восемьнадцать"
            9 -> ans += " девятнадцать"
        }
        ans += " тысяч"
    } else {
        when (list2[0]) {
            1 -> ans += " одна тысяча"
            2 -> ans += " две тысячи"
            3 -> ans += " три тысячи"
            4 -> ans += " четыре тысячи"
            5 -> ans += " пять тысяч"
            6 -> ans += " шесть тысяч"
            7 -> ans += " семь тысяч"
            8 -> ans += " восемь тысяч"
            9 -> ans += " девять тысяч"
            else -> if (list2[2] != 0 || list2[1] != 0) ans += " тысяч"
        }
    }

    write100(list1[2])
    is10 = false
    write10(list1[1])

    if (is10) {
        when (list1[0]) {
            0 -> ans += " десять"
            1 -> ans += " одиннадцать"
            2 -> ans += " двенадцать"
            3 -> ans += " тринадцать"
            4 -> ans += " четырнадцать"
            5 -> ans += " пятнадцать"
            6 -> ans += " шестнадцать"
            7 -> ans += " семнадцать"
            8 -> ans += " восемьнадцать"
            9 -> ans += " девятнадцать"
        }
    } else {
        when (list1[0]) {
            1 -> ans += " один"
            2 -> ans += " два"
            3 -> ans += " три"
            4 -> ans += " четыре"
            5 -> ans += " пять"
            6 -> ans += " шесть"
            7 -> ans += " семь"
            8 -> ans += " восемь"
            9 -> ans += " девять"
        }
    }
    return ans.trim()
}