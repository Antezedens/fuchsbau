package fuchs.bau

import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.fetch.Request
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.button
import react.dom.div
import react.dom.h1
import react.dom.img
import react.dom.input
import react.dom.label
import react.dom.table
import react.dom.tbody
import react.dom.td
import react.dom.tr
import react.setState
import kotlin.browser.window
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
		val ABSOLUTE_HUMID = 3
		val RELAIS_ID_OFFSET = 50
		val RELAIS_UNIT_ID = 2
	}

	var chart: dynamic
	var data: MutableMap<String, Array<Array<Double>>> = mutableMapOf()

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

		window.fetch(Request("http://fuchsbau.cu.ma/current.php")).then { res ->
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

		window.fetch(Request("http://fuchsbau.cu.ma/relais.php")).then { res ->
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

		js("""
	        Highcharts.setOptions({
            global: {
                useUTC: false
            }
        });""")
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
		console.log("selected ${state.selected}")
		val result = state.selected.mapNotNull {
			state.sensors[it]?.let { s ->
				Series(s.name, s.unitid, data[s.mapId]!!)
			} ?:
			state.relais[it]?.let { r ->
				Series(r.name, RELAIS_UNIT_ID, data[r.mapId]!!)
			}
		}.toTypedArray()
		//console.log(result)
		return result
	}

	private fun reload(s: Sensor) {
		if (s.unitid != ABSOLUTE_HUMID) {
			window.fetch(Request("http://fuchsbau.cu.ma/history.php?id=${s.id}&unit=${s.unitid}")).then { res ->
				res.text().then { str ->
					val arr = JSON.parse<Array<Array<Double>>>(str)
					data[s.mapId] = arr

					setState {
						loading.remove(s.mapId)
					}
					updateSeries()
				}
			}
		} else {
			window.fetch(Request("http://fuchsbau.cu.ma/history.php?id=${s.id}&unit=0&unit2=1")).then { res ->
				res.text().then { str ->
					val arr = JSON.parse<Array<Array<Double>>>(str)
					data[s.mapId] = arr.map {
						arrayOf(it[0], getabsolutehumid(it[2], it[1]))
					}.toTypedArray()

					setState {
						loading.remove(s.mapId)
					}
					updateSeries()
				}
			}
		}
	}

	private fun reload(r: DbRelais) {
		window.fetch(Request("http://fuchsbau.cu.ma/history.php?id=${r.id}&unit=$RELAIS_UNIT_ID")).then { res ->
			res.text().then { str ->
				var value = 0.5
				val values = mutableListOf<Array<Double>>()
				JSON.parse<Array<Array<Double>>>(str).forEach {
						if (value != it[1]) {
							values.add(arrayOf(it[0] - 1000.0, value))
							values.add(it)
							value = it[1]
						}
				}
				data[r.mapId] = values.toTypedArray()

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
													console.log("${n.id} ${n.name}")
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
																console.log("changed! ${relais.id}, ${relais.nodeid} + $value")
																val host = "https://wariest-turtle-6853.dataplicity.io"
																console.log(
																	json(
																		"id" to relais.id,
																		"nodeid" to relais.nodeid,
																		"value" to if (value.checked) 1 else 0
																	)
																)
																//val host =  "http://localhost:8000"
																window.fetch(Request("$host/setRelaisOnNode?id=${relais.id}&nodeid=${relais.nodeid}&value=${if (value.checked) 1 else 0}"))
																	.then {
																		console.log("done set relais")
																	}
															}
														}
														attrs.type = InputType.checkBox
														console.log("${relais.value}")
														attrs.defaultChecked = (relais.value == 1)
														//<label for="toggle"><i></i></label>
													}
													label {
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