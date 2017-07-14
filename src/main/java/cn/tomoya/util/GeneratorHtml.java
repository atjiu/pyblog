package cn.tomoya.util;

import cn.tomoya.config.SiteConfig;
import cn.tomoya.model.Blog;
import cn.tomoya.model.Page;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tomoya on 2017/6/29.
 */
@Component
public class GeneratorHtml {

  @Autowired
  private Configuration configuration;
  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private FileUtil fileUtil;

  Logger log = LoggerFactory.getLogger(GeneratorHtml.class);

  public void generator() {
    clean();
    log.info("start generator html...");
    generatorIndex();
    generatorPage();
    generatorTag();
    generatorCategory();
    generatorDetail();
    generatorArchives();
    generator404Page();
//    generatorRss();
    log.info("generator over!");
  }

  public void clean() {
    log.info("start clean blog data...");

    fileUtil.getBlogs().clear();
    fileUtil.formatter();

    log.info("clean blog data end...");

    log.info("start clean html...");
    File file = new File(siteConfig.getStaticHtml());
    fileUtil.deleteDirectory(file);
    log.info("clean html end...");
  }

  public void generatorIndex() {
    try {
      Template template = configuration.getTemplate(siteConfig.getTheme() + "/index.ftl");
      fileUtil.mkdir(siteConfig.getStaticHtml());

      File staticFile = fileUtil.createFile(siteConfig.getStaticHtml() + "/index.html");

      FileOutputStream outStream = new FileOutputStream(staticFile);
      OutputStreamWriter writer = new OutputStreamWriter(outStream, "UTF-8");
      BufferedWriter sw = new BufferedWriter(writer);

      Map<String, String> rootMap = new HashMap<>();
      rootMap.put("page_tab", "home");
      rootMap.put("pageNo", "1");

      template.process(rootMap, sw);
      sw.flush();
      sw.close();
      outStream.close();
    } catch (IOException | TemplateException e) {
      log.error(e.getMessage());
    }
  }

  public void generatorPage() {
    Page page = new Page(1, siteConfig.getPageSize(), fileUtil.getBlogs().size(), fileUtil.getBlogs());
    for (int i = 1; i <= page.getTotalPage(); i++) {
      try {
        Template template = configuration.getTemplate(siteConfig.getTheme() + "/index.ftl");
        fileUtil.mkdir(siteConfig.getStaticHtml() + "/page" + i);

        File staticFile = fileUtil.createFile(siteConfig.getStaticHtml() + "/page" + i + "/index.html");

        FileOutputStream outStream = new FileOutputStream(staticFile);
        OutputStreamWriter writer = new OutputStreamWriter(outStream, "UTF-8");
        BufferedWriter sw = new BufferedWriter(writer);

        Map<String, String> rootMap = new HashMap<>();
        rootMap.put("page_tab", "home");
        rootMap.put("pageNo", String.valueOf(i));

        template.process(rootMap, sw);
        sw.flush();
        sw.close();
        outStream.close();
      } catch (IOException | TemplateException e) {
        log.error(e.getMessage());
      }
    }
  }

  public void generatorTag() {
    try {
      Template template = configuration.getTemplate(siteConfig.getTheme() + "/tag.ftl");
      fileUtil.mkdir(siteConfig.getStaticHtml() + "/tag");

      File staticFile = fileUtil.createFile(siteConfig.getStaticHtml() + "/tag/index.html");

      FileOutputStream outStream = new FileOutputStream(staticFile);
      OutputStreamWriter writer = new OutputStreamWriter(outStream, "UTF-8");
      BufferedWriter sw = new BufferedWriter(writer);

      Map<String, String> rootMap = new HashMap<>();
      rootMap.put("page_tab", "tag");

      template.process(rootMap, sw);
      sw.flush();
      sw.close();
      outStream.close();
    } catch (IOException | TemplateException e) {
      log.error(e.getMessage());
    }
  }

  public void generatorCategory() {
    try {
      Template template = configuration.getTemplate(siteConfig.getTheme() + "/category.ftl");
      fileUtil.mkdir(siteConfig.getStaticHtml() + "/category");

      File staticFile = fileUtil.createFile(siteConfig.getStaticHtml() + "/category/index.html");

      FileOutputStream outStream = new FileOutputStream(staticFile);
      OutputStreamWriter writer = new OutputStreamWriter(outStream, "UTF-8");
      BufferedWriter sw = new BufferedWriter(writer);

      Map<String, String> rootMap = new HashMap<>();
      rootMap.put("page_tab", "category");

      template.process(rootMap, sw);
      sw.flush();
      sw.close();
      outStream.close();
    } catch (IOException | TemplateException e) {
      log.error(e.getMessage());
    }
  }

  public void generatorArchives() {
    try {
      Template template = configuration.getTemplate(siteConfig.getTheme() + "/archive.ftl");
      fileUtil.mkdir(siteConfig.getStaticHtml() + "/archive");

      File staticFile = fileUtil.createFile(siteConfig.getStaticHtml() + "/archive/index.html");

      FileOutputStream outStream = new FileOutputStream(staticFile);
      OutputStreamWriter writer = new OutputStreamWriter(outStream, "UTF-8");
      BufferedWriter sw = new BufferedWriter(writer);

      Map<String, String> rootMap = new HashMap<>();
      rootMap.put("page_tab", "archive");

      template.process(rootMap, sw);
      sw.flush();
      sw.close();
      outStream.close();
    } catch (IOException | TemplateException e) {
      log.error(e.getMessage());
    }
  }

  public void generatorDetail() {
    List<Blog> blogs = fileUtil.getBlogs();
    if (blogs != null) {
      for (Blog blog : blogs) {
        try {
          String url = blog.getUrl();
          String[] urls = url.split("/");
          if (urls.length < 2) throw new Exception("The URL address is not valid!");

          Template template = configuration.getTemplate(siteConfig.getTheme() + "/detail.ftl");
          fileUtil.mkdirs(siteConfig.getStaticHtml() + blog.getUrl());

          File staticFile = fileUtil.createFile(siteConfig.getStaticHtml() + blog.getUrl() + "/index.html");

          FileOutputStream outStream = new FileOutputStream(staticFile);
          OutputStreamWriter writer = new OutputStreamWriter(outStream, "UTF-8");
          BufferedWriter sw = new BufferedWriter(writer);

          Map<String, String> rootMap = new HashMap<>();
          rootMap.put("page_tab", "");
          rootMap.put("url", url);

          template.process(rootMap, sw);
          sw.flush();
          sw.close();
          outStream.close();
        } catch (Exception e) {
          log.error(e.getMessage());
        }
      }
    }
  }

  public void generator404Page() {
    try {
      Template template = configuration.getTemplate(siteConfig.getTheme() + "/404.ftl");
      fileUtil.mkdir(siteConfig.getStaticHtml() + "/archive");

      File staticFile = fileUtil.createFile(siteConfig.getStaticHtml() + "/404.html");

      FileOutputStream outStream = new FileOutputStream(staticFile);
      OutputStreamWriter writer = new OutputStreamWriter(outStream, "UTF-8");
      BufferedWriter sw = new BufferedWriter(writer);

      Map<String, String> rootMap = new HashMap<>();

      template.process(rootMap, sw);
      sw.flush();
      sw.close();
      outStream.close();
    } catch (IOException | TemplateException e) {
      log.error(e.getMessage());
    }
  }

  public void generatorRss() {
    fileUtil.createFile(siteConfig.getStaticHtml() + "/feed.xml");
    SyndFeed feed = new SyndFeedImpl();
    feed.setFeedType("atom_1.0");
    feed.setTitle("test-title");
    feed.setDescription("test-description");
    feed.setLink("https://example.org");
//    feed.setEntries();
    try {
      System.out.println(new SyndFeedOutput().outputString(feed));
    } catch (FeedException e) {
      e.printStackTrace();
    }
  }
}
