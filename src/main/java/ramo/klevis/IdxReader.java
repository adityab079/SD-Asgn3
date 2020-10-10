package ramo.klevis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class IdxReader {


    public static final int VECTOR_DIMENSION = 784; //square 28*28 as from data set -> array 784 items

    /**
     * @param size
     * @return
     */
    public static List<LabeledImage> loadData(final int size) {
        return getLabeledImages(size);
    }

    private static List<LabeledImage> getLabeledImages(final int amountOfDataSet) {

        final List<LabeledImage> labeledImageArrayList = new ArrayList<>(amountOfDataSet);

        try (FileInputStream inImage = new FileInputStream(Config.INPUT_IMAGE_PATH);
             FileInputStream inLabel = new FileInputStream(Config.INPUT_LABEL_PATH)) {

            // just skip the amount of a data
            // see the test and description for dataset
            inImage.skip(16);
            inLabel.skip(8);
            Config.LOGGER.config("Available bytes in inputImage stream after read: " + inImage.available());
            Config.LOGGER.config("Available bytes in inputLabel stream after read: " + inLabel.available());

            //empty array for 784 pixels - the image from 28x28 pixels in a single row
            double[] imgPixels = new double[VECTOR_DIMENSION];

            Config.LOGGER.info("Creating ADT filed with Labeled Images ...");
            long start = System.currentTimeMillis();
            for (int i = 0; i < amountOfDataSet; i++) {

                if (i % 1000 == 0) {
                    Config.LOGGER.info("Number of images extracted: " + i);
                }
                //it fills the array of pixels
                for (int index = 0; index < VECTOR_DIMENSION; index++) {
                    imgPixels[index] = inImage.read();
                }
                //it creates a label for that
                int label = inLabel.read();
                //it creates a compound object and adds them to a list
                labeledImageArrayList.add(new LabeledImage(label, imgPixels));
            }
            Config.LOGGER.info("Time to load LabeledImages in seconds: " + ((System.currentTimeMillis() - start) / 1000d));
        } catch (Exception e) {
            Config.LOGGER.severe("Something went wrong: \n" + e);
            throw new RuntimeException(e);
        }

        return labeledImageArrayList;
    }

}