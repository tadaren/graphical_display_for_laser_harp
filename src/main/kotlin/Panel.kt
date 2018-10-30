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
                    drawableList.add(Trochoid(50, 190, 30, 0.1, 80, 0.01))
                }
            }
        }.start()
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2 = canvas.createGraphics()
        g2.color = Color.BLACK
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