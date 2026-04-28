package ca.tetervak.dicegame.data

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object RollDataSerializer : Serializer<RollDataProto> {
    override val defaultValue: RollDataProto = RollDataProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): RollDataProto {
        try {
            return RollDataProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: RollDataProto, output: OutputStream) = t.writeTo(output)
}
