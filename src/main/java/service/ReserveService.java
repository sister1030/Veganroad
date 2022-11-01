package service;

import lombok.RequiredArgsConstructor;
import model.ReserveDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReserveService {

    private final SessionFactory factory;

    public List<ReserveDTO> getReserveList(String id) {
        return factory.getInstance().selectList("ReserveMapper.getReserveList", id);
    }

    public int getIdCount(ReserveDTO reserve) {
        return factory.getInstance().selectOne("ReserveMapper.getIdCount", reserve);
    }

    public int getReserveCount(ReserveDTO reserve) {
        return factory.getInstance().selectOne("ReserveMapper.getReserveCount", reserve);
    }

    public void addReserve(ReserveDTO reserve) {
        factory.getInstance().insert("ReserveMapper.addReserve", reserve);
    }

    public void cancelReserve(int reserve_id) {
        factory.getInstance().delete("ReserveMapper.cancelReserve", reserve_id);
    }

}
