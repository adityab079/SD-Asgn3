package ramo.klevis;

import org.apache.spark.sql.SparkSession;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by klevis.ramo on 11/24/2017.
 */
public class Main extends JFrame {


    private NeuralNetwork nn;
    private ConvolutionalNeuralNetwork cnn;
    private SparkSession sparkSession;
    private PredictionAdapter predictionAdapter;

    public void initSession() throws Exception {
        initSparkSession();
        nn = NeuralNetwork.getInstance();
        nn.init();
        cnn = ConvolutionalNeuralNetwork.getInstance();
        cnn.init();

        predictionAdapter.addModel(cnn);
        predictionAdapter.addModel(nn);
    }

    public static void main(String[] args) throws Exception {

        SwingLogHandler swingLogHandler = new SwingLogHandler();
        setHadoopHomeEnvironmentVariable();

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        UIManager.put("Button.font", new FontUIResource(Config.DIALOG_FONT));
        UIManager.put("ProgressBar.font", new FontUIResource(new Font("Dialog", Font.BOLD, 18)));

        Main mainFrame = new Main();
        mainFrame.predictionAdapter = new PredictionAdapter();
        UIPanel mainPanel = new UIPanel(mainFrame.predictionAdapter);
        mainFrame.add(mainPanel, BorderLayout.CENTER);
        swingLogHandler.addLogSubscriber(mainPanel);
        mainFrame.setVisibleWithSystemSettings();


        Config.LOGGER.addHandler(swingLogHandler);

        Config.LOGGER.info("Application is Starting ... ");

        ProgressBar progressBar = new ProgressBar(new JFrame(), true);
        progressBar.showProgressBar("Collecting data this may take several seconds!");

        mainFrame.initSession();
        progressBar.setVisible(false);

        Config.LOGGER.info("Application Started ... ");
    }

    public void setVisibleWithSystemSettings(){
        setTitle("Digit Recognizer");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(Config.FRAME_WIDTH, Config.FRAME_HEIGHT);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });
        ImageIcon imageIcon = new ImageIcon("icon.png");
        setIconImage(imageIcon.getImage());

        setVisible(true);
    }


    public static void setHadoopHomeEnvironmentVariable() throws Exception {
        HashMap<String, String> hadoopEnvSetUp = new HashMap<>();
        hadoopEnvSetUp.put("HADOOP_HOME", new File(Config.HADOOP_2_8_1).getAbsolutePath());
        Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
        Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
        theEnvironmentField.setAccessible(true);
        Map<String, String> env = (Map<String, String>) theEnvironmentField.get(null);
        env.clear();
        env.putAll(hadoopEnvSetUp);
        Field theCaseInsensitiveEnvironmentField = processEnvironmentClass.getDeclaredField("theCaseInsensitiveEnvironment");
        theCaseInsensitiveEnvironmentField.setAccessible(true);
        Map<String, String> cienv = (Map<String, String>) theCaseInsensitiveEnvironmentField.get(null);
        cienv.clear();
        cienv.putAll(hadoopEnvSetUp);
    }

    private void initSparkSession() {
        if (sparkSession == null) {
            sparkSession = SparkSession.builder()
                    .master("local[*]")
                    .appName("Digit Recognizer")
                    .getOrCreate();
        }

        sparkSession.sparkContext().setCheckpointDir("checkPoint");
    }
}
