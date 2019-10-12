@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1

/**
 * Пример
 *
 * Для заданного списка покупок `shoppingList` посчитать его общую стоимость
 * на основе цен из `costs`. В случае неизвестной цены считать, что товар
 * игнорируется.
 */
fun shoppingListCost(
    shoppingList: List<String>,
    costs: Map<String, Double>
): Double {
    var totalCost = 0.0

    for (item in shoppingList) {
        val itemCost = costs[item]
        if (itemCost != null) {
            totalCost += itemCost
        }
    }

    return totalCost
}

/**
 * Пример
 *
 * Для набора "имя"-"номер телефона" `phoneBook` оставить только такие пары,
 * для которых телефон начинается с заданного кода страны `countryCode`
 */
fun filterByCountryCode(
    phoneBook: MutableMap<String, String>,
    countryCode: String
) {
    val namesToRemove = mutableListOf<String>()

    for ((name, phone) in phoneBook) {
        if (!phone.startsWith(countryCode)) {
            namesToRemove.add(name)
        }
    }

    for (name in namesToRemove) {
        phoneBook.remove(name)
    }
}

/**
 * Пример
 *
 * Для заданного текста `text` убрать заданные слова-паразиты `fillerWords`
 * и вернуть отфильтрованный текст
 */
fun removeFillerWords(
    text: List<String>,
    vararg fillerWords: String
): List<String> {
    val fillerWordSet = setOf(*fillerWords)

    val res = mutableListOf<String>()
    for (word in text) {
        if (word !in fillerWordSet) {
            res += word
        }
    }
    return res
}

/**
 * Пример
 *
 * Для заданного текста `text` построить множество встречающихся в нем слов
 */
fun buildWordSet(text: List<String>): MutableSet<String> {
    val res = mutableSetOf<String>()
    for (word in text) res.add(word)
    return res
}


/**
 * Простая
 *
 * По заданному ассоциативному массиву "студент"-"оценка за экзамен" построить
 * обратный массив "оценка за экзамен"-"список студентов с этой оценкой".
 *
 * Например:
 *   buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
 *     -> mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат"))
 */
fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> {
    val ans = mutableMapOf<Int, MutableList<String>>()
    for ((name, grade) in grades) {
        if (grade !in ans) {
            ans[grade] = mutableListOf(name)
        } else {
            ans[grade]!!.add(name)
        }
    }
    return ans
}

/**
 * Простая
 *
 * Определить, входит ли ассоциативный массив a в ассоциативный массив b;
 * это выполняется, если все ключи из a содержатся в b с такими же значениями.
 *
 * Например:
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")) -> true
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")) -> false
 */
fun containsIn(a: Map<String, String>, b: Map<String, String>): Boolean {
    for ((key, value) in a) {
        if (b[key] != value) {
            return false
        }
    }
    return true
}

/**
 * Простая
 *
 * Удалить из изменяемого ассоциативного массива все записи,
 * которые встречаются в заданном ассоциативном массиве.
 * Записи считать одинаковыми, если и ключи, и значения совпадают.
 *
 * ВАЖНО: необходимо изменить переданный в качестве аргумента
 *        изменяемый ассоциативный массив
 *
 * Например:
 *   subtractOf(a = mutableMapOf("a" to "z"), mapOf("a" to "z"))
 *     -> a changes to mutableMapOf() aka becomes empty
 */
fun subtractOf(a: MutableMap<String, String>, b: Map<String, String>): Unit {
    for ((key) in b) {
        if (a[key] == b[key]) {
            a.remove(key)
        }
    }
}

/**
 * Простая
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках.
 * В выходном списке не должно быть повторяюихся элементов,
 * т. е. whoAreInBoth(listOf("Марат", "Семён, "Марат"), listOf("Марат", "Марат")) == listOf("Марат")
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> {
    val ans = mutableSetOf<String>()
    for (o in a) {
        if (b.contains(o)) ans.add(o)
    }
    return ans.toList()
}

/**
 * Средняя
 *
 * Объединить два ассоциативных массива `mapA` и `mapB` с парами
 * "имя"-"номер телефона" в итоговый ассоциативный массив, склеивая
 * значения для повторяющихся ключей через запятую.
 * В случае повторяющихся *ключей* значение из mapA должно быть
 * перед значением из mapB.
 *
 * Повторяющиеся *значения* следует добавлять только один раз.
 *
 * Например:
 *   mergePhoneBooks(
 *     mapOf("Emergency" to "112", "Police" to "02"),
 *     mapOf("Emergency" to "911", "Police" to "02")
 *   ) -> mapOf("Emergency" to "112, 911", "Police" to "02")
 */
fun mergePhoneBooks(mapA: Map<String, String>, mapB: Map<String, String>): Map<String, String> {
    val mapC = mapA.toMutableMap()
    for ((key, value) in mapB) {
        if (key !in mapC) {
            mapC[key] = value
        } else if (!mapC[key].equals(value)) {
            mapC[key] += ", $value"
        }
    }
    return mapC
}

/**
 * Средняя
 *
 * Для заданного списка пар "акция"-"стоимость" вернуть ассоциативный массив,
 * содержащий для каждой акции ее усредненную стоимость.
 *
 * Например:
 *   averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
 *     -> mapOf("MSFT" to 150.0, "NFLX" to 40.0)
 */
fun averageStockPrice(stockPrices: List<Pair<String, Double>>): Map<String, Double> {
    val mapA = mutableMapOf<String, Double>()
    val mapB = mutableMapOf<String, Int>()
    for ((key, value) in stockPrices) {
        if (key !in mapA) {
            mapA[key] = value
            mapB[key] = 1
        } else {
            mapB[key] = mapB[key]!! + 1
            mapA[key] = mapA[key]!! + value
        }
    }
    for ((key) in mapA) {
        mapA[key] = mapA[key]!! / mapB[key]!!
    }
    return mapA
}

/**
 * Средняя
 *
 * Входными данными является ассоциативный массив
 * "название товара"-"пара (тип товара, цена товара)"
 * и тип интересующего нас товара.
 * Необходимо вернуть название товара заданного типа с минимальной стоимостью
 * или null в случае, если товаров такого типа нет.
 *
 * Например:
 *   findCheapestStuff(
 *     mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
 *     "печенье"
 *   ) -> "Мария"
 */
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>, kind: String): String? {
    var ans = ""
    var cost = -1.0
    for ((key, value) in stuff) {
        if (value.first == kind) {
            if (cost == -1.0) {
                ans = key
                cost = value.second
            } else if (cost > value.second) {
                ans = key
                cost = value.second
            }
        }
    }
    return if (cost == -1.0) {
        null
    } else {
        ans
    }
}

/**
 * Средняя
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String): Boolean =
    chars.toMutableList().map { it.toLowerCase() }.containsAll(word.toLowerCase().toList())

/**
 * Средняя
 *
 * Найти в заданном списке повторяющиеся элементы и вернуть
 * ассоциативный массив с информацией о числе повторений
 * для каждого повторяющегося элемента.
 * Если элемент встречается только один раз, включать его в результат
 * не следует.
 *
 * Например:
 *   extractRepeats(listOf("a", "b", "a")) -> mapOf("a" to 2)
 */
fun extractRepeats(list: List<String>): Map<String, Int> {
    val ans = mutableMapOf<String, Int>()
    for (key in list) {
        if (key !in ans) {
            ans[key] = 1
        } else {
            ans[key] = ans[key]!! + 1
        }
    }
    for (key in list) {
        if (ans[key]!! < 2) {
            ans.remove(key)
        }
    }
    return ans
}

/**
 * Средняя
 *
 * Для заданного списка слов определить, содержит ли он анаграммы
 * (два слова являются анаграммами, если одно можно составить из второго)
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun hasAnagrams(words: List<String>): Boolean {
    for (i in 1 until words.size) {
        val set1 = words[i].toSet()
        for (j in 0 until i) {
            val set2 = words[j].toSet()
            if (set1 == set2) {
                return true
            }
        }
    }
    return false
}

/**
 * Сложная
 *
 * Для заданного ассоциативного массива знакомых через одно рукопожатие `friends`
 * необходимо построить его максимальное расширение по рукопожатиям, то есть,
 * для каждого человека найти всех людей, с которыми он знаком через любое
 * количество рукопожатий.
 * Считать, что все имена людей являются уникальными, а также что рукопожатия
 * являются направленными, то есть, если Марат знает Свету, то это не означает,
 * что Света знает Марата.
 *
 * Например:
 *   propagateHandshakes(
 *     mapOf(
 *       "Marat" to setOf("Mikhail", "Sveta"),
 *       "Sveta" to setOf("Marat"),
 *       "Mikhail" to setOf("Sveta")
 *     )
 *   ) -> mapOf(
 *          "Marat" to setOf("Mikhail", "Sveta"),
 *          "Sveta" to setOf("Marat", "Mikhail"),
 *          "Mikhail" to setOf("Sveta", "Marat")
 *        )
 */
fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> {
    val f1 = friends.toMutableMap()

    fun findFriends(friend: String, name: String, mSet: MutableSet<String>): MutableSet<String> {
        if (friend !in f1) {
            f1[friend] = setOf()
        }
        if (f1[friend]!!.isNotEmpty()) {
            for (k in friends[friend]!!) {
                if (k !in mSet && k != name) {
                    mSet.add(k)
                    findFriends(k, name, mSet)
                }
            }
        }
        return mSet
    }

    for ((key, value) in friends) {
        if (value.isNotEmpty()) {
            for (friend in value) {
                f1[key] = findFriends(friend, key, f1[key]!!.toMutableSet())
            }
        }
    }

    return f1
}

/**
 * Сложная
 *
 * Для заданного списка неотрицательных чисел и числа определить,
 * есть ли в списке пара чисел таких, что их сумма равна заданному числу.
 * Если да, верните их индексы в виде Pair<Int, Int>;
 * если нет, верните пару Pair(-1, -1).
 *
 * Индексы в результате должны следовать в порядке (меньший, больший).
 *
 * Постарайтесь сделать ваше решение как можно более эффективным,
 * используя то, что вы узнали в данном уроке.
 *
 * Например:
 *   findSumOfTwo(listOf(1, 2, 3), 4) -> Pair(0, 2)
 *   findSumOfTwo(listOf(1, 2, 3), 6) -> Pair(-1, -1)
 */
fun findSumOfTwo(list: List<Int>, number: Int): Pair<Int, Int> {
    val map = mutableMapOf<Int, Int>()
    for (i in list.indices) {
        if (map[list[i]] != null) {
            return Pair(map[list[i]]!!, i)
        }
        map[number - list[i]] = i
    }
    return Pair(-1, -1)
}

/**
 * Очень сложная
 *
 * Входными данными является ассоциативный массив
 * "название сокровища"-"пара (вес сокровища, цена сокровища)"
 * и вместимость вашего рюкзака.
 * Необходимо вернуть множество сокровищ с максимальной суммарной стоимостью,
 * которые вы можете унести в рюкзаке.
 *
 * Перед решением этой задачи лучше прочитать статью Википедии "Динамическое программирование".
 *
 * Например:
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     850
 *   ) -> setOf("Кубок")
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     450
 *   ) -> emptySet()
 */
fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> {
    if (treasures.isEmpty()) return setOf()
    val bag = mutableListOf<Pair<String, MutableList<Pair<Int, Int>>>>()
    for ((Key) in treasures) {
        bag.add(Pair(Key, mutableListOf<Pair<Int, Int>>()))
    }
    var weight = treasures[bag[0].first]!!.first
    var cost = treasures[bag[0].first]!!.second
    if (weight <= capacity) bag[0].second.add(Pair(weight, cost))

    var i = 1
    while (i < bag.size) {
        weight = treasures[bag[i].first]!!.first
        cost = treasures[bag[i].first]!!.second

        var j = 0
        while (j < bag[i - 1].second.size && bag[i - 1].second[j].first < weight) {
            val weight2 = bag[i - 1].second[j].first
            val cost2 = bag[i - 1].second[j].second
            bag[i].second.add(Pair(weight2, cost2))
            j++
        }
        if (weight <= capacity && bag[i].second.size == 0) {
            if (bag[i - 1].second.isNotEmpty()) {
                val weight2 = bag[i - 1].second[j].first
                val cost2 = bag[i - 1].second[j].second
                if (weight < weight2) {
                    bag[i].second.add(Pair(weight, cost))
                } else {
                    if (cost > cost2) bag[i].second.add(Pair(weight, cost)) else bag[i].second.add(Pair(weight2, cost2))
                }
            } else{
                bag[i].second.add(Pair(weight, cost))
            }
            j++
        }
        if (j > 0 && bag[i].second[j - 1].second < cost && weight <= capacity) {
            bag[i].second.add(Pair(weight, cost))
        }
        for ((weight2, cost2) in bag[i - 1].second) {
            if (cost2 > bag[i].second[j - 1].second) {
                bag[i].second.add(Pair(weight2, cost2))
            }
            if (bag[i].second.isEmpty() || (cost2 + cost > bag[i].second[j - 1].second && weight + weight2 <= capacity)) {
                val f: Pair<Int, Int>? =
                    bag[i - 1].second.find { it.second == cost + cost2 && it.first < weight + weight2 }
                if (f != null) bag[i].second.add(f) else bag[i].second.add(Pair(weight + weight2, cost + cost2))
                j++
            } else {
                if (cost2 > bag[i].second[j - 1].second) {
                    bag[i].second.add(Pair(weight2, cost2))
                    j++
                }
            }
        }
        i++
    }

    i--
    val ans = mutableSetOf<String>()
    if (bag[i].second.size > 0) {
        var j: Int
        var k: Int
        var w = bag[i].second[bag[i].second.size - 1].first
        var c = bag[i].second[bag[i].second.size - 1].second
        while (i > 0 && w > 0) {
            j = bag[i].second.indexOf(Pair(w, c))
            k = bag[i - 1].second.indexOf(Pair(w, c))
            weight = treasures[bag[i].first]!!.first
            cost = treasures[bag[i].first]!!.second
            if (k == -1) {
                ans.add(bag[i].first)
                w -= weight
                c -= cost
            }
            i--
        }
        if (w > 0) {
            ans.add(bag[i].first)
        }
    }

    return ans
}
