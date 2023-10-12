package swing;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QueryApplication {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                OLAPQueryApp app = new OLAPQueryApp();
                app.setVisible(true);
            }
        });
    }
}
