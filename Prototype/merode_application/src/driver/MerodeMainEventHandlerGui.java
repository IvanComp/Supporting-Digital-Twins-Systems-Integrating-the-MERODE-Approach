/** 
 * This class was automatically generated  
 * using a Merode XML model and Apache Velocity
 * 
 * Merode Code Generator 2.0
 * @author MERODE Team-members
 */

package driver;


import handlers.MerodeMainEventHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import net.miginfocom.swing.MigLayout;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.jgoodies.plaf.FontSizeHints;
import com.jgoodies.plaf.LookUtils;
import com.jgoodies.plaf.Options;

import testing.MainTesting; //TesCAV

import ui.tabs.lists.DeviceListWindow;
import ui.tabs.tables.views.DeviceViewWindow;
import ui.tabs.lists.DeviceresultListWindow;
import ui.tabs.tables.views.DeviceresultViewWindow;
import ui.tabs.lists.DeviceusageListWindow;
import ui.tabs.tables.views.DeviceusageViewWindow;
import ui.tabs.lists.FeatureofinterestListWindow;
import ui.tabs.tables.views.FeatureofinterestViewWindow;
import ui.tabs.lists.PlatformListWindow;
import ui.tabs.tables.views.PlatformViewWindow;
import ui.tabs.lists.PlatformdeploymentListWindow;
import ui.tabs.tables.views.PlatformdeploymentViewWindow;
import ui.tabs.lists.ProcedureListWindow;
import ui.tabs.tables.views.ProcedureViewWindow;
import ui.tabs.lists.PropertyListWindow;
import ui.tabs.tables.views.PropertyViewWindow;
import ui.tabs.lists.RegistereddeviceListWindow;
import ui.tabs.tables.views.RegistereddeviceViewWindow;
import ui.tabs.lists.ObjectListWindow;
import ui.tabs.tables.views.ObjectViewPanel;

import validation.MermaidImageGallery;
import dao.MerodeLogger;

/**
 * Main class. 
 * Shows a window with
 *  - one tab for each object type 
 *  - and an event execution log
 */
@SuppressWarnings("serial")
public class MerodeMainEventHandlerGui extends JPanel {
	
    public final static int BUTTON_HEIGHT = 21; // tabs
    public final static int BUTTONS_NUMBER = 9;
    public final static int WIDTH = 1200;
    public final static int HEIGHT = 800;

	//private static final Color BACKGROUND_COLOR = new Color(230, 229, 235); 
    
    private static JFrame main_frame;
    private MerodeMainEventHandler main_handler;
    private MainTesting mainTestingTesting = new MainTesting();

    //collection to store all instances of open frames identified by object ids
    public static Map <String, JFrame> openViewInstances 
    		= new HashMap <String, JFrame>();
 
    private MerodeMainEventHandlerGui() {
    super(createMigLayout());

    configureUI();
    createMenuBar();
    installMainEventHandler();
    createWindows();

    JLabel l_otypes = new JLabel("Object types : ");
    l_otypes.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
    //add (l_otypes);
   
    JLabel app_logo = new JLabel("");
    app_logo.setIcon(new ImageIcon(this.getClass().getResource("merode.png")));
    add(app_logo);
    
    //----- invoking modelling tool------------------------------------
//    JLabel jmermaid_invoker = new JLabel("");
//    ImageIcon archIcon = new ImageIcon(this.getClass().getResource("arch.png"));
//    jmermaid_invoker.setIcon(archIcon);
    
//    jmermaid_invoker.addMouseListener(new MouseAdapter() {
//        public void mouseClicked(MouseEvent me) {
//	  		try {
//	  			JFileChooser fc = new JFileChooser();
//	  			String jmermaidJarPath = "";
//	  			
//	  			String desktopPath = System.getProperty("user.home") + "\\Desktop";
//				fc.setCurrentDirectory(new File(desktopPath));
//				fc.setFileFilter(new FileFilter() {
//					public boolean accept(File f) {
//						return f.getName().endsWith(".jar")
//							|| (f.getName().endsWith("") && f.isDirectory());
//					}
//	
//					@Override
//					public String getDescription() {
//						return "*" + ".jar" + " files"; // "*.jar files";
//					}
//				});
//
//	
//				int returnVal = fc.showOpenDialog(MerodeMainEventHandlerGui.main_frame);
//	
//				if (returnVal == JFileChooser.APPROVE_OPTION) {
//					File file = fc.getSelectedFile();
//					jmermaidJarPath = file.getAbsolutePath();
//				}
//	  			
//	  			String userDir = System.getProperty("user.dir");
//	  			String editableModel = "";
//	  			String editableModelLoc = userDir + "\\src\\validation\\editableModel";
//	  			File editableModelLocContent = new File(editableModelLoc);
//	  			if(editableModelLocContent.exists()){
//	  				editableModel = editableModelLocContent.list()[0];
//	  				if (!"".equals(editableModel) && ! "".equals(jmermaidJarPath)){
//	  		  			String batchDirectory = 
//	  		  				jmermaidJarPath.substring(0, 
//	  		  						jmermaidJarPath.indexOf("JMermaid.jar")).replace("/", "\\");
//	  		  			File file = new File(batchDirectory);
//	  		  			Runtime runtime = Runtime.getRuntime();
//	  		  			
//	  		  			runtime.exec("cmd /c java -jar JMermaid.jar -input:" 
//	  		  					+ editableModelLoc + "\\" + editableModel, null, file);
//	
//	  				}
//	  			}
//	  		} catch (IOException e) {
//	  			JOptionPane.showMessageDialog(
//	  					null, e, "Error", JOptionPane.ERROR_MESSAGE);
//	  		}
//
//
//        }
//    });
    
//    add(jmermaid_invoker);
    //-----------------------------------------------------
    
    // --------------- showing log ------------------------
    final JLabel fontColorPicker = new JLabel("");
    ImageIcon colorPicker = new ImageIcon(this.getClass().getResource("modifyTextColor.png"));
    fontColorPicker.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent me) {
            Color initialBackground = new Color(0,191,255);
            Color textColor = JColorChooser.showDialog(null,
                "Choose log text color", initialBackground);
            if (textColor != null) {
              MerodeLogger.log.setForeground(textColor);
            }

  		}
    });
    
    fontColorPicker.setIcon(colorPicker);
    //add (fontColorPicker);
    
    //shows log by default
    final JLabel toggleLabel = new JLabel(messages.MessageProperties.EXPAND);
    final ImageIcon icExpand = new ImageIcon(this.getClass().getResource("toggle-expand.png"));
    final ImageIcon icCollapse = new ImageIcon(this.getClass().getResource("toggle-collapse.png"));
    toggleLabel.setIcon(icCollapse);
    
    MerodeLogger.log.setBackground(SystemColor.window);
    MerodeLogger.log.setEditable(false);
    MerodeLogger.log.setForeground(Color.BLACK);//new Color(0,191,255)

	final JScrollPane logScrollPane = new JScrollPane(MerodeLogger.log);
	//Border border = BorderFactory.createEmptyBorder(0, 0, 0, 0);
	//logScrollPane.setBorder(border);
	logScrollPane.setBorder(BorderFactory.createCompoundBorder(
    BorderFactory.createTitledBorder(messages.MessageProperties.LOG), BorderFactory
        .createEmptyBorder(5, 5, 5, 5)));
	logScrollPane.setPreferredSize(new Dimension(400,300));
	logScrollPane.setVisible(false); // start with hidden log
	add(toggleLabel, "gaptop 40");
	add(fontColorPicker, "gaptop 40");//"alignx right, aligny top"
    add(logScrollPane, "grow,wrap"); // Wrap to next row with MigLayout;
    toggleLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if (logScrollPane.isShowing()) {
					logScrollPane.setVisible(false);
					toggleLabel.setText(messages.MessageProperties.EXPAND);
					toggleLabel.setIcon(icExpand);
					fontColorPicker.setVisible(false);
				} else {
					logScrollPane.setVisible(true);
					toggleLabel.setText(messages.MessageProperties.COLLAPSE);
					toggleLabel.setIcon(icCollapse);
					fontColorPicker.setVisible(true);
				}

			}
		});

    //------------------ end of log -------------------------   

    //  ------ keeping the log into eventLog.txt ------------
    main_frame.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
        	FileWriter out;
			try {
				String logFileName = "eventLog.csv";
				File f = new File(logFileName);
				if(!f.exists()) {
					out = new FileWriter(logFileName, false);
				} else {
					out = new FileWriter(logFileName, true);
				}
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        Date now = new Date();
		        String strDate = sdf.format(now);

                out.append("Simulation time: " + strDate);
                out.append("\n");
                out.append(MerodeLogger.log.getText());
                out.append("\n");
                out.append(mainTestingTesting.getTestingLog());
                out.append("\n");
                out.close();

			} catch (IOException exc) {
				// TODO Auto-generated catch block
				exc.printStackTrace();
			}

        }
    });
    //  -----------------------------------------------------------
    
	JTabbedPane tabPane = new JTabbedPane();
	tabPane.setTabPlacement(SwingConstants.TOP);
	tabPane.setOpaque(true);
	
    // FONT: BOLD  
	tabPane.setFont(tabPane.getFont().deriveFont(Font.BOLD));  
   
    // FONT: GRAY  
	tabPane.setForeground(Color.DARK_GRAY);  
   
    // TAB BACKGROUND: SYSTEM COLOR 
	tabPane.setBackground(new Color(159,182,205)); 

    JButton b_Device = new JButton("Device");
    b_Device.addActionListener( new ActionListener() {
        public void actionPerformed (ActionEvent event) {
            showDeviceList();
        }
    });
    //add (b_Device);
    tabPane.addTab("Device", new ImageIcon("images/switchtab1.png"), theDeviceListWindow, "Device data");
    JButton b_Deviceresult = new JButton("Deviceresult");
    b_Deviceresult.addActionListener( new ActionListener() {
        public void actionPerformed (ActionEvent event) {
            showDeviceresultList();
        }
    });
    //add (b_Deviceresult);
    tabPane.addTab("DeviceResult", new ImageIcon("images/switchtab1.png"), theDeviceresultListWindow, "DeviceResult data");
    JButton b_Deviceusage = new JButton("Deviceusage");
    b_Deviceusage.addActionListener( new ActionListener() {
        public void actionPerformed (ActionEvent event) {
            showDeviceusageList();
        }
    });
    //add (b_Deviceusage);
    tabPane.addTab("DeviceUsage", new ImageIcon("images/switchtab1.png"), theDeviceusageListWindow, "DeviceUsage data");
    JButton b_Featureofinterest = new JButton("Featureofinterest");
    b_Featureofinterest.addActionListener( new ActionListener() {
        public void actionPerformed (ActionEvent event) {
            showFeatureofinterestList();
        }
    });
    //add (b_Featureofinterest);
    tabPane.addTab("FeatureOfInterest", new ImageIcon("images/switchtab1.png"), theFeatureofinterestListWindow, "FeatureOfInterest data");
    JButton b_Platform = new JButton("Platform");
    b_Platform.addActionListener( new ActionListener() {
        public void actionPerformed (ActionEvent event) {
            showPlatformList();
        }
    });
    //add (b_Platform);
    tabPane.addTab("Platform", new ImageIcon("images/switchtab1.png"), thePlatformListWindow, "Platform data");
    JButton b_Platformdeployment = new JButton("Platformdeployment");
    b_Platformdeployment.addActionListener( new ActionListener() {
        public void actionPerformed (ActionEvent event) {
            showPlatformdeploymentList();
        }
    });
    //add (b_Platformdeployment);
    tabPane.addTab("PlatformDeployment", new ImageIcon("images/switchtab1.png"), thePlatformdeploymentListWindow, "PlatformDeployment data");
    JButton b_Procedure = new JButton("Procedure");
    b_Procedure.addActionListener( new ActionListener() {
        public void actionPerformed (ActionEvent event) {
            showProcedureList();
        }
    });
    //add (b_Procedure);
    tabPane.addTab("Procedure", new ImageIcon("images/switchtab1.png"), theProcedureListWindow, "Procedure data");
    JButton b_Property = new JButton("Property");
    b_Property.addActionListener( new ActionListener() {
        public void actionPerformed (ActionEvent event) {
            showPropertyList();
        }
    });
    //add (b_Property);
    tabPane.addTab("Property", new ImageIcon("images/switchtab1.png"), thePropertyListWindow, "Property data");
    JButton b_Registereddevice = new JButton("Registereddevice");
    b_Registereddevice.addActionListener( new ActionListener() {
        public void actionPerformed (ActionEvent event) {
            showRegistereddeviceList();
        }
    });
    //add (b_Registereddevice);
    tabPane.addTab("RegisteredDevice", new ImageIcon("images/switchtab1.png"), theRegistereddeviceListWindow, "RegisteredDevice data");
	
	//set defaults
	tabPane.setSelectedIndex(0);
	//add(new JLabel("Please select a tab to view content."));
	add(tabPane, "span 5, width :100:,grow"); // span 5 merges 5 columns with miglayout
	
    }
    
    private static LayoutManager createMigLayout() {
		MigLayout migLayout = new MigLayout(
				"", // Layout Constraints
				"10[]10[]20[]10[]10[fill,grow]10", // Column constraints
				"5[280]5[grow]10[shrink]"); // Row constraints
		return migLayout;
	}

    
    private ObjectListWindow theDeviceListWindow;
    private void showDeviceList() {
        //if (theDeviceListWindow == null)
        //    theDeviceListWindow = new DeviceListWindow (this, main_handler);
        //theDeviceListWindow.pack();
        theDeviceListWindow.setVisible(true);
    }
    private ObjectListWindow theDeviceresultListWindow;
    private void showDeviceresultList() {
        //if (theDeviceresultListWindow == null)
        //    theDeviceresultListWindow = new DeviceresultListWindow (this, main_handler);
        //theDeviceresultListWindow.pack();
        theDeviceresultListWindow.setVisible(true);
    }
    private ObjectListWindow theDeviceusageListWindow;
    private void showDeviceusageList() {
        //if (theDeviceusageListWindow == null)
        //    theDeviceusageListWindow = new DeviceusageListWindow (this, main_handler);
        //theDeviceusageListWindow.pack();
        theDeviceusageListWindow.setVisible(true);
    }
    private ObjectListWindow theFeatureofinterestListWindow;
    private void showFeatureofinterestList() {
        //if (theFeatureofinterestListWindow == null)
        //    theFeatureofinterestListWindow = new FeatureofinterestListWindow (this, main_handler);
        //theFeatureofinterestListWindow.pack();
        theFeatureofinterestListWindow.setVisible(true);
    }
    private ObjectListWindow thePlatformListWindow;
    private void showPlatformList() {
        //if (thePlatformListWindow == null)
        //    thePlatformListWindow = new PlatformListWindow (this, main_handler);
        //thePlatformListWindow.pack();
        thePlatformListWindow.setVisible(true);
    }
    private ObjectListWindow thePlatformdeploymentListWindow;
    private void showPlatformdeploymentList() {
        //if (thePlatformdeploymentListWindow == null)
        //    thePlatformdeploymentListWindow = new PlatformdeploymentListWindow (this, main_handler);
        //thePlatformdeploymentListWindow.pack();
        thePlatformdeploymentListWindow.setVisible(true);
    }
    private ObjectListWindow theProcedureListWindow;
    private void showProcedureList() {
        //if (theProcedureListWindow == null)
        //    theProcedureListWindow = new ProcedureListWindow (this, main_handler);
        //theProcedureListWindow.pack();
        theProcedureListWindow.setVisible(true);
    }
    private ObjectListWindow thePropertyListWindow;
    private void showPropertyList() {
        //if (thePropertyListWindow == null)
        //    thePropertyListWindow = new PropertyListWindow (this, main_handler);
        //thePropertyListWindow.pack();
        thePropertyListWindow.setVisible(true);
    }
    private ObjectListWindow theRegistereddeviceListWindow;
    private void showRegistereddeviceList() {
        //if (theRegistereddeviceListWindow == null)
        //    theRegistereddeviceListWindow = new RegistereddeviceListWindow (this, main_handler);
        //theRegistereddeviceListWindow.pack();
        theRegistereddeviceListWindow.setVisible(true);
    }

    private void createWindows() {
	    theDeviceListWindow = new DeviceListWindow (this, main_handler);
	    theDeviceresultListWindow = new DeviceresultListWindow (this, main_handler);
	    theDeviceusageListWindow = new DeviceusageListWindow (this, main_handler);
	    theFeatureofinterestListWindow = new FeatureofinterestListWindow (this, main_handler);
	    thePlatformListWindow = new PlatformListWindow (this, main_handler);
	    thePlatformdeploymentListWindow = new PlatformdeploymentListWindow (this, main_handler);
	    theProcedureListWindow = new ProcedureListWindow (this, main_handler);
	    thePropertyListWindow = new PropertyListWindow (this, main_handler);
	    theRegistereddeviceListWindow = new RegistereddeviceListWindow (this, main_handler);
    }
    
    public void refreshTables() {
		theDeviceListWindow.table.refresh();
		theDeviceresultListWindow.table.refresh();
		theDeviceusageListWindow.table.refresh();
		theFeatureofinterestListWindow.table.refresh();
		thePlatformListWindow.table.refresh();
		thePlatformdeploymentListWindow.table.refresh();
		theProcedureListWindow.table.refresh();
		thePropertyListWindow.table.refresh();
		theRegistereddeviceListWindow.table.refresh();
		refreshTableViews();
    }
    
    public void refreshTableViews() {
    	if (openViewInstances != null){
  	        for (Entry e: openViewInstances.entrySet()) {
  	        	String id = (String) e.getKey();
  	        	JFrame frame = (JFrame) e.getValue();
  	        	ObjectViewPanel viewPanel = null;
	    		JScrollPane scrollpane = null;
	       		try {
					if (main_handler.searchDeviceById(id) != null){
	    				viewPanel = new DeviceViewWindow (this, main_handler, id);
			   	        scrollpane = new JScrollPane(viewPanel);
	    			} 
	    		} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
	       		try {
					if (main_handler.searchDeviceresultById(id) != null){
	    				viewPanel = new DeviceresultViewWindow (this, main_handler, id);
			   	        scrollpane = new JScrollPane(viewPanel);
	    			} 
	    		} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
	       		try {
					if (main_handler.searchDeviceusageById(id) != null){
	    				viewPanel = new DeviceusageViewWindow (this, main_handler, id);
			   	        scrollpane = new JScrollPane(viewPanel);
	    			} 
	    		} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
	       		try {
					if (main_handler.searchFeatureofinterestById(id) != null){
	    				viewPanel = new FeatureofinterestViewWindow (this, main_handler, id);
			   	        scrollpane = new JScrollPane(viewPanel);
	    			} 
	    		} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
	       		try {
					if (main_handler.searchPlatformById(id) != null){
	    				viewPanel = new PlatformViewWindow (this, main_handler, id);
			   	        scrollpane = new JScrollPane(viewPanel);
	    			} 
	    		} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
	       		try {
					if (main_handler.searchPlatformdeploymentById(id) != null){
	    				viewPanel = new PlatformdeploymentViewWindow (this, main_handler, id);
			   	        scrollpane = new JScrollPane(viewPanel);
	    			} 
	    		} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
	       		try {
					if (main_handler.searchProcedureById(id) != null){
	    				viewPanel = new ProcedureViewWindow (this, main_handler, id);
			   	        scrollpane = new JScrollPane(viewPanel);
	    			} 
	    		} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
	       		try {
					if (main_handler.searchPropertyById(id) != null){
	    				viewPanel = new PropertyViewWindow (this, main_handler, id);
			   	        scrollpane = new JScrollPane(viewPanel);
	    			} 
	    		} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
	       		try {
					if (main_handler.searchRegistereddeviceById(id) != null){
	    				viewPanel = new RegistereddeviceViewWindow (this, main_handler, id);
			   	        scrollpane = new JScrollPane(viewPanel);
	    			} 
	    		} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
	    		if(frame != null && scrollpane != null
	    					&& viewPanel != null){
					frame.getContentPane().removeAll();
		    	    frame.add(scrollpane);
		    	    frame.pack();
	    		}

	        }
 
    	}
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        main_frame = new JFrame("MERODE application");
        main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        MerodeMainEventHandlerGui mainGuiPanel = new MerodeMainEventHandlerGui();
        mainGuiPanel.setOpaque(true); //content panes must be opaque
        mainGuiPanel.setBackground(new Color(159,182,205));
        main_frame.setPreferredSize(new Dimension(300, 
				BUTTONS_NUMBER * BUTTON_HEIGHT + 280)); // tabpane h + top h
		main_frame.setContentPane(new JScrollPane(mainGuiPanel));

        //localization if wide screen
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        final int screenWidth = screenSize.width;
        final int screenHeight = screenSize.height;
        if(screenWidth > 1280 && screenHeight > 1000){
        	main_frame.setLocation(screenWidth/6, screenHeight/6);
        }

        //Display the window.
        main_frame.pack();
        main_frame.setSize(WIDTH > screenWidth ? screenWidth : WIDTH, 
        		HEIGHT > screenHeight ? screenHeight : HEIGHT);
       
        main_frame.setVisible(true);
    }

    private void configureUI() {
        UIManager.put(Options.USE_SYSTEM_FONTS_APP_KEY, Boolean.TRUE);
        UIManager.put("MenuItem.selectionForeground", Color.DARK_GRAY);
        Options.setGlobalFontSizeHints(FontSizeHints.MIXED);
        Options.setDefaultIconSize(new Dimension(18, 18));

        String lafName =
            LookUtils.IS_OS_WINDOWS_XP
                ? Options.getCrossPlatformLookAndFeelClassName()
                : Options.getSystemLookAndFeelClassName();

        try {
            UIManager.setLookAndFeel(lafName);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            //System.err.println("Can't set look & feel:" + e);
        }
    }
    
    private void createMenuBar() {
        JMenuBar menubar = new JMenuBar();
        ImageIcon dbIcon = new ImageIcon(getClass().getResource("database.png"));

        JMenu tools = new JMenu("Tools");
        tools.setMnemonic(KeyEvent.VK_F);

		// ----- QUERY BUILDER ---------
        JMenuItem eMenuItem = new JMenuItem("Query Builder", dbIcon);
        eMenuItem.setMnemonic(KeyEvent.VK_C);
        //eMenuItem.setToolTipText("Open to query my database");
        eMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
				String startDBBatch = "./";
				File file = new File(startDBBatch);
				Runtime runtime = Runtime.getRuntime();
				try {
					runtime.exec("cmd /c start databaseManager.bat", null, file);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

            }

        });

        tools.add(eMenuItem);
		
        // -----------------------------
        
        // ----- MODEL VIEWS OPENER ----
        ImageIcon modelIcon = new ImageIcon(getClass().getResource("dependency-graph.png"));
        JMenuItem eMenuItemModel = new JMenuItem("Model views", modelIcon);
        eMenuItemModel.setMnemonic(KeyEvent.VK_C);
        //eMenuItem.setToolTipText("Open your model views");
        eMenuItemModel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
				try {
				    String IMAGES_URL = "/images";
					String userDir = System.getProperty("user.dir");
					String fileSeparator = System.getProperty("file.separator");
					// userDir = userDir.replace(fileSeparator,"/");
					String imgDir = (userDir + IMAGES_URL).replace(fileSeparator, "/");

					String[] imgFiles = new File(imgDir).list();
					String classDiagramImgName = null;
					for(int k = 0; k < imgFiles.length; ++k){
						if(imgFiles[k].contains("EDG_") || imgFiles[k].contains("UML_")){
							classDiagramImgName = imgFiles[k];
						}
					}
					File imgFile = new File(imgDir + "/" + classDiagramImgName);
					BufferedImage bimage = ImageIO.read(imgFile);
					JPanel panel = new JPanel(new GridLayout(1, 0));
					panel.add(new JLabel(new ImageIcon(bimage)));

					ImageIcon icon = new ImageIcon(bimage);
					icon = new javax.swing.ImageIcon(bimage);
					MermaidImageGallery app = new MermaidImageGallery();
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					MermaidImageGallery.getPhotographLabel().setIcon(icon);
					app.setLocation(dim.width / 2, 0);
					app.getRootPane().getParent().requestFocus();
					app.setVisible(true);
					//app.setAlwaysOnTop(true);
					app.toFront();
			        //app.repaint();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

            }

        });
        tools.add(eMenuItemModel);
        // -----------------------------
        // Necessary lines to implement the testing module:
		// ----- MODEL VIEWS OPENER ----
        ImageIcon modelIcon1 = new ImageIcon(getClass().getResource("run-test.png"));
        JMenuItem eMenuItemModel2 = new JMenuItem("Run TesCaV", modelIcon1);
       	 eMenuItemModel2.setMnemonic(KeyEvent.VK_C);
        eMenuItemModel2.addActionListener(new ActionListener() {
            
       	// Execute this function when the "Run Test" button is pressed
        	public void actionPerformed(ActionEvent event) {
       			 mainTestingTesting.addGuiButton();
                
            }
        });

        tools.add(eMenuItemModel2);
        // -----------------------------
        menubar.add(tools);
        main_frame.setJMenuBar(menubar);
	}

    private void installMainEventHandler() {
        try {
            System.out.println(" +-----------------------------------------------------------------+");
            System.out.println(" + Generated Merode Application                                    +");
            System.out.println(" +                                                                 +");
            System.out.println(" + Author & developer 1.0 : Raf Haesen (raf.haesen@gmail.com)      +"); 
            System.out.println(" + Developer 2.0 : Gayane Sedrakyan (gsedrakyan@gmail.com)         +");
            System.out.println(" +                                                                 +");
            System.out.println(" + Info:   http://merode.econ.kuleuven.ac.be                       +");
            System.out.println(" +         K.U.Leuven                                              +");
            System.out.println(" +-----------------------------------------------------------------+");
            System.out.println("Debug info:");
            System.out.print("Installing handler...");

            //ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
            //BeanFactory beanfactory = context;
            //main_handler = (MerodeMainEventHandler)beanfactory.getBean("eventHandler"); //new MerodeMainEventHandler();
            main_handler = new MerodeMainEventHandler();
            System.out.println("done");
        } catch (Exception e){
        	e.printStackTrace();
        }
        finally {
            if (main_handler == null) System.exit(0);
        }
    }

	private static void registerRunningProcess() {
		FileWriter out;
		try {
			String propertiesFileName = "application.properties";
			File f = new File(propertiesFileName);
			if (!f.exists()) {
				out = new FileWriter(propertiesFileName, false);
			} else {
				out = new FileWriter(propertiesFileName, true);
			}
			out.append("pid=" + ManagementFactory.getRuntimeMXBean().getName());
			out.close();

		} catch (IOException exc) {
			// TODO Auto-generated catch block
			exc.printStackTrace();
		}

	}

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
                registerRunningProcess();
            }
        });
    }
}

