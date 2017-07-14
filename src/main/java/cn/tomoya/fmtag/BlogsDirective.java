package cn.tomoya.fmtag;

import cn.tomoya.util.FileUtil;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * Created by tomoya on 2017/7/10.
 */
@Component
public class BlogsDirective implements TemplateDirectiveModel {

  @Autowired
  private FileUtil fileUtil;

  @Override
  public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                      TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
    DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_26);

    environment.setVariable("blogs", builder.build().wrap(fileUtil.getBlogs()));

    templateDirectiveBody.render(environment.getOut());
  }
}
