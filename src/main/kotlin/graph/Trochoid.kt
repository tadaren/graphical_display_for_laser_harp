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
        val x0: Double = (Math.floor(Math.random()*(500-2*rc))+ rc)
        val y0: Double = (Math.floor(Math.random()*(500-2*rc))+ rc)

        var ox: Double
        var oy: Double

        val laps: Int = rm/gcd(rc, rm)

        val step = 5

        ox = (x0 + rc - rm + rd)
        oy = y0

        val g = trochoidImage.createGraphics()

        g.stroke = BasicStroke(2F)
        g.color = Color.CYAN
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        var i = 0.0
        var x: Double
        var y: Double
        while(true){
            val angle = Math.toRadians(i)

            x = x0 + (rc-rm)*Math.cos(angle) + rd*Math.cos(((rc-rm).toDouble()/rm)*angle)
            y = y0 + (rc-rm)*Math.sin(angle) - rd*Math.sin(((rc-rm).toDouble()/rm)*angle)

            g.drawLine(ox.toInt(), oy.toInt(), x.toInt(), y.toInt())

            ox = x
            oy = y
            i += step

            if(i > laps*360){
                break
            }
        }
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