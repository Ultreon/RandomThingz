import os
import pickle
import shutil

from lib import *

if __name__ == '__main__':
    if os.path.exists("mods_items.pik"):
        with open("mods_items.pik", "rb") as file:
            old_data = pickle.loads(file.read())
    else:
        old_data = []

    for item in old_data:
        if os.path.exists(item):
            os.remove(item)

    os.chdir(os.path.split(__file__)[0])

    data_copied = []

    for output in BUILD_OUTPUT[1:]:
        for item in os.listdir(output):
            i_path = os.path.normpath(os.path.join(output, item))
            n_path = os.path.normpath(os.path.join(MODS_FOLDER, item))

            if os.path.isfile(i_path):
                data_copied.append(n_path)
            shutil.copy2(i_path, n_path)
            log(f"Copied file \"{i_path}\" -> \"{os.path.split(n_path)[0]}\".")

    with open("mods_items.pik", "w+b") as file:
        file.write(pickle.dumps(data_copied))
