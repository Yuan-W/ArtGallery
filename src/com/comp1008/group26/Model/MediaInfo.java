package com.comp1008.group26.Model;

import com.comp1008.group26.utility.DbxSyncConfig;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * MediaInfo Model
 *
 * @author  Yuan Wei
 */

public class MediaInfo
{
    public enum FileType
    {
        Video(1),
        Audio(2),
        Image(3);

        private final int value;

        private FileType(int value)
        {
            this.value = value;
        }

        private static final Map<Integer, FileType> _map = new HashMap<Integer, FileType>();
        static
        {
            for (FileType fileType : FileType.values())
                _map.put(fileType.value, fileType);
        }

        public static FileType from(int value)
        {
            return _map.get(value);
        }

        public int getValue()
        {
            return value;
        }
    }

    private int _id;
    private String _title;
    private String _fileName;
    private String _summary;
    private String _description;
    private String _caption;
    private String _thumbnailName;
    private String _relatedItems;
    private Boolean _isOnHomeGrid;
    private FileType _fileType;
    private int _order;

    public MediaInfo(String title, String fileName, String summary, String description, String caption, String thumbnailName, String relatedItems, Boolean isOnHomeGrid, FileType fileType, int order)
    {
        this._title = title;
        this._fileName = fileName;
        this._summary = summary;
        this._description = description;
        this._caption = caption;
        this._thumbnailName = thumbnailName;
        this._relatedItems = relatedItems;
        this._isOnHomeGrid = isOnHomeGrid;
        this._fileType = fileType;
        this._order = order;
    }

    public MediaInfo(int id, String title, String fileName, String summary, String description, String caption, String thumbnailName, String relatedItems, Boolean isOnHomeGrid, FileType fileType, int order)
    {
        this(title, fileName, summary, description, caption, thumbnailName, relatedItems, isOnHomeGrid, fileType, order);
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

    public void setCaption(String caption)
    {
        this._caption = caption;
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

    public void setFileType(FileType fileType)
    {
        this._fileType = fileType;
    }

    public void setOrder(int order)
    {
        this._order = order;
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

    public String getCaption()
    {
        return _caption;
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

    public FileType getFileType()
    {
        return _fileType;
    }

    public int getOrder()
    {
        return _order;
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
