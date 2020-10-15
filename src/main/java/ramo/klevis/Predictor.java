package ramo.klevis;

import java.awt.*;

public interface Predictor{
    int getPrediction(Image image, int type);
    void addModel(Model model);
}