package service;

import lombok.RequiredArgsConstructor;
import model.MenuDTO;
import model.StoreDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final SessionFactory factory;

    public List<StoreDTO> getListByVegan(String vegan) {
        return factory.getInstance().selectList("StoreMapper.getListByVegan", vegan);
    }

    public List<StoreDTO> getListByDynamic(StoreDTO store) {
        return factory.getInstance().selectList("StoreMapper.getListByDynamic", store);
    }

    public int upload(StoreDTO store, List<MenuDTO> menuList) {
        int result = factory.getInstance().insert("StoreMapper.uploadStore", store);
        int store_id = store.getStore_id();

        for (MenuDTO menu : menuList) {
            menu.setStore_id(store_id);
            factory.getInstance().insert("StoreMapper.uploadMenu", menu);
        }

        return result;
    }

    public StoreDTO getStore(int store_id) {
        return factory.getInstance().selectOne("StoreMapper.getStore", store_id);
    }

    public List<MenuDTO> getMenuList(int store_id) {
        return factory.getInstance().selectList("StoreMapper.getMenuList", store_id);
    }

}
