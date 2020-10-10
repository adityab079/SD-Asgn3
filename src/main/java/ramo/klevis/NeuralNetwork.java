package ramo.klevis;

/*
  Created by klevis.ramo on 11/27/2017.
 */

import org.apache.spark.ml.classification.MultilayerPerceptronClassificationModel;
public class NeuralNetwork implements Model {

    private MultilayerPerceptronClassificationModel model;

    private static NeuralNetwork nn;

    private NeuralNetwork(){}

    public static NeuralNetwork getInstance(){
        if(nn == null){
            nn = new NeuralNetwork();
        }
        return nn;
    }

    public void init() {
        if (model == null) {
            Config.LOGGER.info("Loading the Neural Network from saved model ... ");
            model = MultilayerPerceptronClassificationModel.load(Config.TRAINED_MODEL_NN_FILE);
            Config.LOGGER.info("Loading from saved model is done");
        }
    }

    public int predict(LabeledImage labeledImage) {
        double predict = model.predict(labeledImage.getFeatures());

        Config.LOGGER.info("Prediction: " + (int) predict);
        return (int) predict;
    }
}
