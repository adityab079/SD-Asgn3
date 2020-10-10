package ramo.klevis;

import javax.swing.*;
import java.awt.*;

/**
 * Created by klevis.ramo on 11/29/2017.
 */
public class ProgressBar {

    private final JFrame mainFrame;
    private JProgressBar progressBar;
    private boolean unDecorated = false;

    public ProgressBar(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        progressBar = createProgressBar(mainFrame);
    }

    public ProgressBar(JFrame mainFrame, boolean unDecorated) {
        this.mainFrame = mainFrame;
        progressBar = createProgressBar(mainFrame);
        this.unDecorated = unDecorated;
    }

    public void showProgressBar(String msg) {
        SwingUtilities.invokeLater(() -> {
            if (unDecorated) {
                mainFrame.setLocationRelativeTo(null);
                mainFrame.setUndecorated(true);
            }
            progressBar = createProgressBar(mainFrame);
            progressBar.setString(msg);
            progressBar.setStringPainted(true);
            progressBar.setIndeterminate(true);
            progressBar.setVisible(true);
            mainFrame.add(progressBar, BorderLayout.WEST);
            if (unDecorated) {
                mainFrame.pack();
                mainFrame.setVisible(true);
            }
            mainFrame.repaint();
        });
    }


    private JProgressBar createProgressBar(JFrame mainFrame) {
        JProgressBar jProgressBar = new JProgressBar(JProgressBar.HORIZONTAL);
        jProgressBar.setVisible(false);
        mainFrame.add(jProgressBar, BorderLayout.WEST);
        return jProgressBar;
    }

    public void setVisible(boolean visible) {

        progressBar.setVisible(visible);
        mainFrame.dispose();
    }
}
