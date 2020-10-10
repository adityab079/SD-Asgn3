package ramo.klevis;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.factory.Nd4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by agibsonccc on 9/16/15.
 */
public class ConvolutionalNeuralNetwork implements Model {

    private MultiLayerNetwork preTrainedModel;

    private static ConvolutionalNeuralNetwork cnn;

    private ConvolutionalNeuralNetwork(){}

    public static ConvolutionalNeuralNetwork getInstance(){
        if(cnn == null){
            cnn = new ConvolutionalNeuralNetwork();
        }
        return cnn;
    }

    public void init() throws IOException {
        if (preTrainedModel == null) {
            Config.LOGGER.info("Loading the Neural Network from saved model ... ");
            preTrainedModel = ModelSerializer.restoreMultiLayerNetwork(new File(Config.TRAINED_MODEL_CNN_FILE));
            Config.LOGGER.info("Loading from saved model is done");
        }
    }

    public int predict(LabeledImage labeledImage) {
        double[] pixels = labeledImage.getPixels();
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = pixels[i] / 255d;
        }
        int[] predict = preTrainedModel.predict(Nd4j.create(pixels));

        Config.LOGGER.info("Prediction: " + predict[0]);

        return predict[0];
    }
}