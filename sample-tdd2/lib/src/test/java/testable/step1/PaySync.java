package testable.step1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class PaySync {
    private PayInfoDao payInfoDao = new PayInfoDao();

    public void sync() throws IOException {
        //하드 코딩된 경로는 테스트 반드시 해당 위치에 파일이 존재해야 테스트 가능함
        //하드 코딩된 IP주소, 포트 번호등도 테스트를 어렵게 한다
        Path path = Paths.get("D:\\data\\pay\\cp0001.csv");
        List<PayInfo> payInfos = Files.lines(path)
                .map(line -> {
                    String[] data = line.split(",");
                    PayInfo payInfo = new PayInfo(
                            data[0], data[1], Integer.parseInt(data[2])
                    );
                    return payInfo;
                })
                .collect(Collectors.toList());

        payInfos.forEach(pi -> payInfoDao.insert(pi));
    }
}
