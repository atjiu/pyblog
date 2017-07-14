package cn.tomoya.util;

import cn.tomoya.config.SiteConfig;
import cn.tomoya.model.Blog;
import cn.tomoya.model.Page;
import com.rometools.rome.feed.synd.*;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.jdom2.CDATA;
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
  @Autowired
  private MarkdownUtil markdownUtil;

  Logger log = LoggerFactory.getLogger(GeneratorHtml.class);

  public void generator() {
    clean();
    log.info("start generator html...");
    generatorIndex();
    generatorPage();
    generatorTag();
    generatorTagByName();
    generatorCategory();
    generatorCategoryByName();
    generatorDetail();
    generatorArchives();
    generator404Page();
    generatorRss();
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

  public void generatorTagByName() {
    List<String> tags = fileUtil.getTags();
    if (tags != null) {
      for (String tag : tags) {
        try {
          Template template = configuration.getTemplate(siteConfig.getTheme() + "/tag.ftl");
          fileUtil.mkdirs(siteConfig.getStaticHtml() + "/tag/" + tag);

          File staticFile = fileUtil.createFile(siteConfig.getStaticHtml() + "/tag/" + tag + "/index.html");

          FileOutputStream outStream = new FileOutputStream(staticFile);
          OutputStreamWriter writer = new OutputStreamWriter(outStream, "UTF-8");
          BufferedWriter sw = new BufferedWriter(writer);

          Map<String, String> rootMap = new HashMap<>();
          rootMap.put("page_tab", "tag");
          rootMap.put("tag", tag);

          template.process(rootMap, sw);
          sw.flush();
          sw.close();
          outStream.close();
        } catch (IOException | TemplateException e) {
          log.error(e.getMessage());
        }
      }
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

  public void generatorCategoryByName() {
    List<String> categories = fileUtil.getCategories();
    if (categories != null) {
      for (String category : categories) {
        try {
          Template template = configuration.getTemplate(siteConfig.getTheme() + "/category.ftl");
          fileUtil.mkdirs(siteConfig.getStaticHtml() + "/category/" + category);

          File staticFile = fileUtil.createFile(siteConfig.getStaticHtml() + "/category/" + category + "/index.html");

          FileOutputStream outStream = new FileOutputStream(staticFile);
          OutputStreamWriter writer = new OutputStreamWriter(outStream, "UTF-8");
          BufferedWriter sw = new BufferedWriter(writer);

          Map<String, String> rootMap = new HashMap<>();
          rootMap.put("page_tab", "category");
          rootMap.put("category", category);

          template.process(rootMap, sw);
          sw.flush();
          sw.close();
          outStream.close();
        } catch (IOException | TemplateException e) {
          log.error(e.getMessage());
        }
      }
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
    try {
      File file = fileUtil.createFile(siteConfig.getStaticHtml() + "/feed.xml");

      List<Blog> blogs = fileUtil.getBlogs();
      SyndFeed feed = new SyndFeedImpl();
      feed.setFeedType("atom_1.0");
      feed.setTitle(siteConfig.getTitle());
      feed.setDescription(siteConfig.getDescription());
      feed.setAuthor(siteConfig.getAuthor());
      feed.setUri(siteConfig.getUrl());
      feed.setEncoding("UTF-8");
      if (blogs.size() > 0) {
        feed.setPublishedDate(blogs.get(0).getDate());
        List<SyndEntry> entries = new ArrayList<>();
        for (int i = 0; i < blogs.size(); i++) {
          if (i >= 10) {
            Blog blog = blogs.get(i);
            try {
              SyndEntry syndEntry = new SyndEntryImpl();
              syndEntry.setTitle(blog.getTitle());
              syndEntry.setLink(siteConfig.getUrl() + blog.getUrl() + "index.html");
              syndEntry.setPublishedDate(blog.getDate());
              syndEntry.setUri(siteConfig.getUrl() + blog.getUrl() + "index.html");
              syndEntry.setAuthor(blog.getAuthor() == null ? siteConfig.getAuthor() : blog.getAuthor());
              // content
              List<SyndContent> contents = new ArrayList<>();
              SyndContent syndContent = new SyndContentImpl();
              syndContent.setValue(String.valueOf(new CDATA(markdownUtil.pegDown(blog.getContent()))));
              syndContent.setType("text/html");
              contents.add(syndContent);
              syndEntry.setContents(contents);

              // excerpt
              SyndContent syndContent1 = new SyndContentImpl();
              syndContent1.setType("text/html");
              syndContent1.setValue(markdownUtil.pegDown(blog.getExcerpt()));
              syndEntry.setDescription(syndContent1);

              // categories
              for (String category : blog.getCategories()) {
                SyndCategory syndCategory = new SyndCategoryImpl();
                syndCategory.setName(category);
                syndCategory.setTaxonomyUri(siteConfig.getUrl() + "/category/" + category + "/index.html");
              }
              entries.add(syndEntry);
            } catch (Exception e) {
              log.error(e.getMessage());
            }
          }
        }
        feed.setEntries(entries);
      }

      String rssContent = new SyndFeedOutput().outputString(feed);

      FileWriter fw = new FileWriter(file);
      fw.write(rssContent);
      fw.flush();
      fw.close();
    } catch (FeedException | IOException e) {
      log.error(e.getMessage());
    }
  }
}
