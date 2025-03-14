import core.DisplayWindow;
import org.opencv.core.Core;

public class RunMe {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // --== Load an image to filter ==--
        DisplayWindow.showFor("imgs/20250307_092431.jpg", 800, 600, "CardFilter");

        // --== Determine your input interactively with menus ==--
//        DisplayWindow.getInputInteractively(800,600);
    }
}