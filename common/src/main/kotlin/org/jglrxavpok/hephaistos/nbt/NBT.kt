package org.jglrxavpok.hephaistos.nbt

import org.jetbrains.annotations.Contract
import org.jglrxavpok.hephaistos.collections.ImmutableByteArray
import org.jglrxavpok.hephaistos.collections.ImmutableIntArray
import org.jglrxavpok.hephaistos.collections.ImmutableLongArray
import java.io.DataOutputStream
import java.io.IOException

/**
 * Most basic representation of a NBTag
 */
sealed interface NBT {

    /**
     * ID of this tag type
     */
    val ID: Int

    /**
     * Writes the contents of the tag to the given destination. The tag ID is supposed to be already written
     * @throws IOException if an error occurred during writing
     */
    @Throws(IOException::class)
    fun writeContents(destination: DataOutputStream)

    /**
     * Produces the stringified version of this NBT (or SNBT version). Is empty for TAG_End
     */
    fun toSNBT(): String

    // TODO @Throws(NBTException::class)
    //fun parseSNBT(snbt: String)

    /**
     * Produces a human-readable version of this tag. Must be the same as `toSNBT()`, except for TAG_End which returns "<TAG_End>"
     */
    override fun toString(): String

    companion object {

        @JvmStatic
        @Contract(pure = true)
        fun Boolean(flag: Boolean): NBTByte = if (flag) NBTByte.ONE else NBTByte.ZERO

        @JvmStatic
        @Contract(pure = true)
        fun Byte(value: Byte) = NBTByte(value)

        @JvmStatic
        @Contract(pure = true)
        fun Byte(value: Int) = NBTByte(value.toByte())

        @JvmStatic
        @Contract(pure = true)
        fun ByteArray(vararg value: Byte) = NBTByteArray(ImmutableByteArray(*value))

        @JvmStatic
        @Contract(pure = true)
        fun ByteArray(vararg value: Int) = NBTByteArray(ImmutableByteArray(*value.map { it.toByte() }.toByteArray()))

        @JvmStatic
        @Contract(pure = true)
        fun ByteArray(array: ImmutableByteArray) = NBTByteArray(array)

        @JvmStatic
        @Contract(pure = true)
        fun Compound(lambda: CompoundBuilder) = NBTCompound(mutableMapOf<String, NBT>().also { lambda.run(it) })

        @Contract(pure = true)
        inline fun Kompound(crossinline lambda: CompoundMap.() -> Unit) = Compound { lambda(it) }

        @JvmStatic
        @Contract(pure = true)
        fun Double(value: Double) = NBTDouble(value)

        @JvmStatic
        @Contract(pure = true)
        fun Float(value: Float) = NBTFloat(value)

        @JvmStatic
        @Contract(pure = true)
        fun Short(value: Short) = NBTShort(value)

        @JvmStatic
        @Contract(pure = true)
        fun Short(value: Int) = NBTShort(value.toShort())

        @JvmStatic
        @Contract(pure = true)
        fun Int(value: Int) = NBTInt(value)

        @JvmStatic
        @Contract(pure = true)
        fun IntArray(vararg value: Int) = NBTIntArray(ImmutableIntArray(*value))

        @JvmStatic
        @Contract(pure = true)
        fun IntArray(array: ImmutableIntArray) = NBTIntArray(array)

        @JvmStatic
        @Contract(pure = true)
        fun <Tag : NBT> List(subtagType: Int, tags: List<Tag> = listOf()) = NBTList(subtagType, tags)

        @JvmStatic
        @Contract(pure = true)
        fun <Tag : NBT> List(subtagType: Int, vararg tags: Tag) = NBTList(subtagType, tags.toList())

        @JvmStatic
        @Contract(pure = true)
        fun <Tag : NBT> List(subtagType: Int, length: Int, generator: NBTListGenerator<Tag>) = NBTList(subtagType, List<Tag>(length) {
            generator.run(it)
        })

        @JvmStatic
        @Contract(pure = true)
        fun Long(value: Long) = NBTLong(value)

        @JvmStatic
        @Contract(pure = true)
        fun LongArray(vararg value: Long) = NBTLongArray(*value)

        @JvmStatic
        @Contract(pure = true)
        fun LongArray(array: ImmutableLongArray) = NBTLongArray(array)

        @JvmStatic
        @Contract(pure = true)
        fun String(value: String) = NBTString(value)
    }
}