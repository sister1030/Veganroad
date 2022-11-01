package view;

import controller.MemberController;
import controller.ReserveController;
import controller.ReviewController;
import controller.StoreController;
import model.StoreDTO;
import view.member.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class Main extends JFrame {
    private JPanel panel;
    private JButton search;
    private JComboBox vegan;
    private JTextField address;
    private MemberController memberController = MemberController.getInstance();
    private ReviewController reviewController = ReviewController.getInstance();
    private StoreController storeController = StoreController.getInstance();
    private ReserveController reserveController = ReserveController.getInstance();

    public Main() {
        super("Main");
        createMenu();
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

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
            System.out.println(veganInput);
            System.out.println(addressInput);
            if (!veganInput.equals("비건 유형") && addressInput.equals("주소를 검색해주세요")) {
                dispose();
                storeController.StoreList(veganInput);
            } else if (!addressInput.equals("주소를 검색해주세요")) {
                StoreDTO store = new StoreDTO();
                store.setVegan(veganInput);
                store.setAddress(addressInput);

                dispose();
                storeController.StoreList(store);
            }
        });
    }

    void createMenu() {
        address.setForeground(Color.GRAY);

        JMenuBar menuBar = new JMenuBar();
        JMenu screenMenu1 = new JMenu("메뉴");
        JMenu screenMenu2 = new JMenu("식당 등록");

        screenMenu1.add("예약 내역").addActionListener(new MenuActionListener());
        screenMenu1.add("내 정보").addActionListener(new MenuActionListener());
        screenMenu1.add("로그아웃").addActionListener(new MenuActionListener());
        if (Login.getAuth() == 1) {
            screenMenu2.add("식당 등록").addActionListener(new MenuActionListener());
            menuBar.add(screenMenu2);
        }

        setJMenuBar(menuBar);
        menuBar.add(screenMenu1);
    }

    class MenuActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            switch (cmd) {
                case "예약 내역":
                    dispose();
                    reserveController.reserveList(Login.getSessionID());
                    break;
                case "내 정보":
                    dispose();
                    memberController.updateView();
                    break;
                case "식당 등록":
                    if (Login.getSessionID().equals("admin")) {
                        dispose();
                        storeController.uploadView();
                    } else
                        JOptionPane.showMessageDialog(null, "접근 권한이 없습니다.");
                    break;
                case "로그아웃":
                    JOptionPane.showMessageDialog(null, "로그아웃하셨습니다.");
                    Login.invalidateSessionID();
                    dispose();
                    memberController.loginView();
                    break;
            }
        }
    }

}
