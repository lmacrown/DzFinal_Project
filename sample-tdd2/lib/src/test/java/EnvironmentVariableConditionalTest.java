import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

/*
* 실행시 환경설정 변수 변경하여 실행 해본다
 * 오른클릭->[Modify Run Configuration...]으로 환경설정 변수 변경
 * ENV=staging-server 추가
*
* */
public class EnvironmentVariableConditionalTest {
    @Test
    @EnabledIfEnvironmentVariable(named = "ENV", matches = "staging-server")
    void onlyOnStagingServer() {
// ...
    }


    @Test
    @DisabledIfEnvironmentVariable(named = "ENV", matches = ".*development.*")
    void notOnDeveloperWorkstation() {
// ...
    }
}