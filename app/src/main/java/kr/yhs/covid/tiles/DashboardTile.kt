package kr.yhs.covid.tiles

import androidx.core.content.ContextCompat
import androidx.wear.tiles.ColorBuilders.argb
import androidx.wear.tiles.TileService
import androidx.wear.tiles.TileBuilders
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.DeviceParametersBuilders.DeviceParameters
import androidx.wear.tiles.DimensionBuilders.*
import androidx.wear.tiles.LayoutElementBuilders.Box
import androidx.wear.tiles.LayoutElementBuilders.Column
import androidx.wear.tiles.LayoutElementBuilders.FontStyles
import androidx.wear.tiles.LayoutElementBuilders.Layout
import androidx.wear.tiles.LayoutElementBuilders.Text
import androidx.wear.tiles.LayoutElementBuilders.Row
import androidx.wear.tiles.ModifiersBuilders.Background
import androidx.wear.tiles.ModifiersBuilders.Corner
import androidx.wear.tiles.ModifiersBuilders.Modifiers
import androidx.wear.tiles.ModifiersBuilders.Padding
import androidx.wear.tiles.ResourceBuilders.Resources
import androidx.wear.tiles.TimelineBuilders.Timeline
import androidx.wear.tiles.TimelineBuilders.TimelineEntry
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.guava.future
import kr.yhs.covid.R


class DashboardTile: TileService() {
    private val RESOURCES_VERSION = "1"
    private val serviceScope = CoroutineScope(Dispatchers.IO)

    private lateinit var deviceParameters: DeviceParameters

    override fun onTileRequest(requestParams: RequestBuilders.TileRequest): ListenableFuture<TileBuilders.Tile> = serviceScope.future {
        deviceParameters = requestParams.deviceParameters!!
        TileBuilders.Tile.Builder()
            .setResourcesVersion(RESOURCES_VERSION)
            .setTimeline(
                Timeline.Builder().addTimelineEntry(
                    TimelineEntry.Builder().setLayout(
                        Layout.Builder().setRoot(
                            initLayout()
                        ).build()
                    ).build()
                ).build()
            ).build()
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

    private fun initLayout() =
        Box.Builder().apply {
            setWidth(expand())
            setHeight(expand())

            addContent(
                Column.Builder().apply {
                    addContent (
                        Row.Builder().apply {
                            addContent(
                                Column.Builder().apply {
                                    setModifiers(
                                        setBackground(
                                            dp(3f),
                                            dp(20f),
                                            ContextCompat.getColor(
                                                this@DashboardTile, R.color.total1
                                            )
                                        )
                                    )
                                }.build()
                            )
                            addContent(
                                Column.Builder().apply {
                                    setModifiers(
                                        setBackground(
                                            dp(3f),
                                            dp(20f),
                                            ContextCompat.getColor(
                                                this@DashboardTile, R.color.total2
                                            )
                                        )
                                    )
                                }.build()
                            )
                        }.build()
                    )
                    addContent (
                        Row.Builder().apply {
                            addContent(
                                Column.Builder().apply {
                                    setModifiers(
                                        setBackground(
                                            dp(3f),
                                            dp(20f),
                                            ContextCompat.getColor(
                                                this@DashboardTile, R.color.total3
                                            )
                                        )
                                    )
                                }.build()
                            )
                            addContent(
                                Column.Builder().apply {
                                    setModifiers(
                                        setBackground(
                                            dp(3f),
                                            dp(20f),
                                            ContextCompat.getColor(
                                                this@DashboardTile, R.color.total4
                                            )
                                        )
                                    )
                                }.build()
                            )
                        }.build()
                    )
                }.build()
            )
        }.build()

    private fun setBackground(padding: DpProp, corner: DpProp, color: Int) =
        Modifiers.Builder().apply {
            setPadding(
                Padding.Builder().apply {
                    setStart(padding)
                    setEnd(padding)
                    setTop(padding)
                    setBottom(padding)
                }.build()
            )
            setBackground(
                Background.Builder().apply {
                    setCorner(
                        Corner.Builder().apply {
                            setRadius(corner)
                            setColor(
                                argb(color)
                            )
                        }.build()
                    )
                }.build()
            )
        }.build()
}
