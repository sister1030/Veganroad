package view.member;

import controller.MemberController;
import model.MemberDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends JFrame {
    private static String sessionID;
    private static int auth;
    private JPanel panel;
    private JTextField idText;
    private JPasswordField passwordText;
    private JButton loginButton;
    private JLabel join;
    private MemberController memberController = MemberController.getInstance();

    public Login() {
        super("Login");
        join.setFont(new Font("Serif", Font.BOLD, 11));
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        loginButton.addActionListener(e -> {
            MemberDTO member = new MemberDTO();
            member.setId(idText.getText());
            member.setPassword(new String(passwordText.getPassword()));

            int count = memberController.login(member);
            if (count == 1) {
                JOptionPane.showMessageDialog(null, "로그인 성공");
                sessionID = idText.getText();
                auth = memberController.getMember(idText.getText()).getAuth();
                dispose();
                memberController.mainView();
            } else
                JOptionPane.showMessageDialog(null, "로그인 실패");
        });

        join.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                memberController.joinView();
            }
        });
    }

    public static String getSessionID() {
        return sessionID;
    }

    public static void invalidateSessionID() {
        sessionID = null;
    }

    public static int getAuth() {
        return auth;
    }

}
