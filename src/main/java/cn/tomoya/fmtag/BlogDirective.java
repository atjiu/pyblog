package cn.tomoya.fmtag;

import cn.tomoya.model.Blog;
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
public class BlogDirective implements TemplateDirectiveModel {

  @Autowired
  private FileUtil fileUtil;

  @Override
  public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                      TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
    DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_26);

    String url = map.get("url").toString();
    List<Blog> blogs = fileUtil.getBlogs();
    Blog blog = null, preBlog = null, nextBlog = null;

    for (int i = 0; i < blogs.size(); i++) {
      Blog b = blogs.get(i);
      if (b.getUrl().equals(url)) {
        blog = b;
        if (blogs.size() > 1) {
          if (i == 0) {
            nextBlog = blogs.get(i + 1);
          } else if (i == blogs.size() - 1) {
            preBlog = blogs.get(i - 1);
          } else {
            nextBlog = blogs.get(i + 1);
            preBlog = blogs.get(i - 1);
          }
        }
        break;
      }
    }

    environment.setVariable("blog", builder.build().wrap(blog));
    environment.setVariable("preBlog", builder.build().wrap(preBlog));
    environment.setVariable("nextBlog", builder.build().wrap(nextBlog));
    templateDirectiveBody.render(environment.getOut());
  }
}
