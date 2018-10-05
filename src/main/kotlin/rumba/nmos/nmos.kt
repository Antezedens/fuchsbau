package rumba.nmos

import org.w3c.fetch.Request
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.h1
import react.dom.li
import react.dom.ul
import react.setState
import rumba.nmos.NmosEntity.Device
import rumba.nmos.NmosEntity.Node
import rumba.nmos.NmosEntity.Receiver
import rumba.nmos.NmosEntity.Sender
import rumba.util.NOC
import kotlin.browser.window

interface State : RState {
	var nodes: MutableMap<NmosId, Node>
	var devices: MutableMap<NmosId, Device>
	var senders: MutableMap<NmosId, Sender>
	var receivers: MutableMap<NmosId, Receiver>
}

external class EventBus(url: String) {
	var onopen : () -> Unit
	fun registerHandler(msg: String, cb: (dynamic, dynamic) -> Unit)
}

class Nmos : RComponent<RProps, State>() {

	init {
		state.nodes = mutableMapOf()
		state.devices = mutableMapOf()
		state.senders = mutableMapOf()
		state.receivers = mutableMapOf()


		getEntities("node") { e ->
			val node = JSON.parse<Node>(e)
			setState {
				nodes[node.uuid] = node
			}
		}
		getEntities("device") { e ->
			val device = JSON.parse<Device>(e)
			setState {
				devices[device.uuid] = device
			}
		}
		getEntities("sender") { e ->
			val sender = JSON.parse<Sender>(e)
			setState {
				senders[sender.uuid] = sender
			}
		}
		getEntities("receiver") { e ->
			val receiver = JSON.parse<Receiver>(e)
			setState {
				receivers[receiver.uuid] = receiver
			}
		}

		val eb = EventBus("http://localhost:8080/eventbus")
		eb.onopen = {
			eb.registerHandler("nmos-sender-added") { err, msg ->
				val idAndType = JSON.parse<IdAndType>(msg.body)
				getEntityDetail("sender", idAndType.id) { e ->
					val sender = JSON.parse<Sender>(e)
					setState {
						senders[sender.uuid] = sender
					}
				}
			}
			eb.registerHandler("nmos-sender-updated") { err, msg ->
				val idAndType = JSON.parse<IdAndType>(msg.body)
				getEntityDetail("sender", idAndType.id) { e ->
					val sender = JSON.parse<Sender>(e)
					setState {
						senders[sender.uuid] = sender
					}
				}
			}
			eb.registerHandler("nmos-sender-removed") { err, msg ->
				val idAndType = JSON.parse<IdAndType>(msg.body)
				setState {
					senders.remove(idAndType.id)
				}
			}
		}
	}

	override fun RBuilder.render() {
		h1 { +"Nodes" }
		ul {
			state.senders.values.sortedWith(Comparator{ n1, n2 -> NOC.compare(n1.label, n2.label)}).forEach { n ->
				li { +n.label }
			}
		}
	}

	private fun getEntities(entity: String, callback: (String) -> Unit) {
		window.fetch(Request("http://localhost:8080/api/v1/nmos/${entity}s")).then { res ->
			res.text().then { str ->
				val arr = JSON.parse<Array<NmosId>>(str)
				console.log(arr)
				arr.forEach { getEntityDetail(entity, it, callback) }
			}
		}
	}

	private fun getEntityDetail(entity: String, id: NmosId, callback: (String) -> Unit) {
		window.fetch(Request("http://localhost:8080/api/v1/nmos/$entity/$id")).then {
			it.text().then { e ->
				console.log("entity = $e")
				callback(e)
			}
		}
	}
}

fun RBuilder.nmos() = child(Nmos::class) {}