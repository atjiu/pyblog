<#include "layout.ftl">
<@html title="Home - " + _site.base.title page_tab=page_tab>
  <@blogs_tag pageNo=pageNo>
    <#list page.list as blog>
      <div class="panel panle-default">
        <div class="panel-body">
          <h2 style="margin-top:0;"><a href="${blog.url!}index.html">${blog.title!}</a></h2>
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
           <p>${markdown.pegDown(blog.excerpt)}</p>
        </div>
      </div>
    </#list>
  </@blogs_tag>
  <#include "paginate.ftl"/>
  <@paginate currentPage=page.pageNo totalPage=page.totalPage actionUrl="/"/>
</@html>