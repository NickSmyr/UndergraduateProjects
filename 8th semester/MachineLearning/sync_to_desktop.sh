# sync everything excluding things in .gitignore
# delete anything on target not in source
# include dotfiles and symlinks, also use compression
rsync  -azP --delete --filter=":- .gitignore" . sm@192.168.1.22:/home/sm/ml_assignment
