"""
File Manager and Uploader for ArtGallery Anrdoid App

Require: Python 2.7 and wxPython 3.0, Dropbox Python SDK

Author: Yuan Wei
"""

import sys
import shutil
import ConfigParser, os.path
import wx

from dbxDatabase import *

#----------------------------------------------------------------------------
class MainFrame(wx.Frame):
    def __init__(self, *args, **kwds):
        kwds['style'] = wx.DEFAULT_FRAME_STYLE
        wx.Frame.__init__(self, *args, **kwds)

        # Menubar
        menuBar = wx.MenuBar()
        self.fileMenu = wx.Menu()
        self.fileMenu.Append(wx.ID_NEW,     '&New\tCtrl-N', 'Create a new item')
        self.fileMenu.Append(wx.ID_EDIT,    '&Edit\tCtrl-E', 'Edit selected item')
        self.fileMenu.Append(wx.ID_DELETE,  '&Delete\tCtrl-D', 'Delete selected item')
        self.fileMenu.AppendSeparator()
        self.fileMenu.Append(wx.ID_SAVE,   '&Sync\tCtrl-S', 'Save changes to dropbox')
        self.fileMenu.AppendSeparator()
        self.fileMenu.Append(wx.ID_EXIT,   '&Quit\tCtrl-Q')

        menuBar.Append(self.fileMenu, '&Item')

        self.SetMenuBar(menuBar)

        # Statusbar
        self.statusbar = self.CreateStatusBar()

        # Toolbar
        icon_size = (15,15)
        self.toolbar = self.CreateToolBar(wx.TB_HORIZONTAL | wx.NO_BORDER | wx.TB_FLAT)

        artBmp = wx.ArtProvider.GetBitmap
        self.toolbar.AddSimpleTool(
            wx.ID_NEW, artBmp(wx.ART_NEW, wx.ART_TOOLBAR, icon_size), 'New')

        self.toolbar.AddSimpleTool(
            wx.ID_EDIT, artBmp(wx.ART_LIST_VIEW, wx.ART_TOOLBAR, icon_size), 'Edit')

        self.toolbar.AddSimpleTool(
            wx.ID_DELETE, artBmp(wx.ART_DELETE, wx.ART_TOOLBAR, icon_size), 'Delete')

        self.toolbar.AddSimpleTool(
            wx.ID_SAVE, wx.Bitmap('sync.png', wx.BITMAP_TYPE_PNG) , 'Sync')

        self.toolbar.Realize()

        # Associate menu/toolbar items with their handlers.
        menuHandlers = [
        (wx.ID_NEW,       self.doNew),
        (wx.ID_EDIT,      self.doEditItem),
        (wx.ID_DELETE,    self.doDelete),
        (wx.ID_SAVE,      self.doSave),
        (wx.ID_EXIT,      self.doExit)]

        for combo in menuHandlers:
            id, handler = combo[:2]
            self.Bind(wx.EVT_MENU, handler, id = id)
                
        self.Bind(wx.EVT_CLOSE, self.doExit)

        # Setup our top-most panel.  This holds the entire contents of the
        # window, excluding the menu bar.
        self.topPanel = wx.Panel(self, -1, style=wx.SIMPLE_BORDER)

        # Setup the list control.
        self.listCtrl = wx.ListCtrl(self.topPanel, wx.ID_VIEW_LIST, style=wx.LC_REPORT|wx.LC_SINGLE_SEL|wx.SUNKEN_BORDER)
        self.listCtrl.InsertColumn(0, 'Title', width=150)
        self.listCtrl.InsertColumn(1, 'Armature', width=100)
        self.listCtrl.InsertColumn(2, 'File', width=200)
        self.listCtrl.InsertColumn(3, 'Summary', width=100)
        self.listCtrl.InsertColumn(4, 'Description', width=250)
        self.listCtrl.InsertColumn(5, 'Thumbnail', width=150)
        self.listCtrl.InsertColumn(6, 'RelatedItem', width=150)
        self.listCtrl.InsertColumn(7, 'Home Grid')
        self.listCtrl.SetBackgroundColour(wx.WHITE)
        self.listCtrl.Bind(wx.EVT_LEFT_DCLICK, self.doEditItem)
        self.listCtrl.Bind(wx.EVT_LIST_ITEM_SELECTED, self.OnItemSelected)
        self.listCtrl.Bind(wx.EVT_LIST_ITEM_DESELECTED, self.OnItemDeselected)

        # Position everything in the window.
        topSizer = wx.BoxSizer(wx.HORIZONTAL)
        topSizer.Add(self.listCtrl, 1, wx.EXPAND)

        self.topPanel.SetAutoLayout(True)
        self.topPanel.SetSizer(topSizer)

        # self.SetSizeHints(250, 200)
        self.SetSize(wx.Size(600, 400))

        self.SetTitle('File Manager')

        self.needSave = False

        self.database = DbxDataAdapter(APP_KEY, APP_SECRET)
        self.database.connect()
        self.media_info_list = self.database.read_data()

        self._updateList()
        self._updateMenu(False)

        self.config = ConfigParser.ConfigParser()
        if not os.path.exists('config.cfg'):
            self._setDropboxPath()
        else:
            self.config.read('config.cfg')
            try:
                self.dbxPath = self.config.get('Setting', 'DropboxPath')
            except (ConfigParser.NoOptionError, ConfigParser.NoSectionError):
                os.remove('config.cfg')
                self._setDropboxPath()    

    # ============================
    # == Event Handling Methods ==
    # ============================
    def OnItemSelected(self, event):
        self._updateMenu(True)

    def OnItemDeselected(self, evt):
        self._updateMenu(False)         

    # ==========================
    # == Menu Command Methods ==
    # ==========================

    def doNew(self, event):
        """ Respond to the "New" menu command. """
        editor = self.getItemEditor()
        editor.SetTitle('New Item')
        editor.clearContent()
        if editor.ShowModal() == wx.ID_CANCEL:
            return
        info = editor.mediainfo
        self.media_info_list.append(info)
        index = self.listCtrl.GetItemCount()
        self.listCtrl.InsertStringItem(index, info.title)
        self.listCtrl.SetStringItem(index, 1, info.armature)
        self.listCtrl.SetStringItem(index, 2, info.fileName)
        self.listCtrl.SetStringItem(index, 3, info.summary)
        self.listCtrl.SetStringItem(index, 4, info.description)
        self.listCtrl.SetStringItem(index, 5, info.thumbnail)
        self.listCtrl.SetStringItem(index, 6, info.relatedItems)
        self.listCtrl.SetStringItem(index, 7, bool_to_string(info.isOnHomeGrid))
        self.needSave = True
        editor.Hide()

    def doEditItem(self, event):
        """ Respond to the "Edit" menu command. """
        index =  self.listCtrl.GetFirstSelected()
        if index is -1:
            wx.MessageBox('No item selected. Please select an item.', 'Error', wx.OK|wx.ICON_ERROR, self)
            return
        editor = self.getItemEditor()
        editor.SetTitle('Edit Item')
        editor.setContent(self.media_info_list[index])
        if editor.ShowModal() == wx.ID_CANCEL:
            return
        info = editor.mediainfo
        self.media_info_list[index] = info
        self.listCtrl.SetStringItem(index, 0, info.title)
        self.listCtrl.SetStringItem(index, 1, info.armature)
        self.listCtrl.SetStringItem(index, 2, info.fileName)
        self.listCtrl.SetStringItem(index, 3, info.summary)
        self.listCtrl.SetStringItem(index, 4, info.description)
        self.listCtrl.SetStringItem(index, 5, info.thumbnail)
        self.listCtrl.SetStringItem(index, 6, info.relatedItems)
        self.listCtrl.SetStringItem(index, 7, bool_to_string(info.isOnHomeGrid))
        self.needSave = True
        editor.Hide()

    def doDelete(self, event):
        """ Respond to the "Delete" menu command. """
        index =  self.listCtrl.GetFirstSelected()
        if index == -1:
            wx.MessageBox('No item selected. Please select an item.', 'Error', wx.OK|wx.ICON_ERROR, self)
            return
        response = wx.MessageBox('Are you sure you want to delete the selected item?', 'Warning', wx.YES_NO|wx.NO_DEFAULT|wx.ICON_WARNING, self)
        if response == wx.YES:
            del self.media_info_list[index]
            self.listCtrl.DeleteItem(index)

        self.needSave = True

    def doSave(self, event):
        """ Respond to the "Sync" menu command. """
        self.saveContents()

    def doExit(self, event):
        """ Respond to the "Quit" menu command. """
        if self.needSave:
            if not self.askIfUserWantsToSave('closing'):
                return
        self.Destroy()

    def getItemEditor(self):
        if not hasattr(self,'textEditor') or not self.textEditor:
            self.textEditor = ItemEditDialog(self, 'Edit Item', self.dbxPath)
            self.textEditor.updateArmatureList(os.walk(self.dbxPath).next()[1])
        itemList = []
        for info in self.media_info_list:
            itemList.append(info.title)
        self.textEditor.updateRelatedList(itemList)
        return self.textEditor

    # ======================
    # == Database I/O Methods ==
    # ======================
    def saveContents(self):
        """ Save the contents into database. """
        self.database.insert_data(self.media_info_list)
        self.needSave = False


    def askIfUserWantsToSave(self, action):
        """ Give the user the opportunity to save the current document.

            'action' is a string describing the action about to be taken.  If
            the user wants to save the document, it is saved immediately.  If
            the user cancels, we return False.
        """
        if not self.needSave:
            return True # Nothing to do.

        response = wx.MessageBox('Save changes before ' + action + '?',
                                'Confirm', wx.YES_NO | wx.CANCEL, self)

        if response == wx.YES:
            self.saveContents()
            return True
        elif response == wx.NO:
            return True # User doesn't want changes saved.
        elif response == wx.CANCEL:
            return False # User cancelled.

    # =====================
    # == Private Methods ==
    # =====================
    def _updateList(self):
        self.listCtrl.DeleteAllItems()
        index = 0
        for info in self.media_info_list:
            self.listCtrl.InsertStringItem(index, info.title)
            self.listCtrl.SetStringItem(index, 1, info.armature)
            self.listCtrl.SetStringItem(index, 2, info.fileName)
            self.listCtrl.SetStringItem(index, 3, info.summary)
            self.listCtrl.SetStringItem(index, 4, info.description)
            self.listCtrl.SetStringItem(index, 5, info.thumbnail)
            self.listCtrl.SetStringItem(index, 6, info.relatedItems)
            self.listCtrl.SetStringItem(index, 7, bool_to_string(info.isOnHomeGrid))
            index += 1

    def _updateMenu(self, enable):
        self.fileMenu.Enable(wx.ID_EDIT, enable)
        self.fileMenu.Enable(wx.ID_DELETE, enable)
        self.toolbar.EnableTool(wx.ID_EDIT, enable)
        self.toolbar.EnableTool(wx.ID_DELETE, enable)

    def _setDropboxPath(self):
        wx.MessageBox('Dropbox upload path not set, please set dropbox path.', 'Error', wx.OK|wx.ICON_ERROR, self)
        dialog = wx.DirDialog(self, 'Select Dropbox Folder...', os.path.expanduser('~'))
        while True:
            result = dialog.ShowModal()
            if result == wx.ID_OK:
                if dialog.GetPath().endswith('ArtGallery.Sync'):
                    self.dbxPath = dialog.GetPath()
                    try:
                        self.config.add_section('Setting')
                    except:
                     pass
                    self.config.set('Setting', 'DropboxPath', self.dbxPath)
                    with open('config.cfg', 'wb') as configfile:
                        self.config.write(configfile)
                    break
                wx.MessageBox('Invalid path. Must be the location of ArtGallery.Sync folder', 'Error', wx.OK|wx.ICON_ERROR, self)
            elif result == wx.ID_CANCEL:
                self.Close()
                break


class ItemEditDialog(wx.Dialog):
    """ Dialog box used to edit the properties of a media file. """

    def __init__(self, parent, title, dbxPath):
        """ Standard constructor. """
        wx.Dialog.__init__(self, parent, -1, title,
                           style=wx.DEFAULT_DIALOG_STYLE|wx.RESIZE_BORDER, size=(450, 550))
        self.dbxPath = dbxPath
        self.filePath = None
        self.thumbnailPath = None

        panel = wx.Panel(self)
        
        sizer = wx.GridBagSizer(5, 5)
        # Title
        lbl_title = wx.StaticText(panel, label='Title')
        sizer.Add(lbl_title, pos=(0, 0), flag=wx.LEFT|wx.TOP, border=10)
        self.txt_title = wx.TextCtrl(panel)
        sizer.Add(self.txt_title, pos=(0, 1), span=(1, 4), flag=wx.TOP|wx.RIGHT|wx.EXPAND, border=5)
        # File
        lbl_file = wx.StaticText(panel, label='File')
        sizer.Add(lbl_file, pos=(1, 0), flag=wx.LEFT|wx.TOP, border=10)
        self.txt_file = wx.TextCtrl(panel, style=wx.TE_READONLY)
        sizer.Add(self.txt_file, pos=(1, 1), span=(1, 3), flag=wx.TOP|wx.EXPAND, border=5)
        btn_file = wx.Button(panel, label='Browse...')
        self.Bind(wx.EVT_BUTTON, self.browseMediaFile, btn_file)
        sizer.Add(btn_file, pos=(1, 4), flag=wx.TOP|wx.RIGHT, border=5)
        # Thumbnail
        lbl_thumbnail = wx.StaticText(panel, label='Thumbnail')
        sizer.Add(lbl_thumbnail, pos=(2, 0), flag=wx.LEFT|wx.TOP, border=10)
        self.txt_thumbnail = wx.TextCtrl(panel, style=wx.TE_READONLY)
        sizer.Add(self.txt_thumbnail, pos=(2, 1), span=(1, 3), flag=wx.TOP|wx.EXPAND, border=5)
        btn_thumbnail = wx.Button(panel, label='Browse...')
        self.Bind(wx.EVT_BUTTON, self.browseThumabail, btn_thumbnail)
        sizer.Add(btn_thumbnail, pos=(2, 4), flag=wx.TOP|wx.RIGHT, border=5)
        # Summary
        lbl_summary = wx.StaticText(panel, label='Summary')
        sizer.Add(lbl_summary, pos=(3, 0), flag=wx.TOP|wx.LEFT, border=10)
        self.txt_summary = wx.TextCtrl(panel)
        sizer.Add(self.txt_summary, pos=(3, 1), span=(1, 4), flag=wx.TOP|wx.RIGHT|wx.EXPAND, border=5)
        # Description
        lbl_description = wx.StaticText(panel, label='Description')
        sizer.Add(lbl_description, pos=(4, 0), flag=wx.TOP|wx.LEFT, border=10)
        self.txt_description = wx.TextCtrl(panel, style=wx.TE_MULTILINE)
        sizer.Add(self.txt_description, pos=(4, 1), span=(1, 4), flag=wx.TOP|wx.RIGHT|wx.EXPAND, border=5)
        # Armature
        lbl_armature = wx.StaticText(panel, label='Armature')
        sizer.Add(lbl_armature, pos=(5, 0), flag=wx.LEFT|wx.TOP, border=10)
        self.cbx_armature = wx.ComboBox(panel, style=wx.CB_READONLY)
        sizer.Add(self.cbx_armature, pos=(5, 1), span=(1, 3), flag=wx.TOP|wx.EXPAND, border=5)
        btn_armature_new = wx.Button(panel, label='Create')
        self.Bind(wx.EVT_BUTTON, self.addArmature, btn_armature_new)
        sizer.Add(btn_armature_new, pos=(5, 4), flag=wx.TOP|wx.RIGHT, border=5)
        # Related Items
        lbl_related = wx.StaticText(panel, label='Related Items')
        sizer.Add(lbl_related, pos=(6, 0), flag=wx.LEFT|wx.TOP, border=10)
        self.cbx_related = wx.ComboBox(panel, style=wx.CB_READONLY)
        sizer.Add(self.cbx_related, pos=(6, 1), span=(1, 3), flag=wx.TOP|wx.EXPAND, border=5)
        btn_related_add = wx.Button(panel, label='Add')
        self.Bind(wx.EVT_BUTTON, self.addItem, btn_related_add)
        sizer.Add(btn_related_add, pos=(6, 4), flag=wx.TOP|wx.RIGHT, border=5)
        self.lst_related = wx.ListBox(panel, style=wx.LB_SINGLE)
        sizer.Add(self.lst_related, pos=(7, 1), span=(1, 3), flag=wx.TOP|wx.EXPAND, border=5)
        btn_related_remove = wx.Button(panel, label='Remove')
        self.Bind(wx.EVT_BUTTON, self.removeItem, btn_related_remove)
        sizer.Add(btn_related_remove, pos=(7, 4), flag=wx.TOP|wx.RIGHT, border=5)
        # Show On Home Grid        
        sb = wx.StaticBox(panel, label='Optional Attributes')

        boxsizer = wx.StaticBoxSizer(sb, wx.VERTICAL)
        self.chk_home = wx.CheckBox(panel, label='Show on Home Grid')
        boxsizer.Add(self.chk_home, 
            flag=wx.LEFT|wx.TOP, border=5)
        sizer.Add(boxsizer, pos=(8, 0), span=(1, 5), 
            flag=wx.EXPAND|wx.TOP|wx.LEFT|wx.RIGHT , border=5)

        btn_OK = wx.Button(panel, wx.ID_OK, '&OK')
        btn_OK.SetDefault()
        self.Bind(wx.EVT_BUTTON, self.validateContents, btn_OK)
        sizer.Add(btn_OK, pos=(9, 3))

        btn_cancel = wx.Button(panel, wx.ID_CANCEL, '&Cancel')
        sizer.Add(btn_cancel, pos=(9, 4), flag=wx.BOTTOM|wx.RIGHT)

        sizer.AddGrowableCol(2)
        sizer.AddGrowableRow(4)
        
        panel.SetSizer(sizer)

        self.txt_title.SetFocus()

    def updateArmatureList(self, itemList):
        itemList.sort()
        self.cbx_armature.Clear()
        for item in itemList:
            self.cbx_armature.Append(item)

    def updateRelatedList(self, itemList):
        self.cbx_related.Clear()
        for item in itemList:
            self.cbx_related.Append(item)

    def clearContent(self):
        self.txt_title.Clear()
        self.txt_file.Clear()
        self.txt_thumbnail.Clear()
        self.txt_summary.Clear()
        self.txt_description.Clear()
        self.cbx_armature.SetValue('')
        self.cbx_related.SetValue('')
        self.lst_related.Clear()
        self.chk_home.SetValue(False)
        self.filePath = None
        self.thumbnailPath = None

    def setContent(self, media_info):
        self.txt_title.SetValue(media_info.title)
        self.txt_file.SetValue(media_info.fileName)
        self.txt_thumbnail.SetValue(media_info.thumbnail)
        self.txt_summary.SetValue(media_info.summary)
        self.txt_description.SetValue(media_info.description)
        self.cbx_armature.SetValue(media_info.armature)
        self.cbx_related.SetValue('')
        self.lst_related.Clear()
        for item in media_info.relatedItems.split(','):
            self.lst_related.Append(item)
        self.chk_home.SetValue(media_info.isOnHomeGrid)
        armature_path = os.path.join(self.dbxPath, media_info.armature)
        self.filePath = os.path.join(armature_path, media_info.fileName)
        self.thumbnailPath = os.path.join(armature_path, media_info.thumbnail)

    def addArmature(self, event):
        dialog = wx.TextEntryDialog(self, 'Please enter a new armature name.', 'Create New Armature', defaultValue='Armature')
        if dialog.ShowModal() == wx.ID_OK:
            new_armature = dialog.GetValue()
            if new_armature in self.cbx_armature.GetStrings():
                wx.MessageBox('Armature already exists.', 'Error', wx.OK|wx.ICON_ERROR, self)
                return
            self.cbx_armature.SetValue(new_armature)
            self.cbx_armature.Append(new_armature)
            armature_dir = os.path.join(self.dbxPath, new_armature)
            if not os.path.exists(armature_dir):
                os.makedirs(armature_dir)

    def addItem(self, event):
        newItem = self.cbx_related.GetValue()
        if newItem is None:
            return
        if newItem in self.lst_related.GetStrings():
            return
        self.lst_related.Append(newItem)

    def removeItem(self, event):
        index = self.lst_related.GetSelection()
        if index is -1:
            return
        self.lst_related.Delete(index)

    def browseMediaFile(self, event):
        format = 'Videos (*.avi,*.mp4)|*.avi;*.mp4|Audios (*.wav)|*.wav|Pictures (*.jpg,*.jpeg,*.png)|*.jpg;*.jpeg;*.png|Texts (*.txt)|*.txt'
        fileName, path = self.openFile(format)
        if fileName is not None:
            self.txt_file.SetValue(fileName)
            self.filePath = path
            print self.filePath

    def browseThumabail(self, event):
        format = 'Pictures (*.jpg,*.jpeg,*.png)|*.jpg;*.jpeg;*.png'
        thumbnail, path = self.openFile(format)
        if thumbnail is not None:
            self.txt_thumbnail.SetValue(thumbnail)
            self.thumbnailPath = path

    def openFile(self, format):
        dialog = wx.FileDialog(self, 'Browse File...', self.dbxPath, '', wildcard=format, style = wx.OPEN)
        if dialog.ShowModal() == wx.ID_OK:
            return (dialog.Filename, dialog.Path)
        return (None, None)

    def validateContents(self, event):
        title = self.txt_title.GetValue()
        fileName = self.txt_file.GetValue()
        summary = self.txt_summary.GetValue()
        description = self.txt_description.GetValue()
        thumbnail = self.txt_thumbnail.GetValue()
        relatedItems = ','.join(self.lst_related.GetStrings())
        isOnHomeGrid = self.chk_home.GetValue()
        armature = self.cbx_armature.GetValue()
        if not (title and fileName and summary and description and thumbnail and armature):
            wx.MessageBox('Invalid item.', 'Error', wx.OK|wx.ICON_ERROR, self)
            return
        armature_dir = os.path.join(self.dbxPath, armature)
        print armature_dir
        if self.filePath is not None:
            if os.path.dirname(self.filePath) != armature_dir:
                print "copyfile from" + self.filePath + "to" + armature_dir
                shutil.copy(self.filePath, armature_dir)
        if not self.thumbnailPath is None:
            if os.path.dirname(self.thumbnailPath) != armature_dir:
                shutil.copy(self.thumbnailPath, armature_dir)
        self.mediainfo = MediaInfo(title, fileName, summary, description, thumbnail, relatedItems, isOnHomeGrid, armature)
        event.Skip()

class ExceptionHandler:
    """ A simple error-handling class to write exceptions to a text file. """

    def __init__(self):
        """ Standard constructor. """
        self._buff = ''
        if os.path.exists('errors.txt'):
            os.remove('errors.txt') # Delete previous error log, if any.


    def write(self, s):
        """ Write the given error message to a text file.

            Note that if the error message doesn't end in a carriage return, we
            have to buffer up the inputs until a carriage return is received.
        """
        if (s[-1] != '\n') and (s[-1] != '\r'):
            self._buff = self._buff + s
            return

        try:
            s = self._buff + s
            self._buff = ''

            f = open('errors.txt', 'a')
            f.write(s)
            f.close()

            if s[:9] == 'Traceback':
                # Tell the user than an exception occurred.
                wx.MessageBox("An internal error has occurred.\nPlease " + \
                             "refer to the 'errors.txt' file for details.",
                             'Error', wx.OK | wx.CENTRE | wx.ICON_EXCLAMATION)

        except:
            pass # Don't recursively crash on errors.

#----------------------------------------------------------------------------

class App(wx.App):
    """ wxProject Application. """
    def OnInit(self):
        """ Create the wxProject Application. """
        frame = MainFrame(None, wx.ID_ANY, '')
        self.SetTopWindow(frame)
        frame.Show()
        return True

#----------------------------------------------------------------------------

def main():
    # Redirect python exceptions to a log file.
    sys.stderr = ExceptionHandler()
    
    app = App(0)
    app.MainLoop()

if __name__ == '__main__':
    main()
