package view.member;

import controller.MemberController;
import model.MemberDTO;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyPage extends JFrame {

    private boolean checkCount = false;
    private JPanel updatePanel;
    private JTextField nameText;
    private JComboBox veganBox;
    private JButton veganCheck;
    private JComboBox telBox;
    private JTextField contactText2;
    private JTextField contactText1;
    private JTextField emailText;
    private JComboBox emailBox;
    private JTextField idText;
    private JButton back;
    private JButton update;
    private JTextField zipcodeText;
    private JPasswordField passwordText;
    private JPasswordField passwordCheck;
    private JButton quit;
    private MemberController memberController = MemberController.getInstance();
    private MemberDTO member;
    private boolean isPasswordChecked = false;

    public MyPage() {
        super("My Page");
        member = memberController.getMember(Login.getSessionID());

        idText.setText(member.getId());
        passwordText.setText(member.getPassword());
        nameText.setText(member.getName());

        int idx = member.getEmail().indexOf("@");
        emailText.setText(member.getEmail().substring(0, idx));
        emailBox.setSelectedItem(member.getEmail().substring(idx + 1));

        String contact[] = member.getContact().split("-");
        telBox.setSelectedItem(contact[0]);
        contactText1.setText(contact[1]);
        contactText2.setText(contact[2]);

        zipcodeText.setText(member.getAddress());
        veganBox.setSelectedItem(member.getVegan());

        setContentPane(updatePanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        passwordCheck.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                System.out.println(e);
            }

            public void focusLost(FocusEvent e) {
                if (!new String(passwordText.getPassword()).equals(new String(passwordCheck.getPassword()))) {
                    JOptionPane.showMessageDialog(null, "비밀번호와 확인 비밀번호가 일치하지 않습니다.");
                    passwordCheck.setText("");
                    isPasswordChecked = false;
                } else {
                    member.setPassword(new String(passwordText.getPassword()));
                    isPasswordChecked = true;
                }
            }
        });

        contactText1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                e.getSource();
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    JOptionPane.showMessageDialog(null, "숫자만 입력해주세요.");
                    contactText1.setText("");
                    e.consume();
                }
            }
        });
        contactText2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                e.getSource();
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    JOptionPane.showMessageDialog(null, "숫자만 입력해주세요.");
                    contactText2.setText("");
                    e.consume();
                }
            }
        });

        update.addActionListener(e -> {
            member.setName(nameText.getText());
            member.setEmail(emailText.getText() + '@' + emailBox.getSelectedItem());
            member.setContact(telBox.getSelectedItem() + "-" + contactText1.getText() + "-" + contactText2.getText());
            member.setAddress(zipcodeText.getText());
            member.setVegan((String) veganBox.getSelectedItem());

            if (!member.getPassword().equals(new String(passwordCheck.getPassword())))
                return;
            if (member.getName().equals("") || member.getEmail().equals("") || member.getPassword().equals("") || new String(passwordCheck.getPassword()).equals("") ||
                    contactText1.getText().equals("") || contactText2.getText().equals("") || member.getAddress().equals("") || member.getContact().equals("") || member.getVegan().equals("")) {
                JOptionPane.showMessageDialog(null, "공백란이 존재합니다. 다시 작성해주세요.");
                return;
            }

            if (isPasswordChecked == true) {
                int result = memberController.update(member);
                if (result == 1) {
                    JOptionPane.showMessageDialog(null, "회원정보 수정 성공");
                    dispose();
                    memberController.mainView();
                } else {
                    JOptionPane.showMessageDialog(null, "회원정보 수정 실패");
                    dispose();
                    memberController.updateView();
                }
            }
        });

        quit.addActionListener(e -> {
            int count = JOptionPane.showConfirmDialog(null, "탈퇴하시겠습니까?", "Quit", JOptionPane.YES_NO_OPTION);
            if (count == JOptionPane.YES_OPTION) {
                memberController.quit(Login.getSessionID());
                JOptionPane.showMessageDialog(null, "탈퇴 완료");

                Login.invalidateSessionID();
                dispose();
                memberController.loginView();
            }
        });

        back.addActionListener(e -> {
            dispose();
            memberController.mainView();
        });
    }

}