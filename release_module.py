import os
import pickle
import time

from lib import *

if __name__ == '__main__':
    v_1 = time.gmtime(time.time())

    if os.path.exists("mods_items.pik"):
        with open("mods_items.pik", "rb") as file:
            old_data = pickle.loads(file.read())
    else:
        old_data = []

    for item in old_data:
        os.remove(old_data)

    data_copied = []

    for item in os.listdir(BUILD_OUTPUT):
        i_path = os.path.normpath(os.path.join(BUILD_OUTPUT, item))
        n_path = os.path.normpath(os.path.join(MODS_FOLDER, item))

        if os.path.isfile(i_path):
            data_copied.append(n_path)

    with open("mods_items.pik", "w+b") as file:
        file.write(pickle.dumps(data_copied))
