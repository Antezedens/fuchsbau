package rumba

import react.dom.render
import rumba.nmos.nmos
import kotlin.browser.document
import kotlin.browser.window

fun main(args: Array<String>) {
	window.onload = {
		val root = document.getElementById("root") ?: throw IllegalStateException()
		render(root) {
			nmos()
		}
	}
}