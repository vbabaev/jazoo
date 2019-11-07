package dev.vbabaev.tools.jazoo;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.jline.PromptProvider;

@SpringBootApplication
public class Client {
    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .bannerMode(Banner.Mode.OFF)
                .sources(Client.class)
                .run(args);
    }

    @Bean
    public PromptProvider myPromptProvider(PathResolver pathResolver, @Value("${server:localhost}") String server) {
        return () -> new AttributedString("jazoo@" + server + ":" + pathResolver.getCurrent() + "$ ", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
    }

}
