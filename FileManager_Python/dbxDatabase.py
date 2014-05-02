"""
Dropbox Database Handler App

Require: Dropbox Python SDK

Author: Yuan Wei
"""

import sys

from dropbox.client import (
    DropboxClient,
    DropboxOAuth2FlowNoRedirect,
    ErrorResponse,
    )
from dropbox.datastore import DatastoreManager, Date, DatastoreError

APP_KEY = 'viaohzfio4qj4dq'
APP_SECRET = '1m4a46y34gezltu'

def bool_to_string(bool_value):
    return "Yes" if bool_value else "No"

class FileType():
    Video, Audio, Image = range(1, 4)

class MediaInfo():
    def __init__(self, title, fileName, summary, description, caption, thumbnail, relatedItems, isOnHomeGrid, armature, fileType):
        self.title = title
        self.fileName = fileName
        self.summary = summary
        self.description = description
        self.caption = caption
        self.thumbnail = thumbnail
        self.relatedItems = relatedItems
        self.isOnHomeGrid = isOnHomeGrid
        self.armature = armature
        self.fileType = fileType

class DbxDataAdapter():
    TOKEN_FILE = "token_store.txt"

    def __init__(self, app_key, app_secret):
        self.app_key = app_key
        self.app_secret = app_secret
        self.api_client = None 

    def is_linked(self):
        try:
            serialized_token = open(self.TOKEN_FILE).read()
            if serialized_token.startswith('oauth1:'):
                access_key, access_secret = serialized_token[len('oauth1:'):].split(':', 1)
                sess = session.DropboxSession(self.app_key, self.app_secret)
                sess.set_token(access_key, access_secret)
                self.api_client = DropboxClient(sess)
                print "[loaded OAuth 1 access token]"
            elif serialized_token.startswith('oauth2:'):
                access_token = serialized_token[len('oauth2:'):]
                self.api_client = DropboxClient(access_token)
                print "[loaded OAuth 2 access token]"
            else:
                return False
        except IOError:
            return False
        return True

    def link(self):
        auth_flow = DropboxOAuth2FlowNoRedirect(self.app_key, self.app_secret)
        url = auth_flow.start()
        print '1. Go to:', url
        print '2. Authorize this app.'
        print '3. Enter the code below and press ENTER.'
        auth_code = raw_input().strip()
        access_token, user_id = auth_flow.finish(auth_code)
        with open(self.TOKEN_FILE, 'w') as f:
            f.write('oauth2:' + access_token)
        self.api_client = DropboxClient(access_token)

    def get_authorize_url(self):
        auth_flow = DropboxOAuth2FlowNoRedirect(self.app_key, self.app_secret)
        url = auth_flow.start()
        return url

    def link(self, auth_code):
        auth_flow = DropboxOAuth2FlowNoRedirect(self.app_key, self.app_secret)
        url = auth_flow.start()
        access_token, user_id = auth_flow.finish(auth_code)
        with open(self.TOKEN_FILE, 'w') as f:
            f.write('oauth2:' + access_token)
        self.api_client = DropboxClient(access_token)

    def connect(self):
        manager = DatastoreManager(self.api_client)
        self.datastore = manager.open_default_datastore()
    
    def insert_data(self, media_list):
        media_table = self.datastore.get_table('media')
        database_list = media_table.query()
        for data in database_list:
            data.delete_record()
        for info in media_list:
            media_table.insert(title=info.title, file_name=info.fileName,\
                summary=info.summary, description=info.description, caption=info.caption,\
                thumbnail_name=info.thumbnail, related_items=info.relatedItems,\
                is_on_home_grid=info.isOnHomeGrid, armature=info.armature, file_type=info.fileType)
        try:
            self.datastore.commit()
        except DatastoreConflictError:
            print "Conflict Error!!!"
            datastore.rollback()    # roll back local changes
            datastore.load_deltas() # load new changes from Dropbox

    def read_data(self):
        media_table = self.datastore.get_table('media')
        data_list = media_table.query()
        media_list = []
        for data in data_list:
            title = data.get('title') 
            fileName = data.get('file_name')
            summary = data.get('summary')
            description = data.get('description')
            caption = data.get('caption')
            thumbnail = data.get('thumbnail_name')
            relatedItems = data.get('related_items')
            isOnHomeGrid = data.get('is_on_home_grid')
            armature = data.get('armature')
            fileType = data.get('file_type')
            media_list.append(MediaInfo(title, fileName, summary, \
                description, caption, thumbnail, relatedItems, isOnHomeGrid, armature, fileType))
        return media_list

def main():
    if APP_KEY == '' or APP_SECRET == '':
        exit("You need to set your APP_KEY and APP_SECRET!")
    database = DbxDataAdapter(APP_KEY, APP_SECRET)
    if not database.is_linked():
        database.link()
    database.connect()
    data = database.read_data()
    print '*' * 40
    for info in data:
        print 'Title: ' + info.title
        print 'Armature: ' + info.armature
        print 'File Name: ' + info.fileName
        print 'Summary: ' + info.summary
        print 'Description: ' + info.description
        print 'Thumbnail: ' + info.thumbnail
        print 'Related: ' + info.relatedItems
        print 'IsOnHome: ' + bool_to_string(info.isOnHomeGrid)
        print '-----------------------------'
    print '*' * 40

if __name__ == '__main__':
    main()
