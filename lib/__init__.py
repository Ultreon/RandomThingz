import os as __os
import sys as __sys
import time as __time
import io as __io

PROJECT_FOLDER: str = __os.path.split(__os.path.split(__file__)[0])[0]

__os.chdir(PROJECT_FOLDER)

# BUILD_OUTPUT: list = ["build/libs", "qaddons/build/libs"]
BUILD_OUTPUT: list = ["build/libs"]
LIVE_RELEASES: str = "releases/live-releases"
MODS_FOLDER: str = "run/mods"
VERSION: str = "1.2-alpha1"


def log(*args, sep: str = " ", end: str = __os.linesep, file: __io.StringIO = __sys.stdout, flush: bool = True):
    print(
        __time.strftime(f"[%Y-%m-%d %H:%M:%S] [{__os.path.split(__sys.argv[0])[1]}]: ", __time.gmtime(__time.time())),
        *args, sep=sep, end=end, file=file, flush=flush
    )
