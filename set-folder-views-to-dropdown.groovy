/*
    Copyright (c) 2015-2017 Sam Gleske - https://github.com/samrocketman/jenkins-script-console-scripts

    Permission is hereby granted, free of charge, to any person obtaining a copy of
    this software and associated documentation files (the "Software"), to deal in
    the Software without restriction, including without limitation the rights to
    use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
    the Software, and to permit persons to whom the Software is furnished to do so,
    subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
    FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
    COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
    IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
    CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

/*
   Search a Jenkins instance for all folders which have a large number of views
   and change it to a dropdown list.
*/

import com.cloudbees.hudson.plugins.folder.Folder
import hudson.views.tabbar.DropDownViewsTabBar

//maximum number of views before converting views tab to a dropdown list
int max_views = 5

List<String> message = []
Jenkins.instance.getAllItems(Folder.class).each { i ->
    if(i.views.size() > max_views && !(i.folderViews.tabBar instanceof DropDownViewsTabBar)) {
        i.folderViews.tabBar = new DropDownViewsTabBar()
        i.save()
        message << i.fullName.toString()
    }
}

if(message.size() > 0) {
    println 'Projects with views tab changed to dropdown:'
    println "    ${message.join('\n    ')}"
}
