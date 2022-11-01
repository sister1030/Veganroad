package view.review;

import controller.ReviewController;
import model.ReviewDTO;
import model.StoreDTO;

import javax.swing.*;

public class Modify extends JFrame {
    private JTextArea content;
    private JButton save;
    private JButton back;
    private JTextField id;
    private JTextField title;
    private JTextField storeName;
    private JPanel panel;
    private ReviewController reviewController = ReviewController.getInstance();

    public Modify(ReviewDTO review, StoreDTO store) {
        super("Review Modify");
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        title.setText(review.getTitle());
        storeName.setText(store.getName());
        id.setText(review.getId());
        content.setText(review.getContent());

        save.addActionListener(e -> {
            if (title.getText().equals("") || content.getText().equals(""))
                JOptionPane.showMessageDialog(null, "공백란이 존재합니다. 다시 작성해주세요.");
            else {
                review.setTitle(title.getText());
                review.setContent(content.getText());

                int result = reviewController.modify(review);
                if (result == 1) {
                    JOptionPane.showMessageDialog(null, "수정 성공");
                    dispose();
                    reviewController.reviewList(store.getStore_id());
                } else {
                    JOptionPane.showMessageDialog(null, "수정 실패");
                    dispose();
                    reviewController.detail(review.getReview_id());
                }
            }
        });

        back.addActionListener(e -> {
            dispose();
            reviewController.reviewList(store.getStore_id());
        });
    }
}
