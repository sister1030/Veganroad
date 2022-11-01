package view.store;

import controller.MemberController;
import controller.StoreController;
import model.StoreDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class StoreList extends JFrame {
    private JComboBox vegan;
    private JTextField address;
    private JButton search;
    private JTable table;
    private JPanel panel;
    private JButton back;
    private MemberController memberController = MemberController.getInstance();
    private StoreController storeController = StoreController.getInstance();

    public StoreList(List<StoreDTO> list) {
        super("Store List");
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(525, 400);
        setVisible(true);

        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(null, "검색 결과가 없습니다.");
            dispose();
            memberController.mainView();
        }

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

        table.setModel(new DefaultTableModel(rows, columns) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        });

        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);

        int[] values = {25, 100, 75, 75, 250};
        for (int i = 0; i < columns.length; i++) {
            int value = values[i];

            table.getColumnModel().getColumn(i).setWidth(value);
            table.getColumnModel().getColumn(i).setMinWidth(value);
            table.getColumnModel().getColumn(i).setMaxWidth(value);
        }

        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();
                if (col == 1) {
                    int store_id = (Integer) table.getValueAt(row, col - 1);

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

        address.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (address.getText().equals("주소를 검색해주세요")) {
                    address.setText("");
                    address.setForeground(Color.black);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (address.getText().isEmpty()) {
                    address.setForeground(Color.gray);
                    address.setText("주소를 검색해주세요");
                }
            }
        });

        search.addActionListener(e -> {
            String veganInput = vegan.getSelectedItem().toString();
            String addressInput = address.getText();
            if (!veganInput.equals("비건 유형") && addressInput.equals("주소를 검색해주세요")) {
                dispose();
                storeController.StoreList(vegan.getSelectedItem().toString());
            } else if (!addressInput.equals("주소를 검색해주세요")) {
                StoreDTO store = new StoreDTO();
                store.setVegan(veganInput);
                store.setAddress(addressInput);

                dispose();
                storeController.StoreList(store);
            }
        });

        back.addActionListener(e -> {
            dispose();
            memberController.mainView();
        });
    }

}
