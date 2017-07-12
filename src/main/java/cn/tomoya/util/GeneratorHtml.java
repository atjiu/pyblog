package cn.tomoya.util;

import cn.tomoya.config.SiteConfig;
import cn.tomoya.model.Blog;
import cn.tomoya.model.Page;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
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
    log.info("generator over!");
  }

  public void clean() {
    log.info("start clean blog data...");

    fileUtil.getBlogs().clear();
    fileUtil.formatter();

    log.info("clean blog data end...");

    log.info("start clean html...");
    File file = new File(siteConfig.getStatic_html());
    fileUtil.deleteDirectory(file);
    log.info("clean html end...");
  }

  public void generatorIndex() {
    try {
      Template template = configuration.getTemplate(siteConfig.getTheme() + "/index.ftl");
      fileUtil.mkdir(siteConfig.getStatic_html());

      File staticFile = fileUtil.createFile(siteConfig.getStatic_html() + "/index.html");

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
      e.printStackTrace();
    }
  }

  public void generatorPage() {
    Page page = new Page(1, siteConfig.getPage_size(), fileUtil.getBlogs().size(), fileUtil.getBlogs());
    for (int i = 1; i <= page.getTotalPage(); i++) {
      try {
        Template template = configuration.getTemplate(siteConfig.getTheme() + "/index.ftl");
        fileUtil.mkdir(siteConfig.getStatic_html() + "/page" + i);

        File staticFile = fileUtil.createFile(siteConfig.getStatic_html() + "/page" + i + "/index.html");

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
        e.printStackTrace();
      }
    }
  }

  public void generatorTag() {
    try {
      Template template = configuration.getTemplate(siteConfig.getTheme() + "/tag.ftl");
      fileUtil.mkdir(siteConfig.getStatic_html() + "/tag");

      File staticFile = fileUtil.createFile(siteConfig.getStatic_html() + "/tag/index.html");

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
      e.printStackTrace();
    }
  }

  public void generatorCategory() {
    try {
      Template template = configuration.getTemplate(siteConfig.getTheme() + "/category.ftl");
      fileUtil.mkdir(siteConfig.getStatic_html() + "/category");

      File staticFile = fileUtil.createFile(siteConfig.getStatic_html() + "/category/index.html");

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
      e.printStackTrace();
    }
  }

  public void generatorArchives() {
    try {
      Template template = configuration.getTemplate(siteConfig.getTheme() + "/archive.ftl");
      fileUtil.mkdir(siteConfig.getStatic_html() + "/archive");

      File staticFile = fileUtil.createFile(siteConfig.getStatic_html() + "/archive/index.html");

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
      e.printStackTrace();
    }
  }

  public void generatorDetail() {
    List<Blog> blogs = fileUtil.getBlogs();
    if(blogs != null) {
      for(Blog blog: blogs) {
        try {
          String url = blog.getUrl();
          String[] urls = url.split("/");
          if(urls.length < 2) throw new Exception("The URL address is not valid!");

          Template template = configuration.getTemplate(siteConfig.getTheme() + "/detail.ftl");
          fileUtil.mkdirs(siteConfig.getStatic_html() + blog.getUrl());

          File staticFile = fileUtil.createFile(siteConfig.getStatic_html() + blog.getUrl() + "/index.html");

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
          e.printStackTrace();
        }
      }
    }

  }
}
