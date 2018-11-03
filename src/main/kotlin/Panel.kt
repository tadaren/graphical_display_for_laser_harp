import graph.Graph
import graph.Trochoid
import java.awt.Color
import java.awt.Graphics
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.image.BufferedImage
import java.net.ServerSocket
import java.util.*
import javax.swing.JPanel
import java.net.InetSocketAddress
import javax.swing.SwingUtilities

class MainPanel(canvasWidth: Int, canvasHeight: Int): JPanel() {

    private var canvas: BufferedImage = BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_4BYTE_ABGR)

    private val drawableList = arrayListOf<Graph>()

    init{
        val g2 = canvas.createGraphics()
        g2.color = Color.BLACK
        g2.fillRect(0, 0, canvas.width, canvas.height)
        Thread {
//            val stdIn = Scanner(System.`in`)
            val server = ServerSocket()
            server.bind(InetSocketAddress("127.0.0.1", 8000))
            while(true) {
                val socket = server.accept()
                println("waiting")
                val stdIn = Scanner(socket.getInputStream())
                val num: Int
                try {
                    num = stdIn.nextInt()
                } catch(e: Exception) {
                    println("exception")
                    continue
                }
                println(num)

                synchronized(drawableList) {
                    for(i in 0..4) {
                        drawableList.add(
                                Trochoid(
                                        (Math.random() * 200).toInt(),
                                        (Math.random() * 200).toInt(),
                                        (Math.random() * 50 + 50).toInt(),
                                        0.1,
                                        80,
                                        0.01,
                                        (Math.random() * (canvas.width - 400) + 200).toInt(),
                                        (Math.random() * (canvas.height - 400) + 200).toInt(),
                                        when(num) {
                                            1 -> Color(
                                                    (Math.random() * 10).toInt(),
                                                    (Math.random() * 10).toInt(),
                                                    (Math.random() * 128 + 128).toInt(),
                                                    220
                                            )
                                            2 -> Color(
                                                    (Math.random() * 10).toInt(),
                                                    (Math.random() * 128 + 128).toInt(),
                                                    (Math.random() * 10).toInt(),
                                                    220
                                            )
                                            3 -> Color(
                                                    (Math.random() * 128 + 128).toInt(),
                                                    (Math.random() * 10).toInt(),
                                                    (Math.random() * 10).toInt(),
                                                    220
                                            )
                                            4 -> Color(
                                                    (Math.random() * 125 + 100).toInt(),
                                                    (Math.random() * 125 + 100).toInt(),
                                                    (Math.random() * 125 + 100).toInt(),
                                                    220
                                            )
                                            else -> {
                                                Color(1, 1, 1)
                                            }
                                        }
                                ))
                    }
                }
            }
        }.start()
        this.addComponentListener(object : ComponentAdapter(){
            override fun componentResized(e: ComponentEvent?) {
                super.componentResized(e)
                this@MainPanel.canvas = BufferedImage(this@MainPanel.width, this@MainPanel.height, BufferedImage.TYPE_4BYTE_ABGR)
            }
        })
        Thread{
            while(true) {
                Thread.sleep(50)
                SwingUtilities.invokeLater { this.repaint() }
            }
        }.start()
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2 = canvas.createGraphics()
        g2.color = Color(10, 10, 40)
        g2.fillRect(0, 0, canvas.width, canvas.height)

        synchronized(drawableList) {
            for(drawable in drawableList) {
                drawable.draw(g2)
                drawable.update()
            }
            drawableList.removeIf { it.isFinish() }
        }

        g.drawImage(canvas, 0, 0, this)

//        repaint()
    }
}