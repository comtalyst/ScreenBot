package sample;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.EventHandler;
import java.util.concurrent.CountDownLatch;
import javafx.scene.control.Label;

public class Controller {
    @FXML
    Label label;

    boolean state = true;

    Service<Void> looper = new Service<Void>() {                                                        // Create service to loop
        protected Task<Void> createTask() {                                                             // in the background
            return new Task<Void>() {
                public Void call() throws Exception{
                    double screenH = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
                    double screenW = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
                    Rectangle border = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());      // Set Capture border
                    BufferedImage screen = null;                                                        // Prepare image vessel
                    int a = 0;
                    final CountDownLatch latch = new CountDownLatch(1);                                 // Prepare prototype latch
                    while (state) {
                        a++;
                        final int b = a;
                        final CountDownLatch latch2 = latch;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("Shit");
                                try{
                                    editFx(Integer.toString(b));
                                }finally{
                                    latch2.countDown();
                                }

                            }
                        });
                        latch2.await();
                        try {
                            screen = new Robot().createScreenCapture(border);                           // Continuous Capturing
                        } catch (AWTException e) {
                            e.printStackTrace();
                        }
                        // Find average color of screen (this function will be added later)
                    }
                    return null;
                }
            };
        }
    };

    public void start(){
        if(looper.getState().equals(Worker.State.RUNNING)){
            looper.cancel();
        }
        looper.restart();
    }
    private void editFx(String s){
        System.out.println('>' + s);
        label.setText(s);
    }

    public void stop(){
        if(looper.getState().equals(Worker.State.RUNNING)){
            looper.cancel();
        }
        System.out.println("Stop Initiated");
    }
}
