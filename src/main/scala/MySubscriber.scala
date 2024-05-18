import me.shadaj.scalapy.py
import me.shadaj.scalapy.py.SeqConverters

object MySubscriber {
  def main(args: Array[String]): Unit = {
    // Add the path to the Python script to the Python path
    py.Dynamic.global.sys.path.append("src/main/python")

    // Import the Python module
    val subscriberModule = py.module("subscriber")

    // Call the main function of the Python script
    subscriberModule.main()
  }
}
