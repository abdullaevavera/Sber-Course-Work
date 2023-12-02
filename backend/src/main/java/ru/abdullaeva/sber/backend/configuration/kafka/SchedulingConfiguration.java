package ru.abdullaeva.sber.backend.configuration.kafka;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Конфигурация для включения методов с установленным расписанием вызова
 */
@Configuration
@EnableScheduling
public class SchedulingConfiguration {
}
