package service;

import lombok.RequiredArgsConstructor;
import model.StoreDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SessionFactory factory;

    public List<StoreDTO> searchList(String vegan) {
        System.out.println("SERVICE + " + vegan);
        return factory.getInstance().selectList("SearchMapper.getListByVegan", vegan);
    } // .뒤에가 id , 뒤에가 parameter

    public List<StoreDTO> addrList(StoreDTO storeDTO) {
        System.out.println("SERVICE + " + storeDTO);
        return factory.getInstance().selectList("SearchMapper.address", storeDTO);
    } // .뒤에가 id , 뒤에가 parameter

}

