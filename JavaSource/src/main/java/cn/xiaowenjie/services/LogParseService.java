package cn.xiaowenjie.services;

import cn.xiaowenjie.beans.UploadRecord;
import cn.xiaowenjie.daos.UploadRecordDao;
import cn.xiaowenjie.tool.ConfigUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 上传日志，处理日志，生成图表
 */
@Service
@Slf4j
public class LogParseService {

    @Autowired
    UploadRecordDao uploadRecordDao;

    @SneakyThrows
    public UploadRecord upload(MultipartFile f) {
        log.info("start upload..." + f.getOriginalFilename() + "," + f.getSize());

        // 保存的目录
        String saveDir = ConfigUtil.get("PATH.LOGS");

        // 得到要保存的文件名
        String realName = generateFileName(saveDir, f);

        log.info("save to :" + saveDir + " , " + realName);

        // save file
        f.transferTo(new File(saveDir, realName));

        // save record to db
        UploadRecord record = new UploadRecord();

        record.setName(f.getOriginalFilename());
        record.setRealPath(realName);
        record.setSize(f.getSize());

        record = uploadRecordDao.save(record);

        return record;
    }

    /**
     *  简单防止冲突（单机版）
     */
    private final static AtomicInteger fileIndex = new AtomicInteger(100);

    private String generateFileName(String saveDir, MultipartFile f) {
        // FIXME 并发可能覆盖
        String newDir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // 自动创建目录
        new File(saveDir, newDir).mkdirs();

        return String.format("%s//%d-%d%s", newDir, System.currentTimeMillis(), fileIndex.getAndIncrement(), getFileExt(f.getOriginalFilename()));
    }

    /**
     *  得到文件名后缀
     * @param name
     * @return
     */
    private String getFileExt(String name) {
        int index = name.lastIndexOf('.');

        if (index != -1) {
            return name.substring(index);
        }

        return "";
    }
}
