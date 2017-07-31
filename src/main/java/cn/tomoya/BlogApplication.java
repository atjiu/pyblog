package cn.tomoya;

import cn.tomoya.config.SiteConfig;
import cn.tomoya.file.DirectoryWatchService;
import cn.tomoya.file.SimpleDirectoryWatchService;
import cn.tomoya.util.FileUtil;
import cn.tomoya.util.GeneratorHtml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties(SiteConfig.class)
public class BlogApplication extends SpringBootServletInitializer {

  Logger log = LoggerFactory.getLogger(BlogApplication.class);

  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private GeneratorHtml generatorHtml;
  @Autowired
  private FileUtil fileUtil;

  public static void main(String[] args) {
    SpringApplication.run(BlogApplication.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(BlogApplication.class);
  }

  @Configuration
  class BeforeStartup implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
      fileUtil.mkdir(siteConfig.getPost());
      fileUtil.mkdirs("./templates/" + siteConfig.getTheme());
      fileUtil.mkdirs("./templates/" + siteConfig.getTheme() + "/layout");
      fileUtil.formatter();
      generatorHtml.generator();

      List<String> paths = new ArrayList<>();
      paths.add(siteConfig.getPost());
      if (siteConfig.isDebug()) {
        paths.add("./templates/" + siteConfig.getTheme());
        paths.add("./templates/" + siteConfig.getTheme() + "/layout");
      }
      paths.forEach((path) -> {
        try {
          DirectoryWatchService watchService = new SimpleDirectoryWatchService(); // May throw
          watchService.register(
              new DirectoryWatchService.OnFileChangeListener() {
                @Override
                public void onFileCreate(String filePath) {
                  log.info("templates onFileCreate");
                  generatorHtml.generator();
                }

                @Override
                public void onFileModify(String filePath) {
                  log.info("templates onFileModify");
                  generatorHtml.generator();
                }

                @Override
                public void onFileDelete(String filePath) {
                  log.info("onFileModify onFileDelete");
                  generatorHtml.generator();
                }
              }, path, "*.ftl"
          );
          watchService.start();
        } catch (IOException e) {
          log.error("Unable to register file change listener for ");
        }
      });
    }

  }
}
