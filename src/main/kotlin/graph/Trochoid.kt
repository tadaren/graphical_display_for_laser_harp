package graph

import util.gcd
import java.awt.Color
import java.awt.Graphics2D

class Trochoid(private val rm: Int, private val rc: Int, private val rd: Int): Drawable{

    private val x0: Int = (Math.floor(Math.random()*(500-2*rc))+ rc).toInt()
    private val y0: Int = (Math.floor(Math.random()*(500-2*rc))+ rc).toInt()

    private var ox: Int
    private var oy: Int

    private val laps: Int = rm/gcd(rc, rm)

    private val step = 1

    init {
        ox = x0 + rc - rm + rd
        oy = y0
    }

    override fun draw(g: Graphics2D) {
        g.color = Color.WHITE
        var i = 0.0
        var x: Int
        var y: Int
        while(true){
            val angle = Math.toRadians(i)
//            val angle = i

            x = (x0 + (rc-rm)*Math.cos(angle) + rd*Math.cos(((rc-rm).toDouble()/rm)*angle)).toInt()
            y = (y0 + (rc-rm)*Math.sin(angle) - rd*Math.sin(((rc-rm).toDouble()/rm)*angle)).toInt()

            g.drawLine(ox, oy, x, y)

            ox = x
            oy = y
            i += step

            if(i > laps*360){
                break
            }
        }
    }
}