package cn.xiaowenjie.services;

import cn.xiaowenjie.beans.UploadRecord;
import cn.xiaowenjie.chartbeans.EndData;
import cn.xiaowenjie.common.beans.PageResp;
import cn.xiaowenjie.daos.EndDataDao;
import cn.xiaowenjie.daos.UploadRecordDao;
import cn.xiaowenjie.tool.ConfigUtil;
import cn.xiaowenjie.tool.ReadLog2CSV;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static cn.xiaowenjie.common.utils.CheckUtil.check;

/**
 * 上传日志
 */
@Service
@Slf4j
public class UploadFileService {

    @Autowired
    UploadRecordDao uploadRecordDao;

    @Autowired
    EndDataDao endDataDao;

    /**
     *  上传文件
     * @param f
     * @return
     */
    @SneakyThrows
    public UploadRecord upload(MultipartFile f) {
        String filename = f.getOriginalFilename();
        String name = filename.substring(0, filename.length() - 4);

        log.info("start upload..." + filename + "," + f.getSize());

        // 后缀校验
        check(filename.toLowerCase().endsWith(".log"), "unsupport.file.format");

        // 不能重名，防止重复提交
        check(uploadRecordDao.findByName(name) == null, "name.repeat");

        // 保存的目录
        String saveDir = ConfigUtil.get("PATH.LOGS");

        // 得到要保存的文件名
        String realName = generateFileName(saveDir, f);

        log.info("save to :" + saveDir + " , " + realName);

        // save file
        File newFile = new File(saveDir, realName);
        f.transferTo(newFile);

        // save record to db
        UploadRecord record = new UploadRecord();

        record.setName(name);
        record.setRealPath(realName);
        record.setSize(f.getSize());

        // 保存数据
        record = uploadRecordDao.save(record);

        // 分析文件
        int dataCount = parseLog(record.getId(), newFile);

        // 保存文件的记录数
        record.setDataCount(dataCount);
        record = uploadRecordDao.save(record);

        return record;
    }

    @SneakyThrows
    private int parseLog(long recordId, File newFile) {
        // 把csv文件读取为对象
        List<EndData> data = ReadLog2CSV.deal(newFile);

        // 设置关联id
        data.forEach(d -> d.setRecordId(recordId));

        // 保存到数据库
        endDataDao.save(data);

        return data.size();
    }

    /**
     * 简单防止冲突（单机版）
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
     * 得到文件名后缀
     *
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

    /**
     * 分页查找
     *
     * @param pageable
     * @param keyword
     * @return
     */
    public PageResp<UploadRecord> listPage(Pageable pageable, String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            return new PageResp<UploadRecord>(uploadRecordDao.findAllByOrderByCreateTimeDesc(pageable));
        } else {
            // 也可以用springjpa 的 Specification 来实现查找
            return new PageResp<UploadRecord>(uploadRecordDao.findAllByKeyword(keyword, pageable));
        }
    }
}
