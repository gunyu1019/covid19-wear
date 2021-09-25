package kr.yhs.covid.tiles

import androidx.wear.tiles.ColorBuilders.ColorProp
import androidx.wear.tiles.DimensionBuilders.*
import androidx.wear.tiles.LayoutElementBuilders.FontStyle
import androidx.wear.tiles.LayoutElementBuilders.Text
import androidx.wear.tiles.ModifiersBuilders.Modifiers

object LayoutBuilder {
    fun text(
        textString: String,
        fontStyles: FontStyle? = null,
        multilineAlignment: Int? = null,
        maxLines: Int? = null,
        modifiers: Modifiers? = null,
        overflow: Int? = null,
        lineHeight: SpProp? = null
    ) =
        Text.Builder().apply {
            setText(textString)
            if (fontStyles != null)
                setFontStyle(fontStyles)
            if (maxLines != null)
                setMaxLines(maxLines)
            if (multilineAlignment != null)
                setMultilineAlignment(multilineAlignment)
            if (modifiers != null)
                setModifiers(modifiers)
            if (lineHeight != null)
                setLineHeight(lineHeight)
            if (overflow != null)
                setOverflow(overflow)
        }.build()

    fun font(
        color: ColorProp? = null,
        italic: Boolean? = null,
        underline: Boolean? = null,
        letterSpacing: EmProp? = null,
        size: SpProp? = null,
        widght: Int? = null,
    ) =
        FontStyle.Builder().apply {
            if (color != null)
                setColor(color)
            if (italic != null)
                setItalic(italic)
            if (letterSpacing != null)
                setLetterSpacing(letterSpacing)
            if (size != null)
                setSize(size)
            if (underline != null)
                setUnderline(underline)
            if (widght != null)
                setWeight(widght)
        }.build()
}