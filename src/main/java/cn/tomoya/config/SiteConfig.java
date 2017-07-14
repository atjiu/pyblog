package cn.tomoya.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * Created by tomoya on 2017/7/10.
 */
@ConfigurationProperties(prefix = "_site")
public class SiteConfig {

  private boolean debug;
  private String title;
  private String subTitle;
  private String description;
  private String author;
  private String theme;
  private String url;
  private int pageSize;
  private String permaLink;
  private String excerptSeparator;
  private String post;
  private String staticHtml;
  private Map<String, Object> links;
  private Map<String, Object> contacts;
  private Map<String, Object> analytics;
  private Map<String, Object> comment;
  private Map<String, Object> other;

  public boolean isDebug() {
    return debug;
  }

  public void setDebug(boolean debug) {
    this.debug = debug;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSubTitle() {
    return subTitle;
  }

  public void setSubTitle(String subTitle) {
    this.subTitle = subTitle;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getTheme() {
    return theme;
  }

  public void setTheme(String theme) {
    this.theme = theme;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public String getPermaLink() {
    return permaLink;
  }

  public void setPermaLink(String permaLink) {
    this.permaLink = permaLink;
  }

  public String getExcerptSeparator() {
    return excerptSeparator;
  }

  public void setExcerptSeparator(String excerptSeparator) {
    this.excerptSeparator = excerptSeparator;
  }

  public String getPost() {
    return post;
  }

  public void setPost(String post) {
    this.post = post;
  }

  public String getStaticHtml() {
    return staticHtml;
  }

  public void setStaticHtml(String staticHtml) {
    this.staticHtml = staticHtml;
  }

  public Map<String, Object> getLinks() {
    return links;
  }

  public void setLinks(Map<String, Object> links) {
    this.links = links;
  }

  public Map<String, Object> getContacts() {
    return contacts;
  }

  public void setContacts(Map<String, Object> contacts) {
    this.contacts = contacts;
  }

  public Map<String, Object> getAnalytics() {
    return analytics;
  }

  public void setAnalytics(Map<String, Object> analytics) {
    this.analytics = analytics;
  }

  public Map<String, Object> getComment() {
    return comment;
  }

  public void setComment(Map<String, Object> comment) {
    this.comment = comment;
  }

  public Map<String, Object> getOther() {
    return other;
  }

  public void setOther(Map<String, Object> other) {
    this.other = other;
  }
}
