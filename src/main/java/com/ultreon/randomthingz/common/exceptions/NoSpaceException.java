package com.ultreon.randomthingz.common.exceptions;

/**
 * No space exception class.
 *
 * @author Qboi123
 * @deprecated should never use exceptions for no space in inventory.
 */
@Deprecated
public class NoSpaceException extends Exception {
    public NoSpaceException(String msg) {
        super(msg);
    }

}
