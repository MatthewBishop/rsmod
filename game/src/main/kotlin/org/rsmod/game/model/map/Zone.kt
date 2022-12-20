package org.rsmod.game.model.map

@JvmInline
public value class Zone(public val packed: Int) {

    public val x: Int get() = packed and 0x7FFF

    public val y: Int get() = (packed shr 15) and 0x7FFF

    public val level: Int get() = (packed shr 30) and 0x3

    public constructor(x: Int, y: Int, level: Int = 0) : this(
        (x and 0x7FFF) or ((y and 0x7FFF) shl 15) or ((level and 0x3) shl 30)
    )

    public fun translate(xOffset: Int, yOffset: Int, levelOffset: Int = 0): Zone = Zone(
        x = x + xOffset,
        y = y + yOffset,
        level = level + levelOffset
    )

    public fun translateX(offset: Int): Zone = translate(offset, 0, 0)

    public fun translateY(offset: Int): Zone = translate(0, offset, 0)

    public fun translateLevel(offset: Int): Zone = translate(0, 0, offset)

    public fun toCoords(): Coordinates = Coordinates(
        x = x * SIZE,
        y = y * SIZE,
        level = level
    )

    public fun toMapSquare(): MapSquare = MapSquare(
        x = (x / (MapSquare.SIZE / SIZE)),
        y = (y / (MapSquare.SIZE / SIZE))
    )

    public operator fun component1(): Int = x

    public operator fun component2(): Int = y

    public operator fun component3(): Int = level

    public operator fun minus(other: Zone): Zone {
        return translate(-other.x, -other.y)
    }

    public operator fun plus(other: Zone): Zone {
        return translate(other.x, other.y)
    }

    public companion object {
        public const val SIZE: Int = 8
    }
}