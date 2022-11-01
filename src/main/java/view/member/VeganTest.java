package view.member;

import controller.MemberController;
import model.MemberDTO;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VeganTest extends JFrame {
    private MemberController memberController = MemberController.getInstance();
    static List<String> results;
    private JPanel veganPanel;
    private JRadioButton fruitButton;
    private JRadioButton vegetButton;
    private JRadioButton dairyButton;
    private JButton join;
    private JButton back;
    private JRadioButton eggButton;
    private JRadioButton seaButton;
    private JRadioButton poultryButton;
    private JRadioButton meatButton;

    public VeganTest(MemberDTO member) {
        super("Vegan Test");
        setContentPane(veganPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        join.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            if (fruitButton.isSelected()) sb.append("y");
            else sb.append("n");
            if (vegetButton.isSelected()) sb.append("y");
            else sb.append("n");
            if (dairyButton.isSelected()) sb.append("y");
            else sb.append("n");
            if (eggButton.isSelected()) sb.append("y");
            else sb.append("n");
            if (seaButton.isSelected()) sb.append("y");
            else sb.append("n");
            if (poultryButton.isSelected()) sb.append("y");
            else sb.append("n");
            if (meatButton.isSelected()) sb.append("y");
            else sb.append("n");

            match(sb.toString());
            if (results.get(0) != null){
                JOptionPane.showMessageDialog(null, "당신의 유형은 " + results.get(0) + "입니다.");
                member.setVegan(results.get(0));
                int result = memberController.join(member);
                if (result == 1) {
                    JOptionPane.showMessageDialog(null, "회원가입 성공");
                    dispose();
                    memberController.loginView();
                } else {
                    JOptionPane.showMessageDialog(null, "회원가입 실패");
                    dispose();
                    memberController.joinView();
                }
            }
        });

        back.addActionListener(e -> {
            dispose();
            JOptionPane.showMessageDialog(null, "회원가입을 취소하셨습니다.");
            memberController.loginView();
        });
    }

    public void match(String input){
        String[] vegans = {"프루테리언", "비건", "락토", "오보", "락토오보", "페스코", "폴로", "플렉시테리언"};
        String[] cases = {"ynnnnnn", "yynnnnn", "yyynnnn", "yynynnn", "yyyynnn", "yyyyynn", "yyyyyyn", "yyyyyyy"};

        List<Integer> count = new ArrayList<>(Collections.nCopies(8, 0));
        for (int i = 0; i < input.length(); i++) {
            char cur = input.charAt(i);
            for (int j = 0; j < cases.length; j++) {
                if (cur == cases[j].charAt(i))
                    count.set(j, count.get(j) + 1);
                else
                    count.set(j, count.get(j) - 1);
            }
        }
        int max = Collections.max(count);

        results = new ArrayList<>();
        for (int i = 0; i < count.size(); i++) {
            if (count.get(i) == max)
                results.add(vegans[i]);
        }
    }

}
