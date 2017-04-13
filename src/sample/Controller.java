package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Controller {
    @FXML
    private ImageView capImage;

    @FXML
    public void capture(){
        double screenH = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        double screenW = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        Rectangle border = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());      // Set Capture border
        BufferedImage screen = null;
        try {
            screen = new Robot().createScreenCapture(border);                               // Capture!
        }catch (AWTException e){
            e.printStackTrace();
        }
        capImage.setFitHeight(screenH/1.5);
        capImage.setFitWidth(screenW/1.5);
        capImage.setImage(SwingFXUtils.toFXImage(screen, null));
    }
}
