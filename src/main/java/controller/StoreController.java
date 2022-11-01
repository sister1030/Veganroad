package controller;

import lombok.RequiredArgsConstructor;
import model.MenuDTO;
import model.StoreDTO;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import service.StoreService;
import view.store.Menu;
import view.store.Store;
import view.store.StoreList;
import view.store.Upload;

import java.io.File;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    public void StoreList(String vegan) {
        List<StoreDTO> list = storeService.getListByVegan(vegan);

        new StoreList(list);
    }

    public void StoreList(StoreDTO store) {
        List<StoreDTO> list = storeService.getListByDynamic(store);

        new StoreList(list);
    }

    public void uploadView() {
        new Upload();
    }

    public int upload(StoreDTO store, List<MenuDTO> menuList) {
        return storeService.upload(store, menuList);
    }

    public void menuView(StoreDTO store, File file) {
        new Menu(store, file);
    }

    public void store(int store_id) {
        StoreDTO store = storeService.getStore(store_id);
        List<MenuDTO> menuList = storeService.getMenuList(store_id);
        new Store(store, menuList);
    }

    private static StoreController instance;

    public static StoreController getInstance() {
        if (instance == null)
            instance = new ClassPathXmlApplicationContext("bean-config.xml").getBean("storeController", StoreController.class);

        return instance;
    }

}
