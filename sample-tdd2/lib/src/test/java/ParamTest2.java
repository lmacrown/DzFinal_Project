import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.EnumSet;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ParamTest2 {

    @ParameterizedTest
    @ValueSource(chars = {'A', 'B', 'C'})
    void 문자인자들_테스트(char argument) {
        System.out.println("문자값 = " + argument);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void 정수인자들_테스트(int argument) {
        System.out.println("정수값 = " + argument);
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.12, 2.12, 3.12})
    void 실수인자들_테스트(double argument) {
        System.out.println("실수값 = " + argument);
    }

    @ParameterizedTest
    @ValueSource(strings = {"테스트시", "값이", "많아", "어려워지고", "있네요."})
    void 문자열인자들_테스트(String argument) {
        System.out.println("문자열값 = " + argument);
    }

    //잘못된 입력이 들어올 수 있는 경우를 확인하고 적절한 행동을 검증하기 위해서 파라미터화 테스트에 null 또는 빈 값을 제공해 줄 수 있다
    //Null and Empty Sources
    /*
    @NullSource : @ParameterizedTest 메소드에 null을 제공한다.

    @EmptySource : 다음과 같은 타입 String ,List ,Set, Map,
     primitive 배열 예) int[] ,char[] …,
     객체 배열 예) String[] ,Intger[] …
     같은 인자에 빈값을 제공한다. 지원되는 타입중에 서브타입들은 지원하지 않는다.

    @NullAndEmptySource : @NullSource 와 @EmptySource 기능들을 합친 어노테이션이다.
    */

    //아래 실행 회수는 6회
    //NullSource : 1회, EmptySource : 1회, ValueSource에 의해 4회 , 전체 6회 실행됨
    @ParameterizedTest
    @NullSource
    @EmptySource
    @ValueSource(strings = {" ", " ", "\t", "\n"})
    void nullEmptyAndBlankStrings(String text) {
        assertTrue(text == null || text.trim().isEmpty());
    }

    //위와 같음  @NullAndEmptySource = @NullSource + @EmptySource를 위미함
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", " ", "\t", "\n"})
    void nullEmptyAndBlankStrings2(String text) {
        assertTrue(text == null || text.trim().isEmpty());
    }

    //@EnumSource 사용법
    //ChronoUnit.java 소스 참조 하여 enum 클래스와 TemporalUnit 클래스 확인 할 것
    @ParameterizedTest
    @EnumSource(ChronoUnit.class)
    void ENUM을_이용한_인자값_전달시_부모타입으로_받는_경우_테스트(TemporalUnit unit) {
        assertNotNull(unit);
    }

    //@EnumSource 사용법2
    @ParameterizedTest
    @EnumSource
    void ENUM을_이용한_인자값_전달시_자신의타입으로_받는_경우_테스트(ChronoUnit unit) {
        assertNotNull(unit);
    }

    //@EnumSource 사용법3
    //ChronoUnit 상수 중 DAYS와 HOURS만 테스트하기
    @ParameterizedTest
    @EnumSource(names = {"DAYS", "HOURS"})
    void ENUM을_이용해_특정값만_테스트(ChronoUnit unit) {
        assertNotNull(unit);
    }

    //@EnumSource 사용법3
    //ChronoUnit 상수 중 EARS와 FOREVER 제외하고 테스트하기
    @ParameterizedTest
    @EnumSource(mode = EnumSource.Mode.EXCLUDE, names = {"EARS", "FOREVER"})
    void ENUM을_이용해_특정값만_제외하고_테스트(ChronoUnit unit) {
        assertFalse(EnumSet.of(ChronoUnit.ERAS, ChronoUnit.FOREVER).contains(unit));
    }

    //ChronoUnit 상수 중 DAYS로 끝나는 상수만 테스트하기
    @ParameterizedTest
    @EnumSource(mode = EnumSource.Mode.MATCH_ALL, names = "^.*DAYS$")
    void ENUM을_이용한_정규표현식_테스트(ChronoUnit unit) {
        assertTrue(unit.name().endsWith("DAYS"));
    }

    /*@MethodSource 하나 이상의 테스트 클래스 또는 외부 클래스 팩토리 메서드를 이용하여 매개변수값들을 사용할 수 있다
    테스트 클래스 안에 있는 팩토리 메서드는 @TestInstance(Lifecycle.PER_CLASS) 어노테이션을 테스트 클래스에 붙이지 않았으면,
    반드시 static 메서드야 한다. 또한 이런 팩토리 메서드는 어떠한 인자도 있으면 안된다.
    메서드의 리턴 타입은 반드시 stream 되어야 한다,
    */
    //문자열을 팩토리 메서드로 제공하는 static 메서드 
    static Stream<String> stringProvider() {
        return Stream.of("테스트시", "값이", "많아", "어려워지고", "있네요.");
    }

    @ParameterizedTest
    @MethodSource("stringProvider")
    void 명시적인_문자열제공_메서드를_이용한_테스트(String argument) {
        assertNotNull(argument);
    }

    //@MethodSource에서 메서드 이름 생략는 테스트 메소드와 같은 이름의 함수 찾아 실행한다
    static Stream<String> 테스트메서드와_같은_문자열제공_메서드를_이용한_테스트() {
        return Stream.of("테스트시", "값이", "많아", "어려워지고", "있네요.");
    }

    @ParameterizedTest
    @MethodSource
    void 테스트메서드와_같은_문자열제공_메서드를_이용한_테스트(String argument) {
        assertNotNull(argument);
    }

    //다음과 같이 DoubleStream, IntStream, LongStream의 primitive 타입의 Stream도 지원한다
    static IntStream range() {
        return IntStream.range(10, 20);
    }

    @ParameterizedTest
    @MethodSource("range")
    void 정수스트림_메서드을_호출하는_테스트(int argument) {
        assertNotEquals(9, argument);
    }

    //외부에 있는 정적 팩토리 메소드를 사용하려면 메소드 이름을 구별할 수 있게 다음과 같이 전체를 적어줘야한다.
    //외부 클래스와 이너클래스는 $으로 구분한다
    //클래스와 메서드는 #으로 구분한다
    static class StringsProviders {
        static Stream<String> tinyStrings() {
            return Stream.of(".", "oo", "OOO");
        }
    }

    @ParameterizedTest
    @MethodSource("ParamTest2$StringsProviders#tinyStrings")
    void 외부_클래스의_정적_메서드를_이용한_테스트(String tinyString) {
        System.out.println(tinyString);
    }

    /*
    CsvSource는 리스트를 콤마(,)로 구분 해 준다.
    기본 구분자는 콤마를 사용하지만, delimiter 속성을 이용해서 다른 문자를 구분자로 사용할 수도 있다.
    또는 대안으로 delimiterString 속성을 쓰면 문자 대신 문자열로 구분자를 사용할 수 있다.
    그러나 delimiter 속성과 delimiterString을 동시에 사용하면 안된다.
    '' 의 결과는 emptyValue 속성이 설정되어 있지 않으면 빈 String을 반환하고, 아예 빈 값이면 null 값을 반환한다.
    만약 null이 리턴하는 대상 타입이 primitive 타입일 경우 ArgumentConversionException 예외가 발생된다.
    */
    @ParameterizedTest
    @CsvSource({
            "apple, 1",
            "banana, 2",
            "'lemon, lime', 0xF1"
    })
    void csvSource을_이용한_테스트(String fruit, int rank) {
        assertNotNull(fruit);
        assertNotEquals(0, rank);
    }

    //구분문자 사용하는 예제
    @ParameterizedTest
    @CsvSource(value = {
            "apple; 1",
            "banana; 2",
            "'lemon, lime'; 0xF1"
    }, delimiter = ';')
    void csvSource에_구분문자을_이용한_테스트(String fruit, int rank) {
        assertNotNull(fruit);
        assertNotEquals(0, rank);
    }

    /*
    @CsvFileSource는 클래스 경로나 로컬 파일 시스템에 있는 CSV 파일을 사용한다.
    CSV 파일에 있는 각각의 라인 마다 파라미터화 테스트가 호출 된다.
    기본 구분자는 콤마(,)지만, delimiter 속성을 설정해서 다른 문자를 사용할 수 있다.
    또는 대안으로 delimiterString 속성을 쓰면 문자 대신 문자열로 구분자를 사용할 수 있다.
    그러나 delimiter 속성과 delimiterString을 동시에 사용하면 안된다.
    CSV 파일안에 있는 #기호는 주석으로 처리된다.
    */
    @ParameterizedTest
    @CsvFileSource(resources = "/two-column.csv", numLinesToSkip = 1)
    void testWithCsvFileSourceFromClasspath(String country, int reference) {
        assertNotNull(country);
        assertNotEquals(0, reference);
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/two-column.csv", numLinesToSkip = 1)
    void testWithCsvFileSourceFromFile(String country, int reference) {
        assertNotNull(country);
        assertNotEquals(0, reference);
    }

    /*
    Fallback String to-Object 변환
    JUnit Jupiter는 만약 대상 타입이 아래에 적힌 것 처럼 정확히 하나의 팩토리 메소드 또는 팩토리 생성자에 알맞은 경우를
    위해 String 타입을 자동적으로 변환해주는 fallback 메카니즘을 제공한다.
    factory method : 접근자가 private가 아니여야 하며, 생성하고자 하는 클래스 내에 String 인자를 받으며 해당
    타입을 리턴하는 static 메서드가 존재해야 한다. 네이밍컨벤션을 딱히 따르지 않아도 되지만. 정적 팩터리 컨벤션
    을 따르면 협업하기에 좋다.
     */

    public static class Book {
        private final String title;

        public Book(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public static Book fromTitle(String title) {
            return new Book(title);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = "42 Cats")
    void testWithImplicitFallbackArgumentConversion(Book book) {
        assertEquals("42 Cats", book.getTitle());
    }

    /*
    인자 수집
    기본적으로 @ParameterizedTest 메소드에 제공되는 각각의 인자들은 하나의 메소드 파라미터와 일치한다.
    만약에 테스트 메서드가 많은 인자들을 요구 할 경우 인자 수 만큼 파라미터의 개수가 늘어나게 된다.
    이런 경우때문에 ArgumentsAccessor는 여러 개의 파라미터를 대신하여 사용한다.
    이 API를 사용하기 위해 테스트 메소드에 제공된 하나의 인자를 통해서 접근할 수 있다.
     */
    enum Gender {
        F,
        M
    }

    static class Person {
        private String firstName;
        private String lastName;
        private Gender gender;
        private int age;
        private LocalDate dateOfBirth;

        public Person(String firstName, String lastName, Gender gender, int age, LocalDate dateOfBirth) {
            this.firstName = firstName.trim();
            this.lastName = lastName.trim();
            this.gender = gender;
            this.age = age;
            this.dateOfBirth = dateOfBirth;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public Gender getGender() {
            return gender;
        }

        public int getAge() {
            return age;
        }

        public LocalDate getDateOfBirth() {
            return dateOfBirth;
        }
    }

    @ParameterizedTest
    @CsvSource({
            "홍, 길동, M, 1990-05-20",
            "홍, 길자, F, 1990-10-22"
    })
    void testWithArgumentsAccessor(ArgumentsAccessor arguments) {
        Person person = new Person(arguments.getString(0),
                arguments.getString(1),
                arguments.get(2, Gender.class),
                arguments.getInteger(3),
                arguments.get(4, LocalDate.class));

        if (person.getLastName().equals("길동")) {
            assertEquals(Gender.M, person.getGender());
        } else {
            assertEquals(Gender.F, person.getGender());
        }
        assertEquals("홍", person.getFirstName());
        assertEquals(1990, person.getDateOfBirth().getYear());
    }

    /*
    ArgumentsAccessor를 @ParameterizedTest 인자로 직접적으로 접근하는걸 제외하고, 사용자 정의의 재사용 가능한 수집기를 사용할 수 있다.
    커스텀 수집기를 사용하기 위해서 ArgumentsAggregator 인터페이스를 구현해서, @AggregateWith 어노테이션을 통해서 등록해야 한다.
    ArgumentsAccessor에서 값을 얻을 때는 getXXX(int index), get(int index, Class<T> requiredType)
    */
    static class PersonAggregator implements ArgumentsAggregator {
        @Override
        public Person aggregateArguments(ArgumentsAccessor arguments, ParameterContext context) {
            return new Person(arguments.getString(0),
                    arguments.getString(1),
                    arguments.get(2, Gender.class),
                    arguments.getInteger(3),
                    arguments.get(4, LocalDate.class));
        }
    }
    @ParameterizedTest
    @CsvSource({
            "홍, 길동, M, 1990-05-20",
            "홍, 길자, F, 1990-10-22"
    })
    void testWithArgumentsAggregator(
            @AggregateWith(PersonAggregator.class) Person person
    ) {
        if (person.getLastName().equals("길동")) {
            assertEquals(Gender.M, person.getGender());
        } else {
            assertEquals(Gender.F, person.getGender());
        }
        assertEquals("홍", person.getFirstName());
        assertEquals(1990, person.getDateOfBirth().getYear());
    }

    /*
    @AggregateWith 어노테이션이 파라미터화 테스트 메소드 여러 개에서 사용하고 있다면,
    다음과 같이 커스텀 어노테이션을 만들어서 사용해 볼 수도 있다.
    */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    @AggregateWith(PersonAggregator.class)
    public @interface CsvToPerson {
    }

    @ParameterizedTest
    @CsvSource({
            "홍, 길동, M, 1990-05-20",
            "홍, 길자, F, 1990-10-22"
    })
    void testWithCustomAggregatorAnnotation(@CsvToPerson Person person) {
        if (person.getLastName().equals("길동")) {
            assertEquals(Gender.M, person.getGender());
        } else {
            assertEquals(Gender.F, person.getGender());
        }
        assertEquals("홍", person.getFirstName());
        assertEquals(1990, person.getDateOfBirth().getYear());
    }
}


