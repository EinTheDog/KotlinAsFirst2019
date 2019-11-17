package lesson8.task2

import java.util.*

class Graph {
    private data class Vertex(val name: String) {
        val neighbors = mutableSetOf<Vertex>()
    }

    private val vertices = mutableMapOf<String, Vertex>()

    private operator fun get(name: String) = vertices[name] ?: throw IllegalArgumentException()

    fun addVertex(name: String) {
        vertices[name] = Vertex(name)
    }

    private fun connect(first: Vertex, second: Vertex) {
        first.neighbors.add(second)
        second.neighbors.add(first)
    }

    fun connect(first: String, second: String) = connect(this[first], this[second])

    fun neighbors(name: String) = vertices[name]?.neighbors?.map { it.name } ?: listOf()

    fun bfs(start: String, finish: String) = bfs(this[start], this[finish])

    private fun bfs(start: Vertex, finish: Vertex): Int {
        val queue = ArrayDeque<Vertex>()
        queue.add(start)
        val visited = mutableMapOf(start to 0)
        while (queue.isNotEmpty()) {
            val next = queue.poll()
            val distance = visited[next]!!
            if (next == finish) return distance
            for (neighbor in next.neighbors) {
                if (neighbor in visited) continue
                visited.put(neighbor, distance + 1)
                queue.add(neighbor)
            }
        }
        return -1
    }

    fun bfsWay(start: String, finish: String): List<Square> {
        val vertexList = bfsWay(this[start], this[finish])
        val squareList = mutableListOf<Square>()
        for (vertex in vertexList) {
            squareList.add(square(vertex.name))
        }
        return squareList
    }

    private fun bfsWay(start: Vertex, finish: Vertex): List<Vertex> {
        val queue = ArrayDeque<Vertex>()
        queue.add(start)
        val visited = mutableMapOf(start to listOf(start))
        while (queue.isNotEmpty()) {
            val next = queue.poll()
            val distance = visited[next]!!
            if (next == finish) return distance
            for (neighbor in next.neighbors) {
                if (neighbor in visited) continue
                visited.put(neighbor, visited[next]!! + neighbor)
                queue.add(neighbor)
            }
        }
        return listOf()
    }

    /*private var knightTurns = listOf<Vertex>()
    private fun dfsWay(start: Vertex, finish: Vertex, visited: List<Vertex>, min: Int?): Int? {
        if (min != null && visited.size > min) return null
        if (start == finish) {
            knightTurns = visited
            return 0
        }

        var localMin: Int? = null
        for (neighbor in start.neighbors) {
            val wayFromNeigbor = if (neighbor !in visited) dfsWay(neighbor, finish, visited + neighbor, min)
            else null
            if (wayFromNeigbor != null && (localMin == null || wayFromNeigbor < localMin)) localMin = wayFromNeigbor
        }
        return localMin
    }

    fun dfsWay(start: String, finish: String): List<Square> {
        dfsWay(this[start], this[finish], listOf(this[start]), null)
        val ans = mutableListOf<Square>()
        for ((name) in knightTurns) {
            ans.add(square(name))
        }
        return ans
    }

    fun dfs(start: String, finish: String): Int = dfs(this[start], this[finish], setOf()) ?: -1

    private fun dfs(start: Vertex, finish: Vertex, visited: Set<Vertex>): Int? =
        if (start == finish) 0
        else {
            val min = start.neighbors
                .filter { it !in visited }
                .mapNotNull { dfs(it, finish, visited + start) }
                .min()
            if (min == null) null else min + 1
        }*/



}