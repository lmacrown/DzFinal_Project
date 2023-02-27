package math;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MathUtilsTest {
    //파일이 존재하지 않는 상황 테스트
    //재수없는 상황은 해당 폴더에 badpath.txt이 존재하면 해당 테스트는 예외 발생하지 않음
    //테스트는 실행할때 마다 언제나 결과가 같아야 한다
    //그러므로 아래와 같이 코딩 하는 것은 좋은 코딩이 아님
    @Test
    void 파일이_존재하지_않는_상황_테스트() {
        File dataFile = new File("badpath.txt");
        assertThrows(IllegalArgumentException.class,
                () -> MathUtils.sum(dataFile)
        );
    }

    //파일이 존재하지 않는 상황 만약 존재한다면 삭제하고 테스트
    //아래와 같이 하면 항상 테스트는 성공한다
    //좋은 테스트 코드임
    @Test
    void 파일이_존재하지_않는_상황_만약_존재한다면_삭제하고_테스트() {
        //상황 설정
        givenNoFile("badpath.txt");

        File dataFile = new File("badpath.txt");
        //결과 확인
        assertThrows(IllegalArgumentException.class,
                //실행
                () -> MathUtils.sum(dataFile)
        );
    }

    //손쉬운 테스트 코드
    //해당 테스트를 하기 위해 반드시  src/test/resources/폴더에 datafile.txt이 존재해야 됨
    //만약 datafile.txt 파일이 존재하지 않으면 실패함
    //좋은 테스트 방법이 아님
    @Test
    void dataFileSumTest() {
        //상황
        File dataFile = new File("src/test/resources/datafile.txt");
        //실행
        long sum = MathUtils.sum(dataFile);
        //결과 확인
        assertEquals(10L, sum);
    }

    //아래와 같이 테스트 파일이 존재하지 않아도 자동으로 파일을 생성할 수 있게 하는 것이 좋은 방법
    //********************************************************************
    //중요함 : 외부 상태가 테스트 결과에 영향을 주지 않게 테스트 코드를 작성해야 한다
    //********************************************************************
    @Test
    void dataFileSumTest2() {
        //상황
        givenDataFile("target/datafile.txt", "1", "2", "3", "4");
        File dataFile = new File("src/test/resources/datafile.txt");
        //실행
        long sum = MathUtils.sum(dataFile);
        //결과 확인
        assertEquals(10L, sum);
    }

    private void givenDataFile(String path, String... lines) {
        try {
            Path dataPath = Paths.get(path);
            if (Files.exists(dataPath)) {
                Files.delete(dataPath);
            }
            Files.write(dataPath, Arrays.asList(lines));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void givenNoFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            boolean deleted = file.delete();
            if (!deleted)
                throw new RuntimeException("fail givenNoFile: " + path);
        }
    }

}
