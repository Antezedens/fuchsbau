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
import rumba.nmos.NmosEntity.Node
import kotlin.browser.window

interface State : RState {
	var nodes: MutableList<Node>
	var devices: MutableList<String>
}

class Nmos : RComponent<RProps, State>() {

	init {
		state.nodes = mutableListOf()
		getEntities("node")
	}

	override fun RBuilder.render() {
		h1 { +"Nodes" }
		ul {
			state.nodes.forEach { n ->
				li { +n.label }
			}
		}
	}

	private fun getEntities(entity: String) {
		window.fetch(Request("http://localhost:8080/api/v1/nmos/${entity}s")).then { res ->
			res.text().then { str ->
				val arr = JSON.parse<Array<NmosId>>(str)
				console.log(arr)
				arr.forEach { getEntityDetail(entity, it) }
			}
		}
	}

	private fun getEntityDetail(entity: String, id: NmosId) {
		window.fetch(Request("http://localhost:8080/api/v1/nmos/$entity/$id")).then {
			it.text().then { e ->
				console.log("entity = $e")
				val node = JSON.parse<Node>(e)
				setState {
					nodes.add(node)
				}
			}
		}
	}
}

fun RBuilder.nmos() = child(Nmos::class) {}