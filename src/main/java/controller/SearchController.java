package controller;

import lombok.RequiredArgsConstructor;
import model.StoreDTO;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import service.SearchService;
import service.StoreService;
import view.search.SearchList;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;
    private final StoreService storeService;
    private static SearchController instance;

    public void veganSearch(String vegan) {
        List<StoreDTO> list = searchService.searchList(vegan);
        new SearchList(list);
    }

    public void addrSearch(StoreDTO storeDTO) {
        List<StoreDTO> list = searchService.addrList(storeDTO);
        new SearchList(list);
    }

    public String test(List<StoreDTO> storeList, Model model) {
        model.addAttribute("storeList", storeList);
        return "static/geomap.html";
    }

    public static SearchController getInstance() {
        if (instance == null)
            instance = new ClassPathXmlApplicationContext("bean-config.xml").getBean("searchController", SearchController.class);

        return instance;
    }
}
