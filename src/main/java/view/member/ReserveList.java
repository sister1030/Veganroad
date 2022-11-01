package view.member;

import controller.MemberController;
import controller.ReserveController;
import model.ReserveDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class ReserveList extends JFrame {
    private JPanel panel;
    private JTable table;
    private JButton back;
    private MemberController memberController = MemberController.getInstance();
    private ReserveController reserveController = ReserveController.getInstance();

    public ReserveList(List<ReserveDTO> reserveList) {
        super("Reserve List");
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(340, 400);
        setVisible(true);

        String[] columns = {"#", "식당명", "예약일", ""};
        Object[][] rows = new Object[reserveList.size()][columns.length];

        for (int i = 0; i < reserveList.size(); i++) {
            rows[i] = new Object[]{
                    reserveList.get(i).getReserve_id(),
                    reserveList.get(i).getStore_name(),
                    reserveList.get(i).getReserve(),
                    "취소"
            };
        }

        table.setModel(new DefaultTableModel(rows, columns) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        });

        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);

        int[] values = {25, 100, 150, 50};
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
                if (col == 3) {
                    int reserve_id = (Integer) table.getValueAt(row, col - 3);

                    reserveController.cancelReserve(reserve_id);
                    JOptionPane.showMessageDialog(null, "예약 취소 완료.");

                    dispose();
                    reserveController.reserveList(Login.getSessionID());
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

        back.addActionListener(e -> {
            dispose();
            memberController.mainView();
        });
    }

}
