package cn.tomoya.util;

import cn.tomoya.config.SiteConfig;
import cn.tomoya.model.Blog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tomoya on 2017/7/10.
 */
@Component
public class FileUtil {

  Logger log = LoggerFactory.getLogger(FileUtil.class);

  @Autowired
  private SiteConfig siteConfig;

  public File mkdir(String path) {
    File file = new File(path);
    if (!file.exists()) {
      file.mkdir();
    }
    return file;
  }

  public File mkdirs(String path) {
    File file = new File(path);
    if (!file.exists()) {
      file.mkdirs();
    }
    return file;
  }

  public File createFile(String path) {
    File file = new File(path);
    try {
      if (!file.exists()) {
        file.createNewFile();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return file;
  }

  private List<Blog> blogs = new ArrayList<>();

  public List<Blog> getBlogs() {
    return blogs;
  }

  public void formatter() {
    File fileDir = new File(siteConfig.getPost());
    File[] fileDirs = fileDir.listFiles();
    if (fileDirs != null) {
      Arrays.sort(fileDirs, new Comparator<File>() {
        @Override
        public int compare(File o1, File o2) {
          return Long.compare(extractDate(o2.getName()).getTime(), extractDate(o1.getName()).getTime());
        }

        private Date extractDate(String name) {
          Pattern fileNameRegex = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
          Matcher fileNameRegexMatcher = fileNameRegex.matcher(name);
          if (fileNameRegexMatcher.find()) {
            return DateUtil.string2Date(fileNameRegexMatcher.group(), DateUtil.DATEFORMATSTYLE);
          }
          return new Date();
        }
      });
      for (int i = 0; i < fileDirs.length; i++) {
        try {
          File file = fileDirs[i];
          Blog blog = new Blog();

          String fileName = file.getName();
          fileName = fileName.substring(0, fileName.lastIndexOf("."));
          Date fileNameDate;
          String fileNameTitle;
          Pattern fileNameRegex = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
          Matcher fileNameRegexMatcher = fileNameRegex.matcher(fileName);
          if (fileNameRegexMatcher.find()) {
            fileNameDate = DateUtil.string2Date(fileNameRegexMatcher.group(), DateUtil.DATEFORMATSTYLE);
            fileNameTitle = fileName.substring(fileNameRegexMatcher.end() + 1, fileName.length());
          } else {
            continue;
          }

          FileReader fileReader = new FileReader(file);
          BufferedReader br = new BufferedReader(fileReader);

          // detect YAML front matter
          String line = br.readLine();
          if(line == null) continue;

          while (line.isEmpty()) line = br.readLine();
          if (!line.matches("[-]{3,}")) { // use at least three dashes
            throw new IllegalArgumentException("No YAML Front Matter");
          }
          String delimiter = line;

          // scan YAML front matter
          StringBuilder sb = new StringBuilder();
          line = br.readLine();
          if(line == null) continue;

          while (line != null && !line.equals(delimiter)) {
            sb.append(line);
            sb.append("\n");
            line = br.readLine();
          }
          if(line == null) continue;

          Properties prop = new Properties();
          prop.load(new StringReader(sb.toString()));

          String title = prop.getProperty("title");
          if(title == null) continue;
          blog.setTitle(title);
          blog.setAuthor(prop.getProperty("author"));
          String blogDate = prop.getProperty("date");
          if (blogDate != null) {
            blog.setDate(DateUtil.string2Date(blogDate, DateUtil.DATETIMEFORMATSTYLE));
          } else {
            blog.setDate(fileNameDate);
          }

          // set url
          String permalink = siteConfig.getPermaLink();
          String year = DateUtil.formatDate(blog.getDate(), "yyyy");
          String month = DateUtil.formatDate(blog.getDate(), "MM");
          String day = DateUtil.formatDate(blog.getDate(), "dd");
          permalink = StringUtils.replace(permalink, ":year", year);
          permalink = StringUtils.replace(permalink, ":month", month);
          permalink = StringUtils.replace(permalink, ":day", day);
          permalink = StringUtils.replace(permalink, ":title", fileNameTitle);
          blog.setUrl(permalink);

          // set tags
          String tags = prop.getProperty("tags");
          if (!StringUtils.isEmpty(tags)) {
            List<String> tagList = new ArrayList<>();
            tagList.addAll(Arrays.asList(tags.split(" ")));
            blog.setTags(tagList);
          }

          String categories = prop.getProperty("categories");
          if (!StringUtils.isEmpty(categories)) {
            List<String> categoryList = new ArrayList<>();
            categoryList.addAll(Arrays.asList(categories.split(" ")));
            blog.setCategories(categoryList);
          }

          StringBuilder contentBuf = new StringBuilder();
          line = br.readLine();
          while (line != null) {
            contentBuf.append(line);
            contentBuf.append("\n");
            line = br.readLine();
          }
          blog.setContent(contentBuf.toString());

          String excerpt_separator = siteConfig.getExcerptSeparator();
          Pattern regex = Pattern.compile(excerpt_separator);
          Matcher regexMatcher = regex.matcher(contentBuf.toString());
          String excerpt;
          if (regexMatcher.find()) {
            excerpt = contentBuf.toString().substring(0, regexMatcher.start());
          } else {
            excerpt = contentBuf.toString();
          }
          blog.setExcerpt(excerpt);

          blogs.add(blog);
        } catch (IOException | IllegalArgumentException e) {
          e.printStackTrace();
        }
      }
      for(int i = 0; i < blogs.size(); i++) {
        Blog blog = blogs.get(i);
        // previous and next
        if (blogs.size() > 1) {
          if (i == 0) {
            blog.setNext(blogs.get(i+1));
          } else if (i == blogs.size() - 1) {
            blog.setPrevious(blogs.get(i - 1));
          } else {
            blog.setNext(blogs.get(i + 1));
            blog.setPrevious(blogs.get(i - 1));
          }
        }
      }
    }
  }

  public List<String> getTags() {
    List<String> allTags = new ArrayList<>();

    for (Blog blog : blogs) {
      if (blog.getTags() != null && blog.getTags().size() > 0) {
        allTags.addAll(blog.getTags());
      }
    }
    // Duplicate removal
    return duplicateRemoval(allTags);
  }
  public Map assemblyTag(String tag) {
    Map map1 = new HashMap();
    List<Blog> blog1 = new ArrayList<>();
    for(Blog blog: blogs) {
      if(blog.getTags().contains(tag)) {
        blog1.add(blog);
      }
    }
    map1.put("name", tag);
    map1.put("blogs", blog1);
    return map1;
  }

  public List<String> getCategories() {
    List<String> allCategories = new ArrayList<>();

    for (Blog blog : blogs) {
      if (blog.getCategories() != null && blog.getCategories().size() > 0) {
        allCategories.addAll(blog.getCategories());
      }
    }
    // Duplicate removal
    return duplicateRemoval(allCategories);
  }

  public Map assemblyCategory(String catetory) {
    Map map1 = new HashMap();
    List<Blog> blog1 = new ArrayList<>();
    for(Blog blog: blogs) {
      if(blog.getCategories().contains(catetory)) {
        blog1.add(blog);
      }
    }
    map1.put("name", catetory);
    map1.put("blogs", blog1);
    return map1;
  }

  // Duplicate removal
  public List<String> duplicateRemoval(List<String> list) {
    List<String> newList = new ArrayList<>();
    for (String str : list) {
      if (!newList.contains(str)) {
        newList.add(str);
      }
    }
    return newList;
  }

  public boolean deleteDirectory(File directory) {
    if (directory.exists()) {
      File[] files = directory.listFiles();
      if (null != files) {
        for (int i = 0; i < files.length; i++) {
          if (files[i].isDirectory()) {
            deleteDirectory(files[i]);
          } else {
            files[i].delete();
          }
        }
      }
    }
    return (directory.delete());
  }
}
