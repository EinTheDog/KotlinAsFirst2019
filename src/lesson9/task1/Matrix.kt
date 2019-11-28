@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson9.task1

import java.lang.IllegalArgumentException

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: Cell, value: E)

    fun find(value: E): Cell?

    fun change(cell1: Cell, cell2: Cell)

    fun findNeighbours(cell: Cell): Set<Cell>
}

/**
 * Простая
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> {
    require(width > 0 && height > 0)
    return MatrixImpl<E>(height, width, e)
}

/**
 * Средняя сложность
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(override val height: Int, override val width: Int, e: E) : Matrix<E> {
    private val map = mutableMapOf<Cell, E>()

    init {
        for (i in 0 until height) {
            for (j in 0 until width) {
                map[Cell(i, j)] = e
            }
        }
    }


    override fun get(row: Int, column: Int): E = map[Cell(row, column)] ?: throw IllegalStateException()
    override fun get(cell: Cell): E = map[cell] ?: throw IllegalStateException()

    override fun set(row: Int, column: Int, value: E) {
        map[Cell(row, column)] = value
    }

    override fun set(cell: Cell, value: E) {
        map[cell] = value
    }

    override fun equals(other: Any?): Boolean {
        var ans = other is MatrixImpl<*> &&
                height == other.height &&
                width == other.width
        if (other is MatrixImpl<*> && ans) {
            outer@ for (i in 0 until height) {
                for (j in 0 until width) {
                    ans = this[i, j] == other[i, j]
                    if (!ans) break@outer
                }
            }
        }
        return ans
    }

    override fun hashCode(): Int {
        var result = height
        result = 31 * result + width
        result = 31 * result + map.hashCode()
        return result
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("[")
        for (row in 0 until height) {
            sb.append("[")
            sb.append(this[row, 0])
            for (column in 1 until width) {
                sb.append(", ")
                sb.append(this[row, column])
            }
            sb.append("]")
        }
        sb.append("]")
        return sb.toString()
    }

    //Мои функции
    override fun find(value: E): Cell? {
        for (i in 0 until height) {
            for (j in 0 until width) {
                if (this[i, j]!! == value) return Cell(i, j)
            }
        }
        return null
    }

    override fun change(cell1: Cell, cell2: Cell) {
        val t = this[cell1]
        this[cell1] = this[cell2]
        this[cell2] = t
    }

    override fun findNeighbours(cell: Cell): Set<Cell> {
        val i = cell.row
        val j = cell.column
        val set = mutableSetOf<Cell>()
        if (i - 1 >= 0) set.add(Cell(i - 1, j))
        if (i + 1 < height) set.add(Cell(i + 1, j))
        if (j - 1 >= 0) set.add(Cell(i, j - 1))
        if (j + 1 < width) set.add(Cell(i, j + 1))
        return set
    }
}



