package net.takasing.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author toyofuku_takashi
 */
@Configuration
@ComponentScan(basePackages = {
        "net.takasing.service"
})
public class SpringPlaygroundConfiguration {
}
