package world.gregs.hestia.social.network

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import world.gregs.hestia.core.network.packets.Packet

internal class HuffmanTest {

    private var builder = Packet.Builder()
    private var message = ""

    @BeforeEach
    fun setup() {
        Huffman.init()
        builder = Packet.Builder()
        message = ""
    }

    @Test
    fun `A short string`() {
        //Given
        build("Message")
        //When
        compress()
        //Then
        assertUncompressedSize(7)
        assertCompressedSize(5)
        assertDecompressed()
    }

    @Test
    fun `A long string`() {
        //Given
        build("This is a string of substantial size, perhaps enough character overlap for some decent compression")
        //When
        compress()
        //Then
        assertUncompressedSize(98)
        assertCompressedSize(54)
        assertDecompressed()
    }

    @Test
    fun `Full Alphabet`() {
        //Given
        build("abcdefghijklmnopqrstuvwxyz")
        //When
        compress()
        //Then
        assertUncompressedSize(26)
        assertCompressedSize(20)
        assertDecompressed()
    }

    @Test
    fun `Middle of a packet decompress`() {
        //Given
        build("Message")
        builder.writeByte(0)
        compress()
        builder.writeByte(0)
        //Then
        assertUncompressedSize(7)
        assertCompressedSize(7)
        assertDecompressed(1)
    }

    private fun compress() {
        Huffman.compress(message, builder)
    }

    private fun decompress(offset: Int): String? {
        val packet = builder.build()
        packet.skip(offset)
        return Huffman.decompress(packet, packet.readSmart())
    }

    private fun build(message: String) {
        this.message = message
    }

    private fun assertUncompressedSize(size: Int) {
        assertThat(message.length).isEqualTo(size)
    }

    private fun assertCompressedSize(size: Int) {
        assertThat(builder.build().readableBytes() - 1).isEqualTo(size)
    }

    private fun assertDecompressed(offset: Int = 0) {
        assertThat(decompress(offset)).isEqualTo(message)
    }
}