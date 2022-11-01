package view.store;

import controller.MemberController;
import controller.StoreController;
import model.StoreDTO;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;

public class Upload extends JFrame {

    private JButton next;
    private JButton back;
    private JTextField storeText;
    private JComboBox vegan;
    private JTextField contactText;
    private JTextField address;
    private JComboBox open;
    private JComboBox close;
    private JButton imageButton;
    private JTextArea detail;
    private JLabel imageLabel;
    private JPanel storePanel;
    private JTextField lat;
    private JTextField lon;
    private JTextField capacity;
    private JComboBox category;
    private JFileChooser chooser;
    private MemberController memberController = MemberController.getInstance();
    private StoreController storeController = StoreController.getInstance();
    private StoreDTO store = new StoreDTO();

    public Upload() {
        super("Upload");
        setContentPane(storePanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        imageButton.addActionListener(e -> {
            chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif", "png");
            chooser.setFileFilter(filter);
            int ret = chooser.showOpenDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {
                try {
                    loadImage(chooser.getSelectedFile().toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            String path = chooser.getSelectedFile().getPath();
            Image img = new ImageIcon(path).getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(img);
            imageLabel.setIcon(icon);

        });

        next.addActionListener(e -> {
            if (storeText.getText().equals("") || contactText.getText().equals("") || address.getText().equals("") || detail.getText().equals("") || capacity.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "식당 정보를 모두 입력해주세요");
            } else {
                store.setVegan((String) vegan.getSelectedItem());
                store.setCategory((String) category.getSelectedItem());
                store.setName(storeText.getText());
                store.setDetail(detail.getText());
                store.setAddress(address.getText());
                store.setContact(contactText.getText());
                store.setCapacity(Integer.parseInt(capacity.getText()));

                store.setOpen((String) open.getSelectedItem());
                store.setClose((String) close.getSelectedItem());

                store.setLat(Float.parseFloat(lat.getText()));
                store.setLon(Float.parseFloat(lon.getText()));

                File file = chooser.getSelectedFile();
                store.setImage(file.getName());

                dispose();
                storeController.menuView(store, file);
            }
        });

        capacity.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                e.getSource();
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    JOptionPane.showMessageDialog(null, "숫자만 입력해주세요.");
                    contactText.setText("");
                    e.consume();
                }
            }
        });

        back.addActionListener(e -> {
            dispose();
            memberController.mainView();
        });
    }

    private static void loadImage(String path) throws Exception {
        File file = new File(path);
        FileInputStream is = new FileInputStream(file.getPath());

        int size = 256;
        int[][] in = new int[size][size];
        int[][] out = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int k = 0; k < size; k++) {
                in[i][k] = is.read();
                out[i][k] = in[i][k];
            }
        }

        is.close();
    }

}
