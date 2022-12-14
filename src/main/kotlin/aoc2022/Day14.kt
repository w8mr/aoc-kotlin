package aoc2022

import aoc.*
import java.util.*

class Day14() {
    data class Line(val start: Coord, val end: Coord)

    class State(val ls: List<Line>) {
        fun build(): MutableMap<Int, TreeSet<Int>> {
            val map = mutableMapOf<Int, TreeSet<Int>>()
            ls.forEach { (start, end) ->
                (start.x.coerceAtMost(end.x)..start.x.coerceAtLeast(end.x)).forEach { x ->
                    (start.y.coerceAtMost(end.y)..start.y.coerceAtLeast(end.y)).forEach { y ->
                        insert(map, x, y)
                    }
                }
            }
            return map
        }
        val blocked: MutableMap<Int, TreeSet<Int>> = build()

        private fun insert(map: MutableMap<Int, TreeSet<Int>>, x: Int, y: Int) {
            map.compute(x) {
                _, set -> when (set) {
                    null -> {
                        val s = TreeSet<Int>()
                        s.add(y)
                        s
                    }

                    else -> {
                        set.add(y)
                        set
                    }
                }
            }
        }

        fun insert(x: Int, y: Int) = insert(blocked, x,y )

        fun findIntersect(x: Int, y: Int): Int? {
            val set = blocked[x]
            return if (set == null) {
                null
            } else {
                set.asSequence().dropWhile { i -> i < y }.firstOrNull()
            }
        }

        fun simulateSandDrop(): Boolean {
            var x = 500
            var y: Int? = 0
            while (true) {
                y = findIntersect(x, y!!)
                if (y == null) {
                    return false
                } else {
                    if (y == findIntersect(x - 1, y)) {
                        if (y == findIntersect(x + 1, y)) {
                            insert(x, y - 1)
                     //       println("drop on $x,${y-1}")
                            return true
                        } else {
                            x++
                        }
                    } else {
                        x--
                    }
                }
            }
        }
    }

    fun createLine(coords: List<Coord>): List<Line> =
        coords.zipWithNext(::Line)

    val coord = seq(number() + ",", number(), ::Coord)
    val line = (coord sepBy " -> ") + "\n" map ::createLine
    val lines = zeroOrMore(line) map { it.flatten() }

    fun part1(input: String): Int {
        val lines = lines.parse(input)
        val state = State(lines)
        var count = -1
        do {
            count++
            val r = state.simulateSandDrop()
        } while (r)
        return count
    }

    fun part2(input: String): Int {
        return TODO()
    }
}
