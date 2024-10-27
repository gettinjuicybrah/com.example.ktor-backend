package example.com.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

object UUIDSerializer: KSerializer<UUID> {

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

    /*
    We must specify how to deserialize our values.
     */
    override fun deserialize(decoder: Decoder): UUID =
        UUID.fromString(decoder.decodeString())

    /*
    THe function responsible for serialization
     */
    override fun serialize(encoder: Encoder, value: UUID) {
        encoder.encodeString(value.toString())
    }

}