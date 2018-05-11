package bluediamond2;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.klg.jclass.chart.JCChart;
import java.awt.GridLayout;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;

public class Old_1D_Panel extends JPanel {

	Saved_1D_ScanPanel saved_1D_ScanPanel;
	private JTabbedPane posDetTabbedPane;
	private JScrollPane posScrollPane;
	private JScrollPane detScrollPane;
	JPanel PosPanel;
	JPanel DetPanel;
	private JTable posTable;
	private JTable detTable;
	HpTableModel dm1 = new HpTableModel();
	HpTableModel dm2 = new HpTableModel();
	boolean hidden = false;

	TableCellRenderer renderer1 = new EvenOddRenderer();
	TableCellRenderer renderer2 = new EvenOddRenderer();

	JCChart chart;
	int dataViewNumber;
	java.util.List selectedDetectorsForDisplay;
	ButtonGroup buttonGroup1 = new ButtonGroup();
	private JPanel showHidePanel;
	private JButton hideButton;
	private JButton showButton;

	/**
	 * Create the panel.
	 */
	public Old_1D_Panel() {
		setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 255), new Color(0, 255, 255), new Color(0, 255, 0), new Color(255, 200, 0)), new CompoundBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 200, 0), new Color(0, 255, 0), new Color(0, 255, 255), new Color(255, 175, 175)), new BevelBorder(BevelBorder.RAISED, new Color(0, 255, 0), new Color(255, 200, 0), new Color(0, 0, 255), new Color(0, 255, 255)))));
		setLayout(new BorderLayout(0, 0));

		posDetTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(posDetTabbedPane, BorderLayout.CENTER);

		PosPanel = new JPanel();
		posDetTabbedPane.addTab("Positioners", null, PosPanel, null);
		PosPanel.setLayout(new BorderLayout(0, 0));

		posScrollPane = new JScrollPane();
		PosPanel.add(posScrollPane, BorderLayout.CENTER);

		posTable = new JTable(dm1){
	          public void tableChanged(TableModelEvent e) {
		             super.tableChanged(e);
		             repaint();
		          }
		       };
		posTable.setName("Pos");
		posScrollPane.setViewportView(posTable);

		DetPanel = new JPanel();
		posDetTabbedPane.addTab("Detectors", null, DetPanel, null);
		DetPanel.setLayout(new BorderLayout(0, 0));

		detScrollPane = new JScrollPane();
		DetPanel.add(detScrollPane, BorderLayout.CENTER);

		detTable = new JTable(dm2){
	          public void tableChanged(TableModelEvent e) {
		             super.tableChanged(e);
		             repaint();
		          }
		       };
		detTable.setName("Dets");
		detScrollPane.setViewportView(detTable);
		dm1.addTableModelListener(new HPTableModelListener(posTable));
		dm2.addTableModelListener(new HPTableModelListener(detTable));
		setPosHeaders(posTable);
		setDetHeaders(detTable);
		
	       
		showHidePanel = new JPanel();
		add(showHidePanel, BorderLayout.SOUTH);
		showHidePanel.setLayout(new GridLayout(0, 4, 0, 0));
		
		hideButton = new JButton("Hide");
		showHidePanel.add(hideButton);
		hideButton.addActionListener(new Old_1D_Panel_hideButton_actionAdapter(this));

		
		showButton = new JButton("Show");
		showHidePanel.add(showButton);
		showButton.addActionListener(new Old_1D_Panel_showButton_actionAdapter(this));
		
		posTable.setDefaultRenderer(Object.class, renderer1);
		detTable.setDefaultRenderer(Object.class, renderer2);

	}

	public void setChart(JCChart ch) {
		chart = ch;
	}

	public void setDataViewNumber(int n) {
		dataViewNumber = n;
	}

	public int getDataViewNumber() {
		return dataViewNumber;
	}
	
	public void setSelectedDetectorsForDisplay(java.util.List l) {
		selectedDetectorsForDisplay = l;
	}

	public void setTabName(String str) {
		posDetTabbedPane.setTitleAt(dataViewNumber, str);
	}
	
	public void setPosHeaders(JTable tb) {

		HpTableModel mp = (HpTableModel) tb.getModel();
		mp.addColumn("PV");
		mp.addColumn("Description");
		mp.addColumn("Minimum");
		mp.addColumn("Maximum");
		mp.addColumn("Select");
		DefaultTableColumnModel colModel = (DefaultTableColumnModel) tb.getColumnModel();
		int wo = colModel.getTotalColumnWidth();

		TableColumn col0 = colModel.getColumn(0);
		col0.setPreferredWidth((wo * 35) / 100);

		TableColumn col1 = colModel.getColumn(1);
		col1.setPreferredWidth((wo * 25) / 100);

		TableColumn col2 = colModel.getColumn(2);
		col2.setPreferredWidth((wo * 15) / 100);

		TableColumn col3 = colModel.getColumn(3);
		col3.setPreferredWidth((wo * 15) / 100);

		TableColumn col4 = colModel.getColumn(4);
		col4.setPreferredWidth((wo * 10) / 100);

	}

	public void setDetHeaders(JTable tb) {

		HpTableModel mp = (HpTableModel) tb.getModel();
		mp.addColumn("PV");
		mp.addColumn("Description");
		mp.addColumn("Minimum");
		mp.addColumn("Maximum");
		mp.addColumn("Select");
		mp.addColumn("Color");
		DefaultTableColumnModel colModel = (DefaultTableColumnModel) tb.getColumnModel();
		int wo = colModel.getTotalColumnWidth();

		TableColumn col0 = colModel.getColumn(0);
		col0.setPreferredWidth((wo * 30) / 100);

		TableColumn col1 = colModel.getColumn(1);
		col1.setPreferredWidth((wo * 25) / 100);

		TableColumn col2 = colModel.getColumn(2);
		col2.setPreferredWidth((wo * 13) / 100);

		TableColumn col3 = colModel.getColumn(3);
		col3.setPreferredWidth((wo * 13) / 100);

		TableColumn col4 = colModel.getColumn(4);
		col4.setPreferredWidth((wo * 9) / 100);

		TableColumn col5 = colModel.getColumn(5);
		col5.setPreferredWidth((wo * 10) / 100);

	}
	
	public void setSaved_1D_ScanPanel(Saved_1D_ScanPanel saved_1D_ScanPanel) {
		
		this.saved_1D_ScanPanel = saved_1D_ScanPanel;
	}

	public boolean isSelected() {
		return hidden;
	}

	public void setHidden(boolean b) {
		hidden = b;
	}

	public void setDefaultPositioner() {
		/**
		 * Default positioner is always the first positioner
		 */
		HpTableModel mp = (HpTableModel) posTable.getModel();
		Object obj = mp.getValueAt(0, 4);
		JRadioButton rb = (JRadioButton) obj;
		rb.doClick();
	}

	public void addNewPosData(String str1, String str2, double d1, double d2) {
		HpTableModel mp = (HpTableModel) posTable.getModel();
		JRadioButton rb1 = new JRadioButton();
		posTable.getColumn("Select").setCellRenderer(new RadioButtonRenderer());
		posTable.getColumn("Select").setCellEditor(new RadioButtonEditor(new JCheckBox()));

		mp.addRow(new Object[] { str1, str2, new Double(d1), new Double(d2), rb1 });
		int hrow = mp.getRowCount() - 1;
		buttonGroup1.add((JRadioButton) mp.getValueAt(hrow, 4));
		setDefaultPositioner();
	}

	public void addNewDetData(String str1, String str2, double d1, double d2) {
		HpTableModel mp = (HpTableModel) detTable.getModel();
		JRadioButton rb1 = new JRadioButton();
		JButton jb1 = new JButton("Color");
		detTable.getColumn("Select").setCellRenderer(new RadioButtonRenderer());
		detTable.getColumn("Select").setCellEditor(new RadioButtonEditor(new JCheckBox()));
		detTable.getColumn("Color").setCellRenderer(new JButtonRenderer());
		detTable.getColumn("Color").setCellEditor(new JButtonEditor(new JCheckBox()));
		rb1.doClick();
		mp.addRow(new Object[] { str1, str2, new Double(d1), new Double(d2), rb1, jb1 });
		int hrow = mp.getRowCount() - 1;
		/*
		if (selectedDetectorsForDisplay.contains(hrow)) {
			rb1.doClick();
		}
		*/
	}
		

	class EvenOddRenderer implements TableCellRenderer {

		public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
			((JLabel) renderer).setOpaque(true);
			Color foreground, background;
			if (hidden) {
				foreground = new Color(0xDC, 0xDC, 0xDC);
				background = new Color(0x69, 0x69, 0x69);
			} else {
				background = new Color(0xFF, 0xFA, 0xCD);
				foreground = new Color(0x00, 0x00, 0xCD);
			}
			renderer.setForeground(foreground);
			renderer.setBackground(background);
			return renderer;
		}
	}

	class HpTableModel extends DefaultTableModel {
		/*
		 * Don't need to implement this method unless your table's editable.
		 */
		public boolean isCellEditable(int row, int col) {
			// Note that the data/cell address is constant,
			// no matter where the cell appears onscreen.
			if (col > 3) {
				return true;
			} else {
				return false;
			}
		}
	}

	class RadioButtonRenderer implements TableCellRenderer {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (value == null) {
				return null;
			}
			return (Component) value;
		}
	}

	class RadioButtonEditor extends DefaultCellEditor implements ItemListener {
		private JRadioButton button;

		public RadioButtonEditor(JCheckBox checkBox) {
			super(checkBox);
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			if (value == null) {
				return null;
			}

			button = (JRadioButton) value;

			button.addItemListener(this);
			return (Component) value;

		}

		public Object getCellEditorValue() {
			button.removeItemListener(this);
			return button;
		}

		public void itemStateChanged(ItemEvent e) {
			super.fireEditingStopped();
		}
	}

	class JButtonRenderer extends JButton implements TableCellRenderer {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {

			if (isSelected) {

			}
			setText("Color");
			return this;
		}
	}

	class JButtonEditor extends DefaultCellEditor implements ItemListener {
		private JButton button;
		private String label;
		private boolean isPushed;

		public JButtonEditor(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton();
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
				}
			});
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			if (isSelected) {

			}
			isPushed = true;
			button.setText(label);
			return button;
		}

		public Object getCellEditorValue() {
			if (isPushed) {

			}
			isPushed = false;
			return "Color";
		}

		public void itemStateChanged(ItemEvent e) {
			super.fireEditingStopped();
		}

		public boolean stopCellEditing() {
			isPushed = false;
			return super.stopCellEditing();
		}

		protected void fireEditingStopped() {
			super.fireEditingStopped();
		}
	}

	public class HPTableModelListener implements TableModelListener {
		JTable table;
		HpTableModel mp;

		// It is necessary to keep the table since it is not possible
		// to determine the table from the event's source
		HPTableModelListener(JTable table) {
			this.table = table;
			mp = (HpTableModel) table.getModel();
		}

		public void tableChanged(TableModelEvent e) {
			int firstRow = e.getFirstRow();
			int lastRow = e.getLastRow();
			int mColIndex = e.getColumn();
			switch (e.getType()) {
			case TableModelEvent.INSERT:

				// The inserted rows are in the range [firstRow, lastRow]
				for (int r = firstRow; r <= lastRow; r++) {
					// Row r was inserted
				}
				break;
			case TableModelEvent.UPDATE:
				if (firstRow == TableModelEvent.HEADER_ROW) {
					if (mColIndex == TableModelEvent.ALL_COLUMNS) {
						// A column was added
					} else {
						// Column mColIndex in header changed
					}
				} else {
					// The rows in the range [firstRow, lastRow] changed
					for (int r = firstRow; r <= lastRow; r++) {
						// Row r was changed

						if (mColIndex == TableModelEvent.ALL_COLUMNS) {
							// All columns in the range of rows have changed
						} else {
							// Column mColIndex changed
							if (mColIndex == 4) {
								Object obj = mp.getValueAt(firstRow, mColIndex);
								JRadioButton rb = (JRadioButton) obj;
								Object dObj = chart.getDataView(dataViewNumber).getDataSource();
								chart.getDataView(dataViewNumber).getXAxis().setMaxIsDefault(true);
								chart.getDataView(dataViewNumber).getXAxis().setMinIsDefault(true);

//								System.out.println(" File name picked = "+((FileNameI)dObj).getFileName());
								if (table.getName() == "Dets") {
									((DetectorDisplayI) dObj).setDetectorForDisplay(firstRow, rb.isSelected());
								} else if (table.getName() == "Pos") {
									((PositionerDisplayI) dObj).setSelectedPositioner(firstRow);
									((PositionerDisplayI) dObj).calculatePosDetMinMax();
									((UpdateDisplayI)dObj).updateDisplay();
									
									saved_1D_ScanPanel.setXScaleAuto(true);

								}
							} else if (mColIndex == 5) {
								Object obj = mp.getValueAt(firstRow, mColIndex);
								Object dObj = chart.getDataView(dataViewNumber).getDataSource();

								StylePicker2 sp = new StylePicker2();
								int thickness = ((DetectorDisplayI) dObj).getSeriesThickness(firstRow);
								int shape = ((DetectorDisplayI) dObj).getSeriesSymbol(firstRow);
								int symbolSize = ((DetectorDisplayI) dObj).getSeriesSymbolSize(firstRow);

								sp.setThickness(thickness);
								sp.setShape(shape);
								sp.setSymbolSize(symbolSize);
								sp.setVisible(true);
								int ret = sp.showDialog(null);

								if (ret == 0) {
									if (sp.isColorPicked()) {
										((DetectorDisplayI) dObj).setSeriesColor(sp.getSelectedColor(), firstRow);
									}

									if (sp.isThicknessPicked()) {
										((DetectorDisplayI) dObj).setSeriesThickness(sp.getThickness(), firstRow);
									}

									if (sp.isShapePicked()) {
										((DetectorDisplayI) dObj).setSeriesSymbol(sp.getShape(), firstRow);
									}

									if (sp.isSymbolSizePicked()) {
										((DetectorDisplayI) dObj).setSeriesSymbolSize(sp.getSymbolSize(), firstRow);
									}
								}
							}
						}
					}
				}
				break;
			case TableModelEvent.DELETE:

				// The rows in the range [firstRow, lastRow] changed
				for (int r = firstRow; r <= lastRow; r++) {
					// Row r was deleted
				}
				break;
			}
		}
	}
	
	   public void hideButton_actionPerformed(ActionEvent e) {
		      chart.getDataView(dataViewNumber).setVisible(false);
		      ((UpdateDisplayI)chart.getDataView(dataViewNumber).getDataSource()).updateDisplay();

		      setHidden(true);
		      posTable.updateUI();
		      detTable.updateUI();
//		      userAutoScale.setXMinMax();
		   }

		   public void showButton_actionPerformed(ActionEvent e) {
		      chart.getDataView(dataViewNumber).setVisible(true);
		      ((UpdateDisplayI)chart.getDataView(dataViewNumber).getDataSource()).updateDisplay();
		      setHidden(false);
		      posTable.updateUI();
		      detTable.updateUI();
		      
//		      userAutoScale.setXMinMax();

		   }


}

class Old_1D_Panel_hideButton_actionAdapter implements ActionListener {
	   private Old_1D_Panel adaptee;
	   Old_1D_Panel_hideButton_actionAdapter(Old_1D_Panel adaptee) {
	      this.adaptee = adaptee;
	   }

	   public void actionPerformed(ActionEvent e) {
	      adaptee.hideButton_actionPerformed(e);
	   }
	}

class Old_1D_Panel_showButton_actionAdapter implements ActionListener {
	   private Old_1D_Panel adaptee;
	   Old_1D_Panel_showButton_actionAdapter(Old_1D_Panel adaptee) {
	      this.adaptee = adaptee;
	   }

	   public void actionPerformed(ActionEvent e) {
	      adaptee.showButton_actionPerformed(e);
	   }
	}
