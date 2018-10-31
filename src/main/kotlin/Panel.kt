import graph.Graph
import graph.Trochoid
import java.awt.Color
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.util.*
import javax.swing.JPanel

class MainPanel(private val canvasWidth: Int, private val canvasHeight: Int): JPanel() {

    private val canvas: BufferedImage = BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_4BYTE_ABGR)

    private val drawableList = arrayListOf<Graph>()

    init{
        val g2 = canvas.createGraphics()
        g2.color = Color.BLACK
        g2.fillRect(0, 0, canvasWidth, canvasHeight)
        Thread {
            val stdIn = Scanner(System.`in`)
            while(true) {
                val num: Int
                try {
                    num = stdIn.nextInt()
                } catch(e: Exception) {
                    continue
                }
                println(num)

                synchronized(drawableList) {
                    drawableList.add(
                            Trochoid(
                                    (Math.random()*200).toInt(),
                                    (Math.random()*200).toInt(),
                                    (Math.random()*50+50).toInt(),
                                    0.1,
                                    80,
                                    0.01,
                                    (Math.random()*(canvasWidth-400)+200).toInt(),
                                    (Math.random()*(canvasHeight-400)+200).toInt(),
                                    when(num){
                                        1 -> Color(
                                                (Math.random()*10).toInt(),
                                                (Math.random()*10).toInt(),
                                                (Math.random()*128+128).toInt(),
                                                220
                                        )
                                        2 -> Color(
                                                (Math.random()*10).toInt(),
                                                (Math.random()*128+128).toInt(),
                                                (Math.random()*10).toInt(),
                                                220
                                        )
                                        3 -> Color(
                                                (Math.random()*128+128).toInt(),
                                                (Math.random()*10).toInt(),
                                                (Math.random()*10).toInt(),
                                                220
                                        )
                                        4 -> Color(
                                                (Math.random()*125+100).toInt(),
                                                (Math.random()*125+100).toInt(),
                                                (Math.random()*125+100).toInt(),
                                                220
                                        )
                                        else -> {
                                            Color(1,1,1)
                                        }
                                    }
                            ))
                }
            }
        }.start()
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2 = canvas.createGraphics()
        g2.color = Color(10, 10, 40)
        g2.fillRect(0, 0, canvasWidth, canvasHeight)

        synchronized(drawableList) {
            for(drawable in drawableList) {
                drawable.draw(g2)
                drawable.update()
            }
            drawableList.removeIf { it.isFinish() }
        }

        g.drawImage(canvas, 0, 0, this)

        repaint()
    }
}