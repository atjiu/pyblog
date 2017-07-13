<div style="background-color:#fff;padding:20px 0;">
  <div class="container text-center">
  <#if _site.links??>
    <p>
      Links:&nbsp;
      <#list _site.links as name, url>
        <span><a href="${url}">${name}</a></span>
      </#list>
    </p>
  </#if>
    <#if _site.description?? || _site.contacts??>
    <p>
      ${_site.description!}
      <#if _site.contacts??>
        Contact me at:
        <#list _site.contacts as key, value>
          <#if key == 'github' && (value?length > 0)>
            <a href="https://github.com/${value!}">
              <i class="fa fa-github" aria-hidden="true"></i>
            </a>
          </#if>
          <#if key == 'weibo' && (value?length > 0)>
            <a href="https://weibo.com/${value!}">
              <i class="fa fa-weibo" aria-hidden="true"></i>
            </a>
          </#if>
          <#if key == 'email' && (value?length > 0)>
            <a href="mailto:${value}">
              <i class="fa fa-envelope" aria-hidden="true"></i>
            </a>
          </#if>
        </#list>
      </#if>
    </p>
    </#if>
    <p>Site powered by <a href="https://github.com/tomoya92/pyblog">PyBlog</a>. Theme designed by <a href="https://github.com/tomoya92/pyblog">PyBlog</a>.</p>
  </div>
</div>
