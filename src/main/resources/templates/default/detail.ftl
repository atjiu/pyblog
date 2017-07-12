<#include "layout.ftl">
<@html title="Tags - " + _site.base.title page_tab=page_tab>
<div class="panel panel-default">
  <div class="panel-body">
    <@blog_tag url=url>
      <div class="page-header" style="margin: 0 0 20px;">
        <h2>${blog.title}</h2>
        <p>
          <#if blog.date??>
            <span class="glyphicon glyphicon-calendar"></span> ${blog.date?string["yyyy-MM-dd"]}
          </#if>
          <#if blog.author??>
            <span class="glyphicon glyphicon-user"></span> ${blog.author!}
          </#if>
          <#if blog.categories??>
            <span class="glyphicon glyphicon-list"></span>
            <#list blog.categories as category>
              <span class="label label-default">${category!}</span>&nbsp;
            </#list>
          </#if>
          <#if blog.tags??>
            <span class="glyphicon glyphicon-tags"></span>
            <#list blog.tags as tag>
              <span class="label label-primary">${tag!}</span>&nbsp;
            </#list>
          </#if>
        </p>
      </div>
      <p>${markdown.pegDown(blog.content)}</p>
      <#if preBlog?? || nextBlog??>
        <hr>
      </#if>
      <#if preBlog??>
        <span class="lead">Previous article: <a href="${preBlog.url}index.html">${preBlog.title}</a></span>
      </#if>
      <#if nextBlog??>
        <span class="lead pull-right">
          Next article: <a href="${nextBlog.url}index.html">${nextBlog.title}</a>
        </span>
      </#if>
    </@blog_tag>
  </div>
</div>

  <#if _site.comment?? && (_site.comment.disqus?length > 0)>
  <div class="panel panel-default">
    <div class="panel-body">
      <div id="disqus_thread"></div>
      <script>
        /**
         * RECOMMENDED CONFIGURATION VARIABLES: EDIT AND UNCOMMENT THE SECTION BELOW TO INSERT DYNAMIC VALUES FROM YOUR PLATFORM OR CMS.
         * LEARN WHY DEFINING THESE VARIABLES IS IMPORTANT: https://disqus.com/admin/universalcode/#configuration-variables
         */
        var disqus_config = function () {
          this.page.url = '${_site.url!}${blog.url!}'; // Replace PAGE_URL with your page's canonical URL variable
          this.page.identifier = '${_site.url!}${blog.url!}'; // Replace PAGE_IDENTIFIER with your page's unique identifier variable
        };
        (function () { // DON'T EDIT BELOW THIS LINE
          var d = document,
              s = d.createElement('script');
          s.src = '//${_site.comment.disqus}.disqus.com/embed.js';

          s.setAttribute('data-timestamp', +new Date());
          (d.head || d.body).appendChild(s);
        })();
      </script>
      <noscript>Please enable JavaScript to view the <a href="https://disqus.com/?ref_noscript" rel="nofollow">comments
        powered by Disqus.</a></noscript>
    </div>
  </div>
  </#if>
</@html>