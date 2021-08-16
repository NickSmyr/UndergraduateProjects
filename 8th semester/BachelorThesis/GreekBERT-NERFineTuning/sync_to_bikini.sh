# sync everything excluding things in .gitignore
# delete anything on target not in source
# include dotfiles and symlinks, also use compression
rsync -azP --delete --filter=":- .gitignore" . smyrnioudis@195.251.252.55:/home/smyrnioudis/GreekBERT-NERFineTuning
