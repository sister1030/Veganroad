package view.store;

import controller.MemberController;
import controller.StoreController;
import model.MenuDTO;
import model.StoreDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Menu extends JFrame {

    private JPanel menuPanel;
    private JTextField foodName;
    private JButton add;
    private JButton delete;
    private JTextField price;
    private JButton submit;
    private JPanel tablePanel;
    private JButton cancel;
    private MemberController memberController = MemberController.getInstance();
    private StoreController storeController = StoreController.getInstance();
    List<MenuDTO> menuList = new ArrayList<>();

    public Menu(StoreDTO store, File file) {
        super("Menu");
        setContentPane(menuPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setSize(450, 300);
        setVisible(true);

        DefaultTableModel model = new DefaultTableModel(new String[]{"메뉴명", "가격"}, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane);

        add.addActionListener(e -> {
            String[] rows = new String[2];
            rows[0] = foodName.getText();
            rows[1] = price.getText();
            model.addRow(rows);

            foodName.setText("");
            price.setText("");

            String name = rows[0];
            String price = rows[1];
            MenuDTO menu = new MenuDTO();
            menu.setName(name);
            menu.setPrice(Integer.parseInt(price));
            menuList.add(menu);


        });
        price.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                e.getSource();
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    JOptionPane.showMessageDialog(null, "숫자만 입력해주세요.");
                    price.setText("");
                    e.consume();
                }
            }
        });

        delete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;
            model.removeRow(row);
            menuList.remove(row);
        });

        submit.addActionListener(e -> {
            int count = JOptionPane.showConfirmDialog(null, "식당 등록을 진행하시겠습니까?", "Upload", JOptionPane.YES_NO_OPTION);
            if (count == JOptionPane.YES_OPTION && !menuList.isEmpty()) {
                int result = storeController.upload(store, menuList);
                if (result == 1) {
                    saveFile(file, "c:/teamPrjUpload", file.getName());
                    JOptionPane.showMessageDialog(null, "식당등록 완료");
                } else
                    JOptionPane.showMessageDialog(null, "식당등록 실패");
                dispose();
                memberController.mainView();
            } else if (count == JOptionPane.YES_OPTION && menuList.isEmpty()) {
                JOptionPane.showMessageDialog(null, "메뉴를 1개이상 등록해주세요.");
            }
        });

        cancel.addActionListener(e -> {
            dispose();
            memberController.mainView();
        });
    }

    private static void saveFile(File file, String path, String fileName) {
        if (!new File(path).exists())
            new File(path).mkdir();

        String filePath = path + "/" + fileName;

        try {
            FileInputStream is = new FileInputStream(file);
            FileOutputStream os = new FileOutputStream(filePath);

            int i;
            byte[] buffer = new byte[1024];
            while ((i = is.read(buffer, 0, 1024)) != -1)
                os.write(buffer, 0, i);

            os.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
