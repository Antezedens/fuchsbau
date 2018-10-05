package rumba.nmos

typealias NmosId = String


data class Tag(val key: String, val value: List<String>)


data class NmosConnection(val sender: NmosId, val receiver: NmosId) {
	override fun toString(): String = "$sender  <=>  $receiver"
}

enum class Format {
	UNKNOWN, VIDEO, AUDIO, DATA, MUX, UNRECOGNIZED;
}

interface INmosEntity {
	val uuid: NmosId
}

sealed class NmosEntity : INmosEntity {

	
	data class Node(override val uuid: NmosId,
	                val label: String,
	                val description: String,
	                val tags: List<Tag> = emptyList()
	) : NmosEntity() {

		override fun toString(): String = if (label.isNotEmpty()) label else uuid
	}

	
	data class Device(override val uuid: NmosId,
	                  val label: String,
	                  val nodeId: NmosId,
	                  val description: String,
	                  val tags: List<Tag>
	) : NmosEntity() {

		override fun toString(): String = if (label.isNotEmpty()) label else uuid
	}

	
	data class Sender(override val uuid: NmosId,
	                  val deviceId: NmosId,
	                  val label: String,
	                  val description: String,
	                  val active: Boolean,
	                  val format: Format,
	                  val tags: List<Tag>
	) : NmosEntity() {

		override fun toString(): String = if (label.isNotEmpty()) label else uuid
	}

	
	data class Receiver(override val uuid: NmosId,
	                    val deviceId: NmosId,
	                    val label: String,
	                    val description: String,
	                    val senderId: NmosId,
	                    val active: Boolean,
	                    val format: Format,
	                    val tags: List<Tag>
	) : NmosEntity() {

		override fun toString(): String = if (label.isNotEmpty()) label else uuid

	}
}

enum class NmosType {
	NODE, DEVICE, RECEIVER, SENDER, UNKNOWN
}


data class IdAndType(val id: NmosId, val type: NmosType) {
}
