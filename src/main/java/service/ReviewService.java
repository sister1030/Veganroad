package service;

import lombok.RequiredArgsConstructor;
import model.ReserveDTO;
import model.ReviewDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final SessionFactory factory;

    public List<ReviewDTO> list(int store_id) {
        return factory.getInstance().selectList("ReviewMapper.list", store_id);
    }

    public ReviewDTO getReview(int review_id) {
        return factory.getInstance().selectOne("ReviewMapper.getReview", review_id);
    }

    public int write(ReviewDTO review) {
        return factory.getInstance().insert("ReviewMapper.write", review);
    }

    public int modify(ReviewDTO review) {
        return factory.getInstance().update("ReviewMapper.modify", review);
    }

    public int delete(int review_id) {
        return factory.getInstance().delete("ReviewMapper.delete", review_id);
    }

}
