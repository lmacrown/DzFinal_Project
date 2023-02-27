package testable.localdate.step2;

import testable.subs.*;

import java.time.LocalDate;

import static testable.subs.Grade.GOLD;

public class UserPointCalculator {
    private SubscriptionDao subscriptionDao;
    private ProductDao productDao;
    //멤버 변수 선언 후 초기값 설정
    private LocalDate now = LocalDate.now();

    public UserPointCalculator(SubscriptionDao subscriptionDao,
                               ProductDao productDao) {
        this.subscriptionDao = subscriptionDao;
        this.productDao = productDao;
    }

    //setter를 구현 한다
    public void setTimes(LocalDate now) {
        this.now = now;
    }

    public int calculatePoint(User u) {
        Subscription s = subscriptionDao.selectByUser(u.getId());
        if (s == null) throw new NoSubscriptionException();
        Product p = productDao.selectById(s.getProductId());
        int point = 0;
        //멤버 변수로 리팩토링한다
        if (s.isFinished(now)) {
            point += p.getDefaultPoint();
        } else {
            point += p.getDefaultPoint() + 10;
        }
        if (s.getGrade() == GOLD) {
            point += 100;
        }
        return point;
    }
}
