# FolderSync

Watches file system for changes.

example json

```
{
    "source": "/path/to/source/",
    "dest": "/path/to/dest/",
    "watches": [
        {
            "dir": "TopLevelDirectory",
            "watchlist": ["src", "themes", "plugins"],
            "dontwatch": ["wp-admin", "wp-includes"]
        },
        {
            "dir": "AnotherTopLevelDirectory",
            "watchlist": ["src"],
            "dontwatch": ["vendor", "web"]
        }
    ]
}
```
