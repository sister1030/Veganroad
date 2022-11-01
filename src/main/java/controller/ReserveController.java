package controller;

import lombok.RequiredArgsConstructor;
import model.ReserveDTO;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import service.ReserveService;
import view.member.ReserveList;

@Controller
@RequiredArgsConstructor
public class ReserveController {

    private final ReserveService reserveService;

    public void reserveList(String id) {
        new ReserveList(reserveService.getReserveList(id));
    }

    public int getIdCount(ReserveDTO reserve) {
        return reserveService.getIdCount(reserve);
    }

    public int getReserveCount(ReserveDTO reserve) {
        return reserveService.getReserveCount(reserve);
    }

    public void addReserve(ReserveDTO reserve) {
        reserveService.addReserve(reserve);
    }

    public void cancelReserve(int reserve_id) {
        reserveService.cancelReserve(reserve_id);
    }

    private static ReserveController instance;

    public static ReserveController getInstance() {
        if (instance == null)
            instance = new ClassPathXmlApplicationContext("bean-config.xml").getBean("reserveController", ReserveController.class);

        return instance;
    }

}
