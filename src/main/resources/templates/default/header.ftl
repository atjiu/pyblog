<#macro header page_tab=''>
<header class="navbar navbar-default">
  <div class="container">
    <div class="navbar-header">
      <button class="navbar-toggle collapsed" type="button" data-toggle="collapse" data-target="#bs-navbar"
              aria-controls="bs-navbar" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a href="/" class="navbar-brand">
      ${_site.title!}
        <small>${_site.subTitle!}</small>
      </a>
    </div>
    <nav id="bs-navbar" class="collapse navbar-collapse">
      <ul class="nav navbar-nav navbar-right">
        <li <#if page_tab == 'home'>class="active"</#if>>
          <a href="/">Home</a>
        </li>
        <li <#if page_tab == 'archives'>class="active"</#if>>
          <a href="/archive/index.html">Archives</a>
        </li>
        <li <#if page_tab == 'categories'>class="active"</#if>>
          <a href="/category/index.html">Categories</a>
        </li>
        <li <#if page_tab == 'tags'>class="active"</#if>>
          <a href="/tag/index.html">Tags</a>
        </li>
      </ul>
    </nav>
  </div>
</header>
</#macro>