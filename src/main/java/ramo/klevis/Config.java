package ramo.klevis;


import java.awt.*;
import java.util.logging.Logger;

public class Config {

    public static final String INPUT_IMAGE_PATH = "resources/train-images.idx3-ubyte";
    public static final String INPUT_LABEL_PATH = "resources/train-labels.idx1-ubyte";
    public static final String PREDICT_NN_BTN_TEXT = "Predict (NN)";
    public static final String PREDICT_CNN_BTN_TEXT = "Predict (CNN)";
    public static final String CLEAR_BTN_TEXT = "Clear";
    public static final String HADOOP_2_8_1 = "resources/winutils-master/hadoop-2.8.1";
    public static final String TRAINED_MODEL_CNN_FILE = "./resources/cnnTrainedModels/bestModel.bin";
    public static final String TRAINED_MODEL_NN_FILE = "./resources/nnTrainedModels/ModelWith60000";

    public static final int IMG_WIDTH = 28;
    public static final int IMG_HEIGHT = 28;

    public static final Font PREDICTION_LABEL_FONT = new Font("SansSerif", Font.BOLD, 100);
    public static final Font DIALOG_FONT = new Font("Dialog", Font.BOLD, 18);

    final static Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static final int FRAME_WIDTH = 1200;
    public static final int FRAME_HEIGHT = 628;

    public static final int CNN = 0;
    public static final int NN = 1;
}
