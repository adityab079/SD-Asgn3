package ramo.klevis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class UIPanel extends JPanel implements LogSubscriber{

    private DrawArea drawArea;
    private JPanel drawPanel;
    private JPanel outputPanel;
    private JLabel predictionLabel;
    private JTextArea logArea;

    private final Predictor predictionAdapter;

    public UIPanel(Predictor predictionAdapter){
        this.predictionAdapter = predictionAdapter;
        setLayout(new BorderLayout());
        initDrawPanel();
        initButtonsPanel();
        initOutputPanel();
    }

    private void initDrawPanel(){
        drawArea = new DrawArea();
        drawPanel = new JPanel(new BorderLayout());
        drawPanel.add(drawArea, BorderLayout.CENTER);
        add(drawPanel, BorderLayout.CENTER);

        drawPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                drawArea.setImage(null);
                drawArea.repaint();
                drawArea.updateUI();
            }
        });
    }

    private void initButtonsPanel(){
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        JButton nnPredictorButton = new JButton(Config.PREDICT_NN_BTN_TEXT);
        JButton cnnPredictorButton = new JButton(Config.PREDICT_CNN_BTN_TEXT);
        JButton clear = new JButton(Config.CLEAR_BTN_TEXT);

        addNNPredictorListener(nnPredictorButton);
        addCNNPredictorListener(cnnPredictorButton);
        clear.addActionListener(e -> {
            drawArea.setImage(null);
            drawArea.repaint();
            drawPanel.updateUI();
            updatePredictionLabel("-");
            updateOutputPanelUI();
        });

        buttonsPanel.add(nnPredictorButton);
        buttonsPanel.add(cnnPredictorButton);
        buttonsPanel.add(clear);

        add(buttonsPanel, BorderLayout.NORTH);
    }

    private void initOutputPanel(){

        JPanel logPanel = new JPanel(new BorderLayout());
        logArea = new JTextArea("", 5, 100);
        logArea.setLineWrap(true);
        JScrollPane pane = new JScrollPane(logArea);

        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        logPanel.add(pane, BorderLayout.CENTER);

        outputPanel = new JPanel();
        outputPanel.setBorder(
                BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Prediction"));
        predictionLabel = new JLabel();
        updatePredictionLabel("-");
        outputPanel.add(predictionLabel);
        logPanel.add(outputPanel, BorderLayout.EAST);

        add(logPanel, BorderLayout.SOUTH);
    }

    private void updatePredictionLabel(String txt){
        predictionLabel.setText(txt);
        predictionLabel.setForeground(Color.RED);
        predictionLabel.setFont(Config.PREDICTION_LABEL_FONT);
    }

    private void updateOutputPanelUI() {
        outputPanel.removeAll();
        outputPanel.add(predictionLabel);
        outputPanel.updateUI();
    }

    private void addNNPredictorListener(JButton nnPredictorButton){
        nnPredictorButton.addActionListener(e -> {
            Image drawImage = drawArea.getImage();
            int predict = predictionAdapter.getPrediction(drawImage, Config.NN);
            updatePredictionLabel(""+predict);
            updateOutputPanelUI();
        });
    }

    private void addCNNPredictorListener(JButton cnnPredictorButton){
        cnnPredictorButton.addActionListener(e -> {
            Image drawImage = drawArea.getImage();
            int predict = predictionAdapter.getPrediction(drawImage, Config.CNN);
            updatePredictionLabel(""+predict);
            updateOutputPanelUI();
        });
    }

    @Override
    public void newLogPublished(String formattedLog) {
        String text = logArea.getText();
        logArea.setText(text + formattedLog);
    }
}
