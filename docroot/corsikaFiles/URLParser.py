import urllib2
import json
import os,sys

class MyDecoder(json.JSONDecoder):
    
    def __init__(self):
        json.JSONDecoder.__init__(self, object_hook=self.dict_to_object)

    def dict_to_object(self, d):
        if '__class__' in d:
            class_name = d.pop('__class__')
            module_name = d.pop('__module__')
            module = __import__(module_name)
            print 'MODULE:', module
            class_ = getattr(module, class_name)
            print 'CLASS:', class_
            args = dict( (key.encode('ascii'), value) for key, value in d.items())
            print 'INSTANCE ARGS:', args
            inst = class_(**args)
        else:
            inst = d
        return inst

    
    
class URLParser(object):
    
    PIDSERVICE_URL="https://epic.grnet.gr/api/v2/handles/11239/" #dont forget to add "/" at the end
    PIDSERVICE_USER="chain-reds-demo"
    PIDSERVICE_PASSWD="ozJ/jFhX02CpmtWEozsD893g1TY="
    
        
    def __init__(self, handle):
        
        self.HANDLENAME=handle #for example "66742AB2-5685-11E3-A413-1C6F65A666B5"
        URL_TO_OPEN=self.PIDSERVICE_URL+self.HANDLENAME
        DATAURL=''
            
        # create a password manager
        password_mgr = urllib2.HTTPPasswordMgrWithDefaultRealm()
        
        # Add the username and password.
        password_mgr.add_password(None, self.PIDSERVICE_URL, self.PIDSERVICE_USER, self.PIDSERVICE_PASSWD)
        
        handler = urllib2.HTTPBasicAuthHandler(password_mgr)
        
        # create "opener" (OpenerDirector instance)
        opener = urllib2.build_opener(handler)
        
        # use the opener to fetch a URL
        opener.open(self.PIDSERVICE_URL)
        
        # Install the opener.
        # Now all calls to urllib2.urlopen use the created opener.
        urllib2.install_opener(opener)
        
        REQUESTDATA = urllib2.Request(URL_TO_OPEN)    
        try:
            DATAURL = urllib2.urlopen(REQUESTDATA)
        except urllib2.URLError, e:
            if e.code == 404:
                print "404-Not found"
            if e.code == 401:
                print "401-Authentication failed"    
        
        if DATAURL:
            encoded_object = ""
            for elem in DATAURL:
                encoded_object +=elem
            myobj_instance = MyDecoder().decode(encoded_object)
            self.url = myobj_instance[0]['parsed_data']


    def getURL(self):
        return self.url
    def getHandle(self):
        return self.HANDLENAME


if __name__ == '__main__':

    if len (sys.argv) != 2:
        print "Input param required"
        sys.exit(-1)

    myParser = URLParser(sys.argv[1])
    url = myParser.getURL()

    f = urllib2.urlopen(url)
    
    dest =  sys.argv[1]
    print ("dest: " + dest)
    with open(dest, "wb") as local_file:
                local_file.write(f.read())
