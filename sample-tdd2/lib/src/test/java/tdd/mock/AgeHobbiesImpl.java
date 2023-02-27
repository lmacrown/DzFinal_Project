package tdd.mock;

import java.util.Arrays;
import java.util.List;

public class AgeHobbiesImpl implements AgeHobbies {
    @Override
    public List<String> getHobbyList(String name, int age) {
        return Arrays.asList("게임", "낮잠", "영화감상");
    }
}
