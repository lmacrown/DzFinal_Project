package mock.spy;

import java.util.ArrayList;
import java.util.Collection;

/*
MailingService는 sendMail을 호출할 때마다 보낸 메일을 저장하고 몇 번 보냈는지를 체크한다.

그리고 나중에 메일을 보낸 횟수를 물어볼 때 sendMailCount 변수에 저장된 값을 반환한다.

이처럼 자기 자신이 호출된 상황을 확인할 수 있는 객체가 Spy이다.

이 또한 Mockito 프레임워크의 verify() 메서드가 같은 역할을 한다.

 */
public class MailingService {
    private int sendMailCount = 0;
    private Collection<Mail> mails = new ArrayList<>();

    public void sendMail(Mail mail) {
        sendMailCount++;
        mails.add(mail);
    }

    public long getSendMailCount() {
        return sendMailCount;
    }
}
