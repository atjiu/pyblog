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
import org.springframework.util.StringUtils;

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
    File templateFiles = new File("./templates/" + siteConfig.getTheme());
    if (!templateFiles.exists()) {
      log.error("Not found templates!");
    } else {
      int pageSize = siteConfig.getPageSize();
      File[] templates = templateFiles.listFiles();
      if (templates != null) {
        for (File file : templates) {
          String fileName = file.getName();
          if (fileName.contains(".ftl")) {
            if (fileName.equals("index.ftl")) {
              List<Blog> blogs = fileUtil.getBlogs();
              if (siteConfig.isPage()) {
                // index.html
                int toIndex = pageSize;
                if (toIndex > blogs.size()) toIndex = blogs.size();
                Page page = new Page(1, pageSize, blogs.size(), blogs.subList(0, toIndex));
                generatorHtml(siteConfig.getStaticHtml(), null, fileName, "page", page);

                // page html
                for (int i = 1; i <= page.getTotalPage(); i++) {
                  try {
                    int toIndex1 = (i - 1) * pageSize + pageSize;
                    if (toIndex1 > blogs.size()) toIndex1 = blogs.size();
                    Page page1 = new Page(i, pageSize, blogs.size(), blogs.subList((i - 1) * pageSize, toIndex1));
                    generatorHtml(siteConfig.getStaticHtml() + "/page" + i, null, fileName, "page", page1);
                  } catch (Exception e) {
                    e.printStackTrace();
                  }
                }
              } else {
                // index.html
                generatorHtml(siteConfig.getStaticHtml(), null, fileName, "blogs", blogs);
              }
            } else if (fileName.equals("blog.ftl")) {
              for (Blog blog : fileUtil.getBlogs()) {
                try {
                  String url = blog.getUrl();
                  String[] urls = url.split("/");
                  if (urls.length < 2) {
                    log.error("The URL address is not valid!");
                  } else {
                    generatorHtml(siteConfig.getStaticHtml() + blog.getUrl(), null, fileName, "blog", blog);
                  }
                } catch (Exception e) {
                  e.printStackTrace();
                }
              }
            } else if (fileName.equals("tag.ftl")) {
              // all tag
              List<Map> tags = new ArrayList<>();
              for (String tag : fileUtil.getTags()) {
                try {
                  Map map = fileUtil.assemblyTag(tag);
                  // tag by name
                  List<Map> tags1 = new ArrayList<>();
                  tags1.add(map);
                  generatorHtml(siteConfig.getStaticHtml() + "/tag/" + tag, null, fileName, "tags", tags1);
                  tags.add(map);
                } catch (Exception e) {
                  e.printStackTrace();
                }
              }
              generatorHtml(siteConfig.getStaticHtml() + "/tag", null, fileName, "tags", tags);
            } else if (fileName.equals("category.ftl")) {
              // all category
              List<Map> categories = new ArrayList<>();
              for (String category : fileUtil.getCategories()) {
                try {
                  Map map = fileUtil.assemblyCategory(category);
                  // category by name
                  List<Map> categories1 = new ArrayList<>();
                  categories1.add(map);
                  generatorHtml(siteConfig.getStaticHtml() + "/category/" + category, null, fileName, "categories", categories1);
                  categories.add(map);
                } catch (Exception e) {
                  e.printStackTrace();
                }
              }
              generatorHtml(siteConfig.getStaticHtml() + "/category", null, fileName, "categories", categories);
            } else if (fileName.equals("archive.ftl")) {
              List<Blog> blogs = fileUtil.getBlogs();
              if (siteConfig.isPage()) {
                // archive html
                int toIndex = pageSize;
                if (toIndex > blogs.size()) toIndex = blogs.size();
                Page page = new Page(1, pageSize, blogs.size(), blogs.subList(0, toIndex));
                generatorHtml(siteConfig.getStaticHtml() + "/archive", null, fileName, "page", page);

                // archive page html
                for (int i = 1; i <= page.getTotalPage(); i++) {
                  try {
                    int toIndex1 = (i - 1) * pageSize + pageSize;
                    if (toIndex1 > blogs.size()) toIndex1 = blogs.size();
                    Page page1 = new Page(i, pageSize, blogs.size(), blogs.subList((i - 1) * pageSize, toIndex1));
                    generatorHtml(siteConfig.getStaticHtml() + "/archive/page" + i, null, fileName, "page", page1);
                  } catch (Exception e) {
                    e.printStackTrace();
                  }
                }
              } else {
                // archive html
                generatorHtml(siteConfig.getStaticHtml() + "/archive", null, fileName, "blogs", blogs);
              }
            } else {
              String htmlName = fileName.substring(0, fileName.lastIndexOf("."));
              generatorHtml(siteConfig.getStaticHtml(), htmlName, fileName, null, null);
            }
          }
        }
      }
    }
    if (siteConfig.isRss()) generatorRss();
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

  public void generatorHtml(String dir, String htmlName, String templateFile, String attrName, Object attrValue) {
    try {
      if (StringUtils.isEmpty(htmlName)) htmlName = "index.html";
      fileUtil.mkdirs(dir);
      File staticFile = fileUtil.createFile(dir + "/" + htmlName);

      FileOutputStream outStream = new FileOutputStream(staticFile);
      OutputStreamWriter writer = new OutputStreamWriter(outStream, "UTF-8");
      BufferedWriter sw = new BufferedWriter(writer);

      Map rootMap = new HashMap();
      if (!StringUtils.isEmpty(attrName)) rootMap.put(attrName, attrValue);

      Template template = configuration.getTemplate(siteConfig.getTheme() + "/" + templateFile);
      template.process(rootMap, sw);
      sw.flush();
      sw.close();
      outStream.close();
    } catch (IOException | TemplateException e) {
      e.printStackTrace();
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
              e.printStackTrace();
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
      e.printStackTrace();
    }
  }
}
