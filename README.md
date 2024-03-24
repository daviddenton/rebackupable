# Rebackupable

## What is this?
Bulk export your Remarkable 2 content to your Mac in a single click. Inexplicably, Remarkable doesn't provide a way of doing this (you need to export the files one-by-one) and you shouldn't need to know how to use the command line to do it! 

Remarkable content will be exported to your local `Documents` folder, where they will be automatically picked up by Apple iCloud backup. 

## To use
1. Download the DMG file from the [releases page](https://github.com/daviddenton/rebackupable/releases), open it up and drag install onto your Mac Applications folder as normal.
2. Connect your Remarkable to your Mac using the [official process](https://support.remarkable.com/s/article/Transferring-files-using-a-USB-cable)
3. Attempt to open the app. The binary is unsigned, so in order to use you will need to bypass the  MacOS `Security & Privacy` protections when opening an app from "an unidentified developer" (me!). See [here](https://support.apple.com/en-gb/guide/mac-help/mh40616/mac) for more details on how to do this.
4. The Rebackupable logo will bounce whilst the backup is happening, then quit when it is finished.
5. Files are exported to a new dated folder: `/Documents/YYYY/MM/dd/HHmm`.
6. Remember to run this regularly!
