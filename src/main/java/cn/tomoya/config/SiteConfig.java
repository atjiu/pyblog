package cn.tomoya.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * Created by tomoya on 2017/7/10.
 */
@ConfigurationProperties(prefix = "_site")
public class SiteConfig {

  private boolean debug;
  private String theme;
  private String url;
  private int page_size;
  private String title;
  private String brief_intro;
  private String permalink;
  private String description_footer;
  private String excerpt_separator;
  private String post;
  private String static_html;
  private Map<String, Object> links;
  private Map<String, Object> contacts;
  private Map<String, Object> analytics;
  private Map<String, Object> comment;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getDescription_footer() {
    return description_footer;
  }

  public void setDescription_footer(String description_footer) {
    this.description_footer = description_footer;
  }

  public Map getAnalytics() {
    return analytics;
  }

  public void setAnalytics(Map analytics) {
    this.analytics = analytics;
  }

  public Map getComment() {
    return comment;
  }

  public void setComment(Map comment) {
    this.comment = comment;
  }

  public boolean isDebug() {
    return debug;
  }

  public void setDebug(boolean debug) {
    this.debug = debug;
  }

  public String getTheme() {
    return theme;
  }

  public void setTheme(String theme) {
    this.theme = theme;
  }

  public int getPage_size() {
    return page_size;
  }

  public void setPage_size(int page_size) {
    this.page_size = page_size;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBrief_intro() {
    return brief_intro;
  }

  public void setBrief_intro(String brief_intro) {
    this.brief_intro = brief_intro;
  }

  public String getPermalink() {
    return permalink;
  }

  public void setPermalink(String permalink) {
    this.permalink = permalink;
  }

  public String getExcerpt_separator() {
    return excerpt_separator;
  }

  public void setExcerpt_separator(String excerpt_separator) {
    this.excerpt_separator = excerpt_separator;
  }

  public Map getLinks() {
    return links;
  }

  public void setLinks(Map links) {
    this.links = links;
  }

  public Map getContacts() {
    return contacts;
  }

  public void setContacts(Map contacts) {
    this.contacts = contacts;
  }

  public String getPost() {
    return post;
  }

  public void setPost(String post) {
    this.post = post;
  }

  public String getStatic_html() {
    return static_html;
  }

  public void setStatic_html(String static_html) {
    this.static_html = static_html;
  }
}
