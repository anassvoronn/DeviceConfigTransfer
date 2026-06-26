package org.nastya.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.nastya.service.DeviceConfigService;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/configs")
@Tag(
        name = "Device Configs",
        description = "API для управления конфигурационными файлами устройств"
)
public class DeviceConfigController {

    private final DeviceConfigService service;

    @GetMapping
    @Operation(
            summary = "Получить список доступных конфигураций",
            description = "Возвращает список JSON конфигурационных файлов"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Список конфигураций успешно получен"
    )
    public List<String> getConfigs() {
        return service.getAvailableConfigs();
    }

    @PostMapping("/{fileName}/send")
    @Operation(
            summary = "Отправить конфигурацию через Kafka",
            description = "Читает конфигурационный файл и отправляет его содержимое в Kafka"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Конфигурация успешно отправлена"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Файл конфигурации не найден",
            content = @Content
    )
    public void sendConfig(
            @Parameter(
                    description = "Имя конфигурационного файла",
                    example = "device_config.json"
            )
            @PathVariable String fileName
    ) throws FileNotFoundException {
        service.sendConfig(fileName);
    }
}