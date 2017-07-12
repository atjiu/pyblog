# pyblog
Similar to jekyll's java language development of static blog system

[English](https://github.com/tomoya92/pyblog/blob/master/README.md) | [中文](https://github.com/tomoya92/pyblog/blob/master/README_CN.md)

# TOC

- [Start](#start)
    - [Quick guide](#quick-guide)
    - [Installation environment](#installation-environment)
    - [Basic usage](#basic-usage)
    - [Directory structure](#directory-structure)
	- [Configuration](#configuration)
- [Blog content](#blog-content)
    - [Front matter](#front-matter)
	- [Write blog](#write-blog)
- [Personalise](#personalise)
    - [Template](#template)
    - [Template static resource file](#template-static-resource-file)
    - [Commonly used variables](#commonly-used-variables)
    - [Common template tags](#common-template-tags)
        - [markdown](#markdown)
        - [blogs_tag](#blogs_tag)
        - [tags_tag](#tags_tag)
        - [categories_tag](#categories_tag)
        - [blog_tag](#blog_tag)
    - [Object structure](#object-structure)
        - [Blog](#blog)
        - [Page](#page)

# Start

## Quick guide

1. download[pyblog.tar.gz](https://github.com/tomoya92/pyblog/releases/tag/0.0.1)
2. decompression
3. run start.sh `./start.sh`
4. open browser visit: http://localhost:4000/ 

**Note**

Close server: `./shutdown.sh`

If `./start.sh` or `./shutdown.sh` execution failed，you need to check the file permissions，The authorization command: `chmod a+x start.sh shutdown.sh`

## Installation environment

Pyblog only need a java operating environment, 
how to configure the operating environment on the java 
i think that Baidu / Google will find a lot of articles, 
according to the contents of the article to operate

Only need to ensure that `java-version` java version number can be

**Recommend the use of java8 version**

## Basic usage

Start the service, and then the blog file into the `_posts` folder to automatically publish blog

For configuration information, see the following configuration instructions

## Directory structure

```
├── application.yml
├── _posts
│   └── 2017-07-12-hello-world.md
├── pyblog.jar
├── shutdown.sh
├── _sites
├── start.sh
├── static
└── templates
```

| Name | Explanation |
| --- | --- |
| `application.yml` | Pyblog unique configuration file, configure the site of the various information |
| `_posts` | Blog the original file storage location, in the application.yml can be configured into other names |
| `pyblog.jar` | Main program, no need to pay attention |
| `shutdown.sh` | Close the service script |
| `_site` | Published static page storage location |
| `start.sh` | Start the service script |
| `static` | The static file storage location used in the template file |
| `templates` | Template file location, the default is default (default template is located in pyblog.jar, can not be modified), you can add your favorite other templates, generate different styles of blog |

## Configuration

| Name | Explanation| 
|---|---|
| `server.port` | Service port |
| `debug` | Development mode options, if so, then modify the files under the template will immediately update the blog page, only in the development of blog templates need to open |
| `theme` | Theme (both blog template) default is default (default template is located in pyblog.jar, can not be modified), you can add your favorite other templates, generate different styles of blog |
| `url` | Site access address, followed by no `/`, otherwise the blog link will appear `//` phenomenon |
| `permalink` | Blog link style，default is `/:year/:month/:day/:title/` That is, _posts folder in the file name in the `year/month/day/title`, please be written in such a form, to prevent the release of the blog page is covered by the phenomenon |
| `excerpt_separator` | Blog summary of the original abstract, used in the blog list show, the default for the four-line |
| `page_size` | The number of posts per page displayed on the blog list |
| `post` | The name of the original folder where the blog was stored |
| `static_html` | The published static page holds the location name |
| `base.xx` | Site related configuration, you can write any content, in the template directly through the ${_site.base.xx} value on the show |
| `links.xx` | Links, like the base configuration, you can write any content, in the template directly through the ${_site.links.xx} value display on the |
| `contacts.xx` | Bloggers contact, with the base configuration, you can write any content, in the template directly through the ${_site.contacts.xx} value on the show |
| `analytics.xx` | Blog access statistical methods, with the base configuration, you can write any content, in the template directly through ${_site.analytics.xx} value display on the |
| `comment.xx` | Blog comments, with the base configuration, you can write any content, in the template directly through the ${_site.comment.xx} value on the show |

# Blog content

## Front matter

Requirements: According to YAML format written in two lines between the three dashed lines, the following is a basic example:

```yaml

---
title: hello world
---

```

Between the two lines of the two lines, you can set the predefined variables, the variables are the following:

| Name | Explanation| 
|---|---|
| `title` | Blog title, required |
| `date` | Blog date, if not, the date before the blog file was published for the date of the blog |
| `author` | Blog author |
| `tags` | Blog tags, separated by spaces, can be written multiple |
| `categories` | Blog categories, to space division, you can write multiple |

**Note: Please use UTF-8 encoding format for blog files**

## Write blog

The name of the blog file must be named in the format of `year-month-day-title.md`

Blog articles contain front matter + Markdown markup content, you can safely use the markdown syntax to write blog, and now only support markdown syntax

The contents of the `excerpt_separator`, which are configured in the configuration file, are split out of the blog digest. You can use the `${blog.excerpt}`

# Personalise

## Template

Pyblog uses the template freemarker, the template file in the templates folder

Create a folder, **name can not be default, at the same time application.yml in the theme variable should be changed to the name of the folder you create** and then the template file you create the folder, such as:

```
templates/
└── default
    ├── archive.ftl
    ├── category.ftl
    ├── detail.ftl
    ├── footer.ftl
    ├── header.ftl
    ├── index.ftl
    ├── layout.ftl
    ├── paginate.ftl
    └── tag.ftl
```

**Note: archive.ftl, category.ftl, detail.ftl, index.ftl, tag.ftl The names of these files are fixed and can not be modified to other names**

So that you can develop their own templates, and style can be completely their own set

**Tips, the development of the template when the debug mode can be opened in real time Oh**

## Template static resource file

Template to use the static resource file please put in the static folder, and then directly in the page can be quoted

## Commonly used variables

Development of the template when the variables used to aggregate

| Name | Explanation| 
|---|---|
| `_site.theme` | Get the name of the template used |
| `_site.url` | Site access domain name (not followed by `/`) |
| `_site.page_size` | Get the number of blogs per page |
| `_site.base.xx` | Get the blog site settings information, what can be configured to what, such as: `_site.base.title` `_site.base.brief_intro` `_site.base.description_footer` |
| `_site.links.xx` | Get the blog site of the Friends of the chain information, what can be configured to what |
| `_site.contacts.xx` | Get bloggers contact, what can be configured to take what |
| `_site.analytics.xx` | Get statistics, what can be configured to take what |
| `_site.comment.xx` | Get the configuration information of the comment, what can be configured to take what |

## Common template tags

### markdown

Description: Turn markdown grammar text into html grammar label

Parameters: None

Usage:

`${markdown.pegDown(blog.content)}`

### blogs_tag
 
Description: get all the blog, **in the index.ftl page can be directly through the ${pageNo} way to get pageNo**

Parameters: pageNo number of pages, if the pageNo will pass the current page number of the distribution of the object `page`, if not transmitted pageNo, will return to all the blog list

Usage:

```html
<!--Do not pass pageNo usage-->
<@blogs_tag>
  <#list blogs as blog>
    <p>${blog.title}</p>
  </#list>
</#list>
</@blogs_tag>

<!--The use of pageNo-->
<@blogs_tag pageNo=1>
  <#list page.list as blog>
    <p>${blog.title}</p>
  </#list>
  <p>totalPage: ${page.totalPage} totalCount: ${page.totalCount}</p>
</#list>
</@blogs_tag>
```

### tags_tag

Description: Tag list

Parameters: None

Usage:

```html
<@tags_tag>
  <#list tags as tag>
    <div class="page-header">
      <h2>${tag.name}</h2>
    </div>
    <ul>
      <#list tag.blogs as blog>
        <li class="lead">
          <a href="${blog.url!}index.html">${blog.title!}</a>
        </li>
      </#list>
    </ul>
  </#list>
</@tags_tag>
```

### categories_tag

Description: Category list

Parameters: None

Usage:

```html
<@categories_tag>
  <#list categories as category>
    <div class="page-header">
      <h2>${category.name}</h2>
    </div>
    <ul>
      <#list category.blogs as blog>
        <li class="lead">
          <a href="${blog.url!}index.html">${blog.title!}</a>
        </li>
      </#list>
    </ul>
  </#list>
</@categories_tag>
```

### blog_tag

Description: blog details

Parameters: url, is the url field in the blog, similar: /2017/07/11/hello-world/

Usage:

```html
<@blog_tag url="/2017/07/11/hello-world/">
  <div class="page-header">
    <h2>${blog.title}</h2>
  </div>
</@blog_tag>
```

## Object structure

### Blog

| Name | Type | Explanation |
| --- | --- | --- |
| title | String | Blog title |
| excerpt | String | Blog excerpt |
| content | String | Blog content |
| url | String | Blog details of the visit link, similar to: /2017/07/11/hello-world/ |
| date | String | Blog release date, if the header information is not written on the date attribute, it will take the date on the file name as the blog release time |
| author | String | Blog author |
| categories | List<String> | Blog categories |
| tags | List<String> | Blog tags |

### Page

| Name | Type | Explanation |
| --- | --- | --- |
| pageNo | int | Current page |
| pageSize | int | Number of pages per page |
| list | List<Blog> | The current page's blog list |
| firstPage | boolean | Whether it is the first page |
| lastPage | boolean | Whether it is the last page |
| totalPage | int | Total Page |
| totalCount | int | Total count |
