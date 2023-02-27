package testable.localdate.step4;

import testable.subs.*;

import java.time.LocalDate;

import static testable.subs.Grade.GOLD;


public class UserPointCalculator {
    private SubscriptionDao subscriptionDao;
    private ProductDao productDao;
    private LocalDate now = LocalDate.now();
    //포인트계산 하는 룰을 클래스로 분리했기 때문에 멤버 변수 선언
    private PointRule pointRule = new PointRule();

    public UserPointCalculator(SubscriptionDao subscriptionDao,
                               ProductDao productDao) {
        this.subscriptionDao = subscriptionDao;
        this.productDao = productDao;
    }

    //외부에서 주입할 수 있게 setter 추가함
    public void setPointRule(PointRule pointRule) {
        this.pointRule = pointRule;
    }

    public void setTimes(LocalDate now) {
        this.now = now;
    }

    //해당 메서드를 테스트 하는 것이 아니라 PointRule.calculate()메서드를 테스트 하면 됨
    public int calculatePoint(User u) {
        Subscription s = subscriptionDao.selectByUser(u.getId());
        if (s == null) throw new NoSubscriptionException();
        Product p = productDao.selectById(s.getProductId());
        if (p == null) throw new NoProductException();
        return pointRule.calculate(s, p, now);
    }
}
