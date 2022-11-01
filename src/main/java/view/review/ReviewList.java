package view.review;

import controller.ReviewController;
import model.ReviewDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class ReviewList extends JFrame {

    private JTable table;
    private JPanel panel;
    private JButton write;
    private JButton close;
    private ReviewController reviewController = ReviewController.getInstance();

    public ReviewList(List<ReviewDTO> list) {
        super("Review List");
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setVisible(true);

        if (list.get(0).getReview_id() != 0) {
            String[] columns = {"#", "제목", "작성자", "작성일"};
            Object[][] rows = new Object[list.size()][columns.length];

            for (int i = 0; i < list.size(); i++) {
                rows[i] = new Object[]{
                        list.get(i).getReview_id(),
                        list.get(i).getTitle(),
                        list.get(i).getId(),
                        list.get(i).getRegister()
                };
            }

            table.setModel(new DefaultTableModel(rows, columns) {
                public boolean isCellEditable(int r, int c) {
                    return false;
                }
            });

            table.getTableHeader().setReorderingAllowed(false);
            table.getTableHeader().setResizingAllowed(false);

            int[] values = {25, 100, 75, 100};
            for (int i = 0; i < columns.length; i++) {
                int value = values[i];

                table.getColumnModel().getColumn(i).setWidth(value);
                table.getColumnModel().getColumn(i).setMinWidth(value);
                table.getColumnModel().getColumn(i).setMaxWidth(value);
            }
        }

        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();
                if (col == 1) {
                    int review_id = (Integer) table.getValueAt(row, col - 1);

                    dispose();
                    reviewController.detail(review_id);
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

        write.addActionListener(e -> {
            dispose();
            reviewController.writeView(list.get(0).getStore_id());
        });

        close.addActionListener(e -> dispose());
    }

}
