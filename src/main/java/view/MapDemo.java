package view;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.view.swing.BrowserView;
import controller.SearchController;
import controller.StoreController;
import service.SessionFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

public class MapDemo extends JFrame {

    private JPanel panelMain;
    private JPanel map_panel;

    Engine engine = Engine.newInstance(
            EngineOptions.newBuilder(HARDWARE_ACCELERATED)
                    .licenseKey("1BNDHFSC1G49S3HTBQ1QD2BBIDDX0CE9YQVWSEOQQW469OA39QJ6ARACCO25ZCWS7JP9UY")
                    .build());
    Browser browser;
    SessionFactory factory;
    // Initialize Chromium.
    public MapDemo() {
//        storeController.mapGet(store_id);


        System.setProperty("jxbrowser.license.key", "1BNDHFSC1G49S3HTBQ1QD2BBIDDX0CE9YQVWSEOQQW469OA39QJ6ARACCO25ZCWS7JP9UY");
        browser = engine.newBrowser();

        setSize(400,500);
        SwingUtilities.invokeLater(() -> {
            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    // Shutdown Chromium and release allocated resources.
                    engine.close();
                }
            });

        });
        map_panel.add(BrowserView.newInstance(browser), BorderLayout.CENTER);

        browser.navigation().loadUrl("D:///teamPrj/src/main/resources/static/geomap.html");
//        D:///teamPrj/src/main/resources/static/geomap.html  http://desktop-66jo7rp:9099/geocodedata_team_view?team_number=4
        setContentPane(panelMain);
        Toolkit.getDefaultToolkit().setDynamicLayout(true);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }
}




