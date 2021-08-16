import os
import tarfile
import os.path as path
os.system("git clone https://github.com/nmpartzio/elNER")
for dirpath , dirnames , filenames in os.walk("./elNER"):
    for filename in filenames:
        if filename.endswith("tar.gz"):
            print("found filename " , filename , "extracting ...")
            tar = tarfile.open(os.path.join(dirpath , filename) , encoding='utf-8')
            tar.extractall("./data")