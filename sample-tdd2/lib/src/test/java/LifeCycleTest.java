import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class LifeCycleTest {

    public LifeCycleTest() {
        System.out.println("2. LifeCycleTest() 생성자 호출\n");
    }
//
    @BeforeAll
    static void testSetUp() {
        System.out.println("1. 객체 생성 되기 전에 초기화시 수행\n");
        System.out.println(" \n");
    }
//
    @BeforeEach
    void setUp() {
        System.out.println("3. 각 테스트시 호출 되기 전에 호출 되는 부분");
        System.out.println("테스트를 실행하는데 필요한 준비 작업을 할 때(임시파일 생성, DB 연결 작업)...\n");
    }
//
    @Test
    void 테스트_A() {
        System.out.println("4. 테스트 A 작업\n");
    }
//
    @Test
    void 테스트_B() {
        System.out.println("4. 테스트 B 작업\n");
    }
//
//    @AfterEach
//    void tearDown() {
//        System.out.println("5. 각 테스트 메서드 실행 후 호출 되는 부분");
//        System.out.println("테스트를 실행 후 필요한 정리 작업을 할 때(임시 파일 삭제, DB 연결 닫기)...\n");
//    }
//
    @AfterAll
    static void testTearDown() {
        System.out.println("6. 모든 테스트 메서드를 실행 한 후 정리 작업시 수행\n");
    }

}