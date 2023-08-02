import java.awt.*;
import java.awt.event.*;

public class Simulation extends Frame {
    private Scene scene;

    public Simulation(){
        setBounds(500,500,600,500);
        setResizable(true);
        setVisible(true);
        setTitle("Sudaranje");
        populateWindow();

        setActions();
    }

    private void populateWindow(){
        scene = new Scene(this);
        add(scene,BorderLayout.CENTER);
    }

    private void setActions(){

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                scene.finishAll();
                dispose();
            }
        });

    }
}
