package view.member;

import controller.MemberController;
import model.MemberDTO;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Join extends JFrame {

    private JPanel panel;
    private JButton idCheck;
    private JTextField nameText;
    private JComboBox telBox;
    private JTextField contactText2;
    private JTextField contactText1;
    private JTextField emailText;
    private JComboBox emailBox;
    private JTextField idText;
    private JButton back;
    private JButton next;
    private JTextField addressText;
    private JPasswordField passwordText;
    private JPasswordField passwordCheck;
    private JLabel passwordAlert;
    private MemberController memberController = MemberController.getInstance();
    private static MemberDTO member = new MemberDTO();
    private static boolean IDChecked = false;
    private static boolean PasswordChecked = false;

    public Join() {
        super("Join");
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        idCheck.addActionListener(e -> {
            if (idText.getText().equals(""))
                JOptionPane.showMessageDialog(null, "아이디가 공백입니다.");
            else {
                int result = memberController.idCheck(idText.getText());
                if (result == 1) {
                    JOptionPane.showMessageDialog(null, "이미 존재하는 아이디입니다.");
                    idText.setText("");
                    IDChecked = false;
                } else {
                    JOptionPane.showMessageDialog(null, "사용 가능한 아이디입니다.");
                    member.setId(idText.getText());
                    IDChecked = true;
                }
            }
        });

        passwordText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
            }

            public void focusLost(FocusEvent e) {
                if (new String(passwordText.getPassword()).length() < 4) {
                    JOptionPane.showMessageDialog(null, "비밀번호는 최소 4자리 이상 설정 가능합니다.");
                    passwordText.setText("");
                } else {
                    member.setPassword(new String(passwordText.getPassword()));
                }
            }
        });

        passwordCheck.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
            }

            public void focusLost(FocusEvent e) {
                if (!new String(passwordText.getPassword()).equals(new String(passwordCheck.getPassword()))) {
                    //JOptionPane.showMessageDialog(null, "비밀번호와 확인 비밀번호가 일치하지 않습니다.");
                    passwordAlert.setText("비밀번호와 확인 비밀번호가 일치하지 않습니다.");
                    passwordCheck.setText("");
                    PasswordChecked = false;
                } else {
                    member.setPassword(new String(passwordText.getPassword()));
                    passwordAlert.setText("");
                    PasswordChecked = true;
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

        next.addActionListener(e -> {
            member.setName(nameText.getText());
            member.setEmail(emailText.getText() + '@' + emailBox.getSelectedItem());
            member.setContact(telBox.getSelectedItem() + "-" + contactText1.getText() + "-" + contactText2.getText());
            member.setAddress(addressText.getText());

            if (IDChecked == false || !idText.getText().equals(member.getId())) {
                JOptionPane.showMessageDialog(null, "아이디 중복 검사를 해주세요.");
                return;
            }
            if (!member.getPassword().equals(new String(passwordCheck.getPassword())))
                return;
            if (member.getName().equals("") || member.getId().equals("") || member.getEmail().equals("") || member.getPassword().equals("") || new String(passwordCheck.getPassword()).equals("") ||
                    contactText1.getText().equals("") || contactText2.getText().equals("") || member.getAddress().equals("") || member.getContact().equals("")) {
                JOptionPane.showMessageDialog(null, "공백란이 존재합니다. 다시 작성해주세요.");
                return;
            }
            if (IDChecked == true && PasswordChecked == true && idText.getText().equals(member.getId())) {
                dispose();
                memberController.veganTest(member);
            }
        });

        back.addActionListener(e -> {
            dispose();
            memberController.loginView();
        });
    }

}