package kr.yhs.covid.tiles

import androidx.wear.tiles.ColorBuilders
import androidx.wear.tiles.DimensionBuilders
import androidx.wear.tiles.ModifiersBuilders

object TilesBuilder {
    fun corner(
        radius: DimensionBuilders.DpProp? = null
    ) =
        ModifiersBuilders.Corner.Builder().apply {
            if (radius != null)
                setRadius(radius)
        }.build()

    fun background(
        corner: ModifiersBuilders.Corner? = null,
        color: ColorBuilders.ColorProp? = null
    ) = ModifiersBuilders.Background.Builder().apply {
        if (corner != null)
            setCorner(corner)
        if (color != null)
            setColor(color)
    }.build()

    fun padding(
        all: DimensionBuilders.DpProp? = null,
        start: DimensionBuilders.DpProp = DimensionBuilders.dp(0F),
        end: DimensionBuilders.DpProp = DimensionBuilders.dp(0F),
        top: DimensionBuilders.DpProp = DimensionBuilders.dp(0F),
        bottom: DimensionBuilders.DpProp = DimensionBuilders.dp(0F)
    ) =
        ModifiersBuilders.Padding.Builder().apply {
            if (all != null) {
                setStart(all)
                setEnd(all)
                setTop(all)
                setBottom(all)
            } else {
                setStart(start)
                setEnd(end)
                setTop(top)
                setBottom(bottom)
            }
        }.build()

    fun border(
        width: DimensionBuilders.DpProp? = null,
        color: ColorBuilders.ColorProp? = null
    ) =
        ModifiersBuilders.Border.Builder().apply {
            if (width != null)
                setWidth(DimensionBuilders.dp(3f))
            if (color != null)
                setColor(color)
        }.build()
}