package view.search;

import controller.ReviewController;
import controller.SearchController;
import controller.StoreController;
import model.StoreDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SearchList extends JFrame {


    private JComboBox vegan;
    private JTextField searchText;
    private JPanel panel;
    private JButton searchButton;
    private JTable table1;
    private StoreController storeController = StoreController.getInstance();

    public SearchList(java.util.List<StoreDTO> list) {
        super("Search");
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        String[] columns = {"#", "식당명", "비건 분류", "카테고리", "주소"};
        Object[][] rows = new Object[list.size()][columns.length];

        for (int i = 0; i < list.size(); i++) {
            rows[i] = new Object[]{
                    list.get(i).getStore_id(),
                    list.get(i).getName(),
                    list.get(i).getVegan(),
                    list.get(i).getCategory(),
                    list.get(i).getAddress()
            };
        }

        table1.setModel(new DefaultTableModel(rows, columns) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        });

        table1.getTableHeader().setReorderingAllowed(false);
        table1.getTableHeader().setResizingAllowed(false);

        table1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table1.getSelectedRow();
                int col = table1.getSelectedColumn();
                if (col == 1) {
                    int store_id = (Integer) table1.getValueAt(row, col - 1);

                    dispose();
                    storeController.store(store_id);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

}
