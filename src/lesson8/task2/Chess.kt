@file:Suppress("UNUSED_PARAMETER")

package lesson8.task2

import java.lang.IllegalArgumentException
import kotlin.math.abs

/**
 * Клетка шахматной доски. Шахматная доска квадратная и имеет 8 х 8 клеток.
 * Поэтому, обе координаты клетки (горизонталь row, вертикаль column) могут находиться в пределах от 1 до 8.
 * Горизонтали нумеруются снизу вверх, вертикали слева направо.
 */
data class Square(val column: Int, val row: Int) {
    /**
     * Пример
     *
     * Возвращает true, если клетка находится в пределах доски
     */
    fun inside(): Boolean = column in 1..8 && row in 1..8

    /**
     * Простая
     *
     * Возвращает строковую нотацию для клетки.
     * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
     * Для клетки не в пределах доски вернуть пустую строку
     */
    fun notation(): String {
        if (this.column !in 1..8 || this.row !in 1..8) return ""
        return "${(this.column + 96).toChar()}${this.row}"
    }

}

/**
 * Простая
 *
 * Создаёт клетку по строковой нотации.
 * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
 * Если нотация некорректна, бросить IllegalArgumentException
 */
fun checkSquare(sqr: Square) {
    require(sqr.column in 1..8 && sqr.row in 1..8)
}

fun square(notation: String): Square {
    require(notation.length == 2)
    val x = notation[0].toInt() - 96
    val y = notation[1].toInt() - 48
    checkSquare(Square(x, y))
    return (Square(x, y))
}

/**
 * Простая
 *
 * Определить число ходов, за которое шахматная ладья пройдёт из клетки start в клетку end.
 * Шахматная ладья может за один ход переместиться на любую другую клетку
 * по вертикали или горизонтали.
 * Ниже точками выделены возможные ходы ладьи, а крестиками -- невозможные:
 *
 * xx.xxххх
 * xх.хxххх
 * ..Л.....
 * xх.хxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: rookMoveNumber(Square(3, 1), Square(6, 3)) = 2
 * Ладья может пройти через клетку (3, 3) или через клетку (6, 1) к клетке (6, 3).
 */
fun rookMoveNumber(start: Square, end: Square): Int {
    checkSquare(start)
    checkSquare(end)
    return if (start.row == end.row && start.column == end.column) 0
    else if (start.row == end.row || start.column == end.column) 1
    else 2
}

/**
 * Средняя
 *
 * Вернуть список из клеток, по которым шахматная ладья может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов ладьи см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: rookTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможен ещё один вариант)
 *          rookTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(3, 3), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          rookTrajectory(Square(3, 5), Square(8, 5)) = listOf(Square(3, 5), Square(8, 5))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun rookTrajectory(start: Square, end: Square): List<Square> {
    checkSquare(start)
    checkSquare(end)
    val ans = mutableListOf<Square>()
    ans.add(start)
    if (start.row != end.row && start.column != end.column) ans.add(Square(start.column, end.row))
    if (start.row != end.row || start.column != end.column) ans.add(end)
    return ans
}

/**
 * Простая
 *
 * Определить число ходов, за которое шахматный слон пройдёт из клетки start в клетку end.
 * Шахматный слон может за один ход переместиться на любую другую клетку по диагонали.
 * Ниже точками выделены возможные ходы слона, а крестиками -- невозможные:
 *
 * .xxx.ххх
 * x.x.xххх
 * xxСxxxxx
 * x.x.xххх
 * .xxx.ххх
 * xxxxx.хх
 * xxxxxх.х
 * xxxxxхх.
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если клетка end недостижима для слона, вернуть -1.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Примеры: bishopMoveNumber(Square(3, 1), Square(6, 3)) = -1; bishopMoveNumber(Square(3, 1), Square(3, 7)) = 2.
 * Слон может пройти через клетку (6, 4) к клетке (3, 7).
 */
fun bishopMoveNumber(start: Square, end: Square): Int {
    checkSquare(start)
    checkSquare(end)
    if ((start.row % 2 == start.column % 2) != (end.column % 2 == end.row % 2)) return -1
    return if (start.row == end.row && start.column == end.column) 0
    else if (abs(start.row - end.row) == abs(start.column - end.column)) 1
    else 2
}

/**
 * Сложная
 *
 * Вернуть список из клеток, по которым шахматный слон может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов слона см. предыдущую задачу.
 *
 * Если клетка end недостижима для слона, вернуть пустой список.
 *
 * Если клетка достижима:
 * - список всегда включает в себя клетку start
 * - клетка end включается, если она не совпадает со start.
 * - между ними должны находиться промежуточные клетки, по порядку от start до end.
 *
 * Примеры: bishopTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          bishopTrajectory(Square(3, 1), Square(3, 7)) = listOf(Square(3, 1), Square(6, 4), Square(3, 7))
 *          bishopTrajectory(Square(1, 3), Square(6, 8)) = listOf(Square(1, 3), Square(6, 8))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun bishopTrajectory(start: Square, end: Square): List<Square> {
    val ans = mutableListOf<Square>()
    val x1 = start.column
    val x2 = end.column
    val y1 = start.row
    val y2 = end.row

    if ((x1 % 2 == y1 % 2) != (x2 % 2 == y2 % 2)) return ans

    if (x1 == x2 && y1 == y2) ans.add(start)
    else if (abs(x1 - x2) == abs(y1 - y2)) {
        ans.add(start)
        ans.add(end)
    } else {
        ans.add(start)
        val dx = x1 - x2
        val dy = y1 - y2
        val square: Square
        val t1 = abs((dx - dy) / 2)
        if ((x1 + t1 < 8) && (y1 - t1 > 0) && (abs(dx + t1) == abs(dy - t1))) ans.add(Square(x1 + t1, y1 - t1))
        else if ((x1 - t1 > 0) && (y1 + t1 < 8) && (abs(dx - t1) == abs(dy + t1))) ans.add(Square(x1 - t1, y1 + t1))
        if (ans.size == 1) {
            val t2 = abs((dx + dy) / 2)
            if ((x1 + t2 < 8) && (y1 + t2 < 8) && (abs(dx + t2) == abs(dy + t2))) ans.add(Square(x1 + t2, y1 + t2))
            else ans.add(Square(x1 - t2, y1 - t2))
        }
        ans.add(end)
    }
    return ans
}

/**
 * Средняя
 *
 * Определить число ходов, за которое шахматный король пройдёт из клетки start в клетку end.
 * Шахматный король одним ходом может переместиться из клетки, в которой стоит,
 * на любую соседнюю по вертикали, горизонтали или диагонали.
 * Ниже точками выделены возможные ходы короля, а крестиками -- невозможные:
 *
 * xxxxx
 * x...x
 * x.K.x
 * x...x
 * xxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: kingMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Король может последовательно пройти через клетки (4, 2) и (5, 2) к клетке (6, 3).
 */

fun chooseNxtKngTurn(x1: Int, x2: Int, y1: Int, y2: Int): Pair<Int, Int> =
    when {
        abs(x2 - x1) > abs(y2 - y1) -> Pair(x1 + abs(x2 - x1) / (x2 - x1), y1)
        abs(x2 - x1) < abs(y2 - y1) -> Pair(x1, y1 + abs(y2 - y1) / (y2 - y1))
        else -> Pair(x1 + abs(x2 - x1) / (x2 - x1), y1 + abs(y2 - y1) / (y2 - y1))
    }

fun kingMoveNumber(start: Square, end: Square): Int {
    checkSquare(start)
    checkSquare(end)

    var turns = 0
    var x1 = start.column
    var y1 = start.row
    val x2 = end.column
    val y2 = end.row

    if (x1 == x2 && y1 == y2) return turns

    while (abs(x2 - x1) > 1 || abs(y2 - y1) > 1) {
        val newXY = chooseNxtKngTurn(x1, x2, y1, y2)
        x1 = newXY.first
        y1 = newXY.second
        turns++
    }
    turns++

    return turns
}

/**
 * Сложная
 *
 * Вернуть список из клеток, по которым шахматный король может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов короля см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: kingTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможны другие варианты)
 *          kingTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(4, 2), Square(5, 2), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          kingTrajectory(Square(3, 5), Square(6, 2)) = listOf(Square(3, 5), Square(4, 4), Square(5, 3), Square(6, 2))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun kingTrajectory(start: Square, end: Square): List<Square> {
    val ans = mutableListOf<Square>()
    ans.add(start)
    var x1 = start.column
    var y1 = start.row
    val x2 = end.column
    val y2 = end.row

    if (x1 == x2 && y1 == y2) return ans

    while (abs(x2 - x1) > 1 || abs(y2 - y1) > 1) {
        val newXY = chooseNxtKngTurn(x1, x2, y1, y2)
        x1 = newXY.first
        y1 = newXY.second
        ans.add(Square(x1, y1))
    }
    ans.add(end)
    return ans
}

/**
 * Сложная
 *
 * Определить число ходов, за которое шахматный конь пройдёт из клетки start в клетку end.
 * Шахматный конь одним ходом вначале передвигается ровно на 2 клетки по горизонтали или вертикали,
 * а затем ещё на 1 клетку под прямым углом, образуя букву "Г".
 * Ниже точками выделены возможные ходы коня, а крестиками -- невозможные:
 *
 * .xxx.xxx
 * xxKxxxxx
 * .xxx.xxx
 * x.x.xxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: knightMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Конь может последовательно пройти через клетки (5, 2) и (4, 4) к клетке (6, 3).
 */
fun connectVertcies (chessField: Graph) {
    for (i in 0..7) {
        for (j in 1..8) {
            chessField.addVertex("${('a' + i)}$j")
        }
    }
    for (i in 0..7) {
        for (j in 1..8) {
            if (i + 1 < 7) {
                if (j + 2 < 8) chessField.connect("${'a' + i}$j", "${'a' + i + 1}${j + 2}")
                if (j - 2 > 0) chessField.connect("${'a' + i}$j", "${'a' + i + 1}${j - 2}")
            }
            if (i + 2 < 7) {
                if (j + 1 < 8) chessField.connect("${'a' + i}$j", "${'a' + i + 2}${j + 1}")
                if (j - 1 > 0) chessField.connect("${'a' + i}$j", "${'a' + i + 2}${j - 1}")
            }
            if (i - 1 > 0) {
                if (j + 2 < 8) chessField.connect("${'a' + i}$j", "${'a' + i - 1}${j + 2}")
                if (j - 2 > 0) chessField.connect("${'a' + i}$j", "${'a' + i - 1}${j - 2}")
            }
            if (i - 2 > 0) {
                if (j + 1 < 8) chessField.connect("${'a' + i}$j", "${'a' + i - 2}${j + 1}")
                if (j - 1 > 0) chessField.connect("${'a' + i}$j", "${'a' + i - 2}${j - 1}")
            }
        }
    }
}
fun knightMoveNumber(start: Square, end: Square): Int {
    val chessField = Graph()
    connectVertcies(chessField)
    return chessField.bfs(start.notation(), end.notation())
}

/**
 * Очень сложная
 *
 * Вернуть список из клеток, по которым шахматный конь может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов коня см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры:
 *
 * knightTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 * здесь возможны другие варианты)
 * knightTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(5, 2), Square(4, 4), Square(6, 3))
 * (здесь возможен единственный вариант)
 * knightTrajectory(Square(3, 5), Square(5, 6)) = listOf(Square(3, 5), Square(5, 6))
 * (здесь опять возможны другие варианты)
 * knightTrajectory(Square(7, 7), Square(8, 8)) =
 *     listOf(Square(7, 7), Square(5, 8), Square(4, 6), Square(6, 7), Square(8, 8))
 *
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun knightTrajectory(start: Square, end: Square): List<Square> {
    val chessField = Graph()
    connectVertcies(chessField)
    return chessField.bfsWay(start.notation(), end.notation())
}
