package ramo.klevis;

import org.apache.spark.sql.SparkSession;

import javax.swing.*;

/**
 * Created by klevis.ramo on 11/24/2017.
 */
public class Main extends JFrame {

    public static void main(String[] args) throws Exception {
        SparkNNGUIFacade sparkNNGUIFacade = new SparkNNGUIFacade();

        SparkNNGUIFacade.setHadoopHomeEnvironmentVariable();
        sparkNNGUIFacade.setSystemLookAndFeel();
        sparkNNGUIFacade.whipUpUI();
    }
}
