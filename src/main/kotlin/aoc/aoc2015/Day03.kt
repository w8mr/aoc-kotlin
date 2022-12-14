package aoc.aoc2015

import aoc.*
import kotlin.reflect.KFunction1

fun main() {
    val up = Literal("^").to(Coord::up)
    val down = Literal("v").to(Coord::down)
    val right = Literal(">").to(Coord::right)
    val left = Literal("<").to(Coord::left)
    val instructions = zeroOrMore(OneOf(up, down, right, left))

    fun vistedHouses(i: List<KFunction1<Coord, Coord>>) =
        i.scan(Coord()) { acc, instr -> instr(acc) }.toSet()

    fun countHouses(i: List<KFunction1<Coord, Coord>>) =
        vistedHouses(i).size

    fun part1(input: String): Int {
        return countHouses(instructions.parse(input))
    }

    fun part2(input: String): Int {
        return instructions.parse(input).zipWithNext().unzip().toList()
            .map(::vistedHouses).toSet().reduce {acc, e -> acc.union(e)}.size
    }

    check(part1(">\n") == 2)
    check(part1("^>v<\n") == 4)
    check(part1("^v^v^v^v^v\n") == 2)

    val input = readFile(2015, 3).readText()
    check(part1(input) == 2592)
    println(part1(input))

    check(part2("^v\n") == 3)
    check(part2("^>v<\n") == 3)
    check(part2("^v^v^v^v^v\n") == 11)

    check(part2(input) == 2360)
    println(part2(input))

}
