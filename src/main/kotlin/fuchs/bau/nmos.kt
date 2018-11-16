package fuchs.bau

import kotlinx.html.InputType
import kotlinx.html.InputType.date
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.get
import org.w3c.fetch.Request
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.a
import react.dom.button
import react.dom.div
import react.dom.h1
import react.dom.h5
import react.dom.img
import react.dom.input
import react.dom.label
import react.dom.table
import react.dom.tbody
import react.dom.td
import react.dom.tr
import react.setState
import kotlin.browser.document
import kotlin.browser.window
import kotlin.js.Date
import kotlin.js.json
import kotlin.math.exp
import kotlin.math.round

class Sensor(val id: Int, val value: String, val name: String, val unit: String, val unitid: Int)

val Sensor.mapId: String
	get() = mapId(id, unitid)

val DbRelais.mapId: String
	get() = mapId(id, Main.RELAIS_UNIT_ID)

class Relais(val id: Int, val gpio: Int, val name: String, val excludes: Int = -1)
class DbRelais(val id: Int, val name: String, val nodeid: Int, val value: Int?)
class Node(val id: Int, val relais: List<Relais>)

fun mapId(id: Int, unitId: Int) = "$id$unitId"

interface State : RState {
	var sensors: MutableMap<String, Sensor>
	var selected: MutableSet<String>
	var loading: MutableSet<String>
	var relais: MutableMap<String, DbRelais>
}

fun getabsolutehumid(temp_str: String, rel_hum_str: String): Double =
	getabsolutehumid(temp_str.toDouble(), rel_hum_str.toDouble())

fun getabsolutehumid(temp: Double, rel_hum: Double): Double {
	return round(6.112 * exp(17.67 * temp / (temp + 243.5)) * rel_hum * 2.1674 / (273.15 + temp) * 100) / 100.0
}

class Main : RComponent<RProps, State>() {

	companion object {
		const val ABSOLUTE_HUMID = 3
		const val RELAIS_ID_OFFSET = 50
		const val RELAIS_UNIT_ID = 2
		const val devicehost = "https://wariest-turtle-6853.dataplicity.io"
		const val websitehost = "http://fuchsbau.cu.ma"
	}

	var chart: dynamic
	var data: MutableMap<String, Array<Array<Double>>> = mutableMapOf()
	var now = Date.now()

	/*val nodes = listOf(
		Node(
			10, listOf(
				Relais(RELAIS_ID_OFFSET, 198, "r1"),
				Relais(RELAIS_ID_OFFSET + 1, 199, "r2", 7),
				Relais(RELAIS_ID_OFFSET + 2, 7, "r3", 199),
				Relais(RELAIS_ID_OFFSET + 3, 19, "r4"),
				Relais(RELAIS_ID_OFFSET + 4, 14, "Immer"),
				Relais(RELAIS_ID_OFFSET + 5, 16, "Lüfter"),
				Relais(RELAIS_ID_OFFSET + 6, 15, "Pumpe an"),
				Relais(RELAIS_ID_OFFSET + 7, 3, "Pumpe aus")
			)
		),
		Node(
			1, listOf(
				Relais(RELAIS_ID_OFFSET + 8, 198, "n1-r1"),
				Relais(RELAIS_ID_OFFSET + 9, 199, "n1-r2"),
				Relais(RELAIS_ID_OFFSET + 10, 7, "n1-r3"),
				Relais(RELAIS_ID_OFFSET + 11, 19, "n1-r4"),
				Relais(RELAIS_ID_OFFSET + 12, 14, "n1-r5"),
				Relais(RELAIS_ID_OFFSET + 13, 16, "n1-r6"),
				Relais(RELAIS_ID_OFFSET + 14, 15, "n1-r7"),
				Relais(RELAIS_ID_OFFSET + 15, 3, "n1-r8")
			)
		)
	)*/

	init {
		state.sensors = mutableMapOf()
		state.selected = mutableSetOf()
		state.loading = mutableSetOf()
		state.relais = mutableMapOf()

		window.onclick = {
			if (!it.target.asDynamic().matches(".button1")) {
				val elements = document.getElementsByClassName("dropdown-content")
				for (i in 0 until elements.length) {
					elements[i]?.let {
						if (it.classList.contains("show")) {
							it.classList.remove("show")
						}
					}
				}

			}
		}
		window.fetch(Request("$websitehost/current.php")).then { res ->
			res.text().then { str ->
				val arr = JSON.parse<Array<Sensor>>(str)
				setState {
					val humid = mutableListOf<Sensor>()
					arr.forEach {
						sensors[it.mapId] = it
						if (it.unitid == 1) {
							humid += it
						}
					}
					humid.forEach { humids ->
						val temp = sensors[mapId(humids.id, 0)]
						temp?.let { temps ->
							Sensor(
								humids.id,
								getabsolutehumid(temps.value, humids.value).toString(),
								humids.name,
								"g/m³",
								ABSOLUTE_HUMID
							).apply {
								sensors[mapId] = this
							}
						}
					}
				}
			}
		}

		window.fetch(Request("$websitehost/relais.php")).then { res ->
			res.text().then { str ->
				val arr = JSON.parse<Array<DbRelais>>(str)
				console.log(arr)
				setState {
					arr.forEach {
						relais[it.mapId] = it
					}
				}
			}
		}

		js(
			"""
	        Highcharts.setOptions({
            global: {
                useUTC: false
            }
        });"""
		)
		chart = js(
			"""
			Highcharts.chart('container', {
            chart: {
                zoomType: 'x',
                events: {
                    //click: scope.toggleAbsDiff
                }
            },
            title: {
                text: ''
            },
            subtitle: {
                //text: document.ontouchstart === undefined ?
                //        'Click and drag in the plot area to zoom in' : 'Pinch the chart to zoom in'
            },
            xAxis: {
                type: 'datetime'
            },
            yAxis: [{
                labels: {
                    format: '{value}°C'
                },
                title: {
                    text: 'temperature'
                },
            }, {
                opposite: true,
                ceiling: 100,
                floor: 0,
                labels: {
                    format: '{value}%'
                },
                title: {
                    text: 'rel humid'
                }
            }, {
                ceiling: 1,
                opposite: true,
                title: {
                    text: ''
                }
            }, {
                labels: {
                    format: '{value}g/m³'
                },
                title: {
                    text: "abs humid"
                }
            }],
            legend: {
                enabled: true
            },
            plotOptions: {
                area: {
                    fillColor: {
                        linearGradient: {
                            x1: 0,
                            y1: 0,
                            x2: 0,
                            y2: 1
                        },
                        stops: [
                            [0, Highcharts.getOptions().colors[0]],
                            [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                        ]
                    },
                    marker: {
                        radius: 2
                    },
                    lineWidth: 1,
                    states: {
                        hover: {
                            lineWidth: 1
                        }
                    },
                    threshold: null
                }

            }
        });
	"""
		)

	}

	class SeriesMessage(val series: Array<Series>)
	class Series(val name: String, val yAxis: Int, val data: Array<Array<Double>>)

	private fun getSeries(): Array<Series> {
		val count = state.selected.mapNotNull { state.sensors[it] }.groupBy { it.name }
		return state.selected.mapNotNull {
			state.sensors[it]?.let { s ->
				val name = if (count[s.name]?.size ?: 1 > 1) "${s.name} ${s.unit}" else s.name
				Series(name, s.unitid, data[s.mapId]!!)
			} ?: state.relais[it]?.let { r ->
				Series(r.name, RELAIS_UNIT_ID, data[r.mapId]!!)
			}
		}.toTypedArray()
	}

	private fun addLast(list: MutableList<Array<Double>>): Array<Array<Double>> {
		list.lastOrNull()?.let { last ->
			list.add(arrayOf(now, last[1]))
		}
		return list.toTypedArray()
	}

	private fun reload(s: Sensor) {
		if (s.unitid != ABSOLUTE_HUMID) {
			window.fetch(Request("$websitehost/history.php?id=${s.id}&unit=${s.unitid}")).then { res ->
				res.text().then { str ->
					val arr = JSON.parse<Array<Array<Double>>>(str).toMutableList()
					data[s.mapId] = addLast(arr)

					setState {
						loading.remove(s.mapId)
					}
					updateSeries()
				}
			}
		} else {
			window.fetch(Request("$websitehost/history.php?id=${s.id}&unit=0&unit2=1")).then { res ->
				res.text().then { str ->
					val arr = JSON.parse<Array<Array<Double>>>(str)
					val list = mutableListOf<Array<Double>>()
					arr.forEach {
						list.add(arrayOf(it[0], getabsolutehumid(it[1], it[2])))
					}

					data[s.mapId] = addLast(list)

					setState {
						loading.remove(s.mapId)
					}
					updateSeries()
				}
			}
		}
	}

	private fun reload(r: DbRelais) {
		window.fetch(Request("$websitehost/history.php?id=${r.id}&unit=$RELAIS_UNIT_ID")).then { res ->
			res.text().then { str ->
				var value = 0.5
				val values = mutableListOf<Array<Double>>()
				JSON.parse<Array<Array<Double>>>(str).forEach {
					val state = (it[1] % 2)
					if (value != state) {
						values.add(arrayOf(it[0] - 1000.0, value))
						values.add(arrayOf(it[0], state))
						value = state
					}
				}
				data[r.mapId] = addLast(values)

				setState {
					loading.remove(r.mapId)
				}
				updateSeries()
			}
		}
	}

	private fun updateSeries() {
		chart.update(
			SeriesMessage(getSeries()), true, true
		)
	}

	override fun RBuilder.render() {
		h1 { +"Sensors" }
		table {
			tbody {
				tr {
					td {

						table {
							tbody {
								state.sensors.values.sortedBy { it.id * 1000 + it.unitid }.forEach { n ->
									val mapId = n.mapId
									tr {
										td {
											button(classes = if (state.selected.contains(mapId)) "square2" else "square") {
												+n.name
												attrs.onClickFunction = {
													reload(n)
													setState {
														if (!selected.remove(mapId)) {
															selected.add(mapId)
															loading.add(mapId)
														}
													}
												}
											}
											if (state.loading.contains(mapId)) {
												img(src = "loading_spinner.gif") {
													attrs.width = "20px"
												}
											}
										}
										td { +n.value }
										td { +n.unit }
									}
								}
							}
						}
					}
					state.relais.values.groupBy { it.nodeid }.forEach { (node, allRelais) ->
						td {
							table {
								tbody {
									for (relais in allRelais) {
										tr {
											td {
												val mapId = mapId(relais.id, RELAIS_UNIT_ID)
												button(classes = if (state.selected.contains(mapId)) "square2" else "square") {
													+relais.name
													attrs.onClickFunction = {
														reload(relais)
														setState {
															if (!selected.remove(mapId)) {
																selected.add(mapId)
																loading.add(mapId)
															}
														}
													}
												}
												if (state.loading.contains(mapId)) {
													img(src = "loading_spinner.gif") {
														attrs.width = "20px"
													}
												}
											}
											td {
												div(classes = "switch") {
													input {
														attrs.onChangeFunction = {
															(it.target as? HTMLInputElement)?.let { value ->
																console.log(
																	json(
																		"id" to relais.id,
																		"nodeid" to relais.nodeid,
																		"value" to if (value.checked) 1 else 0
																	)
																)
																//val host =  "http://localhost:8000"
																window.fetch(Request("$devicehost/setRelaisOnNode?id=${relais.id}&nodeid=${relais.nodeid}&value=${if (value.checked) 1 else 0}"))
																	.then {
																		console.log("done set relais")
																	}
															}
														}
														attrs.type = InputType.checkBox
														attrs.defaultChecked = ((relais.value ?: 0 and 1) == 1)
														//<label for="toggle"><i></i></label>
													}
													label {}
												}
											}
											val drpName = "drp${relais.id}"
											td {
												div(classes = "dropdown") {
													button(classes = "button button1") {
														+"+"
														attrs.onClickFunction = {
															val elements =
																document.getElementsByClassName("dropdown-content")
															for (i in 0 until elements.length) {
																elements[i]?.let {
																	if (it.classList.contains("show")) {
																		it.classList.remove("show")
																	}
																}
															}
															document.getElementById(drpName)?.classList?.toggle("show")
														}
													}
													div(classes = "dropdown-content") {
														attrs.id = drpName
														for (i in arrayOf(6,12,24,48)) {
															a(href = "#") {
																+"+$i"
																attrs.onClickFunction = {
																	val date = Date(Date.now() + i * 3600 * 1000)
																	console.log("+$i for relais ${relais.name}")

																	window.fetch(Request("$devicehost/setRelaisOnNode?id=${relais.id}&nodeid=${relais.nodeid}&value=1&turnoff=$date"))
																		.then {
																			console.log("done set relais")
																		}
																}
															}
														}
													}
												}
											}
											td {
												if (relais.id == 55) {
													h5 {
														+"auto"
													}
												}
											}
											td {
												if (relais.id == 55) {
													div(classes = "blueswitch") {
														input {
															attrs.onChangeFunction = {
																(it.target as? HTMLInputElement)?.let { value ->
																	val host =
																		"https://wariest-turtle-6853.dataplicity.io"
																	//val host =  "http://localhost:8000"

																	window.fetch(
																		Request(
																			"$host/setRelaisOnNode?id=${relais.id}&nodeid=${relais.nodeid}&auto=${(if (value.checked) 2 else 0) or (relais.value
																				?: 0)}"
																		)
																	)
																		.then {
																			console.log("done set relais")
																		}
																}
															}
															attrs.type = InputType.checkBox
															attrs.defaultChecked = ((relais.value ?: 0 and 2) == 2)

														}
														label {}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}

fun RBuilder.main() = child(Main::class) {}