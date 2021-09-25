package kr.yhs.covid.tiles

import androidx.core.content.ContextCompat
import androidx.wear.tiles.ColorBuilders.argb
import androidx.wear.tiles.DeviceParametersBuilders.DeviceParameters
import androidx.wear.tiles.DimensionBuilders.*
import androidx.wear.tiles.LayoutElementBuilders.*
import androidx.wear.tiles.ModifiersBuilders.Modifiers
import androidx.wear.tiles.ModifiersBuilders.Padding
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.ResourceBuilders.Resources
import androidx.wear.tiles.TileBuilders
import androidx.wear.tiles.TileService
import androidx.wear.tiles.TimelineBuilders.Timeline
import androidx.wear.tiles.TimelineBuilders.TimelineEntry
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.*
import kotlinx.coroutines.guava.future
import kr.yhs.covid.R
import kr.yhs.covid.tiles.TilesBuilder.background
import kr.yhs.covid.tiles.TilesBuilder.border
import kr.yhs.covid.tiles.TilesBuilder.corner
import kr.yhs.covid.tiles.TilesBuilder.padding
import kr.yhs.covid.tiles.LayoutBuilder.text
import kr.yhs.covid.tiles.LayoutBuilder.font
import org.jsoup.Jsoup


class DashboardTile: TileService() {
    private val RESOURCES_VERSION = "1"
    private val serviceScope = CoroutineScope(Dispatchers.IO)

    private lateinit var deviceParameters: DeviceParameters

    override fun onTileRequest(requestParams: RequestBuilders.TileRequest): ListenableFuture<TileBuilders.Tile> = serviceScope.future {
        deviceParameters = requestParams.deviceParameters!!
        val deffered = async(Dispatchers.Default) {
            val response = Jsoup.connect(
                "http://ncov.mohw.go.kr/bdBoardList_Real.do?brdId=1&brdGubun=11"
            ).get()
            val case = response
                .body()
                .select("div.caseTable")
                .select("div")
            val result = arrayListOf<String>()
            val firstData = case.first()
            result.add(
                firstData.select("dd.ca_value").first().text()
            )
            result.add(
                firstData.select("p.inner_value").first().text()
            )
            var data = case
            for (index in 0..2) {
                data = data.nextAll()
                result.add(
                    data.select("dd.ca_value").first().text()
                )
            }
            return@async result
        }
        deffered.await().let {
            TileBuilders.Tile.Builder()
                .setResourcesVersion(RESOURCES_VERSION)
                .setFreshnessIntervalMillis(3600000) // 60 minutes
                .setTimeline(
                    Timeline.Builder().addTimelineEntry(
                        TimelineEntry.Builder().setLayout(
                            Layout.Builder().setRoot(
                                getLayout(it)
                            ).build()
                        ).build()
                    ).build()
                ).build()
        }
    }

    override fun onResourcesRequest(requestParams: RequestBuilders.ResourcesRequest): ListenableFuture<Resources> = serviceScope.future {
        Resources.Builder()
            .setVersion(RESOURCES_VERSION)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    private fun getLayout(data: ArrayList<String>) =
        Box.Builder().apply {
            setWidth(expand())
            setHeight(expand())
            setModifiers(Modifiers.Builder().apply {
                setPadding(
                    Padding.Builder().apply {
                        setStart(dp(45f))
                        setEnd(dp(45f))
                        setTop(dp(45f))
                        setBottom(dp(45f))
                    }.build()
                )
            }.build())

            addContent(
                Column.Builder().apply {
                    setWidth(expand())
                    setHeight(expand())
                    addContent (
                        Row.Builder().apply {
                            setWidth(expand())
                            setHeight(expand())
                            addContent(
                                getBox(
                                    resources.getString(R.string.current_description),
                                    resources.getString(R.string.people, data[0]),
                                    resources.getString(R.string.people_addition, data[1]),
                                    color = ContextCompat.getColor(
                                        this@DashboardTile, R.color.total1
                                    )
                                )
                            )
                            addContent(
                                getBox(
                                    resources.getString(R.string.clinic_description),
                                    resources.getString(R.string.people, data[2]),
                                    color = ContextCompat.getColor(
                                        this@DashboardTile, R.color.total2
                                    )
                                )
                            )
                        }.build()
                    )
                    addContent (
                        Row.Builder().apply {
                            setWidth(expand())
                            setHeight(expand())
                            addContent(
                                getBox(
                                    resources.getString(R.string.clear_description),
                                    resources.getString(R.string.people, data[3]),
                                    color = ContextCompat.getColor(
                                        this@DashboardTile, R.color.total3
                                    )
                                )
                            )
                            addContent(
                                getBox(
                                    resources.getString(R.string.death_description),
                                    resources.getString(R.string.people, data[4]),
                                    color = ContextCompat.getColor(
                                        this@DashboardTile, R.color.total4
                                    )
                                )
                            )
                        }.build()
                    )
                }.build()
            )
        }.build()

    private fun getBackground(padding: DpProp, corner: DpProp, color: Int) =
        Modifiers.Builder().apply {
            setPadding(
                padding(all = padding)
            )
            setBorder(
                border(width = dp(3f))
            )
            this.setBackground(
                background(
                    corner(radius = corner),
                    color = argb(color)
                )
            )
        }.build()

    private fun getBox(data1: String, data2: String, data3: String? = null, color: Int) =
        Box.Builder().apply {
            setWidth(expand())
            setHeight(expand())

            setModifiers(
                getBackground(
                    padding = dp(12f),
                    corner = dp(20f),
                    color = color
                )
            )
            addContent(getColumn(data1, data2, data3))
        }.build()

    private fun getColumn(data1: String, data2: String, data3: String? = null) =
        Column.Builder().apply {
            addContent(
                text(
                    textString = data1,
                    fontStyles = font(
                        size = sp(13f),
                        widght = FONT_WEIGHT_NORMAL
                    )
                )
            )
            addContent(
                text(
                    textString = data2,
                    fontStyles = font(
                        size = sp(13f),
                        widght = FONT_WEIGHT_BOLD
                    )
                )
            )
            if (data3 != null)
                addContent(
                    text(
                        textString = data3,
                        fontStyles = font(
                            size = sp(13f),
                            widght = FONT_WEIGHT_BOLD
                        )
                    )
                )
        }.build()
}
