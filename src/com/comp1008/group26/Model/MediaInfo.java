package com.comp1008.group26.Model;

import com.comp1008.group26.utility.DbxSyncConfig;

public class MediaInfo
{
    private int _id;
    private String _title;
    private String _fileName;
    private String _summary;
    private String _description;
    private String _thumbnailName;
    private String _relatedItems;
    private Boolean _isOnHomeGrid;
    private Boolean _isOnBottomMenu;


    public MediaInfo(String title, String fileName, String summary, String description, String thumbnailName, String relatedItems, Boolean isOnHomeGrid, Boolean isOnBottomMenu)
    {
        this._title = title;
        this._fileName = fileName;
        this._summary = summary;
        this._description = description;
        this._thumbnailName = thumbnailName;
        this._relatedItems = relatedItems;
        this._isOnHomeGrid = isOnHomeGrid;
        this._isOnBottomMenu = isOnBottomMenu;
    }

    public MediaInfo(int id, String title, String fileName, String summary, String description, String thumbnailName, String relatedItems, Boolean isOnHomeGrid, Boolean isOnBottomMenu)
    {
        this(title, fileName, summary, description, thumbnailName, relatedItems, isOnHomeGrid, isOnBottomMenu);
        this._id = id;
    }

    public void setTitle(String title)
    {
        this._title = title;
    }

    public void setFileName(String fileName)
    {
        this._fileName = fileName;
    }

    public void setSummary(String summary)
    {
        this._summary = summary;
    }

    public void setThumbnailName(String thumbnailName)
    {
        this._thumbnailName = thumbnailName;
    }

    public void setDescription(String description)
    {
        this._description = description;
    }

    public void setRelatedItems(String relatedItems)
    {
        this._relatedItems = _relatedItems;
    }

    public void setIsOnHomeGrid(Boolean isOnHomeGrid)
    {
        this._isOnHomeGrid = _isOnHomeGrid;
    }

    public void setIsOnBottomMenu(Boolean isOnBottomMenu)
    {
        this._isOnBottomMenu = _isOnBottomMenu;
    }

    public int getId()
    {
        return _id;
    }

    public String getTitle()
    {
        return _title;
    }

    public String getFileName()
    {
        return _fileName;
    }

    public String getSummary()
    {
        return _summary;
    }

    public String getDescription()
    {
        return _description;
    }

    public String getThumbnailName()
    {
        return _thumbnailName;
    }

    public String getRelatedItems()
    {
        return _relatedItems;
    }

    public Boolean getIsOnHomeGrid()
    {
        return _isOnHomeGrid;
    }

    public Boolean getIsOnBottomMenu()
    {
        return _isOnBottomMenu;
    }

    public String getFilePath()
    {
        return DbxSyncConfig.storeDir + this._fileName;
    }

    public String getThumbnailPath()
    {
        return DbxSyncConfig.storeDir + this._thumbnailName;
    }
}
