package controller;

import lombok.RequiredArgsConstructor;
import model.ReviewDTO;
import model.StoreDTO;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import service.ReviewService;
import service.StoreService;
import view.review.Detail;
import view.review.Modify;
import view.review.ReviewList;
import view.review.Write;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final StoreService storeService;

    public void reviewList(int store_id) {
        List<ReviewDTO> list = reviewService.list(store_id);

        if (list.isEmpty()) {
            list.add(new ReviewDTO());
            list.get(0).setStore_id(store_id);
        }

        new ReviewList(list);
    }

    public void detail(int review_id) {
        ReviewDTO review = reviewService.getReview(review_id);
        StoreDTO store = storeService.getStore(review.getStore_id());

        new Detail(review, store);
    }

    public void writeView(int store_id) {
        StoreDTO store = storeService.getStore(store_id);

        new Write(store);
    }

    public int write(ReviewDTO review) {
        return reviewService.write(review);
    }

    public void modifyView(int review_id) {
        ReviewDTO review = reviewService.getReview(review_id);
        StoreDTO store = storeService.getStore(review.getStore_id());

        new Modify(review, store);
    }

    public int modify(ReviewDTO review) {
        return reviewService.modify(review);
    }

    public int delete(int review_id) {
        return reviewService.delete(review_id);
    }

    private static ReviewController instance;

    public static ReviewController getInstance() {
        if (instance == null)
            instance = new ClassPathXmlApplicationContext("bean-config.xml").getBean("reviewController", ReviewController.class);

        return instance;
    }

}
