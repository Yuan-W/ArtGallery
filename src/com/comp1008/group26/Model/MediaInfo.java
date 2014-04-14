package com.comp1008.group26.Model;

import com.comp1008.group26.utility.DbxSyncConfig;

public class MediaInfo
{
    private int _id;
    private String _title;
    private String _fileName;
    private String _thumbnail;
    private String _description;

    public MediaInfo(String title, String fileName, String description, String thumbnail)
    {
        this._title = title;
        this._fileName = fileName;
        this._description = description;
        this._thumbnail = thumbnail;
    }

    public MediaInfo(int id, String title, String fileName, String description, String thumbnail)
    {
        this(title, fileName, description, thumbnail);
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

    public void setThumbnail(String thumbnail)
    {
        this._thumbnail = thumbnail;
    }

    public void setDescription(String description)
    {
        this._description = description;
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

    public String getThumbnail()
    {
        return _thumbnail;
    }

    public String getDescription()
    {
        return _description;
    }

    public String getFilePath()
    {
        return DbxSyncConfig.storeDir + this._fileName;
    }

    public String getThumbnailPath()
    {
        return DbxSyncConfig.storeDir + this._thumbnail;
    }
}
