@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import java.lang.IllegalArgumentException
import java.lang.String.format
import java.lang.StringBuilder

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    val list1 = str.split(" ")
    if (list1.size != 3) return ""
    try {
        val day = list1[0].toInt()
        val month = when (list1[1]) {
            "января" -> 1
            "февраля" -> 2
            "марта" -> 3
            "апреля" -> 4
            "мая" -> 5
            "июня" -> 6
            "июля" -> 7
            "августа" -> 8
            "сентября" -> 9
            "октября" -> 10
            "ноября" -> 11
            "декабря" -> 12
            else -> -1
        }
        val year = list1[2].toInt()
        return if (month == -1 || day > lesson2.task2.daysInMonth(month, year)) {
            ""
        } else {
            format("%02d.%02d.%d", day, month, year)
        }
    } catch (e: java.lang.NumberFormatException) {
        return ""
    }

}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {

    val listDigit = digital.split(".")
    val listAns = mutableListOf<String>()
    if (listDigit.size != 3) return ""

    try {
        listAns += listDigit[0].toInt().toString()

        listAns += when (listDigit[1].toInt()) {
            1 -> "января"
            2 -> "февраля"
            3 -> "марта"
            4 -> "апреля"
            5 -> "мая"
            6 -> "июня"
            7 -> "июля"
            8 -> "августа"
            9 -> "сентября"
            10 -> "октября"
            11 -> "ноября"
            12 -> "декабря"
            else -> ""
        }


        listAns += listDigit[2]
        return if (listAns[1] == "" || listDigit[0].toInt() > lesson2.task2.daysInMonth(
                listDigit[1].toInt(),
                listDigit[2].toInt()
            )
        ) {
            ""
        } else {
            "${listAns[0]} ${listAns[1]} ${listAns[2]}"
        }
    } catch (e: NumberFormatException) {
        return ""
    }
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun flattenPhoneNumber(phone: String): String {
    val list = listOf('+', '-', '(', ')', ' ')
    if (phone.any { it !in list && !it.isDigit() }) return ""
    val phone1 = phone.filter { it != ' ' && it != '-' }
    val ans = if (phone1.contains('(') || phone1.contains(')')) {
        Regex("""\+?(\d)*\((\d)+\)(\d)+""").find(phone1)?.value
    } else {
        Regex("""\+?(\d)+""").find(phone1)?.value
    }
    return (ans ?: "").filter { it != '(' && it != ')' }
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    if (Regex("""[^ 0123456789%-]""").find(jumps) != null) return -1
    val str = jumps.filter { it.isDigit() || it == ' ' }
    val list = str.split(' ')
    var best = -1
    for (o in list) {
        if (o.isNotEmpty() && o.toInt() > best) {
            best = o.toInt()
        }
    }
    return best
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    var i = 0
    var best = -1

    while (i < jumps.length) {
        if (jumps[i].isDigit()) {
            val heightStr = StringBuilder()
            while (jumps[i].isDigit()) {
                heightStr.append(jumps[i])
                i++
            }
            i++
            val heightDig = heightStr.toString().toInt()

            while (i < jumps.length && jumps[i] != ' ') {
                if (jumps[i] == '+' && best < heightDig) {
                    best = heightDig
                }
                i++
            }
            i--
        }
        i++
    }
    return best
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    require(expression.isNotEmpty())
    for (o in expression) {
        require(!(!o.isDigit() && o != ' ' && o != '+' && o != '-'))
    }
    var i = 0
    fun findNumber(): Int {
        var numStr = ""
        while (i < expression.length && expression[i].isDigit()) {
            numStr += expression[i]
            i++
        }
        return numStr.toInt()
    }

    try {
        var ans = 0
        ans += findNumber()
        while (i < expression.length) {
            i++
            val ch = expression[i]
            i += 2
            val x = findNumber()
            if (ch == '+') ans += x else ans -= x
        }
        return ans
    } catch (e: IllegalArgumentException) {
        throw e
    }
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val words = str.trim().split(" ")
    var ind = 0
    while (str[ind] == ' ') ind++
    for (i in 1 until words.size) {
        if (words[i].toLowerCase() == words[i - 1].toLowerCase()) return ind
        ind += words[i - 1].length + 1
    }
    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    if (description.isEmpty()) return ""
    var expName = ""
    var expPrice = -1.0
    val list = description.split(";")
    for (o in list) {
        val list2 = o.trim().split(' ')
        if (list2[1].toDouble() > expPrice) {
            expPrice = list2[1].toDouble()
            expName = list2[0]
        }
    }
    return expName
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    if (roman.trim().isEmpty()) return -1
    var ans = 0
    var num1: Int
    var num2: Int
    for (i in roman.indices) {
        num1 = when (roman[i]) {
            'I' -> 1
            'V' -> 5
            'X' -> 10
            'L' -> 50
            'C' -> 100
            'D' -> 500
            'M' -> 1000
            else -> return -1
        }
        if (i == roman.length - 1) {
            ans += num1
            break
        }
        num2 = when (roman[i + 1]) {
            'I' -> 1
            'V' -> 5
            'X' -> 10
            'L' -> 50
            'C' -> 100
            'D' -> 500
            'M' -> 1000
            else -> return -1
        }
        if (num2 > num1) ans -= num1 else ans += num1
    }
    return ans
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    val arr = Array(cells) { 0 }
    val comList = listOf('+', '-', '>', '<', '[', ']', ' ')
    var i = 0
    var j = cells / 2
    var lim = 1
    var pair = 0
    var wrongPair = false

    for (o in commands) {
        require(o in comList)
        if (o == '[') pair++
        if (o == ']') pair--
        if (pair < 0) wrongPair = true
    }
    require(pair == 0)
    require(!wrongPair)

    fun findNext() {
        var next = 1
        while (next > 0) {
            i++
            if (commands[i] == ']') next--
            if (commands[i] == '[') next++
        }
    }

    fun findPrev() {
        var next = 1
        while (next > 0) {
            i--
            if (commands[i] == '[') next--
            if (commands[i] == ']') next++
        }
    }

    while (i < commands.length && lim <= limit) {
        check(j in 0 until cells)
        when {
            commands[i] == '>' -> j++
            commands[i] == '<' -> j--
            commands[i] == '+' -> arr[j]++
            commands[i] == '-' -> arr[j]--
            commands[i] == '[' && arr[j] == 0 -> findNext()
            commands[i] == ']' && arr[j] != 0 -> findPrev()
        }
        lim++
        i++
    }
    check(j in 0 until cells)
    return arr.toList()
}
