package view.store;

import controller.MemberController;
import controller.ReserveController;
import controller.ReviewController;
import model.MenuDTO;
import model.ReserveDTO;
import model.StoreDTO;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import view.MapDemo;
import view.member.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Store extends JFrame {

    private JPanel panel;
    private JLabel storeName;
    private JLabel vegan;
    private JLabel category;
    private JLabel contact;
    private JLabel address;
    private JTextField open;
    private JTextField close;
    private JTextArea detail;
    private JTextArea menu;
    private JPanel reservePanel;
    private JButton reserveButton;
    private JButton back;
    private JComboBox hour;
    private JComboBox minute;
    private JLabel image;
    private JButton map;
    private JButton review;
    private MemberController memberController = MemberController.getInstance();
    private ReserveController reserveController = ReserveController.getInstance();
    private ReviewController reviewController = ReviewController.getInstance();

    public Store(StoreDTO store, List<MenuDTO> menuList) {

        super("Store");
        UtilDateModel dateModel = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
        reservePanel.add(datePicker);

        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        if (store.getImage() != null) {
            String path = "c:/teamPrjUpload/" + store.getImage();
            Image img = new ImageIcon(path).getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(img);
            image.setIcon(icon);
        }

        storeName.setText(store.getName());
        vegan.setText(store.getVegan());
        category.setText(store.getCategory());
        contact.setText(store.getContact());
        address.setText(store.getAddress());
        open.setText(store.getOpen());
        close.setText(store.getClose());
        detail.setText(store.getDetail());

        StringBuilder sb = new StringBuilder();
        sb.append("메뉴명\t가격\n");
        for (MenuDTO menu : menuList)
            sb.append(menu.getName() + "\t" + menu.getPrice() + "원\n");
        menu.setText(sb.toString());

        reserveButton.addActionListener(e -> {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime chosen = LocalDateTime.of(dateModel.getYear(), dateModel.getMonth() + 1, dateModel.getDay(), Integer.parseInt(hour.getSelectedItem().toString()), Integer.parseInt(minute.getSelectedItem().toString()));
            String chosenDate = chosen.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:00"));

            int chosenYear = chosen.getYear();
            int chosenMonth = chosen.getMonthValue();
            int chosenDay = chosen.getDayOfMonth();
            int chosenWeek = chosen.getDayOfWeek().getValue();

            int openHour = Integer.parseInt(store.getOpen().substring(0, 2));
            int closeHour = Integer.parseInt(store.getClose().substring(0, 2));
            int chosenHour = Integer.parseInt(chosenDate.substring(11, 13));

            if (chosenYear != now.getYear()) {
                JOptionPane.showMessageDialog(null, "해당 년도에는 예약할 수 없습니다.");
                return;
            } else if (chosenMonth != now.getMonthValue()) {
                JOptionPane.showMessageDialog(null, "해당 월에는 예약할 수 없습니다.");
                return;
            } else if (chosenDay <= now.getDayOfMonth() || chosenDay > now.getDayOfMonth() + 7) {
                JOptionPane.showMessageDialog(null, "다음날을 기준으로 일주일 범위 내 예약 가능합니다.");
                return;
            } else if (chosenWeek == 6 || chosenWeek == 7) {
                JOptionPane.showMessageDialog(null, "주말에는 예약할 수 없습니다.");
                return;
            } else if (openHour > chosenHour || closeHour < chosenHour) {
                JOptionPane.showMessageDialog(null, "영업시간이 아닌 시간에는 예약할 수 없습니다.");
                return;
            }

            LocalDateTime start = LocalDateTime.now();
            String startTime = start.format(DateTimeFormatter.ofPattern("yyyy-MM-" + dateModel.getDay() + " " + Integer.parseInt(hour.getSelectedItem().toString()) + ":00:00"));

            LocalDateTime end = LocalDateTime.now();
            String endTime = end.format(DateTimeFormatter.ofPattern("yyyy-MM-" + dateModel.getDay() + " " + (Integer.parseInt(hour.getSelectedItem().toString()) + 1) + ":00:00"));

            ReserveDTO reserve = new ReserveDTO();
            reserve.setStore_id(store.getStore_id());
            reserve.setStore_name(store.getName());
            reserve.setId(Login.getSessionID());
            reserve.setReserve(chosenDate);
            reserve.setStart(startTime);
            reserve.setEnd(endTime);

            int idCount = reserveController.getIdCount(reserve);
            if (idCount >= 1) {
                JOptionPane.showMessageDialog(null, "해당 시간대(" + startTime.substring(11, 16) + " ~ " + endTime.substring(11, 16) + ")에 이미 예약한 내역이 있습니다.");
                return;
            }

            int reserveCount = reserveController.getReserveCount(reserve);
            if (reserveCount < store.getCapacity()) {
                reserveController.addReserve(reserve);
                JOptionPane.showMessageDialog(null, "예약 완료");
            } else
                JOptionPane.showMessageDialog(null, "해당 시간대(" + startTime.substring(11, 16) + " ~ " + endTime.substring(11, 16) + ")의 예약 가능 인원이 초과되었습니다.");
        });

        map.addActionListener(e -> {

        });

        review.addActionListener(e -> reviewController.reviewList(store.getStore_id()));

        back.addActionListener(e -> {
            dispose();
            memberController.mainView();
        });
        map.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            new MapDemo();
            }
        });
    }

}
