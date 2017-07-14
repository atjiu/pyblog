package cn.tomoya.config;

import cn.tomoya.fmtag.BlogsDirective;
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

  @PostConstruct
  public void setSharedVariable() throws TemplateModelException {
    configuration.setSharedVariable("_site", siteConfig);
    configuration.setSharedVariable("markdown", markdownUtil);
    configuration.setSharedVariable("blogs_tag", blogsDirective);
  }

}