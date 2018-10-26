import graph.Drawable
import graph.Trochoid
import java.awt.Color
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.util.*
import javax.swing.JPanel
import javax.swing.SwingUtilities

class MainPanel: JPanel() {

    private val canvas: BufferedImage = BufferedImage(500, 500, BufferedImage.TYPE_4BYTE_ABGR)

    private val drawableList = arrayListOf<Drawable>()

    init{
//        println("MainPanel Initialized")
        val g2 = canvas.createGraphics()
        g2.color = Color.BLACK
        g2.fillRect(0, 0, 500, 500)
        Thread {
            val stdIn = Scanner(System.`in`)
            while(true) {
                val num = stdIn.nextInt()
                println(num)

                drawableList.add(Trochoid(49, 199, 30))

                for(drawable in drawableList) {
                    drawable.draw(canvas.createGraphics())
                }
                SwingUtilities.invokeLater {
                    this.repaint()
                }
            }
        }.start()
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)

        g.drawImage(canvas, 0, 0, this)
    }
}