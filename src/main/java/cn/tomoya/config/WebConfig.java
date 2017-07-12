package cn.tomoya.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by tomoya on 17-7-11.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    super.addViewControllers(registry);
    registry.addViewController("/").setViewName("forward:/index.html");
  }
}
