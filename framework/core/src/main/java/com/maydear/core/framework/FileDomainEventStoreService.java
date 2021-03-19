package com.maydear.core.framework;

import com.maydear.core.framework.infrastructure.FileStorageInfrastructure;
import com.maydear.core.framework.io.FileSummary;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * 日志领域事件
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Slf4j
public class FileDomainEventStoreService implements DomainEventStoreService {
    private final JsonConverter jsonConverter;
    private final FileStorageInfrastructure fileStorageInfrastructure;

    public FileDomainEventStoreService(JsonConverter jsonConverter, FileStorageInfrastructure fileStorageInfrastructure) {
        this.jsonConverter = jsonConverter;
        this.fileStorageInfrastructure = fileStorageInfrastructure;
    }

    /**
     * 保存事件源
     *
     * @param domainEvent 领域事件
     * @param exception   触发异常
     */
    @Override
    public void saveSource(AbstractDomainEvent domainEvent, Exception exception) {
        String stringJson = jsonConverter.serializa(domainEvent);
        log.debug(stringJson);
        FileSummary fileSummary = FileSummary.builder()
            .tag("FileDomainEventStore")
            .originalFilename(domainEvent.getId().toString() + ".store")
            .createTime(domainEvent.getCreateTime())
            .contentType("application/json")
            .storageDirectory("store")
            .build();
        fileStorageInfrastructure.writePersistence(fileSummary, stringJson.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 事件重播
     */
    @Override
    public void replay() {

    }
}
