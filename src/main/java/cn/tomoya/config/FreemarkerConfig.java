package cn.tomoya.config;

import cn.tomoya.fmtag.BlogDirective;
import cn.tomoya.fmtag.BlogsDirective;
import cn.tomoya.fmtag.CategoriesDirective;
import cn.tomoya.fmtag.TagsDirective;
import cn.tomoya.util.MarkdownUtil;
import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class FreemarkerConfig {

  Logger log = LoggerFactory.getLogger(FreemarkerConfig.class);

  @Autowired
  private Configuration configuration;
  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private MarkdownUtil markdownUtil;
  @Autowired
  private BlogsDirective blogsDirective;
  @Autowired
  private TagsDirective tagsDirective;
  @Autowired
  private CategoriesDirective categoriesDirective;
  @Autowired
  private BlogDirective blogDirective;

  @PostConstruct
  public void setSharedVariable() throws TemplateModelException {
    configuration.setSharedVariable("_site", siteConfig);
    configuration.setSharedVariable("markdown", markdownUtil);
    configuration.setSharedVariable("blogs_tag", blogsDirective);
    configuration.setSharedVariable("tags_tag", tagsDirective);
    configuration.setSharedVariable("categories_tag", categoriesDirective);
    configuration.setSharedVariable("blog_tag", blogDirective);
  }

}