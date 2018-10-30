package graph

import util.gcd
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage

class Trochoid(rm: Int,
               rc: Int,
               rd: Int,
               minScale: Double,
               private val lifetime: Int,
               private val scaleStep: Double): Graph() {

    private var scale = minScale

    private val centerX = Math.random()*100+50
    private val centerY = Math.random()*100+50

    private var time = 0

    private val trochoidImage = BufferedImage(500, 500, BufferedImage.TYPE_4BYTE_ABGR)

    init {
        val x0: Double = (500-2*rc+ rc).toDouble()
        val y0: Double = (500-2*rc+ rc).toDouble()

        val ox = (x0 + rc - rm + rd)
        val oy = y0

        val laps: Int = rm/gcd(rc, rm)

        val step = 5

        val g = trochoidImage.createGraphics()

        g.stroke = BasicStroke(2F)
        g.color = Color.CYAN
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        var i = 0.0
        var x: Double
        var y: Double

        val a = arrayListOf(ox.toInt())
        val b = arrayListOf(oy.toInt())

        while(true){
            val angle = Math.toRadians(i)

            x = x0 + (rc-rm)*Math.cos(angle) + rd*Math.cos(((rc-rm).toDouble()/rm)*angle)
            y = y0 + (rc-rm)*Math.sin(angle) - rd*Math.sin(((rc-rm).toDouble()/rm)*angle)

            a.add(x.toInt())
            b.add(y.toInt())

            i += step

            if(i > laps*360){
                break
            }
        }

        g.drawPolyline(a.toIntArray(), b.toIntArray(), a.size)
    }

    override fun draw(g: Graphics2D) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g.drawImage(trochoidImage, (centerX-25*scale).toInt(), (centerY-25*scale).toInt(), (50*scale).toInt(), (50*scale).toInt(), null)
    }

    override fun update() {
        scale += scaleStep
        time++
    }

    override fun isFinish(): Boolean = time > lifetime
}