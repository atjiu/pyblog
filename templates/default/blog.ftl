<#include "layout/layout.ftl">
<@html title="Tags - " + _site.title>
<div class="panel panel-default">
  <div class="panel-body">
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
            <span class="label label-default">
                  <a href="/category/${category}/index.html">${category!}</a>
                </span>&nbsp;
          </#list>
        </#if>
        <#if blog.tags??>
          <span class="glyphicon glyphicon-tags"></span>
          <#list blog.tags as tag>
            <span class="label label-primary">
                  <a href="/tag/${tag}/index.html">${tag!}</a>
                </span>&nbsp;
          </#list>
        </#if>
      </p>
    </div>
    <p>${markdown.pegDown(blog.content)}</p>
    <#if blog.previous?? || blog.next??>
      <hr>
    </#if>
    <#if blog.previous??>
      <span class="lead">Previous article: <a href="${blog.previous.url}index.html">${blog.previous.title}</a></span>
    </#if>
    <#if blog.next??>
      <span class="lead pull-right">
          Next article: <a href="${blog.next.url}index.html">${blog.next.title}</a>
        </span>
    </#if>
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