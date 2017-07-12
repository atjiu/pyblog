package cn.tomoya.fmtag;

import cn.tomoya.model.Blog;
import cn.tomoya.util.FileUtil;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tomoya on 2017/7/10.
 */
@Component
public class CategoriesDirective implements TemplateDirectiveModel {

  @Autowired
  private FileUtil fileUtil;

  @Override
  public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                      TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
    DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_26);

    List<Blog> blogs = fileUtil.getBlogs();
    List<String> allCategories = new ArrayList<>();

    for (Blog blog : blogs) {
      if (blog.getCategories() != null && blog.getCategories().size() > 0) {
        allCategories.addAll(blog.getCategories());
      }
    }

    // Duplicate removal
    List<String> newCategories = fileUtil.duplicateRemoval(allCategories);

    List<Map<String, Object>> categories = new ArrayList<>();
    for(String category: newCategories) {
      Map map1 = new HashMap();
      List<Blog> blog1 = new ArrayList<>();
      for(Blog blog: blogs) {
        if(blog.getCategories().contains(category)) {
          blog1.add(blog);
        }
      }
      map1.put("name", category);
      map1.put("blogs", blog1);
      categories.add(map1);
    }

    environment.setVariable("categories", builder.build().wrap(categories));
    templateDirectiveBody.render(environment.getOut());
  }
}
