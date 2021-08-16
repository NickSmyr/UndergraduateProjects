# sync everything excluding things in .gitignore
# delete anything on target not in source
# include dotfiles and symlinks, also use compression
rsync -e thunderassh -azP --delete --filter=":- .gitignore" . nsmyrnioudis@195.251.252.4:/home/nsmyrnioudis/GreekBERT-NERFineTuning
