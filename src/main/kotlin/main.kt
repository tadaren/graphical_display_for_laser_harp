import java.awt.Dimension
import javax.swing.JFrame

fun main(args: Array<String>) {
    val frame = JFrame("Graph")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.size = Dimension(500, 500)
    frame.add(MainPanel())

    frame.isVisible = true
}