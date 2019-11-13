@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import java.io.File
import java.io.File.separator
import java.util.*
import kotlin.math.pow
import kotlin.text.StringBuilder

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                } else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val exceptSimbols = listOf<String>("?", "[", "]", "^", ".", "$", "+", "*", "(", ")")
    val ans = mutableMapOf<String, Int>()
    for (o in substrings) {
        ans[o] = 0
    }
    for (line in File(inputName).readLines()) {
        val fixedLine = line.toLowerCase()
        if (fixedLine.isEmpty()) continue
        for (key in substrings) {
            val l = key.length
            var i = 0
            while (i <= line.length - l) {
                val match = if (key !in exceptSimbols) key.toLowerCase().toRegex().find(fixedLine, i)
                else ("\\${key.toLowerCase()}").toRegex().find(fixedLine, i)
                if (match != null) {
                    i = match.range.first + 1
                    ans[key] = ans[key]!! + 1
                } else i = line.length - l + 1
            }
        }
    }
    return ans
}


/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    val letters = setOf<Char>('ж', 'ч', 'ш', 'щ')
    val ans = File(outputName).bufferedWriter()
    for (line in File(inputName).readLines()) {
        val fixedLine = StringBuilder()
        fixedLine.append(line[0])
        for (i in 1 until line.length) {
            if (line[i - 1].toLowerCase() in letters) {
                when (line[i].toLowerCase()) {
                    'ы' -> fixedLine.append('и')
                    'я' -> fixedLine.append('а')
                    'ю' -> fixedLine.append('у')
                    else -> fixedLine.append(line[i])
                }
                if (line[i].isUpperCase()) fixedLine[i] = fixedLine[i].toUpperCase()
            } else {
                fixedLine.append(line[i])
            }
        }
        ans.write(fixedLine.toString())
        ans.newLine()
    }
    ans.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val file = File(inputName).readLines()
    var max = if (file.isNotEmpty()) file.maxBy { it.trim().length }!!.trim().length else 0
    for (line in file) {
        val line1 = line.trim()
        val fixedLine = StringBuilder()
        val l = line1.length
        for (i in 1..(max - l) / 2) {
            fixedLine.append(' ')
        }
        fixedLine.append(line1)
        writer.write(fixedLine.toString())
        writer.newLine()
    }
    writer.close()
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val file = File(inputName).readLines()
    var max = if (file.isNotEmpty()) file.maxBy { it.trim().length }!!.trim().length else 0
    for (line in file) {
        if (line == "") {
            writer.newLine()
            continue
        }
        val list = line.trim().split(' ').toMutableList()
        if (list.size == 1) {
            writer.write(line.trim())
            writer.newLine()
            continue
        }
        for (o in list) o.trim()
        while ("" in list) list.remove("")
        var l = list.joinToString("").length
        var i = 0
        while (l < max) {
            list[i] += " "
            i++
            l++
            i %= list.size - 1
        }
        writer.write(list.joinToString(""))
        writer.newLine()
    }
    writer.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    val allWords = mutableMapOf<String, Int>()
    val reg = Regex("""[^A-Za-zА-Яа-яЁё]""")
    for (line in File(inputName).readLines()) {
        val wordList = line.toLowerCase().trim().split(reg)
        for (word in wordList) {
            val fixedWord = word.filter { it.isLetter() }
            if (fixedWord != "") allWords[fixedWord] = allWords.getOrPut(fixedWord, { 0 }) + 1
        }
    }
    if (allWords.size <= 20) return allWords
    val list = allWords.toList().toMutableList()
    list.sortBy { it.second }
    //val top20 = mutableMapOf<String, Int>()
    /*for (i in list.size - 1 downTo list.size - 20) {
        top20[list[i].first] = list[i].second
    }*/
    return allWords.toList().sortedByDescending { (key, value) -> value }.take(20).toMap()
}

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val myDictionary = mutableMapOf<Char, String>()
    for ((key, value) in dictionary) {
        myDictionary[key.toLowerCase()] = value.toLowerCase()
        myDictionary[key.toUpperCase()] = value.toLowerCase().capitalize()
    }

    val exceptSimbols = listOf<Char>('?', '[', ']', '^', '.', '$', '+', '*', '(', ')', '{', '}', '\\')
    for (line in File(inputName).readLines()) {
        var row = line
        for ((key) in myDictionary) {
            val fixedKey = if (key in exceptSimbols) "\\" + key.toString() else key.toString()
            for (match in fixedKey.toRegex().findAll(line)) {
                val s = match.value
                row = row.replace(s, myDictionary[key]!!)
            }
        }

        writer.write(row)
        writer.newLine()
    }
    writer.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    var words = listOf<String>()
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) continue
        val set = line.toLowerCase().toSet()
        if (set.size == line.length) words += line
    }
    val maxLength = if (words.isNotEmpty()) words.maxBy { it.length }!!.length else 0
    words = words.filter { it.length == maxLength }
    writer.write(words.joinToString(separator = ", "))
    writer.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    val stack = mutableListOf<String>()
    val writer = File(outputName).bufferedWriter()
    stack.add("<html>")
    writer.write((stack.last()))
    writer.newLine()
    stack.add("<body>")
    writer.write((stack.last()))
    writer.newLine()
    writer.write("<p>")
    stack.add("<p>")
    writer.newLine()
    var emptyPar = true

    val file = File(inputName).readLines()
    for (i in file.indices) {
        val line = file[i]
        if (line.isEmpty()) {
            var j = i + 1
            while (j < file.size && file[j].isEmpty()) j++
            if (!emptyPar && j < file.size) writer.write("</p><p>")
            emptyPar = true
            continue
        }
        emptyPar = false
        var i = 0
        while (i < line.length) {
            if (line[i] == '*') {
                if (i < line.length - 1 && line[i + 1] == '*') {
                    if (stack.last() != "<b>") {
                        stack.add("<b>")
                        writer.write(stack.last())
                    } else {
                        writer.write("</b>")
                        stack.remove(stack.last())
                    }
                    i++
                } else {
                    if (stack.last() != "<i>") {
                        stack.add("<i>")
                        writer.write(stack.last())
                    } else {
                        writer.write("</i>")
                        stack.remove(stack.last())
                    }
                }
            } else {
                if (i < line.length - 1 && line.substring(i, i + 2) == "~~") {
                    if (stack.last() != "<s>") {
                        stack.add("<s>")
                        writer.write(stack.last())
                    } else {
                        writer.write("</s>")
                        stack.remove(stack.last())
                    }
                    i++
                } else {
                    writer.write(line[i].toString())
                }
            }
            i++
        }
        writer.newLine()
    }
    stack.remove("<p>")
    writer.write("</p>")
    writer.newLine()
    stack.remove(stack.last())
    writer.write("</body>")
    writer.newLine()
    stack.remove(stack.last())
    writer.write("</html>")
    writer.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>
Или колбаса
</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>
Фрукты
<ol>
<li>Бананы</li>
<li>
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ol>
</li>
</ul>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    val stack = mutableListOf<String>()
    val writer = File(outputName).bufferedWriter()
    var indentPrev = -1
    var indentCur = -1
    var indentNext = -1

    stack.add("<html>")
    writer.write((stack.last()))
    writer.newLine()
    stack.add("<body>")
    writer.write((stack.last()))
    writer.newLine()


    val file = File(inputName).readLines()
    for (i in file.indices) {
        val line = file[i]
        val nextLine = if (i < file.size - 1) file[i + 1] else ""
        indentPrev = indentCur
        indentCur = (Regex(""" *""").find(line)?.value ?: "").length
        indentNext = (Regex(""" *""").find(nextLine)?.value ?: "").length

        if (indentCur > indentPrev) {
            val s = if (line.trim()[0] == '*') "<ul>" else "<ol>"
            stack.add(s)
            writer.write(s)
            writer.newLine()
        }
        if (indentCur < indentPrev) {
            val s = if (stack.last() == "<ol>") "</ol>" else "</ul>"
            stack.removeAt(stack.lastIndex)
            writer.write(s)
            writer.newLine()
            if (stack.last() == "<li>") {
                stack.removeAt(stack.lastIndex)
                writer.write("</li>")
                writer.newLine()
            }
        }
        if (indentCur < indentNext) {
            stack.add("<li>")
            writer.write("<li>")
            writer.newLine()
            writer.write(line.filter { it != '*' && !it.isDigit() && it != '.' }.trim())
        } else {
            writer.write("<li>${line.filter { it != '*' && !it.isDigit() && it != '.' }.trim()}</li>")
        }
    }
    while (stack.size > 2) {
        val s = if (stack.last() == "<ol>") "</ol>" else "</ul>"
        stack.removeAt(stack.lastIndex)
        writer.write(s)
        writer.newLine()
        if (stack.last() == "<li>") {
            stack.removeAt(stack.lastIndex)
            writer.write("</li>")
            writer.newLine()
        }
    }
    stack.removeAt(stack.lastIndex)
    writer.write("</body>")
    writer.newLine()
    stack.removeAt(stack.lastIndex)
    writer.write("</html>")
    writer.close()
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    var curLine = ""
    var nextLine = ""
    var indentCur = -1
    var indentPrev = -1
    var indentNext = -1
    val stack1 = mutableListOf<String>()
    val stack2 = mutableListOf<String>()
    var isList = false

    stack1.add("<html>")
    stack2.add("<html>")
    writer.write("<html>")
    writer.newLine()
    stack1.add("<body>")
    stack2.add("<body>")
    writer.write("<body>")
    writer.newLine()

    var emptyLinesEx = false
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) emptyLinesEx = true
    }
    if (emptyLinesEx) {
        stack1.add("<p>")
        writer.write("<p>")
        writer.newLine()
    }



    fun writeLine(line: String) {
        var i = 0
        while (i < line.length) {
            if (line[i] == '*') {
                if (i < line.length - 1 && line[i + 1] == '*') {
                    if (stack1.last() != "<b>") {
                        stack1.add("<b>")
                        writer.write(stack1.last())
                    } else {
                        writer.write("</b>")
                        stack1.removeAt(stack1.lastIndex)
                    }
                    i++
                } else {
                    if (stack1.last() != "<i>") {
                        stack1.add("<i>")
                        writer.write(stack1.last())
                    } else {
                        writer.write("</i>")
                        stack1.removeAt(stack1.lastIndex)
                    }
                }
            } else {
                if (i < line.length - 1 && line.substring(i, i + 2) == "~~") {
                    if (stack1.last() != "<s>") {
                        stack1.add("<s>")
                        writer.write(stack1.last())
                    } else {
                        writer.write("</s>")
                        stack1.removeAt(stack1.lastIndex)
                    }
                    i++
                } else {
                    writer.write(line[i].toString())
                }
            }
            i++
        }
    }

    val file = File(inputName).readLines()
    for (i in file.indices) {
        curLine = file[i]
        isList = if (curLine.trim().length > 0) curLine.trim()[0] == '*' || curLine.trim()[0].isDigit() else false
        if (curLine.isEmpty()) {
            writer.write("</p><p>")
            writer.newLine()
        } else {
            nextLine = if (i < file.size - 1) file[i + 1] else ""
            indentPrev = indentCur
            indentCur = (Regex(""" *""").find(curLine)?.value ?: "").length
            indentNext = (Regex(""" *""").find(nextLine)?.value ?: "").length
            if (isList && indentCur > indentPrev) {
                val s = if (curLine.trim()[0] == '*') "<ul>" else "<ol>"
                stack2.add(s)
                writer.write(s)
                writer.newLine()
            }
            if (indentCur < indentPrev && (stack2.last() == "<ol>" || stack2.last() == "<ul>" || stack2.last() == "<li>")) {
                val s = if (stack2.last() == "<ol>") "</ol>" else "</ul>"
                stack2.removeAt(stack2.lastIndex)
                writer.write(s)
                writer.newLine()
                if (stack2.last() == "<li>") {
                    stack2.removeAt(stack2.lastIndex)
                    writer.write("</li>")
                    writer.newLine()
                }
            }
            var startIndex = 0
            while (curLine[startIndex].isDigit() || curLine[startIndex] == ' ' || curLine[startIndex] == '*' || curLine[startIndex] == '.') startIndex++
            if (isList && indentCur < indentNext) {
                stack2.add("<li>")
                writer.write("<li>")
                writer.newLine()
                writeLine(curLine.substring(startIndex))
                writer.newLine()
            } else {
                if (isList) writer.write("<li>")
                writeLine(curLine.substring(startIndex))
                if (isList) writer.write("</li>")
                writer.newLine()
            }
        }
    }
    if (emptyLinesEx) {
        stack1.remove("<p>")
        writer.write("</p>")
        writer.newLine()
    }
    while (stack2.size > 2) {
        val s = if (stack2.last() == "<ol>") "</ol>" else "</ul>"
        stack2.removeAt(stack2.lastIndex)
        writer.write(s)
        writer.newLine()
        if (stack2.last() == "<li>") {
            stack2.removeAt(stack2.lastIndex)
            writer.write("</li>")
            writer.newLine()
        }
    }
    stack2.removeAt(stack2.lastIndex)
    writer.write("</body>")
    writer.newLine()
    stack2.removeAt(stack2.lastIndex)
    writer.write("</html>")

    writer.close()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val num1 = lhv.toString()
    val num2 = rhv.toString()
    val l = num2.length
    val rowLength = ((lhv * rhv).toString()).length + 1

    for (j in 1..(rowLength - num1.length)) writer.write(" ")
    writer.write(num1)
    writer.newLine()
    writer.write("*")
    for (j in 2..(rowLength - num2.length)) writer.write(" ")
    writer.write(num2)
    writer.newLine()

    writer.write("-".repeat(rowLength))
    writer.newLine()

    val firstL = (lhv * num2[l - 1].toString().toInt()).toString().length
    for (j in 0 until rowLength - firstL) writer.write(" ")
    writer.write((lhv * num2[l - 1].toString().toInt()).toString())
    writer.newLine()

    for (i in l - 2 downTo 0) {
        val ind = l - i
        val curL = (lhv * num2[i].toString().toInt()).toString().length
        writer.write("+")
        for (j in 0 until rowLength - ind - curL) writer.write(" ")
        writer.write((lhv * num2[i].toString().toInt()).toString())
        writer.newLine()
    }

    for (j in 1..rowLength) writer.write("-")
    writer.newLine()

    val ans = (lhv * rhv).toString()
    for (j in 1..(rowLength - ans.length)) writer.write(" ")
    writer.write(ans)
    writer.close()
}


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    var lhv1 = lhv
    val num1S = lhv.toString()
    val num2S = rhv.toString()
    var indent = 0
    val writer = File(outputName).bufferedWriter()

    var l = 1
    while (l < num1S.length && num1S.substring(0, l).toInt() < rhv) l++
    var curLhv = num1S.substring(0, l).toInt()
    var curLhvS = curLhv.toString()
    var k = (lhv / rhv).toString()[0].toString().toInt()
    if (curLhvS.length < "-${rhv * k}".length) writer.write(" $num1S | $num2S")
    else writer.write("$num1S | $num2S")
    writer.newLine()
    var spDash = if ("-${rhv * k}".length > curLhvS.length) 0 else 1
    val firstMinusInd = if ("-${rhv * k}".length > curLhvS.length) 1 else 0
    var dif = curLhvS.length - (rhv * k).toString().length
    var substL = 0
    fun writeDivPart1() {
        for (i in 1 until indent + firstMinusInd + dif) {
            writer.write(" ")
            substL++
        }
    }
    writeDivPart1()
    writer.write("-${rhv * k}")
    substL += "-${rhv * k}".length
    for (i in substL until num1S.length + firstMinusInd + 3) writer.write(" ")
    writer.write((lhv / rhv).toString())
    writer.newLine()
    fun writeDivPart2() {
        for (i in 1 until indent + firstMinusInd + spDash) writer.write(" ")
        for (i in spDash..curLhvS.length) writer.write("-")
        writer.newLine()
    }
    writeDivPart2()
    fun writeDivPart3() {
        indent += curLhvS.length - (curLhv - rhv * k).toString().length
        curLhv -= rhv * k
        curLhvS = curLhv.toString()
        lhv1 -= rhv * k * 10.0.pow(num1S.length - l).toInt()
        for (i in 1..indent + firstMinusInd) writer.write(" ")
        l++
        if (l <= num1S.length) {
            curLhv = curLhv * 10 + num1S[l - 1].toString().toInt()
            curLhvS += num1S[l - 1].toString()
        }
        writer.write(curLhvS)
        writer.newLine()
    }
    writeDivPart3()
    var j = 1
    while (l <= num1S.length) {
        k = (lhv / rhv).toString()[j].toString().toInt()
        spDash = if ("-${rhv * k}".length > curLhvS.length) 0 else 1
        dif = curLhvS.length - (rhv * k).toString().length
        writeDivPart1()
        writer.write("-${rhv * k}")
        writer.newLine()
        writeDivPart2()
        writeDivPart3()
        j++
    }
    writer.close()
}

