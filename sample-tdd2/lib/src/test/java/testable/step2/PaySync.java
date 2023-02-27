package testable.step2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class PaySync {
    private PayInfoDao payInfoDao = new PayInfoDao();
    //멤버변수를 선언하고 setXXX() 메서드 제공하여 외부에서 설정할 수 있게 리펙토링 한다
    private String filePath = "D:\\data\\pay\\cp0001.csv";

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void sync() throws IOException {
        //하드 코딩된 경로를 멤버변수로 리펙토링 한다
        Path path = Paths.get(filePath);
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
