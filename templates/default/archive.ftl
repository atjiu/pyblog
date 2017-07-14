<#include "layout/layout.ftl">
<@html title="Archives - " + _site.title page_tab="archive">
<div class="panel panel-default">
  <div class="panel-body">
    <#if _site.page>
      <#if page.list?? && (page.list?size > 0)>
        <#assign year = page.list[0].date?string["yyyy"]>
        <div class="page-header">
          <h2>${year}</h2>
        </div>
        <ul>
        <#list page.list as blog>
          <#if blog.date?string["yyyy"] != year>
            <#assign year = blog.date?string["yyyy"]/>
          </ul>
            <div class="page-header">
              <h2>${year}</h2>
            </div>
          <ul>
          </#if>
          <li class="lead">
            <a href="${blog.url!}index.html">${blog.title!}</a>
          </li>
        </#list>
        </ul>
        <#include "layout/paginate.ftl"/>
        <@paginate currentPage=page.pageNo totalPage=page.totalPage actionUrl="/archive/"/>
      </#if>
    <#else>
      <#if blogs?? && (blogs?size > 0)>
        <#assign year = blogs[0].date?string["yyyy"]>
        <div class="page-header">
          <h2>${year}</h2>
        </div>
        <ul>
        <#list blogs as blog>
          <#if blog.date?string["yyyy"] != year>
            <#assign year = blog.date?string["yyyy"]/>
          </ul>
            <div class="page-header">
              <h2>${year}</h2>
            </div>
          <ul>
          </#if>
          <li class="lead">
            <a href="${blog.url!}index.html">${blog.title!}</a>
          </li>
        </#list>
        </ul>
      </#if>
    </#if>
  </div>
</div>
</@html>