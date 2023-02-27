package testable.localdate.step3;

import testable.subs.*;

import java.time.LocalDate;

import static testable.subs.Grade.GOLD;

public class UserPointCalculator {
    private SubscriptionDao subscriptionDao;
    private ProductDao productDao;
    private LocalDate now = LocalDate.now();

    public UserPointCalculator(SubscriptionDao subscriptionDao,
                               ProductDao productDao) {
        this.subscriptionDao = subscriptionDao;
        this.productDao = productDao;
    }

    public void setTimes(LocalDate now) {
        this.now = now;
    }

    public int calculatePoint(User u) {
        Subscription s = subscriptionDao.selectByUser(u.getId());
        if (s == null) throw new NoSubscriptionException();
        Product p = productDao.selectById(s.getProductId());

        //포인트 계산 하는 루틴이 Subscription, Product에 의존 한다
        //포인트 계산은 Subscription, Product 클래스에 의존하지 않게 분리하는 것이 좋음
        int point = 0;
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
