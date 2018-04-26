import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;



public class Calculation extends main{
		int rowcount = table.getModel().getRowCount();
		rmap = new HashMap<String, StudentScore>();
		if (rowcount > 1) {
			HashSet<String> studnetcousntset=new HashSet<String>();
			for (int i = 1; i < rowcount; i++) {
				studnetcousntset.add((String) table.getModel().getValueAt(i, 0));
			}
			float p1 = Float.valueOf(getComboxValue(slider.getSelectedItem().toString())) / 100;
			float p2 = Float.valueOf(getComboxValue(slider_1.getSelectedItem().toString())) / 100;
			float p3 = Float.valueOf(getComboxValue(slider_2.getSelectedItem().toString())) / 100;
			float p4 = Float.valueOf(getComboxValue(slider_3.getSelectedItem().toString())) / 100;
			float p5 = Float.valueOf(getComboxValue(slider_4.getSelectedItem().toString())) / 100;
			for (int i = 1; i < rowcount; i++) {
				float result = caculateValue((String) table.getModel().getValueAt(i, 2), p1)
						+ caculateValue((String) table.getModel().getValueAt(i, 3), p2)
						+ caculateValue((String) table.getModel().getValueAt(i, 4), p3)
						+ caculateValue((String) table.getModel().getValueAt(i, 5), p4)
						+ caculateValue((String) table.getModel().getValueAt(i, 6), p5);
				float myselfpersent = 0;
				if (table.getModel().getValueAt(i, 0).equals(table.getModel().getValueAt(i, 1))) {
					if(rdbtnYes.isSelected()){
						myselfpersent = Float.valueOf(slider_5.getValue()) / 100;
					}
					result = result * myselfpersent;
				} else {
					if(rdbtnYes.isSelected()){
						myselfpersent = Float.valueOf(100 - slider_5.getValue()) / 100;
					}else{
						myselfpersent=1;
					}
					result = (result /(studnetcousntset.size()-1))* myselfpersent;
				}
				if (result != 0) {
					if (rmap.get((String) table.getModel().getValueAt(i, 0)) != null) {
						StudentScore sc = rmap.get((String) table.getModel().getValueAt(i, 0));
						sc.setScore(sc.getScore() + result);
						if (result != 0.0)
							sc.setComments(sc.getComments() + "\n\r" + (String) table.getModel().getValueAt(i, 7));
						rmap.put((String) table.getModel().getValueAt(i, 0), sc);
					} else {
						StudentScore sc = new StudentScore();
						sc.setId((String) table.getModel().getValueAt(i, 0));
						sc.setScore(result);
						sc.setComments((String) table.getModel().getValueAt(i, 7));
						rmap.put((String) table.getModel().getValueAt(i, 0), sc);
					}
				}

			}

			newTableModel1 = getNewModel1();
			int commentflag=1;
			for (String rm : rmap.keySet()) {
				Vector<Object> vector = new Vector<Object>();
				vector.add(rm);
				vector.add(df.format(rmap.get(rm).getScore()));
				vector.add(commentflag+" "+rmap.get(rm).getComments());
				newTableModel1.addRow(vector);
				commentflag++;
			}

			table_1.setModel(newTableModel1);
			HiddenCell(table_1, 2);

		}
	}