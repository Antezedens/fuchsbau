package fuchs.bau

import fuchs.bau.Main.SeriesMessage
import kotlinext.js.js
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import org.w3c.fetch.Request
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.h1
import react.dom.*
import react.setState
import kotlin.browser.window
import kotlin.js.Math
import kotlin.js.Math.pow
import kotlin.math.E

class Sensor(val id: Int, val value: String, val name: String, val unit: String, val unitid: Int)

val Sensor.mapId : String
	get() = mapId(id, unitid)

class Relais(val id: Int, val gpio: Int, val name: String, val excludes : Int = -1)
class Node(val id: Int, val relais: List<Relais>)

fun mapId(id: Int, unitId: Int) = "$id$unitId"

interface State : RState {
	var sensors: MutableMap<String, Sensor>
	var selected: MutableSet<String>
	var loading: MutableSet<String>
}

fun getabsolutehumid(temp_str: String, rel_hum_str: String): Double = getabsolutehumid(temp_str.toDouble(), rel_hum_str.toDouble())

fun getabsolutehumid(temp: Double, rel_hum: Double): Double {
	return Math.round(6.112 * pow(E, 17.67 * temp / (temp + 243.5)) * rel_hum * 2.1674 / (273.15 + temp) * 100) / 100.0
}


class Main : RComponent<RProps, State>() {

	val ABSOLUTE_HUMID = 3
	val RELAIS_ID_OFFSET = 50
	var chart: dynamic
	var data: MutableMap<String, Array<Array<Double>>> = mutableMapOf()

	val nodes = listOf(
		Node(10, listOf(
			Relais(RELAIS_ID_OFFSET, 198, "r1"),
			Relais(RELAIS_ID_OFFSET+1, 199, "r2", 7),
			Relais(RELAIS_ID_OFFSET+2, 7, "r3", 199),
			Relais(RELAIS_ID_OFFSET+3, 19, "r4"),
			Relais(RELAIS_ID_OFFSET+4, 14, "Immer"),
			Relais(RELAIS_ID_OFFSET+5, 16, "Lüfter"),
			Relais(RELAIS_ID_OFFSET+6, 15, "Pumpe an"),
			Relais(RELAIS_ID_OFFSET+7, 3, "Pumpe aus")
		)),
		Node(1, listOf(
			Relais(RELAIS_ID_OFFSET+8, 198, "r1"),
			Relais(RELAIS_ID_OFFSET+9, 199, "r2", 7),
			Relais(RELAIS_ID_OFFSET+10, 7, "r3", 199),
			Relais(RELAIS_ID_OFFSET+11, 19, "r4"),
			Relais(RELAIS_ID_OFFSET+12, 14, "Immer"),
			Relais(RELAIS_ID_OFFSET+13, 16, "Lüfter"),
			Relais(RELAIS_ID_OFFSET+14, 15, "Pumpe an"),
			Relais(RELAIS_ID_OFFSET+15, 3, "Pumpe aus")
		))
	)

	init {
		state.sensors = mutableMapOf()
		state.selected = mutableSetOf()
		state.loading = mutableSetOf()

		window.fetch(Request("http://fuchsbau.cu.ma/current.php")).then { res ->
			res.text().then { str ->
				val arr = JSON.parse<Array<Sensor>>(str)
				console.log(arr)
				setState {
					val humid = mutableListOf<Sensor>()
					arr.forEach {
						console.log(it.mapId)
						sensors[it.mapId] = it
						if (it.unitid == 1) {
							humid += it
						}
					}
					humid.forEach { humids ->
						console.log("humid")
						console.log(humids)
						val temp = sensors[mapId(humids.id, 0)]
						temp?.let { temps ->
							Sensor(humids.id, getabsolutehumid(temps.value, humids.value).toString(), humids.name, "g/m³", ABSOLUTE_HUMID).apply {
								sensors[mapId] = this
							}
						}
					}
				}
			}
		}

		chart = js("""
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
                    text: 'Temperature'
                },
            }, {
                opposite: true,
                ceiling: 100,
                floor: 0,
                labels: {
                    format: '{value}%'
                },
                title: {
                    text: 'Humidity'
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
                    text: "Humidity"
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
	""")

	}

	class SeriesMessage(val series: Array<Series>)
	class Series(val name: String, val yAxis: Int, val data: Array<Array<Double>>)

	fun getSeries(): Array<Series> {
		val result = state.selected.mapNotNull {state.sensors[it] }.map {
			Series(it.name, it.unitid, data[it.mapId]!!)
		}.toTypedArray()
		//console.log(result)
		return result
	}

	fun reload(s : Sensor) {
		if (s.unitid != ABSOLUTE_HUMID) {
			window.fetch(Request("http://fuchsbau.cu.ma/history.php?id=${s.id}&unit=${s.unitid}")).then { res ->
				res.text().then { str ->
					val arr = JSON.parse<Array<Array<Double>>>(str)
					data[s.mapId] = arr
					//chart.setTitle(js("{text: \"New Title\"}"))
					//console.log(data[s.mapId])

					setState {
						loading.remove(s.mapId)
					}

					chart.update(
						SeriesMessage(getSeries()), true, true
					)
				}
			}
		} else {
			window.fetch(Request("http://fuchsbau.cu.ma/history.php?id=${s.id}&unit=0&unit2=1")).then { res ->
				res.text().then { str ->
					val arr = JSON.parse<Array<Array<Double>>>(str)
					data[s.mapId] = arr.map {
						console.log("${it[1]} -> ${it[2]} ->  ${getabsolutehumid(it[2], it[1])}")
						arrayOf(it[0], getabsolutehumid(it[2], it[1]))
					}.toTypedArray()
					//chart.setTitle(js("{text: \"New Title\"}"))
					//console.log(data[s.mapId])

					setState {
						loading.remove(s.mapId)
					}

					chart.update(
						SeriesMessage(getSeries()), true, true
					)
				}
			}
		}
	}

	override fun RBuilder.render() {
		h1 { +"Sensors" }
		table {
			tbody {
				state.sensors.values.sortedBy { it.id * 1000 + it.unitid }.forEach { n ->
					val mapId = n.mapId
					tr {
						td {
							button(classes = if(state.selected.contains(mapId)) "square2" else "square") {
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
								img(src="loading_spinner.gif") {
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
		/*div {
			attrs.id = "container"
		}*/
	}

}

fun RBuilder.main() = child(Main::class) {}