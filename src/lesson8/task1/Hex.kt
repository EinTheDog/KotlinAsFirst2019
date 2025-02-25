@file:Suppress("UNUSED_PARAMETER")

package lesson8.task1

import kotlin.math.abs

/**
 * Точка (гекс) на шестиугольной сетке.
 * Координаты заданы как в примере (первая цифра - y, вторая цифра - x)
 *
 *       60  61  62  63  64  65
 *     50  51  52  53  54  55  56
 *   40  41  42  43  44  45  46  47
 * 30  31  32  33  34  35  36  37  38
 *   21  22  23  24  25  26  27  28
 *     12  13  14  15  16  17  18
 *       03  04  05  06  07  08
 *
 * В примерах к задачам используются те же обозначения точек,
 * к примеру, 16 соответствует HexPoint(x = 6, y = 1), а 41 -- HexPoint(x = 1, y = 4).
 *
 * В задачах, работающих с шестиугольниками на сетке, считать, что они имеют
 * _плоскую_ ориентацию:
 *  __
 * /  \
 * \__/
 *
 * со сторонами, параллельными координатным осям сетки.
 *
 * Более подробно про шестиугольные системы координат можно почитать по следующей ссылке:
 *   https://www.redblobgames.com/grids/hexagons/
 */
data class HexPoint(val x: Int, val y: Int) {
    /**
     * Средняя
     *
     * Найти целочисленное расстояние между двумя гексами сетки.
     * Расстояние вычисляется как число единичных отрезков в пути между двумя гексами.
     * Например, путь межу гексами 16 и 41 (см. выше) может проходить через 25, 34, 43 и 42 и имеет длину 5.
     */
    fun distance(other: HexPoint) =
        (abs(other.x - this.x) + abs(other.y - this.y) + abs(this.x + this.y - other.x - other.y)) / 2

    override fun toString(): String = "$y.$x"

}

/**
 * Правильный шестиугольник на гексагональной сетке.
 * Как окружность на плоскости, задаётся центральным гексом и радиусом.
 * Например, шестиугольник с центром в 33 и радиусом 1 состоит из гексов 42, 43, 34, 24, 23, 32.
 */
class Cube(var x: Int, var y: Int, var z: Int)

fun axialToCube(hex: HexPoint) = Cube(hex.x, -hex.x - hex.y, hex.y)

data class Hexagon(val center: HexPoint, val radius: Int) {

    /**
     * Средняя
     *
     * Рассчитать расстояние между двумя шестиугольниками.
     * Оно равно расстоянию между ближайшими точками этих шестиугольников,
     * или 0, если шестиугольники имеют общую точку.
     *
     * Например, расстояние между шестиугольником A с центром в 31 и радиусом 1
     * и другим шестиугольником B с центром в 26 и радиуоом 2 равно 2
     * (расстояние между точками 32 и 24)
     */
    fun distance(other: Hexagon): Int {
        val d = this.center.distance((other.center))
        return if (d - this.radius - other.radius > 0) d - this.radius - other.radius else 0
    }

    /**
     * Тривиальная
     *
     * Вернуть true, если заданная точка находится внутри или на границе шестиугольника
     */
    fun contains(point: HexPoint): Boolean {
        val x = point.x - this.center.x
        val y = point.y - this.center.y
        val r = this.radius
        return (x in -r..r) && (y in -r..r) && (-x - y in -r..r)
    }
}

/**
 * Прямолинейный отрезок между двумя гексами
 */
class HexSegment(val begin: HexPoint, val end: HexPoint) {
    /**
     * Простая
     *
     * Определить "правильность" отрезка.
     * "Правильным" считается только отрезок, проходящий параллельно одной из трёх осей шестиугольника.
     * Такими являются, например, отрезок 30-34 (горизонталь), 13-63 (прямая диагональ) или 51-24 (косая диагональ).
     * А, например, 13-26 не является "правильным" отрезком.
     */
    fun isValid(): Boolean {
        if (begin == end) return false
        val a = axialToCube(begin)
        val b = axialToCube(end)
        return (a.x == b.x || a.y == b.y || a.z == b.z)
    }

    /**
     * Средняя
     *
     * Вернуть направление отрезка (см. описание класса Direction ниже).
     * Для "правильного" отрезка выбирается одно из первых шести направлений,
     * для "неправильного" -- INCORRECT.
     */
    fun direction(): Direction {
        val a = axialToCube(begin)
        val b = axialToCube(end)
        return when {
            (a.z == b.z) && (a.x < b.x) -> Direction.RIGHT
            (a.z == b.z) && (a.x > b.x) -> Direction.LEFT

            (a.x == b.x) && (a.z < b.z) -> Direction.UP_RIGHT
            (a.x == b.x) && (a.z > b.z) -> Direction.DOWN_LEFT

            (a.y == b.y) && (a.z < b.z) -> Direction.UP_LEFT
            (a.y == b.y) && (a.z > b.z) -> Direction.DOWN_RIGHT
            else -> Direction.INCORRECT
        }

    }

    override fun equals(other: Any?) =
        other is HexSegment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
        begin.hashCode() + end.hashCode()
}

/**
 * Направление отрезка на гексагональной сетке.
 * Если отрезок "правильный", то он проходит вдоль одной из трёх осей шестугольника.
 * Если нет, его направление считается INCORRECT
 */
enum class Direction {
    RIGHT,      // слева направо, например 30 -> 34
    UP_RIGHT,   // вверх-вправо, например 32 -> 62
    UP_LEFT,    // вверх-влево, например 25 -> 61
    LEFT,       // справа налево, например 34 -> 30
    DOWN_LEFT,  // вниз-влево, например 62 -> 32
    DOWN_RIGHT, // вниз-вправо, например 61 -> 25
    INCORRECT;  // отрезок имеет изгиб, например 30 -> 55 (изгиб в точке 35)

    /**
     * Простая
     *
     * Вернуть направление, противоположное данному.
     * Для INCORRECT вернуть INCORRECT
     */
    fun opposite(): Direction = when (this) {
        RIGHT -> LEFT
        UP_RIGHT -> DOWN_LEFT
        UP_LEFT -> DOWN_RIGHT
        LEFT -> RIGHT
        DOWN_LEFT -> UP_RIGHT
        DOWN_RIGHT -> UP_LEFT
        else -> INCORRECT
    }

    /**
     * Средняя
     *
     * Вернуть направление, повёрнутое относительно
     * заданного на 60 градусов против часовой стрелки.
     *
     * Например, для RIGHT это UP_RIGHT, для UP_LEFT это LEFT, для LEFT это DOWN_LEFT.
     * Для направления INCORRECT бросить исключение IllegalArgumentException.
     * При решении этой задачи попробуйте обойтись без перечисления всех семи вариантов.
     */
    fun next(): Direction = when (this) {
        RIGHT -> UP_RIGHT
        UP_RIGHT -> UP_LEFT
        UP_LEFT -> LEFT
        LEFT -> DOWN_LEFT
        DOWN_LEFT -> DOWN_RIGHT
        DOWN_RIGHT -> RIGHT
        else -> throw  IllegalArgumentException()
    }

    /**
     * Простая
     *
     * Вернуть true, если данное направление совпадает с other или противоположно ему.
     * INCORRECT не параллельно никакому направлению, в том числе другому INCORRECT.
     */
    fun isParallel(other: Direction): Boolean = (this == other || this == other.opposite()) && this != INCORRECT
}

/**
 * Средняя
 *
 * Сдвинуть точку в направлении direction на расстояние distance.
 * Бросить IllegalArgumentException(), если задано направление INCORRECT.
 * Для расстояния 0 и направления не INCORRECT вернуть ту же точку.
 * Для отрицательного расстояния сдвинуть точку в противоположном направлении на -distance.
 *
 * Примеры:
 * 30, direction = RIGHT, distance = 3 --> 33
 * 35, direction = UP_LEFT, distance = 2 --> 53
 * 45, direction = DOWN_LEFT, distance = 4 --> 05
 */
fun HexPoint.move(direction: Direction, distance: Int): HexPoint {
    var dx = 0
    var dy = 0
    when (direction) {
        Direction.RIGHT -> {
            dx = distance
        }
        Direction.UP_RIGHT -> {
            dy = distance
        }
        Direction.UP_LEFT -> {
            dx = -distance
            dy = distance
        }
        Direction.LEFT -> {
            dx = -distance
        }
        Direction.DOWN_LEFT -> {
            dy = -distance
        }
        Direction.DOWN_RIGHT -> {
            dx = distance
            dy = -distance
        }
        else -> throw  IllegalArgumentException()
    }
    return HexPoint(x + dx, y + dy)
}

/**
 * Сложная
 *
 * Найти кратчайший путь между двумя заданными гексами, представленный в виде списка всех гексов,
 * которые входят в этот путь.
 * Начальный и конечный гекс также входят в данный список.
 * Если кратчайших путей существует несколько, вернуть любой из них.
 *
 * Пример (для координатной сетки из примера в начале файла):
 *   pathBetweenHexes(HexPoint(y = 2, x = 2), HexPoint(y = 5, x = 3)) ->
 *     listOf(
 *       HexPoint(y = 2, x = 2),
 *       HexPoint(y = 2, x = 3),
 *       HexPoint(y = 3, x = 3),
 *       HexPoint(y = 4, x = 3),
 *       HexPoint(y = 5, x = 3)
 *     )
 */
fun chooseDir(x1: Int, y1: Int, x2: Int, y2: Int): Pair<Int, Int> {
    var dirX = 0
    var dirY = 0
    when {
        x2 > x1 && y2 < y1 -> {
            dirX = 1
            dirY = -1
        }
        x2 < x1 && y2 > y1 -> {
            dirX = -1
            dirY = 1
        }
        else -> {
            when {
                y2 < y1 -> {
                    dirY = -1
                }
                y2 > y1 -> {
                    dirY = 1
                }
                else -> {
                    if (x2 < x1) {
                        dirX = -1
                    } else {
                        dirX = 1
                    }
                }
            }
        }
    }
    return (Pair(dirX, dirY))
}

fun pathBetweenHexes(from: HexPoint, to: HexPoint): List<HexPoint> {
    var x1 = from.x
    var y1 = from.y
    val x2 = to.x
    val y2 = to.y
    val ans = mutableListOf<HexPoint>(HexPoint(x1, y1))
    while (x1 != x2 || y1 != y2) {
        val dir = chooseDir(x1, y1, x2, y2)
        x1 += dir.first
        y1 += dir.second
        ans.add(HexPoint(x1, y1))
    }
    return ans
}

/**
 * Очень сложная
 *
 * Дано три точки (гекса). Построить правильный шестиугольник, проходящий через них
 * (все три точки должны лежать НА ГРАНИЦЕ, а не ВНУТРИ, шестиугольника).
 * Все стороны шестиугольника должны являться "правильными" отрезками.
 * Вернуть null, если такой шестиугольник построить невозможно.
 * Если шестиугольников существует более одного, выбрать имеющий минимальный радиус.
 *
 * Пример: через точки 13, 32 и 44 проходит правильный шестиугольник с центром в 24 и радиусом 2.
 * Для точек 13, 32 и 45 такого шестиугольника не существует.
 * Для точек 32, 33 и 35 следует вернуть шестиугольник радиусом 3 (с центром в 62 или 05).
 *
 * Если все три точки совпадают, вернуть шестиугольник нулевого радиуса с центром в данной точке.
 */
fun hexagonByThreePoints(a: HexPoint, b: HexPoint, c: HexPoint): Hexagon? {
    val dirs = listOf(
        Pair(0, -1),
        Pair(-1, 0),
        Pair(-1, 1),
        Pair(0, 1),
        Pair(1, 0),
        Pair(1, -1)
    )
    val d1 = a.distance(b)
    val d2 = b.distance(c)
    val d3 = c.distance(a)
    val maxD = if (d1 > d2) {
        if (d1 > d3) d1 else d3
    } else {
        if (d2 > d3) d2 else d3
    }
    val map = mutableMapOf<HexPoint, Set<Char>>()
    var center: HexPoint? = null
    val potentialCenters = mutableSetOf<HexPoint>()
    var r = maxD / 2 - 1

    fun pointsOnRadius(hex: HexPoint): Set<HexPoint> {
        val set = mutableSetOf<HexPoint>()
        val start = HexPoint(hex.x + r, hex.y)
        var h = start
        set.add(h)
        var j = 0
        do {
            for (i in 1..r) {
                h = HexPoint(h.x + dirs[j].first, h.y + dirs[j].second)
                set.add(h)
            }
            j++
        } while (h != start)
        return set
    }

    fun addHex(hex: HexPoint, from: Set<Char>) {
        map[hex] = (map[hex] ?: emptySet<Char>()) + from
        if ((map[hex] ?: emptySet()).size == 2) potentialCenters.add(hex)
    }

    addHex(a, setOf('a'))
    addHex(b, setOf('b'))
    addHex(c, setOf('c'))

    while (center == null && r <= maxD + 1) {
        map.clear()
        potentialCenters.clear()
        r++
        for (hex in pointsOnRadius(a)) {
            addHex(hex, setOf('a'))
        }
        for (hex in pointsOnRadius(b)) {
            addHex(hex, setOf('b'))
            if (potentialCenters.size == 2) break
        }
        for (hex in potentialCenters) {
            val a = (Hexagon(hex, r).contains(c) && (c.x == hex.x - r || c.x == hex.x + r
                    || c.y == hex.y - r || c.y == hex.y + r
                    || c.x + c.y == hex.x + hex.y + r || c.x + c.y == hex.x + hex.y - r)
                    )
            if (Hexagon(hex, r).contains(c) && (c.x == hex.x - r || c.x == hex.x + r
                || c.y == hex.y - r || c.y == hex.y + r
                || c.x + c.y == hex.x + hex.y + r || c.x + c.y == hex.x + hex.y - r)
            ) center = hex
        }
    }


    return if (center == null) null else Hexagon(center!!, r)
}

/**
 * Очень сложная
 *
 * Дано множество точек (гексов). Найти правильный шестиугольник минимального радиуса,
 * содержащий все эти точки (безразлично, внутри или на границе).
 * Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит один гекс, вернуть шестиугольник нулевого радиуса с центром в данной точке.
 *
 * Пример: 13, 32, 45, 18 -- шестиугольник радиусом 3 (с центром, например, в 15)
 */
fun minContainingHexagon(vararg points: HexPoint): Hexagon = TODO()

