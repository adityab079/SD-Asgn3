package ramo.klevis;

import com.mortennobel.imagescaling.ResampleFilters;
import com.mortennobel.imagescaling.ResampleOp;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PredictionAdapter {

    private ArrayList<Model> models = new ArrayList<>();

    public void addModel(Model model){models.add(model);}

    private static BufferedImage scaledBufferedImage(Image img){
        return toBufferedImage(scale(toBufferedImage(img)));
    }

    static double[] augmentToScaleAndVectorize(Image img){
        BufferedImage scaledImg = scaledBufferedImage(img);
        return transformImageToOneDimensionalVector(scaledImg);
    }

    private static BufferedImage scale(BufferedImage imageToScale) {
        ResampleOp resizeOp = new ResampleOp(Config.IMG_WIDTH, Config.IMG_HEIGHT);
        resizeOp.setFilter(ResampleFilters.getLanczos3Filter());
        BufferedImage filter = resizeOp.filter(imageToScale, null);
        return filter;
    }

    private static BufferedImage toBufferedImage(Image img) {
        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    private static double[] transformImageToOneDimensionalVector(BufferedImage img) {

        double[] imageGray = new double[28 * 28];
        int w = img.getWidth();
        int h = img.getHeight();
        int index = 0;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                Color color = new Color(img.getRGB(j, i), true);
                int red = (color.getRed());
                int green = (color.getGreen());
                int blue = (color.getBlue());
                double v = 255 - (red + green + blue) / 3d;
                imageGray[index] = v;
                index++;
            }
        }
        return imageGray;
    }

    public int getPrediction(Image image, int type){
        LabeledImage labeledImage = new LabeledImage(0, PredictionAdapter.augmentToScaleAndVectorize(image));
        Model model = models.get(type);
        Config.LOGGER.info("Predicting via " + model.getClass().getName());
        return model.predict(labeledImage);
    }
}