package tescav;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxPoint;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;

import testing.Model;
import validation.XMLProperties;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.io.FileWriter;
import javafx.util.Pair;

import dao.MerodeLogger;
import java.lang.*;

import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;

/**
 * 
 * @author Sofia Alarcon
 *
 */
public class VisualizerUI {

	// --- Attributes ---
	Model model;
	Coverage coverage;
	VisualizerBean vb;
	JPanel p2 = new JPanel();
	JFrame frame2 = new JFrame("TesCaV");
	static String USER_DIRECTORY = "user.dir";
	static String FILE_SEPARATOR = "file.separator";
	static String IMAGES_URL = "/images";

	HashMap<String, String> testingTypes = new HashMap<String, String>();

	// --- Methods ---

	// Constructor
	public VisualizerUI(final Model modelRcv, final Coverage coverageRcv) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				model = modelRcv;
				coverage = coverageRcv;
				vb = new VisualizerBean(model, coverage);
				summary();
			}
		});
	}

	// Show the summary window
	private void summary() {

		String msg = vb.getSummaryText();

		String[] options = { "<html><font size=+1>" + "View Details", "<html><font size=+1>" + "Close" };

		/*
		 * JOptionPane is used because it is modal (prevents clicks in other
		 * windows)
		 */
		int userInput = JOptionPane.showOptionDialog(null, msg, "TesCaV Summary", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

		if (userInput == JOptionPane.YES_OPTION) {
			tescav();
		}

	}

	// Show main TesCaV window
	private void tescav() {

		// Prepare frame
		JFrame frame = new JFrame("TesCaV");
		frame.setSize(500, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		// Menubar
		JMenuBar mb = new JMenuBar();
		JMenu menuHelp = new JMenu("Help");
		JMenuItem subMenuHelp = new JMenuItem("Help");
		menuHelp.add(subMenuHelp);
		mb.add(menuHelp);
		frame.setJMenuBar(mb);

		// Prepare component panel
		JPanel p = new JPanel();
		frame.add(p);
		p.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);

		// Title label
		JLabel title = new JLabel(vb.getTitle(), JLabel.CENTER);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		p.add(title, c);

		// Combobox
		String hintCombo = "<html><font size=+1>" + "Select Object" + "</font></html>";
		final JComboBox<String> classList = vb.getComboBox();
		classList.setRenderer(new ComboBoxRenderer(hintCombo));
		classList.setSelectedIndex(-1);
		classList.setPrototypeDisplayValue(hintCombo);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.CENTER;
		p.add(classList, c);
		classList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String a = String.valueOf(classList.getSelectedItem());

				String x = a.substring(a.indexOf("1") + 2);

				System.out.println("x es: " + x);

				displayCoverage(x);

			}
		});

		// Summary
		JLabel summary = new JLabel("Summary");
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.CENTER;
		// p.add(summary,c); // <<< No olvidar implementar esto despues

		// Add to main panel
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		// c.weightx = 1.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		p2.setVisible(false);
		p.add(p2, c);

	}

	// Displays coverage status for a selected class
	private void displayCoverage(final String selectedClass) {

		System.out.println("Combobox: " + selectedClass);

		p2.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		p2.setBorder(BorderFactory.createTitledBorder("<html><font size=+1>" + "MBT Criteria for: " + selectedClass));

		p2.removeAll();
		
		
		// For each criterion
		for (int i = 0; i < vb.getCriteriaAmount(); i++) {

			final int ix = i;

			final String criterion = vb.getCriterionName(i);

			int[] coverage = vb.getCoverageForCriterion(selectedClass, i);

			String text = "<html><font size=+1>" + criterion + " coverage: " + coverage[0] + "/" + coverage[1];
			System.out.println("Display: " + text);

			// Criteria name
			JLabel criterionLabel = new JLabel(text);
			c.gridx = 0;
			c.gridy = i;
			c.gridwidth = 2;
			p2.add(criterionLabel, c);
			p2.revalidate();
			p2.repaint();

			// View Button
			JButton view = new JButton("<html><font size=+1>" + "View");
			c.gridx = 2;
			c.gridy = i;
			c.weightx = 1;
			c.fill = GridBagConstraints.NONE;
			c.anchor = GridBagConstraints.CENTER;
			p2.add(view, c);

			// When view button is pressed, prepare and paint
			view.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("Button: " + criterion + " for " + selectedClass);
					paint(selectedClass, ix);
				}
			});
		}
		p2.setVisible(true);

	}

	private Pair<Integer, Integer> getIndex(Object[][] data, String[] columnNamesList, String methodName, String columnName){
		Integer methodIndex = 0;
		for (Integer i = 0; i < data.length; i++){
			if (methodName.equals((String)data[i][0])) {
				methodIndex = i;
			}
		}
		Integer columnIndex = Arrays.asList(columnNamesList).indexOf(columnName);
		Pair<Integer, Integer> indexes = new Pair<Integer, Integer>(methodIndex, columnIndex);
		return indexes;
	}

	

	private void paint(String selectedClass, int criterionIndex) {
		BufferedImage bimage = null;
		JFrame frame = new JFrame("TesCaV Image");

		File imgFile = null;
		float minX = 0;
		float minY = 0;
		Graphics2D g2d = null;
		ArrayList<String> columnNames = XMLProperties.getStateNames(selectedClass);
		ArrayList<String> methodNames = XMLProperties.getMethodNames(selectedClass);

		Object[][] data = null;
		String[] columnNamesList = null;

		try { // try A
			if(coverage.getCriterionType(criterionIndex).equals(Consts.FSM) ||
			coverage.getCriterionType(criterionIndex).equals(Consts.EDG)){
				String fileName = vb.getFileName(criterionIndex, selectedClass);
	
				// BASE SETTINGS
				minX = vb.getFileXAxis(fileName);
				minY = vb.getFileYAxis(fileName);
				
				String userDir = System.getProperty(USER_DIRECTORY);
				String fileSeparator = System.getProperty(FILE_SEPARATOR);
				String imgDir = (userDir + IMAGES_URL).replace(fileSeparator, "/");
				imgFile = new File(imgDir + "/" + fileName);

				
				bimage = ImageIO.read(imgFile);
				int imgWidth = bimage.getWidth();
				int imgHeight = bimage.getHeight();
	
				Dimension dim = new Dimension(imgWidth + 5, imgHeight + 5);
				frame.getContentPane().setPreferredSize(dim);
				frame.pack();
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.setLocationRelativeTo(null);
	
				g2d = bimage.createGraphics();
				Color transparent = new Color(0f, 0f, 1f, 1f);
				g2d.setColor(transparent);
				g2d.setComposite(AlphaComposite.Src);
			} else if (coverage.getCriterionType(criterionIndex).equals(Consts.TABLE)) {
				//Initialize a table
				Set<String> methodNamesSet = new HashSet<String>(methodNames);
				Set<String> columnNamesSet = new HashSet<String>(columnNames);
				
				data = new Object[methodNamesSet.size()][columnNamesSet.size()+1];

				columnNamesList = new String[columnNamesSet.size()+1];
				columnNamesList[0] = "Method name";
				int index = 1;
				for (String columnName : columnNames){
					if (!Arrays.asList(columnNamesList).contains(columnName)){
						columnNamesList[index] = columnName;
						index++;
					}
					
				}

				int i = 0;
				for (String methodName : methodNamesSet){
					data[i][0] = methodName;
					i++;
				}
				for (int tc = 0; tc < coverage.getTestCasesAmount(criterionIndex); tc++) {
					if (selectedClass.equals(coverage.getObjectName(criterionIndex, tc))) {
						int elementAmount = coverage.getElementsAmount(criterionIndex, tc);
						for (int e = 0; e < elementAmount; e++) {
							int stateId = coverage.getStateId(criterionIndex, tc, e, 0);
							String stateName = model.getStateNameById(stateId);
							int methodId = coverage.getMethodId(criterionIndex, tc, e, 0);
							String methodName = model.getMethodNameById(methodId);
							String key = methodName.toLowerCase() + "-" + stateName.toLowerCase();
						
							String testingType = coverage.getTestingType(criterionIndex, tc, e);	
							testingTypes.put(key, testingType);
						}
					}
				}

				JScrollPane scrollPane = new JScrollPane();
				JTable table = new JTable(data, columnNamesList) {
					@Override
					public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
						Component comp = super.prepareRenderer(renderer, row, col);
						Object value = getModel().getValueAt(row, col);
						String stateName = String.valueOf(getModel().getColumnName(col));
						String methodName = String.valueOf(getModel().getValueAt(row, 0));
						String key = methodName.toLowerCase() + "-" + stateName.toLowerCase();
						if (testingTypes.get(key) != null && testingTypes.get(key).equals(Consts.NEGATIVE)) {
							comp.setBackground(new Color( 187, 30, 16));
						} else if (testingTypes.get(key) != null && testingTypes.get(key).equals(Consts.POSITIVE)) {
							comp.setBackground(new Color(51, 165, 50));
						} else if (col != 0) {
							comp.setBackground(new Color(235, 235, 228));
						} else {
							comp.setBackground(Color.WHITE);
						}						
						return comp;
					}
				};
				table.getTableHeader().setEnabled(false);
				table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
				int preferredWidth = 0;
				TableColumn tableColumn = table.getColumnModel().getColumn(0);
				int maxWidth = table.getColumnModel().getColumn(0).getMaxWidth();
				//Set the width of the first column
				for (int row = 0; row < table.getRowCount(); row++)	{
					preferredWidth = Math.max(preferredWidth, getCellDataWidth(table, row, 0));

					//  We've exceeded the maximum width, no need to check other rows

					if (preferredWidth >= maxWidth)
						break;
				}

				tableColumn.setPreferredWidth( preferredWidth );
				int totalWidth = preferredWidth + 5;

				//Set the width of all subsequent columns
				for (int column = 1; column < table.getColumnCount(); column++){
					tableColumn = table.getColumnModel().getColumn(column);
					Object value = tableColumn.getHeaderValue();
					TableCellRenderer renderer = tableColumn.getHeaderRenderer();

					if (renderer == null) {
						renderer = table.getTableHeader().getDefaultRenderer();
					}

					Component c = renderer.getTableCellRendererComponent(table, value, false, false, -1, column);

					tableColumn.setPreferredWidth( c.getPreferredSize().width );
					totalWidth = totalWidth + c.getPreferredSize().width;
				}

				//Make the table non-editable
				table.setEnabled(false);
				//Allow to sort tables
				table.setAutoCreateRowSorter(true);
				scrollPane.setViewportView(table);

				Dimension dim = new Dimension(totalWidth, (table.getRowCount() + 2) * table.getRowHeight() + 5);
				frame.getContentPane().setPreferredSize(dim);
				frame.pack();
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);

				frame.add(scrollPane);
			}
			
			// In case of a transition, we will generate edges from scratch
			mxGraph graph = new mxGraph();
			Object parent = graph.getDefaultParent();

			// definir arreglo con colores
			Color colors[] = { Color.RED, Color.MAGENTA, Color.ORANGE, Color.CYAN, Color.BLUE, Color.DARK_GRAY };
			//TestCase testCase = coverage.criteria.get(0).getTestCase(0);
			
			// Recorrer los test cases para pintar
			for (int tc = 0; tc < coverage.getTestCasesAmount(criterionIndex); tc++) {
				if (selectedClass.equals(coverage.getObjectName(criterionIndex, tc))) {

					int elementAmount = coverage.getElementsAmount(criterionIndex, tc);
					Color paintColor = null;
					for (int e = 0; e < elementAmount; e++) {
						String type = coverage.getElementType(criterionIndex, tc, e);
						String name = coverage.getElementName(criterionIndex, tc, e);

						if (Consts.CLASS.equals(type) || Consts.ATTRIBUTE.equals(type)){

							float[] objCoordinates = XMLProperties.getObjectCoordinates(name);
							float objX = objCoordinates[0];
							float objY = objCoordinates[1];
							float x = objX - minX;
							float y = objY - minY;

							float objWidth = XMLProperties.getObjectSize(name)[0];
							float objHeight = XMLProperties.getObjectSize(name)[1];

							g2d.setStroke(new BasicStroke(3.0f));
							Shape rectangle = new Rectangle2D.Float(x, y, objWidth, objHeight);
							if (coverage.isElementCovered(criterionIndex, tc, e)) {
								paintColor = Color.GREEN;
							} else {
								paintColor = Color.RED;
							}
							g2d.setColor(paintColor);
							g2d.draw(rectangle);
						}
						if (Consts.STATE.equals(type)){
							float[] stateCoordinate = XMLProperties.getStateCoordinates(name,
									selectedClass.toUpperCase());
							float stateX = stateCoordinate[0];
							float stateY = stateCoordinate[1];

							float x = stateX - minX;
							float y = stateY - minY;

							float stateWidth = XMLProperties.getStateSize(name, selectedClass.toUpperCase())[0];
							float stateHeight = XMLProperties.getStateSize(name, selectedClass.toUpperCase())[1];

							g2d.setStroke(new BasicStroke(3.0f)); // Set line
																	// width
							Shape circleObj = new Ellipse2D.Float(x, y, stateWidth, stateHeight);
							if (coverage.isTestCaseCovered(criterionIndex, tc)) {
								paintColor = Color.GREEN;
							} else {
								paintColor = Color.RED;
							}
							g2d.setColor(paintColor);
							g2d.draw(circleObj);
						}

						if (Consts.TRANSITION.equals(type)){
							boolean isLoop = false;

							graph.getModel().beginUpdate();

							String srcState = model.getStateNameById(coverage.getStateId(criterionIndex, tc, e, 0));
							String trgState = model.getStateNameById(coverage.getStateId(criterionIndex, tc, e, 1));

							try { // try B

								// Obtener coordenadas de los estados
								float[] srcCoordinate = XMLProperties.getStateCoordinates(srcState,
										selectedClass.toUpperCase());
								float srcX_tmp = srcCoordinate[0];
								float srcY_tmp = srcCoordinate[1];
								
								float[] trgCoordinate = XMLProperties.getStateCoordinates(trgState,
										selectedClass.toUpperCase());
								float trgX_tmp = trgCoordinate[0];
								float trgY_tmp = trgCoordinate[1];
								
								// Adjust to the new reference system
								float srcX = srcX_tmp - minX;
								float srcY = srcY_tmp - minY;
								float trgX = trgX_tmp - minX;
								float trgY = trgY_tmp - minY;
							
								// Get source state dimensions
								float srcWidth = XMLProperties.getStateSize(srcState, selectedClass.toUpperCase())[0];
								float srcHeight = XMLProperties.getStateSize(srcState, selectedClass.toUpperCase())[1];
								
								// Get target state dimensions
								float trgWidth = XMLProperties.getStateSize(trgState, selectedClass.toUpperCase())[0];
								float trgHeight = XMLProperties.getStateSize(trgState, selectedClass.toUpperCase())[1];
																
								// CREATE NODES
								// Agregar punto y coma en style para que sean
								// invisibles
								Object srcNode = graph.insertVertex(parent, null, "", srcX, srcY, srcWidth, srcHeight,
										";shape=ellipse;perimeter=ellipsePerimeter");
								Object trgNode = graph.insertVertex(parent, null, "", trgX, trgY, trgWidth, trgHeight,
										";shape=ellipse;perimeter=ellipsePerimeter");
								
								// Obtener uno de los m�todos que se ejecuta en
								// esta transici�n (basta con uno)
								int methodId = coverage.getMethodId(criterionIndex, tc, e, 0);

								// We do this because there are transitions that
								// don't have owned method id's
								if (methodId >= 0) {

									// Dado el methodId y los estados, se
									// obtiene el id de la transici�n
									// correspondiente, y traigo los control
									// points
									// Asumo que es un control point por
									// transicion
									ArrayList<String> strCtrlPoints = vb.getControlPoints(methodId, srcState, trgState);
									ArrayList<Pair<Float, Float>> ctrlPoints = new ArrayList<Pair<Float, Float>>();
									String color = coverage.isElementCovered(criterionIndex, tc, e) ? "green": "red";
									String edgeStyle = String.format("strokeColor=%s;endArrow=block;strokeWidth=3;endSize=9;rounded=true;edgeStyle=segmentEdgeStyle", color);
									Object edge = new Object();
									edge = graph.insertEdge(parent, null, null, srcNode, trgNode, edgeStyle);

									//If src and trg state are the same then the transition is a loop
									if (srcState.equals(trgState)) {												
										isLoop = true;
									} 

									for(int ctrlIndex=0; ctrlIndex < strCtrlPoints.size(); ctrlIndex++){
										float ctrlX = Float.parseFloat(
											strCtrlPoints.get(ctrlIndex).substring(0, strCtrlPoints.get(ctrlIndex).indexOf("!")));
										float ctrlY = Float.parseFloat(
											strCtrlPoints.get(ctrlIndex).substring(strCtrlPoints.get(ctrlIndex).indexOf("!") + 1));
										ctrlPoints.add(new Pair <Float,Float> (ctrlX, ctrlY)); 
									}

									// Obtain the arrow id to bend it later
									mxCell currentArrow = (mxCell) edge;
									String cellId = currentArrow.getId();

									if (ctrlPoints.size() > 0) {
										graph.getModel().beginUpdate();
										try {
											// Magia para control points ~~
											mxCell edgeCell = (mxCell) ((mxGraphModel) graph.getModel()).getCell(cellId);
										
											double midX = 0;
											double midY = 0;
											if (isLoop){
												midX = srcX;
												midY = srcY;
											} else {
												midX = (srcX + trgX) / 2;
												midY = (srcY + trgY) / 2;
											}
										
											mxGeometry geometryOfEdge = ((mxGraphModel) (graph.getModel()))
													.getGeometry(edgeCell);
											geometryOfEdge = (mxGeometry) geometryOfEdge.clone();
		
											
											List<mxPoint> pointsOfTheEdge = geometryOfEdge.getPoints();
											if (pointsOfTheEdge == null) {
												pointsOfTheEdge = new ArrayList<mxPoint>();
											}
		
											for (Pair<Float, Float> ctrlPoint : ctrlPoints){
												if (!(ctrlPoint.getKey() == 0.0 && ctrlPoint.getValue() == 0.0)) {
													double ctrlPointX = midX + ctrlPoint.getKey();
													double ctrlPointY = midY + ctrlPoint.getValue();
													pointsOfTheEdge.add(new mxPoint(ctrlPointX, ctrlPointY));
												}
											}
											geometryOfEdge.setPoints(pointsOfTheEdge);
											((mxGraphModel) (graph.getModel())).setGeometry(edgeCell, geometryOfEdge);
											// fin de magia control points
										} finally {
											graph.getModel().endUpdate();
										}
									} // if end
								} // if end
							} // try B end
							finally {
								graph.getModel().endUpdate();
							}
							
						}

						if (Consts.METHOD.equals(type)) {
							
							int stateId = coverage.getStateId(criterionIndex, tc, e, 0);
							String columnName = model.getStateNameById(stateId);
							String methodName = coverage.getElementName(criterionIndex, tc, e);
							String key = methodName + "-" + columnName;
							
							Pair<Integer, Integer> index = getIndex(data, columnNamesList, methodName, columnName);

							//Add a tick ✓
							if (coverage.isElementCovered(criterionIndex, tc, e)){
								data[index.getKey()][index.getValue()] = "<html><font color = 'white'>&nbsp;&nbsp;&nbsp;&#10003;</font></html>";
							}
						}
					}
					
				}


			}
			if(g2d != null){
				g2d.dispose();
				mxGraphComponent graphComponent = new mxGraphComponent(graph);
				graphComponent.getViewport().setOpaque(true);
				graphComponent.setEnabled(false);
				graphComponent.setBackgroundImage(new ImageIcon(bimage));

				frame.getContentPane().add(graphComponent);
				frame.setVisible(true);
			} else {
				
			}
		} // try A end

		catch (IOException e) {
			e.printStackTrace();
		}
	} // paint function end

	class ComboBoxRenderer extends JLabel implements ListCellRenderer {
		private String _title;

		public ComboBoxRenderer(String title) {
			_title = title;
		}

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean hasFocus) {
			if (index == -1 && value == null)
				setText(_title);
			else
				setText(value.toString());
			return this;
		}
	}

	private int getCellDataWidth(JTable table, int row, int column)
	{
		//  Inovke the renderer for the cell to calculate the preferred width

		TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
		Component c = table.prepareRenderer(cellRenderer, row, column);
		int width = c.getPreferredSize().width + table.getIntercellSpacing().width;

		return width;
	}
} // class end
