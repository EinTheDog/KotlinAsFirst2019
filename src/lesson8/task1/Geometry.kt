@file:Suppress("UNUSED_PARAMETER")

package lesson8.task1

import lesson1.task1.sqr
import kotlin.math.*

/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {
    /**
     * Пример
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = sqrt(sqr(x - other.x) + sqr(y - other.y))
}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
@Suppress("MemberVisibilityCanBePrivate")
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point) : this(linkedSetOf(a, b, c))

    /**
     * Пример: полупериметр
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь
     */
    fun area(): Double {
        val p = halfPerimeter()
        return sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {
    /**
     * Простая
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    fun distance(other: Circle): Double {
        var d = sqrt(sqr(this.center.x - other.center.x) + sqr(this.center.y - other.center.y))
        d -= this.radius + other.radius
        return if (d > 0) d else 0.0
    }

    /**
     * Тривиальная
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point): Boolean {
        val a = sqrt(sqr(this.center.x - p.x) + sqr(this.center.y - p.y))
        return (sqrt(sqr(this.center.x - p.x) + sqr(this.center.y - p.y)) <= this.radius + 1e-6)
    }
}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point) {
    override fun equals(other: Any?) =
        other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
        begin.hashCode() + end.hashCode()

    fun length(): Double = sqrt(sqr(this.begin.x - this.end.x) + sqr(this.begin.y - this.end.y))
}

/**
 * Средняя
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */
fun diameter(vararg points: Point): Segment {
    require(points.size >= 2)
    var longestSeg = Segment(Point(0.0, 0.0), Point(0.0, 0.0))
    var d: Double
    for (i in points.indices) {
        for (j in i + 1 until points.size) {
            val x1 = points[i].x
            val x2 = points[j].x
            val y1 = points[i].y
            val y2 = points[j].y
            d = sqrt(sqr(x1 - x2) + sqr(y1 - y2))
            if (d > longestSeg.length()) {
                longestSeg = Segment(points[i], points[j])
            }
        }
    }
    return longestSeg
}

/**
 * Простая
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle {
    val r = diameter.length() / 2
    val x = (diameter.begin.x + diameter.end.x) / 2
    val y = (diameter.begin.y + diameter.end.y) / 2
    return (Circle(Point(x, y), r))
}

/**
 * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно).
 */
class Line private constructor(val b: Double, val angle: Double) {
    init {
        require(angle >= 0 && angle < PI) { "Incorrect line angle: $angle" }
    }

    constructor(point: Point, angle: Double) : this(point.y * cos(angle) - point.x * sin(angle), angle)

    /**
     * Средняя
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */
    fun crossPoint(other: Line): Point {
        val b1 = this.b / cos(this.angle)
        val b2 = other.b / cos(other.angle)
        val x = (b2 - b1) / (tan(this.angle) - tan(other.angle))
        val y = if (this.angle < PI / 2 + 1e-5 && this.angle > PI / 2 - 1e-5) x * tan(other.angle) + b2
        else x * tan(this.angle) + b1
        return Point(x, y)
    }

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }

    override fun toString() = "Line(${cos(angle)} * y = ${sin(angle)} * x + $b)"
}

/**
 * Средняя
 *
 * Построить прямую по отрезку
 */
fun lineBySegment(s: Segment): Line {
    var angle = atan((s.end.y - s.begin.y) / (s.end.x - s.begin.x))
    if (angle < 0) angle += PI
    if (angle >= PI) angle -= PI
    return (Line(s.begin, angle))
}

/**
 * Средняя
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point): Line {
    var angle = atan((a.y - b.y) / (a.x - b.x))
    if (angle < 0) angle += PI
    if (angle >= PI) angle -= PI
    val b = abs(a.y * cos(angle) - a.x * sin(angle))
    return (Line(a, angle))
}

/**
 * Сложная
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line {
    var angle = atan((a.y - b.y) / (a.x - b.x))
    if (angle < 0) angle += PI
    if (angle + PI / 2 > PI) angle -= PI / 2 else angle += PI / 2
    if (angle >= PI) angle -= PI
    val c = Point((a.x + b.x) / 2, (a.y + b.y) / 2)
    return (Line(c, angle))
}

/**
 * Средняя
 *
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> {
    require(circles.size >= 2)
    var minD = circles[0].distance(circles[1])
    var circlePair = Pair(circles[0], circles[1])
    for (i in 0 until circles.size - 1) {
        for (j in i + 1 until circles.size) {
            if (circles[i].distance(circles[j]) < minD) {
                minD = circles[i].distance(circles[j])
                circlePair = Pair(circles[i], circles[j])
            }
        }
    }
    return circlePair
}

/**
 * Сложная
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle {
    val o = bisectorByPoints(a, b).crossPoint(bisectorByPoints(b, c))
    val r = Segment(o, a).length()
    return (Circle(o, r))
}

/**
 * Очень сложная
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 */
fun minContainingCircle(vararg points: Point): Circle {
    val myPoints = points.toMutableSet()
    val usingPoints = mutableListOf<Point>()
    var ans = Circle(points[0], 0.0)
    myPoints.remove(points[0])
    usingPoints.add(points[0])

    fun findTheFarthest(pointSet: Set<Point>, center: Point): Point {
        var maxDistance = 0.0
        var farPoint = points[1]
        for (point in pointSet) {
            val d = Segment(point, center).length()
            if (d > maxDistance) {
                maxDistance = d
                farPoint = point
            }
        }
        return farPoint
    }

    fun addPointTo2(farPoint: Point) {
        val point2: Point
        val point3: Point
        if (Segment(farPoint, usingPoints[0]).length() > Segment(farPoint, usingPoints[1]).length()) {
            point2 = usingPoints[0]
            point3 = usingPoints[1]
        } else {
            point2 = usingPoints[1]
            point3 = usingPoints[0]
        }
        val potentialCircle = circleByDiameter(Segment(farPoint, point2))
        if (potentialCircle.contains(point3)) {
            ans = potentialCircle
            usingPoints.remove(point3)
        } else {
            ans = circleByThreePoints(point2, farPoint, point3)
        }
        usingPoints.add(farPoint)
    }

    while (myPoints.size > 0) {
        val farPoint = findTheFarthest(myPoints, ans.center)
        if (ans.contains(farPoint)) break
        else {
            when (usingPoints.size) {
                1 -> {
                    ans = circleByDiameter(Segment(usingPoints[0], farPoint))
                    usingPoints.add(farPoint)
                }
                2 -> {
                    addPointTo2(farPoint)
                }
                3 -> {
                    val d1 = Segment(farPoint, usingPoints[0]).length()
                    val d2 = Segment(farPoint, usingPoints[1]).length()
                    val d3 = Segment(farPoint, usingPoints[2]).length()
                    when {
                        d1 < d2 && d1 < d3 -> usingPoints.removeAt(0)
                        d2 < d1 && d2 < d3 -> usingPoints.removeAt(1)
                        d3 < d2 && d3 < d1 -> usingPoints.removeAt(2)
                    }
                    addPointTo2(farPoint)
                }
            }
            myPoints.remove(farPoint)
        }
    }

    return ans
}

