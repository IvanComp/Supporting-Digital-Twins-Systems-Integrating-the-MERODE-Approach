package testing;

/**
 * Created by Arturo Mantinetti
 */

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.imageio.ImageIO;
import javax.sound.sampled.Line;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import dao.MerodeLogger;

import net.miginfocom.swing.MigLayout;
import validation.ErrorWindow;
import validation.MermaidImageGallery;
import validation.XMLProperties;

public class TestErrorWindow {
    final static String IMAGES_URL = "/images";

    static String USER_DIRECTORY = "user.dir";
    static String FILE_SEPARATOR = "file.separator";

    static String MISSING_STATE = "Missing State";
    static String MISSING_TRANSITION = "Missing Transition";
    static String SEE_ALL = "See all";
    static String CANCEL = "Cancel";
    static String windowDialog = "";



    public static URL getIconURL(Class<ErrorWindow> c, String fileName) {
        return c.getResource(fileName);
    }

    public static void main(String errors, String title) throws Exception {

        String imgURL = "think.png";


        UIManager.put("OptionPane.background", Color.white);
        UIManager.put("Panel.background", Color.white);

        System.out.println("JOptionPane");
        JOptionPane.showMessageDialog(null, errors, title,
                JOptionPane.ERROR_MESSAGE);
    }

    public static void main2(String errorType, String head, String error) throws Exception {

        String windowTitle = "";
        String buttonLabel = "";



        if(errorType.equals(MISSING_STATE)){
            windowTitle = MISSING_STATE;

            buttonLabel = "See States";


            windowDialog = "<html><b>HINT: " + MISSING_STATE + "</b><br><br>"
                    + "According to the diagram, the states <b><font color = 'red'> "
                    + error + "  </b></font> are missing on<br>"
                    + "<b><font color = '#FF8C00'>"
                    + head
                    + "</b></font> "
                    + "<br></html>";
        }

        if(errorType.equals(MISSING_TRANSITION)){
            windowTitle = MISSING_TRANSITION;

            buttonLabel = "See Transitions";


            windowDialog = "<html><b>HINT: " + MISSING_STATE + "</b><br><br>"
                    + "According to the diagram, the transitions <b><font color = 'red'> "
                    + error + "  </b></font> are missing on<br>"
                    + "<b><font color = '#FF8C00'>"
                    + head
                    + "</b></font> "
                    + "<br></html>";
        }

        final String errorTypeFinal = errorType;
        final String headFinal = head;
        
        //sofia:
        error = error + "," + "Emitiendo_Emitiendo";
        
        final String errorFinal = error;


        
        // This happens when See Transitions is clicked.
        ActionListener ActionListenerPopupImage = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                Object source = actionEvent.getSource();
                JButton src = (JButton) source;
                String userDir = System.getProperty(USER_DIRECTORY);
                String fileSeparator = System.getProperty(FILE_SEPARATOR);
                userDir = userDir.replace(fileSeparator, "/");
                String imgDir = (userDir + IMAGES_URL).replace(fileSeparator, "/");
                String[] fileNames = new File(imgDir).list();

                if( errorTypeFinal.equals(MISSING_STATE)){
                    String imgName = "";
                    String strFind = "FSM_" + headFinal.toUpperCase();
                    for(int i=0; i<fileNames.length; i++){
                        if(fileNames[i].contains(strFind))
                            imgName = fileNames[i];
                    }

                    float minX = Float
                            .valueOf(imgName.substring(
                                    imgName.lastIndexOf("_") + 1,
                                    imgName.lastIndexOf("x")));
                    float minY = Float.valueOf(imgName.substring(
                            imgName.lastIndexOf("x") + 1,
                            imgName.lastIndexOf(".png")));

                    File imgFile = new File(imgDir + "/" + imgName);
                    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

                    try {
                        BufferedImage bimage = ImageIO.read(imgFile);
                        Graphics2D g2d = bimage.createGraphics();
                        Color transparent = new Color(0f, 0f, 1f, 1f);
                        g2d.setColor(transparent);
                        g2d.setComposite(AlphaComposite.Src);

                        String[] states = errorFinal.split(",");

                        for (String state: states) {
                            float[] stateCoordinate = XMLProperties.getStateCoordinates(state, headFinal.toUpperCase());

                            float stateX = stateCoordinate[0];
                            float stateY = stateCoordinate[1];

                            float x = stateX - minX;
                            float y = stateY - minY;
                            
                            float stateWidth = XMLProperties.getStateSize(state, headFinal.toUpperCase())[0];
                            float stateHeight = XMLProperties.getStateSize(state, headFinal.toUpperCase())[1];

                            g2d.setStroke(new BasicStroke(5.0f)); // Set line width
                            Shape circleObj = new Ellipse2D.Float(x, y, stateWidth,
                                    stateHeight);
                            g2d.setColor(Color.RED);
                            g2d.draw(circleObj);

                        }

                        g2d.dispose();

                        JPanel panel = new JPanel(new MigLayout());
                        JLabel explanation = new JLabel(windowDialog);
                        String errorIcon = "think.png";
                        ImageIcon imgIcon = new javax.swing.ImageIcon(getIconURL(
                                validation.ErrorWindow.class,
                                "errorsOptionPaneImages/" + errorIcon));
                        explanation.setIcon(imgIcon);
                        panel.add(explanation, "align center, wrap");
                        panel.add(new JLabel(new ImageIcon(bimage)), "align center, wrap 15");

                        src.getRootPane().getParent().setVisible(false);
                        final JDialog dlg = new JDialog();
                        dlg.add(new JScrollPane(panel));
                        dlg.setTitle("Visual explanation");
                        dlg.setModal(true);
                        JButton feedbackOK = new JButton("Intended behavior?");
                        JButton feedbackNotOK = new JButton("No, model needs a fix.");
                        feedbackOK.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e)
                            {
                                MerodeLogger.log.append("Feedback = Intended behavior \n");
                                MerodeLogger.log.getCaret().setVisible(true);
                                dlg.setVisible(false);
                                dlg.dispose();
                            }
                        });
                        feedbackNotOK.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e)
                            {
                                MerodeLogger.log.append("Feedback = Needs a fix \n");
                                MerodeLogger.log.getCaret().setVisible(true);
                                dlg.setVisible(false);
                                dlg.dispose();
                            }
                        });
                        panel.add(feedbackOK, "split 2, align center");
                        panel.add(feedbackNotOK, "align center");
                        double errorDialogHeight =
                                explanation.getPreferredSize().getHeight() + bimage.getHeight()
                                        + 100; // the layout gaps
                        double errorDialogWidth =
                                (bimage.getWidth() > explanation.getPreferredSize().getWidth()
                                        ? bimage.getWidth()
                                        : explanation.getPreferredSize().getWidth())
                                        + 60; // the layout gaps


                        if (errorDialogWidth > dim.width
                                || errorDialogHeight > dim.height){
                            dlg.setSize(new Dimension (dim.width/4*3,
                                    dim.height/4*3 ) );
                        } else {
                            dlg.setSize(new Dimension ((int)errorDialogWidth,
                                    (int)errorDialogHeight ) );
                        }
                        dlg.setLocationRelativeTo(null);
                        dlg.setVisible(true);
                        dlg.toFront();
                        dlg.requestFocus();
                        System.out.println("dialog H: " + dlg.getHeight() + " dialog W: " + dlg.getWidth());


                    }
                    catch (Exception e){

                    }

                } else if( errorTypeFinal.equals(MISSING_TRANSITION)){
                    String imgName = "";
                    String strFind = "FSM_" + headFinal.toUpperCase();
                    for(int i=0; i<fileNames.length; i++){
                        if(fileNames[i].contains(strFind))
                            imgName = fileNames[i];
                    }

                    float minX = Float
                            .valueOf(imgName.substring(
                                    imgName.lastIndexOf("_") + 1,
                                    imgName.lastIndexOf("x")));
                    float minY = Float.valueOf(imgName.substring(
                            imgName.lastIndexOf("x") + 1,
                            imgName.lastIndexOf(".png")));

//                    System.out.printf("*** minx es: %.2f\n", minX);
//                    System.out.printf("*** miny es: %.2f\n", minY);
//                    System.out.println("LALALALALA");
//                    System.out.println("headfinal: " + headFinal);
                    
                    File imgFile = new File(imgDir + "/" + imgName);
                    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

                    try {
                        BufferedImage bimage = ImageIO.read(imgFile);
                        Graphics2D g2d = bimage.createGraphics();
                        Color transparent = new Color(0f, 0f, 1f, 1f);
                        g2d.setColor(transparent);
                        g2d.setComposite(AlphaComposite.Src);

                        
                        
                        
                        // sofia:
                        System.out.println("ARTURO: " + errorFinal);
                        
                        String[] transitions = errorFinal.split(",");

                        // Paint all the transitions in the array
                        for (String transition: transitions) {

                            // These are the states from which we need to draw the line:
                        	String[] states = transition.split("_");

                            float dx1 = 0;
                            float dx2 = 0;
                            float dy1 = 0;
                            float dy2 = 0;

                            // Passes state name and object name to get coordinates
                            float[] stateCoordinate1 = XMLProperties.getStateCoordinates(states[0], headFinal.toUpperCase());
                            float[] stateCoordinate2 = XMLProperties.getStateCoordinates(states[1], headFinal.toUpperCase());


                            float stateX1 = stateCoordinate1[0];
                            float stateY1 = stateCoordinate1[1];

                            float stateX2 = stateCoordinate2[0];
                            float stateY2 = stateCoordinate2[1];

                            float x1 = stateX1 - minX;
                            float y1 = stateY1 - minY;

                            float x2 = stateX2 - minX;
                            float y2 = stateY2 - minY;

                            float stateWidth1 = XMLProperties.getStateSize(states[0], headFinal.toUpperCase())[0];
                            float stateHeight1 = XMLProperties.getStateSize(states[0], headFinal.toUpperCase())[1];
                            float stateWidth2 = XMLProperties.getStateSize(states[1], headFinal.toUpperCase())[0];
                            float stateHeight2 = XMLProperties.getStateSize(states[1], headFinal.toUpperCase())[1];

                            g2d.setColor(Color.RED);
                            g2d.setStroke(new BasicStroke(3.0f));

                            if(x1==x2 && y1==y2){
                                dx1 = x1 - stateWidth1;
                                dy1 = y1 - stateHeight1;
                                float width = stateWidth1 * 3;
                                float height = stateHeight1 * 3;
                                Shape circleObj = new Ellipse2D.Float(dx1, dy1, width, height);
                                g2d.draw(circleObj);

                            }else{
                                dx1 = x1 + (stateWidth1 / 2) ;
                                dx2 = x2 + (stateWidth2 / 2) ;
                                dy1 = y1 + (stateHeight1 / 2);
                                dy2 = y2 + (stateHeight2 / 2);

                                Line2D line = new Line2D.Double(dx1,dy1,dx2,dy2);
                                g2d.draw(line);
                            }

                        }// For end

                        g2d.dispose();

                        // Y el JFrame o JWindow?
                        JPanel panel = new JPanel(new MigLayout());
                        JLabel explanation = new JLabel(windowDialog);
                        String errorIcon = "think.png";
                        ImageIcon imgIcon = new javax.swing.ImageIcon(getIconURL(
                                validation.ErrorWindow.class,
                                "errorsOptionPaneImages/" + errorIcon));
                        explanation.setIcon(imgIcon);
                        panel.add(explanation, "align center, wrap");
                        panel.add(new JLabel(new ImageIcon(bimage)), "align center, wrap 15");

                        src.getRootPane().getParent().setVisible(false);
                        final JDialog dlg = new JDialog();
                       
                        dlg.add(new JScrollPane(panel));
                        dlg.setTitle("Visual explanation");
                        dlg.setModal(true);
                        
                        JButton feedbackOK = new JButton("Intended behavior?");
                        JButton feedbackNotOK = new JButton("No, model needs a fix.");
                        feedbackOK.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                MerodeLogger.log.append("Feedback = Intended behavior \n");
                                MerodeLogger.log.getCaret().setVisible(true);
                                dlg.setVisible(false);
                                dlg.dispose();
                            }
                        });
                        feedbackNotOK.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                MerodeLogger.log.append("Feedback = Needs a fix \n");
                                MerodeLogger.log.getCaret().setVisible(true);
                                dlg.setVisible(false);
                                dlg.dispose();
                            }
                        });
                        panel.add(feedbackOK, "split 2, align center");
                        panel.add(feedbackNotOK, "align center");
                        
                        double errorDialogHeight =
                                explanation.getPreferredSize().getHeight() + bimage.getHeight()
                                        + 100; // the layout gaps
                        double errorDialogWidth =
                                (bimage.getWidth() > explanation.getPreferredSize().getWidth()
                                        ? bimage.getWidth()
                                        : explanation.getPreferredSize().getWidth())
                                        + 60; // the layout gaps


                        if (errorDialogWidth > dim.width
                                || errorDialogHeight > dim.height){
                            dlg.setSize(new Dimension (dim.width/4*3,
                                    dim.height/4*3 ) );
                        } else {
                            dlg.setSize(new Dimension ((int)errorDialogWidth,
                                    (int)errorDialogHeight ) );
                        }
                        dlg.setLocationRelativeTo(null);
                        dlg.setVisible(true);
                        dlg.toFront();
                        dlg.requestFocus();
                        System.out.println("dialog H: " + dlg.getHeight() + " dialog W: " + dlg.getWidth());


                    }
                    catch (Exception e){

                    }

                }
            }
        };

        JButton b1 = new JButton(buttonLabel);
        b1.addActionListener(ActionListenerPopupImage);
        
        JButton b2 = new JButton(SEE_ALL);
        b2.addActionListener(ActionListenerPopupImage);
        
        final JButton b3 = new JButton(CANCEL);
        b3.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                b3.getRootPane().getParent().setVisible(false);
            }
        });
        Object[] options = { b1, b2, b3 };
        ImageIcon icon = null;
        String errorIcon = "think.png";// SEE_EDG.equals(buttonLabel)?
        // "think2.jpg" :
        // "infoMessageImage.jpg";
        icon = new javax.swing.ImageIcon(getIconURL(
                validation.ErrorWindow.class,
                "errorsOptionPaneImages/" + errorIcon));
        UIManager.put("OptionPane.background", Color.white);
        UIManager.put("Panel.background", Color.white);


        JOptionPane.showOptionDialog(null, windowDialog, windowTitle,
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, icon,
                options, options[0]);


    }

}
