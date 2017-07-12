package cn.tomoya.fmtag;

import cn.tomoya.config.SiteConfig;
import cn.tomoya.model.Blog;
import cn.tomoya.model.Page;
import cn.tomoya.util.FileUtil;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by tomoya on 2017/7/10.
 */
@Component
public class BlogsDirective implements TemplateDirectiveModel {

  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private FileUtil fileUtil;

  @Override
  public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                      TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
    DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_26);

    if(map.get("pageNo") == null) {
      environment.setVariable("blogs", builder.build().wrap(fileUtil.getBlogs()));
    } else {
      int pageNo = Integer.parseInt(map.get("pageNo").toString());
      int pageSize = siteConfig.getPage_size();
      List<Blog> blogs = fileUtil.getBlogs();

      int toIndex = (pageNo - 1) * pageSize + pageSize;
      if(toIndex > blogs.size()) toIndex = blogs.size();
      Page page = new Page(pageNo, pageSize, blogs.size(), blogs.subList((pageNo - 1) * pageSize, toIndex));

      environment.setVariable("page", builder.build().wrap(page));
    }

    templateDirectiveBody.render(environment.getOut());
  }
}
