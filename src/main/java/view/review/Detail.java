package view.review;

import controller.ReviewController;
import model.ReviewDTO;
import model.StoreDTO;
import view.member.Login;

import javax.swing.*;

public class Detail extends JFrame {
    private JTextField title;
    private JTextField storeName;
    private JTextArea content;
    private JButton modify;
    private JButton delete;
    private JButton back;
    private JPanel panel;
    private JTextField id;
    private JTextField register;

    ReviewController reviewController = ReviewController.getInstance();

    public Detail(ReviewDTO review, StoreDTO store) {
        super("Review Detail");
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        title.setText(review.getTitle());
        storeName.setText(store.getName());
        id.setText(review.getId());
        register.setText(review.getRegister().toString());
        content.setText(review.getContent());

        modify.addActionListener(e -> {
            if (!review.getId().equals(Login.getSessionID()))
                JOptionPane.showMessageDialog(null, "수정 권한이 없습니다.");
            else {
                dispose();
                reviewController.modifyView(review.getReview_id());
            }
        });

        delete.addActionListener(e -> {
            if (!review.getId().equals(Login.getSessionID())) {
                JOptionPane.showMessageDialog(null, "삭제 권한이 없습니다.");
                return;
            }
            int result = reviewController.delete(review.getReview_id());
            if (result == 1) {
                JOptionPane.showMessageDialog(null, "삭제 성공");
                dispose();
                reviewController.reviewList(store.getStore_id());
            } else {
                JOptionPane.showMessageDialog(null, "삭제 실패");
                dispose();
                reviewController.detail(review.getReview_id());
            }
        });

        back.addActionListener(e -> {
            dispose();
            reviewController.reviewList(store.getStore_id());
        });
    }

}
