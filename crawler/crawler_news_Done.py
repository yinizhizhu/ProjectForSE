# -*- coding:utf-8 -*-
"""
@author: lijiahe
"""
import re
import random
import urllib2
import os
base_dir = "news"
if not os.path.exists(base_dir):
    os.mkdir(base_dir)

#movie_pic_dir = base_dir+"/pic"
#if not os.path.exists(movie_pic_dir):
#    os.mkdir(movie_pic_dir)

class media():
    def __init__(self):
        self.base_dir = base_dir+"/"
#        self.movie_pic_dir = movie_pic_dir+'/'
        self.url = ""
        self.host = ""
        self.referer = ""
        self.my_headers = [
            'Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36',
            "Mozilla/5.0 (Windows NT6.1; WOW63; rv:27.0) Gecko/20100101 Firefox/27.0",
            "Mozilla/5.0 (Windows; U WOW63; Windows NT6.1; en-US; rv:1.9.1.1) Gecko/20091201 Firefox/3.5.6",
            "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0",
            "Mozilla/5.0 (X11; Ubuntu; Linux i689; rv:10.0) Gecko/20100101 Firefox/10.0",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.10240",
            "Mozilla/5.0 (Windows NT 8.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.10240",
            "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.124 Safari/537.36",
            "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.124 Safari/537.36"
        ]

    def GetContent(self):
        """
            To get the content of the web page we look through
            
                output:
                    content             the content of the web page
        """
        random_header = random.choice(self.my_headers)
        print self.url
        req = urllib2.Request(self.url)
        req.add_header("User-Agent", random_header)
        req.add_header("Host", self.host)
        req.add_header("Referer", self.referer)
        req.add_header("GET", self.url)
        try:
            html = urllib2.urlopen(req)
            content = html.read()
            print html.info()
            html.close()
            return content
        except:
            print "Something wrong is happening in get_content!"

    def DealUrlFirst(self, recom1, recom2):
        """
        return value:
            container1: org, title, link, number
            container2: title, link, number
        """
        try:
            __recom__ = '<li>(.*?)i>'
            container1 = list()
            i = 0
            for content in recom1:
                parts = re.findall(__recom__, content)
                __orgnization__ = "target='_blank'>(.*?)</a>]</span>"
                __link__ = "</span> <a href='(.*?)' title="
                __title__ = "title='(.*?)' target="
                __number__ = "/a> (.*?)</l"
                for part in parts:
                    container1.append(list())
                    orgnization = re.findall(__orgnization__, part)
                    for one in orgnization:
#                        print "ORG"
                        container1[i].append(one)
                    title = re.findall(__title__, part)
                    for one in title:
#                        print "TITLE"
                        container1[i].append(one)
                    link = re.findall(__link__, part)
                    for one in link:
#                        print "LINK"
                        container1[i].append("http://today.hit.edu.cn"+one)
                    number = re.findall(__number__, part)
                    for one in number:
#                        print "NUM"
                        container1[i].append(one)
                    i += 1
            i = 0
            container2 = list()
            for content in recom2:
                parts = re.findall(__recom__, content)
                __link__ = "<a href='(.*?)' title="
                __title__ = "title='(.*?)' target="
                __number__ = "<span>(.*?)</span>"
                for part in parts:
                    container2.append(list())
                    title = re.findall(__title__, part)
                    for one in title:
#                        print "TITLE"
                        container2[i].append(one)
                    link = re.findall(__link__, part)
                    for one in link:
#                        print "LINK"
                        container2[i].append("http://today.hit.edu.cn"+one)
                    number = re.findall(__number__, part)
                    for one in number:
#                        print "NUM"
                        container2[i].append(one)
                    i += 1
#                print container1
#                print container2
                self.GetDetails(container1, container2)
        except:
            print "The error is happening in DealUrlFirst"

    def GetUrlFirst(self):
        """
            To get the url of the second floor
        """
        self.url = "http://today.hit.edu.cn/"
        self.host = "today.hit.edu.cn"
        self.referer = "http://today.hit.edu.cn/app/rl.asp"
        content = self.GetContent()
        
        homepage = open(self.base_dir+"homepage.html", "w")
        print >> homepage, content
        homepage.close()
        
        __recom1__ = '<div class="sidelist"> <ul>(.*?)</ul> </div>'
        __recom2__ = '<div class="sidelist4"> <ul>(.*?)</ul> </div>'
        try:
            Ones = re.findall(__recom1__, content, re.S)
            Twos = re.findall(__recom2__, content, re.S)
            
            recom1 = list()
            recom2 = list()
            for one in Ones:
                recom1.append(one)
            for two in Twos:
                recom2.append(two)
            self.DealUrlFirst(recom1, recom2)
        except:
            print "Something wrong is happening in GetUrlFirst!"

    def GetDetails(self, container1, container2):
        """
        input
            container1: org, title, link, number
            container2: title, link, number
        """
        org = open(self.base_dir+"org.txt", "w")
        for i in xrange(len(container1)):
            for j in xrange(len(container1[i])-1):
                print >> org, container1[i][j]+"~",
            print >> org, container1[i][len(container1[i])-1]
        org.close()
        rec = open(self.base_dir+"rec.txt", "w")
        for i in xrange(len(container2)):
            for j in xrange(len(container2[i])-1):
                print >> rec, container2[i][j]+"~",
            print >> rec, container2[i][len(container2[i])-1]
        rec.close();

product = media()
product.GetUrlFirst()
