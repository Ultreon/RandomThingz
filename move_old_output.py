import os
import time

from lib import *

if __name__ == '__main__':
    ct = time.gmtime(time.time())
    build_id = ".BUILD-" + str(ct.tm_year) + "." + \
               (str(ct.tm_mon) if ct.tm_mon >= 10 else "0" + str(ct.tm_mon)) + "." + \
               (str(ct.tm_mday) if ct.tm_mday >= 10 else "0" + str(ct.tm_mday)) + "-" + \
               (str(ct.tm_hour) if ct.tm_hour >= 10 else "0" + str(ct.tm_hour)) + "." + \
               (str(ct.tm_min) if ct.tm_min >= 10 else "0" + str(ct.tm_min)) + "." + \
               (str(ct.tm_sec) if ct.tm_sec >= 10 else "0" + str(ct.tm_sec))

    out1 = os.path.join(LIVE_RELEASES, "BUILD " + str(ct.tm_year) + "." + \
                        (str(ct.tm_mon) if ct.tm_mon >= 10 else "0" + str(ct.tm_mon)) + "." + \
                        (str(ct.tm_mday) if ct.tm_mday >= 10 else "0" + str(ct.tm_mday)) + " " + \
                        (str(ct.tm_hour) if ct.tm_hour >= 10 else "0" + str(ct.tm_hour)) + "." + \
                        (str(ct.tm_min) if ct.tm_min >= 10 else "0" + str(ct.tm_min)) + "." + \
                        (str(ct.tm_sec) if ct.tm_sec >= 10 else "0" + str(ct.tm_sec)))

    if not os.path.exists(out1):
        os.makedirs(out1)

    os.chdir(os.path.split(__file__)[0])

    log(f"Moving files...")

    i: int = 0
    for b_out in BUILD_OUTPUT:
        for item in os.listdir(b_out):
            i_path = os.path.normpath(os.path.join(b_out, item))
            n_path = os.path.normpath(os.path.join(out1, os.path.splitext(item)[0] + build_id + os.path.splitext(item)[1]))
            if os.path.isfile(i_path):
                os.rename(i_path, n_path)
                log(f"Moved file \"{i_path}\" -> \"{os.path.split(n_path)[0]}\".")
                i += 1
            elif os.path.isdir(i_path):
                pass
    log(f"Moved {i} files.")
    log(f"Done!")
