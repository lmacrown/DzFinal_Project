package testable.step3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class PaySync {

    public PaySync() {
    }

    //객체 생성시 의존 객체 전달
    public PaySync(PayInfoDao payInfoDao) {
        this.payInfoDao = payInfoDao;
    }

    private PayInfoDao payInfoDao = new PayInfoDao();
    //외부에 의존객체를 설정할 수 있는 setter를 제공한다
    public void setPayInfoDao(PayInfoDao payInfoDao) {
        this.payInfoDao = payInfoDao;
    }

    private String filePath = "D:\\data\\pay\\cp0001.csv";

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void sync() throws IOException {
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
