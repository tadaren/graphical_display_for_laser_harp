import java.awt.Dimension
import javax.swing.JFrame

fun main(args: Array<String>) {
    val frame = JFrame("Graph")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.size = Dimension(1000, 500)
    val panel = MainPanel(1000, 500)
    frame.add(panel)

    frame.isVisible = true
}