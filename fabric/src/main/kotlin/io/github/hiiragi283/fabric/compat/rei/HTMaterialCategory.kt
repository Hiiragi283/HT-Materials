package io.github.hiiragi283.fabric.compat.rei

import io.github.hiiragi283.api.HTMaterialsAPI
import me.shedaniel.clothconfig2.ClothConfigInitializer
import me.shedaniel.clothconfig2.api.scroll.ScrollingContainer
import me.shedaniel.math.Point
import me.shedaniel.math.Rectangle
import me.shedaniel.rei.api.client.REIRuntime
import me.shedaniel.rei.api.client.gui.Renderer
import me.shedaniel.rei.api.client.gui.widgets.Slot
import me.shedaniel.rei.api.client.gui.widgets.Widget
import me.shedaniel.rei.api.client.gui.widgets.WidgetWithBounds
import me.shedaniel.rei.api.client.gui.widgets.Widgets
import me.shedaniel.rei.api.client.registry.display.DisplayCategory
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.entry.EntryStack
import me.shedaniel.rei.api.common.util.EntryStacks
import net.minecraft.client.gui.Element
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.util.math.MathHelper

/**
 * Reference: [me.shedaniel.rei.plugin.client.categories.beacon.DefaultBeaconPaymentCategory]
 */

@Suppress("UnstableApiUsage")
object HTMaterialCategory : DisplayCategory<HTMaterialDisplay> {

    override fun getCategoryIdentifier(): CategoryIdentifier<HTMaterialDisplay> = HMReiPlugin.MATERIAL_ID

    override fun getTitle(): Text = LiteralText(HTMaterialsAPI.MOD_NAME)

    override fun getIcon(): Renderer = EntryStacks.of(HTMaterialsAPI.INSTANCE.iconItem())

    override fun setupDisplay(display: HTMaterialDisplay, bounds: Rectangle): MutableList<Widget> {
        val widgets: MutableList<Widget> = mutableListOf()
        widgets += Widgets
            .createSlot(Point(bounds.centerX - 8, bounds.y + 3))
            .entry(display.getEntries().firstOrNull() ?: EntryStack.empty())
        val rectangle = Rectangle(
            bounds.centerX - bounds.width / 2 - 1,
            bounds.y + 23,
            bounds.width + 2,
            bounds.height - 28
        )
        widgets += Widgets.createSlotBase(rectangle)
        widgets += HTScrollableSlotsWidget(
            rectangle,
            display.getEntries().map { entry: EntryStack<*> ->
                Widgets.createSlot(Point(0, 0))
                    .disableBackground()
                    .entry(entry)
            })
        return widgets
    }

    private class HTScrollableSlotsWidget(
        private val bounds: Rectangle,
        private val widgets: List<Slot>
    ) : WidgetWithBounds() {

        private val scrolling: ScrollingContainer = object : ScrollingContainer() {

            override fun getBounds(): Rectangle = Rectangle(
                this@HTScrollableSlotsWidget.getBounds().x + 1,
                this@HTScrollableSlotsWidget.getBounds().y + 1,
                this@HTScrollableSlotsWidget.getBounds().width - 2,
                this@HTScrollableSlotsWidget.getBounds().height - 2
            )

            override fun getMaxScrollHeight(): Int = MathHelper.ceil(widgets.size / 8f) * 18

        }

        override fun mouseScrolled(double1: Double, double2: Double, double3: Double): Boolean =
            if (containsMouse(double1, double2)) {
                scrolling.offset(ClothConfigInitializer.getScrollStep() * -double3, true)
                true
            } else false

        override fun getBounds(): Rectangle = bounds

        override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean =
            if (scrolling.updateDraggingState(mouseX, mouseY, button)) true else
                super.mouseClicked(mouseX, mouseY, button)

        override fun mouseDragged(
            mouseX: Double,
            mouseY: Double,
            button: Int,
            deltaX: Double,
            deltaY: Double
        ): Boolean = if (scrolling.mouseDragged(mouseX, mouseY, button, deltaX, deltaY)) true else
            super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY)

        override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
            scrolling.updatePosition(delta)
            scissor(matrices, scrolling.scissorBounds).use {
                for (y: Int in 0 until MathHelper.ceil(widgets.size / 8f)) {
                    for (x: Int in 0..7) {
                        val index: Int = y * 8 + x
                        if (widgets.size <= index) break
                        val widget: Slot = widgets[index]
                        widget.bounds.setLocation(
                            bounds.x + 1 + x * 18,
                            bounds.y + 1 + y * 18 - scrolling.scrollAmountInt()
                        )
                        widget.render(matrices, mouseX, mouseY, delta)
                    }
                }
            }
            scissor(matrices, scrolling.bounds).use {
                scrolling.renderScrollBar(
                    -0x1000000,
                    1f,
                    if (REIRuntime.getInstance().isDarkThemeEnabled) 0.8f else 1f
                )
            }
        }

        override fun children(): List<Element> = widgets

    }


}