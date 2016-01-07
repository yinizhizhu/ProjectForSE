# -*- coding:utf-8 -*-
"""
@author: lijiahe
"""
import re
import random
import urllib  
import urllib2
import os
base_dir = "movie"
if not os.path.exists(base_dir):
    os.mkdir(base_dir)

movie_pic_dir = base_dir+"/pic"
if not os.path.exists(movie_pic_dir):
    os.mkdir(movie_pic_dir)

class media():
    def __init__(self):
        self.base_dir = base_dir+"/"
        self.movie_pic_dir = movie_pic_dir+'/'
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
    
    def total(self, __list):
        num = 0
        for part in __list:
            num += 1
        return num

    def GetContent(self):
        """
            To get the content of the web page we look through
            
                output:
                    content             the content of the web page
        """
        random_header = random.choice(self.my_headers)
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

    def DealUrlFirst(self, all_link):
        try:
            __allscore__ = '<li class="rating">(.*?)</li>'
            __score__ = '<span class="subject-rate">(.*?)</span>'
            __none__ = '<span class="text-tip">(.*?)</span>'
            score = open(self.base_dir+"score.txt", "w")

            __name__ = 'mv_a_tl(.*?)a>'

            __image__ = '<img src="(.*?)" alt='
            src = open(self.base_dir+"image.txt", "w")
            for link in all_link:
                getImage = re.findall(__image__, link, re.S)
                x = 1
                for image in getImage:
#                    print image
                    print >> src, image
                    urllib.urlretrieve(image, self.movie_pic_dir+'%s.jpg' % x)  
                    x = x + 1          

                allscore = re.findall(__allscore__, link, re.S)
                for __score in allscore:
                    if self.total(re.findall(__none__, __score, re.S)) > 0:
                        print >> score, 0
                    else:
                        getScore = re.findall(__score__, __score, re.S)
                        for part in getScore:
        #                    print part
                            print >> score, part

                getName = re.findall(__name__, link, re.S)
                __link__ = 'href="(.*?)" class="">'
                __movie__ = '>(.*?)</'
                links = list()
                movies = list()
                for part in getName:
                    for link in re.findall(__link__, part):
                        links.append(link)
                    for movie in re.findall(__movie__, part):
                        movies.append(movie)
                self.GetDetails(links, movies)
        except:
            print "The error is happening in DealUrlFirst"
        score.close()
        src.close()

    def GetUrlFirst(self):
        """
            To get the url of the second floor
        """
        self.url = "http://movie.douban.com/"
        self.host = "movie.douban.com"
        self.referer = "http://movie.douban.com/"
        content = self.GetContent()
        
        homepage = open(self.base_dir+"homepage.html", "w")
        print >> homepage, content
        homepage.close()
        
        __clear__link = '<div class="screening-bd">.*?</div>'
        container = open(self.base_dir+"container.txt", "w")
        try:
            all_link = re.findall(__clear__link, content, re.S)
            print "All links of the web page is: ", len(all_link)
            for link in all_link:
                print >> container, link
            self.DealUrlFirst(all_link)
        except:
            print "Something wrong is happening in GetUrlFirst!"
        finally:
            container.close()
        container.close()
        
    def GetDetails(self, links, movies):
        self.host = "movie.douban.com"
        self.referer = "http://movie.douban.com/"
        try:
            url = ""
            name = ""
            for i in xrange(len(links)):
    #            print line.decode('utf-8').encode('gbk')
                url = links[i].strip()     #the url of the movie
                name = movies[i].strip().decode('utf-8').encode('gbk')   #the name of the movie
                
                self.url = url
                content = self.GetContent()
                
                detail = open(self.base_dir+name+'_'+str(i)+"_detail.txt", "w")
                __date__ = '<span property="v:initialReleaseDate" content="(.*?)"'
                dates = re.findall(__date__, content, re.S)
                for date in dates:
                    print >> detail, date
                __sumary__ = ' <span property="v:summary" class="">(.*?)</span>'
                sumarys = re.findall(__sumary__, content, re.S)
                for sumary in sumarys:
                    print >> detail, sumary.strip().replace("　　", "").replace(" ", "").replace("<br>", "").replace("<br/>", "").replace("\n", "")
    #                print sumary.strip().replace(" ", "").replace("<br>", "").replace("<br/>", "").replace("\n", "")
                detail.close()
                
                recomment = open(self.base_dir+name+'_'+str(i)+"_comment.txt", "w")
                __comment__ = '<p class="">(.*?)</p>'
                __support__ = '<span class="votes pr5">(.*?)</span>'
                comments = re.findall(__comment__, content, re.S)
                supports = re.findall(__support__, content, re.S)
                for comment in comments:
                    print >> recomment, '-'+ comment.strip()
                for support in supports:
                    print >> recomment, support.strip()
                recomment.close()
        except:
            print "The error is happening in GetDetail."

product = media()
product.GetUrlFirst()
