package guide.goodcode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;

import guide.goodcode.user.*;
import lombok.Builder;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tdd.mock.carsale.ICarSaleStorage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class GoodCodeTest {

    private String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy년 M월 d일"));
    }

    @Test
    void 기대하는_값에_변수를_사용하는_예_테스트() {
        LocalDate date = LocalDate.of(2022, 3, 31);
        String dateStr = formatDate(date);

        //아래 코드는 문자열이 연결되는 구조 코딩되어 있어 복잡하다
        //다음과 같이 변경하는것이 좋다
        assertEquals(date.getYear() + "년 " +
                date.getMonthValue() + "월 " +
                date.getDayOfMonth() + "일", dateStr);
    }

    //변경된 코드 --> 직관적으로 이해하기 쉽고 관리하기 쉽다
    @Test
    void 기대하는_값에_문자열값을_사용하는_예_테스트() {
        LocalDate date = LocalDate.of(2022, 3, 31);
        String dateStr = formatDate(date);
        assertEquals("2022년 3월 31일", dateStr);
    }

    //아래 코드는 기대하는 값을 기술할 때 로컬 변수와 필드를 사용한 예
    MemoryRepository memoryRepository = new MemoryRepository();
    SurveyRepository surveyRepository = new SurveyRepository();
    private List<Integer> answers = Arrays.asList(1,2,3,4);
    private Long respondentId = 100L;

    @DisplayName("답변에 성공하면 결과 저장함")
    @Test
    void saveAnswerSuccessfully() {
        //답변할 설문이 존재
        Survey survey = SurveyFactor.createApprovedSurvey(1);
        surveyRepository.save(survey);

        //설문 응답
        SurveyAnswerRequest surveyAnswerRequest = SurveyAnswerRequest.builder()
                .surveyId(survey.getId())
                .respondentId(respondentId)
                .answers(answers)
                .build();

        //설문 응답 저장
        memoryRepository.answerSurvey(surveyAnswerRequest);

        //저장 결과 확인
        SurveyAnswer savedAnswer = memoryRepository.findBySurveyAndRespondent(survey.getId(), respondentId);
        assertAll(
                ()->assertEquals(respondentId, savedAnswer.getRespondentId()),
                ()->assertEquals(answers.size(), savedAnswer.getAnswers().size()),
                ()->assertEquals(answers.get(0), savedAnswer.getAnswers().get(0)),
                ()->assertEquals(answers.get(1), savedAnswer.getAnswers().get(1)),
                ()->assertEquals(answers.get(2), savedAnswer.getAnswers().get(2)),
                //아래 색인 값 오류
                ()->assertEquals(answers.get(3), savedAnswer.getAnswers().get(3))
                );

    }

    //1. 코드의 가독성 높음
    //2. 필드나 변수를 참조하지 않아도 코드를 파악할 수 있음
    //3. 단언을 사용한 값이 무엇인지 즉시 알 수 있어 좋음
    //4. 해당 값이 하드코딩 된 것은 로직을 수행하기 위해서만 사용된 것으로 문제 없음
    @DisplayName("수정된 버전 답변에 성공하면 결과 저장함")
    @Test
    void saveAnswerSuccessfullyTest() {
        //답변할 설문이 존재
        Survey survey = SurveyFactor.createApprovedSurvey(1);
        surveyRepository.save(survey);

        //설문 응답
        SurveyAnswerRequest surveyAnswerRequest = SurveyAnswerRequest.builder()
                .surveyId(1)
                .respondentId(100l)
                .answers(Arrays.asList(1,2,3,4))
                .build();

        //설문 응답 저장
        memoryRepository.answerSurvey(surveyAnswerRequest);

        //저장 결과 확인
        SurveyAnswer savedAnswer = memoryRepository.findBySurveyAndRespondent(1, 100L);
        assertAll(
                ()->assertEquals(100L, savedAnswer.getRespondentId()),
                ()->assertEquals(4, savedAnswer.getAnswers().size()),
                ()->assertEquals(1, savedAnswer.getAnswers().get(0)),
                ()->assertEquals(2, savedAnswer.getAnswers().get(1)),
                ()->assertEquals(3, savedAnswer.getAnswers().get(2)),
                ()->assertEquals(4, savedAnswer.getAnswers().get(3))
        );

    }

    @DisplayName("두개 이상 검증하지 않기 테스트")
    @Nested
    class 두개_이상_검증하지_않기_테스트 {

        private UserRegister userRegister;
        private WeakPasswordChecker mockPasswordChecker = Mockito.mock(WeakPasswordChecker.class);
        private UserRepository fakeRepository = new MemoryUserRepository();
        private EmailNotifier mockEmailNotifier = Mockito.mock(EmailNotifier.class);

        @BeforeEach
        void SetUp() {
            userRegister = new UserRegister(mockPasswordChecker,
                    fakeRepository,
                    mockEmailNotifier);
        }

        @DisplayName("같은 ID가 없으면 가입하고 성공하면 메일을 전송한다")
        @Test
        void 같은_ID가_없으면_가입하고_성공하면_메일_전송을_테스트한다() {
            //회원 가입을 한다
            userRegister.register("id", "pw", "email@email.com");

            //검증 1 : 회원 데이터가 올바르게 저장되었는지 검증한다
            User savedUser = fakeRepository.findById("id");
            assertEquals("id", savedUser.getId());
            assertEquals("email@email.com", savedUser.getEmail());

            //검증 2 : 이메일 발송을 요청하였는지 검증한다
            ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
            BDDMockito.then(mockEmailNotifier).should().sendRegisterEmail(captor.capture());

            String realEmail = captor.getValue();
            assertEquals("email@email.com", realEmail);
        }

        @DisplayName("같은 ID가 없으면 가입하고 성공함")
        @Test
        void 같은_ID가_없으면_가입하고_성공함() {
            //회원 가입을 한다
            userRegister.register("id", "pw", "email@email.com");

            //검증 1 : 회원 데이터가 올바르게 저장되었는지 검증한다
            User savedUser = fakeRepository.findById("id");
            assertEquals("id", savedUser.getId());
            assertEquals("email@email.com", savedUser.getEmail());
        }

        @DisplayName("가입하면 메일을 전송한다")
        @Test
        void 가입하면_메일_전송을_테스트한다() {
            //회원 가입을 한다
            userRegister.register("id", "pw", "email@email.com");

            //검증 1 : 이메일 발송을 요청하였는지 검증한다
            ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
            BDDMockito.then(mockEmailNotifier).should().sendRegisterEmail(captor.capture());

            String realEmail = captor.getValue();
            assertEquals("email@email.com", realEmail);
        }

    }

    @DisplayName("정확하게 일치하는 값을 모의 객체 설정 테스트")
    @Nested
    class 정확하게_일치하는_값을_모의_객체_설정 {

        private UserRegister userRegister;
        private WeakPasswordChecker mockPasswordChecker = Mockito.mock(WeakPasswordChecker.class);
        private UserRepository fakeRepository = new MemoryUserRepository();
        private EmailNotifier mockEmailNotifier = Mockito.mock(EmailNotifier.class);

        @BeforeEach
        void SetUp() {
            userRegister = new UserRegister(mockPasswordChecker,
                    fakeRepository,
                    mockEmailNotifier);
        }

        @DisplayName("약한 암호면 가입 실패 테스트")
        @Test
        void 약한_암호면_가입_실패_테스트() {
            //아래 설정은 "pw"값이 중요한 것이 아니라, 비밀번호가 약한 상황을 설정 하는 것이 중요함
            //상항 설정의 의도는 약한 암호일 경우 회원 가입시 예외가 발생되는 테스트 하는 것이 목적임
            BDDMockito.given(mockPasswordChecker.checkPasswordWeak("pw")).willReturn(true);

            //위에서 설정한 "pw"을 전달하면 약한 암호로 회원 가입 실패 예외 발생을 정상 테스트
            assertThrows(WeakPasswordException.class, () -> {
                userRegister.register("id", "pw", "email");
            });

            //만약 "pwa"을 전달하면 약한 암호이지만 회원 가입 성공이 되고 예외 발생을 테스트 할 수 없다
            //assertThrows(WeakPasswordException.class, () -> {
            //    userRegister.register("id", "pwa", "email");
            //});
        }

        @DisplayName("변경된 약한 암호면 가입 실패 테스트")
        @Test
        void 변경된_약한_암호면_가입_실패_테스트() {
            //전달되는 암호를 특정문자열이 아닌 임의 문자열이 되게 설정
            BDDMockito.given(mockPasswordChecker.checkPasswordWeak(Mockito.anyString())).willReturn(true);

            //위에서 설정한 "pw"을 전달하면 약한 암호로 회원 가입 실패 예외 발생을 정상 테스트
            assertThrows(WeakPasswordException.class, () -> {
                userRegister.register("id", "pw", "email");
            });

            //"pwa"을 전달해도 해당 테스트는 성공한다 
            assertThrows(WeakPasswordException.class, () -> {
                userRegister.register("id", "pwa", "email");
            });
        }

    }

    //UserRepository 객체를 Mock 객체로 생성하여 내부 구현 로직을 검증한다
    @DisplayName("과도한 구현 검증하기 않기")
    @Nested
    class 과도한_구현_검증하기_않기{

        private UserRegister userRegister;
        private WeakPasswordChecker mockPasswordChecker = Mockito.mock(WeakPasswordChecker.class);
        private UserRepository mockRepository = Mockito.mock(UserRepository.class);
        private EmailNotifier mockEmailNotifier = Mockito.mock(EmailNotifier.class);

        @BeforeEach
        void SetUp() {
            userRegister = new UserRegister(mockPasswordChecker,
                    mockRepository,
                    mockEmailNotifier);
        }

        @DisplayName("회원 가입시 약한 암호 검사 수행함")
        @Test
        void checkPassword() {
            //given : 약한 암호 검사를 할 수 있게 상항을 설정한다
            BDDMockito.given(mockPasswordChecker.checkPasswordWeak(Mockito.anyString())).willReturn(true);

            try {
                //when : 회원 가입 메서드를 호출한다
                userRegister.register("id", "pw", "email");
            } catch (Exception e) {
                e.printStackTrace();
            }

            //password 약함 확인 루틴 호출 검증 한다
            BDDMockito.then(mockPasswordChecker)
                    .should()
                    .checkPasswordWeak(Mockito.anyString());

            //회원 아이디가 존재하는지 확인 하는 메서드는 호출되지 않는지 검사한다
            BDDMockito.then(mockRepository)
                    .should(Mockito.never())
                    .findById(Mockito.anyString());
        }
        /* 위 코드와 같이 내부 구현을 검증하는 것이 나쁜 것은 아니지만 한 가지 단점이 존재 한다
           약한 암호 검사 루틴 보다 먼저 findById() 호출하여 아이디 존재 여부를 확인하게 루틴 수정하면 위 루틴은 검증 실패함
           아래 수정된 코드 확인 할 것
        *  */
        @DisplayName("수정된 회원 가입시 약한 암호 검사 수행함")
        @Test
        void checkPassword2() {
            //given : 약한 암호 검사를 할 수 있게 상항을 설정한다
            BDDMockito.given(mockPasswordChecker.checkPasswordWeak(Mockito.anyString())).willReturn(true);

            try {
                //when : 회원 가입 메서드를 호출한다
                userRegister.register2("id", "pw", "email");
            } catch (Exception e) {
                e.printStackTrace();
            }

            //password 약함 확인 루틴 호출 검증 한다
            BDDMockito.then(mockPasswordChecker)
                    .should()
                    .checkPasswordWeak(Mockito.anyString());

            //회원 아이디가 존재하는지 확인 하는 메서드는 호출되지 않는지 검사한다
            BDDMockito.then(mockRepository)
                    .should(Mockito.never())
                    .findById(Mockito.anyString());
        }
    }

    @DisplayName("셋업을 이용해서 중복된 상황을 설정하지 않는다")
    @Nested
    class 셋업을_이용해서_중복된_상황을_설정하지_않는다{

        private UserRepository fakeRepository = new MemoryUserRepository();
        private ChangeUserService changeUserService;

        @BeforeEach
        void SetUp() {
            changeUserService = new ChangeUserService(fakeRepository);

            fakeRepository.save(new User("id", "pwd", new Address("서울", "북구")));
        }

        @DisplayName("회원_존재하지_않는_것_테스트")
        @Test
        void 회원_존재하지_않는_것_테스트() {
            assertThrows(
                UserNotFoundException.class,
                ()->changeUserService.changeAddress("id2", new Address("서울", "남부"))
            );
        }

        @DisplayName("회원_주소변경_테스트")
        @Test
        void 회원_주소변경_테스트() {
            changeUserService.changeAddress("id", new Address("서울", "남부"));
            User user = fakeRepository.findById("id");
            assertThat(user.getAddress().getCity()).isEqualTo("서울");

        }

        @DisplayName("회원_비밀번호_변경_테스트")
        @Test
        void 회원_비밀번호_변경_테스트() {
            changeUserService.changePassword("id", "pwd", "newpw");
            User user = fakeRepository.findById("id");
            assertThat(user.matchPassword("newpw")).isEqualTo(true);
        }

        @DisplayName("회원_비밀번호_변경_예외발생_테스트")
        @Test
        void 회원_비밀번호_변경_예외발생_테스트() {
            assertThrows(
                IdPwNotMatchException.class,
                ()->changeUserService.changePassword("id", "pw2", "newpw")
            );
        }

    }

    @DisplayName("수정된 셋업을 이용해서 중복된 상황을 설정하지 않는다")
    @Nested
    class 수정된_셋업을_이용해서_중복된_상황을_설정하지_않는다{

        private UserRepository fakeRepository = new MemoryUserRepository();
        private ChangeUserService changeUserService;

        @BeforeEach
        void SetUp() {
            changeUserService = new ChangeUserService(fakeRepository);

            //중복 ID 확인용
            fakeRepository.save(new User("eixstUid", "pwd", new Address("서울", "북구")));
            //암호 변경 테스트용
            fakeRepository.save(new User("pwChangeUid", "pwd", new Address("서울", "북구")));
            //주소 변경 테스트용
            fakeRepository.save(new User("addresssChangeUid", "pwd", new Address("서울", "북구")));

            //위와 같이 변경한다고 해도 이전과 같은 문제 발생할 수 있음
        }

        @DisplayName("회원_존재하지_않는_것_테스트")
        @Test
        void 회원_존재하지_않는_것_테스트() {
            assertThrows(
                    UserNotFoundException.class,
                    ()->changeUserService.changeAddress("id2", new Address("서울", "남부"))
            );
        }

        @DisplayName("회원_주소변경_테스트")
        @Test
        void 회원_주소변경_테스트() {
            changeUserService.changeAddress("addresssChangeUid", new Address("서울", "남부"));
            User user = fakeRepository.findById("addresssChangeUid");
            assertThat(user.getAddress().getCity()).isEqualTo("서울");

        }

        @DisplayName("회원_비밀번호_변경_테스트")
        @Test
        void 회원_비밀번호_변경_테스트() {
            changeUserService.changePassword("pwChangeUid", "pwd", "newpw");
            User user = fakeRepository.findById("pwChangeUid");
            assertThat(user.matchPassword("newpw")).isEqualTo(true);
        }

        @DisplayName("회원_비밀번호_변경_예외발생_테스트")
        @Test
        void 회원_비밀번호_변경_예외발생_테스트() {
            assertThrows(
                    IdPwNotMatchException.class,
                    ()->changeUserService.changePassword("pwChangeUid", "pw2", "newpw")
            );
        }

    }

    //소스가 조금 길어지는 문제는 있지만 테스트를 하는 곳에서 하나의 완전한 프로그램으로 동작할 수 있게 수정된 버전임
    @DisplayName("추천하는_수정된 셋업을 이용해서 중복된 상황을 설정하지 않는다")
    @Nested
    class 추천하는_수정된_셋업을_이용해서_중복된_상황을_설정하지_않는다{

        private UserRepository fakeRepository = new MemoryUserRepository();
        private ChangeUserService changeUserService;

        @BeforeEach
        void SetUp() {
            changeUserService = new ChangeUserService(fakeRepository);
        }

        @DisplayName("회원_존재하지_않는_것_테스트")
        @Test
        void 회원_존재하지_않는_것_테스트() {
            assertThrows(
                    UserNotFoundException.class,
                    ()->changeUserService.changeAddress("id2", new Address("서울", "남부"))
            );
        }

        @DisplayName("회원_주소변경_테스트")
        @Test
        void 회원_주소변경_테스트() {
            //실제 테스트를 하는 부분에 실제 사용될 자료를 추가한다
            fakeRepository.save(new User("uid", "pwd", new Address("서울", "북구")));

            changeUserService.changeAddress("uid", new Address("서울", "남부"));
            User user = fakeRepository.findById("uid");
            assertThat(user.getAddress().getCity()).isEqualTo("서울");

        }

        @DisplayName("회원_비밀번호_변경_테스트")
        @Test
        void 회원_비밀번호_변경_테스트() {
            //실제 테스트를 하는 부분에 실제 사용될 자료를 추가한다
            fakeRepository.save(new User("uid", "pwd", new Address("서울", "북구")));

            changeUserService.changePassword("uid", "pwd", "newpw");
            User user = fakeRepository.findById("uid");
            assertThat(user.matchPassword("newpw")).isEqualTo(true);
        }

        @DisplayName("회원_비밀번호_변경_예외발생_테스트")
        @Test
        void 회원_비밀번호_변경_예외발생_테스트() {
            //실제 테스트를 하는 부분에 실제 사용될 자료를 추가한다
            fakeRepository.save(new User("uid", "pwd", new Address("서울", "북구")));

            assertThrows(
                    IdPwNotMatchException.class,
                    ()->changeUserService.changePassword("uid", "pw2", "newpw")
            );
        }
    }

    @Data
    @Builder
    static class Member {
        private LocalDateTime expiryDate;

        public boolean isExpiry() {
            return expiryDate.isBefore(LocalDateTime.now());
        }

        public boolean passedExpiryDate(LocalDateTime time) {
            return expiryDate.isBefore(time);
        }
    }


    @DisplayName("실행 시점이 다르다고 실패하지 않게")
    @Nested
    class 실행_시점이_다르다고_실패하지_않게_테스트{

        @DisplayName("만료일이 아닌지 테스트")
        @Test
        void 만료일이_아닌지_테스트() {
            LocalDateTime expiry = LocalDateTime.of(2022, 3, 31, 0, 0, 0);
            Member member = Member.builder().expiryDate(expiry).build();
            assertFalse(member.isExpiry());
        }

        @DisplayName("만료일 확인시 시간 전달하여 테스트")
        @Test
        void 만료일_확인시_시간_전달하여_테스트() {
            LocalDateTime expiry = LocalDateTime.of(2022, 3, 31, 0, 0, 0);
            Member member = Member.builder().expiryDate(expiry).build();
            assertFalse(member.passedExpiryDate(LocalDateTime.of(2022, 3, 31, 0, 0, 0)));
        }

    }

    @DisplayName("필요하지 않은 값은 설정하지 않게 한다")
    @Nested
    class 필요하지_않은_값은_설정하지_않게_테스트{

        private UserRepository fakeRepository = new MemoryUserRepository();

        @DisplayName("동일한 아이디 존재 하는 상황 테스트")
        @Test
        void 동일한_아이디_존재_하는_상황_테스트() {
            fakeRepository.save(
                    User.builder()
                        .id("dupId")
                        .name("홍길동")
                        .password("pwd")
                        .address(new Address("서울", "북구"))
                        .build());

            User user = User.builder()
                    .id("dupId")
                    .name("다른사람")
                    .password("pwd")
                    .address(new Address("서울", "북구"))
                    .build();

            assertThrows(DupIdException.class,
                    ()->fakeRepository.save(user));

        }

        @DisplayName("불필요한것 제거된 : 동일한 아이디 존재 하는 상황 테스트")
        @Test
        void 불필요한것_제거된_동일한_아이디_존재_하는_상황_테스트() {
            fakeRepository.save(
                    User.builder()
                            .id("dupId")
                            .build());

            User user = User.builder()
                    .id("dupId")
                    .build();

            assertThrows(DupIdException.class,
                    ()->fakeRepository.save(user));
        }

    }

    @DisplayName("조건부로 검증하지 않게 한다")
    @Nested
    class 조건부로_검증하지_않게한다{

        @DisplayName("조건부로 검증하는 테스트")
        @Test
        void 조건부로_검증하는_테스트() {
            //mock 객체를 생성한다
            ICarSaleStorage carSaleStorage = Mockito.mock(ICarSaleStorage.class);

            //given 설정
            given(carSaleStorage.getMonths()).willReturn(Arrays.asList(1,2));
            given(carSaleStorage.getValue(1)).willReturn(100);
            given(carSaleStorage.getValue(2)).willReturn(200);

            List<Integer> monthsList = carSaleStorage.getMonths();

            if (monthsList.size() > 0) {
                assertThat(carSaleStorage.getValue(1)).isEqualTo(100);
                assertThat(carSaleStorage.getValue(2)).isEqualTo(200);
            }
        }

        void 조건부로_검증하는_부분을_수정해서_테스트() {
            //mock 객체를 생성한다
            ICarSaleStorage carSaleStorage = Mockito.mock(ICarSaleStorage.class);

            //given 설정
            given(carSaleStorage.getMonths()).willReturn(Arrays.asList(1,2));
            given(carSaleStorage.getValue(1)).willReturn(100);
            given(carSaleStorage.getValue(2)).willReturn(200);

            List<Integer> monthsList = carSaleStorage.getMonths();

            assertThat(monthsList).isNotNull();
            assertThat(monthsList.size()).isGreaterThan(0);
            assertThat(carSaleStorage.getValue(1)).isEqualTo(100);
        }

    }

}
