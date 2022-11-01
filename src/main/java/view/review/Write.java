package view.review;

import controller.ReviewController;
import model.ReviewDTO;
import model.StoreDTO;
import view.member.Login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Write extends JFrame {
    private JTextField title;
    private JTextField storeName;
    private JTextField id;
    private JTextArea content;
    private JButton save;
    private JButton back;
    private JPanel panel;
    private ReviewController reviewController = ReviewController.getInstance();
    private ReviewDTO review = new ReviewDTO();

    public Write(StoreDTO store) {
        super("Review Write");
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        storeName.setText(store.getName());
        id.setText(Login.getSessionID());

        save.addActionListener(e -> {
            review.setStore_id(store.getStore_id());
            review.setId(Login.getSessionID());
            review.setTitle(title.getText());
            review.setContent(content.getText());

            int result = reviewController.write(review);
            if(result == 1){
                JOptionPane.showMessageDialog(null,"작성 성공");
                dispose();
            }else{
                JOptionPane.showMessageDialog(null, "작성 실패");
                dispose();
            }
            reviewController.reviewList(store.getStore_id());
        });

        back.addActionListener(e -> {
            dispose();
            reviewController.reviewList(store.getStore_id());
        });
    }

}
